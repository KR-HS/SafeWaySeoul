package com.project.userapp.controller;

import com.project.userapp.children.service.ChildrenService;
import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequestMapping("/child")
public class ChildController {

    @Autowired
    private ChildrenService childrenService;

    @PostMapping("/regist")
    public String regist(ChildrenVO vo, @RequestParam("file")MultipartFile file, HttpServletRequest request, RedirectAttributes ra) {
        UserVO parent = (UserVO)request.getSession().getAttribute("userInfo");
        vo.setParentKey(parent.getUserKey());
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            String previousUrl = request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf("/")+1);
            ra.addFlashAttribute("msg","이미지 형식이 아닙니다");
            return "redirect:/"+previousUrl;
        }

        int result = childrenService.registChild(vo,file);
        if(result!=1){
            String previousUrl = request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf("/")+1);
            ra.addFlashAttribute("msg", "자녀 등록에 실패했습니다.");
            return "redirect:/"+previousUrl;

        }


        return "redirect:/home";
    }
}
