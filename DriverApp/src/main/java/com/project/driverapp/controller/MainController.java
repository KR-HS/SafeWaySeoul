package com.project.driverapp.controller;

import com.project.driverapp.children.service.ChildrenService;
import com.project.driverapp.command.ChildrenVO;
import com.project.driverapp.command.DriverVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    ChildrenService childrenService;


    @GetMapping("/*")
    public String loading() {
        return "loading";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        DriverVO vo = (DriverVO) session.getAttribute("dirverInfo");
        System.out.println(vo.toString());
        List<ChildrenVO> list = childrenService.findByKinder(vo.getUserPostcode(), vo.getUserAddressDetail());

        model.addAttribute("children",list);
        return "home";
    }
    @GetMapping("/manage")
    public String manage() {
        return "driver/manage";
    } 
    
    @GetMapping("/startManage")
    public String startManage() {return "modal/startManage";}

    @GetMapping("/childDetail")
    public String childDetail() {return "child/childDetail";}

}

