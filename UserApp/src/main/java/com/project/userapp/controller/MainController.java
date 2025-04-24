package com.project.userapp.controller;

import com.project.userapp.children.service.ChildrenService;
import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.KinderVO;
import com.project.userapp.command.UserVO;
import com.project.userapp.kinder.service.KinderService;
import com.project.userapp.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ChildrenService childrenService;

    @Autowired
    private KinderService kinderService;
    @Autowired
    private LocationService locationService;


    @GetMapping("/*")
    public String loading() {
        return "loading";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        UserVO vo = (UserVO) session.getAttribute("userInfo");
        System.out.println(vo.toString());
        List<ChildrenVO> list = childrenService.myChildren(vo.getUserKey());

        //전체 유치원에 관한 정보 받아오기
//        List<KinderVO> kinderList = kinderService.getKinderList();

        model.addAttribute("children",list);
//        model.addAttribute("kinderInfo",kinderList);
        return "home";
    }

    @GetMapping("/child")
    public String child(Model model, HttpSession session) {
        UserVO vo = (UserVO) session.getAttribute("userInfo");
        System.out.println(vo.toString());

        List<ChildrenVO> list = childrenService.myChildren(vo.getUserKey());

        model.addAttribute("childrenList", list); // 자녀 정보도 리스트에 담아 전달
        model.addAttribute("userInfo", vo);       // 사용자 정보 전달


        return "/user/mypage";
    }

    @GetMapping("/regChild")
    public String regChild(){ return "/modal/regChild";}

    @GetMapping("/tracing")
    public String tracing(Model model , @RequestParam("childKey") int childKey){

        List<ChildrenVO> childrenList = locationService.mychildRoutebyrecordKey(childKey);

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
        //도착지 - 마지막 아이의 주소
        String endAddress = waypointAddresses.get(waypointAddresses.size() - 1);
        //경유지 - 중간에 내릴 아이들의 주소
        List<String> waypoints = waypointAddresses.subList(0, waypointAddresses.size() - 1);

        model.addAttribute("startAddress", startAddress);
        model.addAttribute("waypoints", waypoints);
        model.addAttribute("endAddress", endAddress);

        return "/user/tracing";
    }

    @GetMapping("/idFind")
    public String idFind(){
        return "/login/IdFind";
    }

    //로그인 성공창
    @GetMapping("/FindUserId")
    public String findUserIdPage() {
        return "FindUserId"; //
    }
    //비밀번호 재설정창
    @GetMapping("/updatePw")
    public String updatePw() {
        return "updatePw"; //
    }


}
