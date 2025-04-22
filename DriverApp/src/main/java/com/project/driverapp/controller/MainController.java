package com.project.driverapp.controller;

import com.project.driverapp.children.service.ChildrenService;
import com.project.driverapp.command.ChildrenVO;
import com.project.driverapp.command.DriverVO;
import com.project.driverapp.command.KinderVO;
import com.project.driverapp.command.RecordVO;
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
import java.util.List;

@Controller
public class MainController {

    @Autowired
    ChildrenService childrenService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private DriverService driverService;


    @GetMapping("/*")
    public String loading() {
        return "loading";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        DriverVO vo = (DriverVO) session.getAttribute("driverInfo");
        System.out.println(vo.toString());

        List<RecordVO> list = recordService.getRecordList(vo.getUserKey());
        System.out.println(list.toString());

        List<KinderVO> kinderVo = driverService.findKinderForDriver(vo.getUserKey());
        System.out.println(kinderVo.toString());

        model.addAttribute("driverInfo", vo);
        model.addAttribute("recordInfo", list);
        model.addAttribute("kinderInfo", kinderVo.get(0));

        return "home";
    }
    @GetMapping("/manage")
    public String manage(Model model, @RequestParam(required = false) Integer recordKey) {

        // recordKey에 해당하는 아이들 정보 조회
        List<ChildrenVO> childrenList = driverService.manageOfChildren(recordKey);

        model.addAttribute("childrenList", childrenList);

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

}

