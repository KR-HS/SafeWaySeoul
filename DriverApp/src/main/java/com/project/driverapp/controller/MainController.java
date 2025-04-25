package com.project.driverapp.controller;

import com.project.driverapp.children.service.ChildrenService;
import com.project.driverapp.command.*;
import com.project.driverapp.driveInfo.service.DriveInfoService;
import com.project.driverapp.driver.service.DriverService;
import com.project.driverapp.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    ChildrenService childrenService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriveInfoService driveInfoService;


    @GetMapping("/loading")
    public String loading() {
        return "loading";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        DriverVO vo = (DriverVO) session.getAttribute("driverInfo");
        System.out.println(vo.toString());

        //로그인된 기사의 운행정보 불러오기
        List<DriveInfoVO> list = driveInfoService.getDriveInfo(vo.getUserKey());
        System.out.println(list.toString());

        List<KinderVO> kinderVo = driverService.findKinderForDriver(vo.getUserKey());
        System.out.println(kinderVo.toString());

        model.addAttribute("driverInfo", vo);
        model.addAttribute("driveInfo", list);
        if(kinderVo != null && kinderVo.size() > 0) {
            model.addAttribute("kinderInfo", kinderVo.get(0));
        }

        return "home";




    }
    @GetMapping("/manage")
    public String manage(Model model,
                         @RequestParam(required = false) Integer recordKey,
                         @RequestParam(required = false) String driveInfoName) {

        // recordKey에 해당하는 아이들 정보 조회
        List<ChildrenVO> childrenList = driverService.manageOfChildren(recordKey);
        //배차정보제목조회-그냥 첫번째애 배차정보로 조회
        //ChildrenVO childrevo= childrenList.get(0);

        model.addAttribute("childrenList", childrenList);
        model.addAttribute("driveInfoName", driveInfoName);

        for (ChildrenVO child : childrenList) {
            System.out.println("dropState: " + child.getDropState());
        }

        //태초에 맵화면상에 출발지,도착지, 경유지 db상에서 불러와서 맵에 띄우기위한작업
        //유치원 주소
        KinderVO kinder = childrenList.get(0).getKinderVO();
        //시작점- 유치원
        String startAddress = kinder.getKinderAddress();

        // 아이들 집 주소 리스트
        List<String> waypointAddresses = new ArrayList<>();
        for (int i = 0; i < childrenList.size(); i++) {

            String addr = childrenList.get(i).getUserVO().getUserAddress() + " " +
                    childrenList.get(i).getUserVO().getUserAddressDetail();
            waypointAddresses.add(addr);
        }
        String endAddress = waypointAddresses.get(waypointAddresses.size() - 1);
        List<String> waypoints = waypointAddresses.subList(0, waypointAddresses.size() - 1);

        model.addAttribute("startAddress", startAddress);
        model.addAttribute("waypoints", waypoints);
        model.addAttribute("endAddress", endAddress);


        return "driver/manage";
    }

    @PostMapping("/updateDropState")
    @ResponseBody
    public String updateDropState(
            @RequestParam int recordKey,
            @RequestParam int childKey,
            @RequestParam String dropState) {

        int result = driverService.updateDropState(recordKey, childKey, dropState);
        return result > 0 ? "success" : "fail";
    }
    
    @GetMapping("/startManage")
    public String startManage() {return "modal/startManage";}

    @GetMapping("/childDetail")
    public String childDetail() {return "child/childDetail";}


    @GetMapping("/driving")
    public String driving() {return "driving";}
}

