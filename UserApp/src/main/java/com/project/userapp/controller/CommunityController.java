package com.project.userapp.controller;

import com.project.userapp.command.PostVO;
import com.project.userapp.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/postList")
    public String postList(Model model) {
        List<PostVO> postList = communityService.getPostList();

        // 디버깅용 콘솔 출력
//        for (PostVO post : postList) {
//            System.out.println(post);
//        }

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



}
