package com.project.userapp.controller;

import com.project.userapp.command.PostVO;
import com.project.userapp.command.UserVO;
import com.project.userapp.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/postList")
    public String postList(Model model) {
        List<PostVO> postList = communityService.getPostList();
        model.addAttribute("postList", postList);

        return "community/postList";
    }

    @GetMapping("/postDetail")
    public String postDetail(Model model,
                             @RequestParam("postKey") Integer postId) {
        PostVO vo = communityService.getPostById(postId);
        model.addAttribute("post", vo);

        return "community/postDetail";
    }

    @GetMapping("/postWrite")
    public String postWrite() {return "community/postWrite";}

    @PostMapping("/postWrite")
    public String postWrite(PostVO vo, HttpSession session) {

        UserVO userInfo = (UserVO) session.getAttribute("userInfo");
        //userVO 안에 있는 userKey를 postVO에 저장해야함
        int userKey = userInfo.getUserKey();
        vo.setUserKey(userKey);

        int result = communityService.write(vo);


        return "redirect:/community/postList";
    }

}
