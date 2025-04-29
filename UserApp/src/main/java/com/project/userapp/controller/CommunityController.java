package com.project.userapp.controller;

import com.project.userapp.command.CommentVO;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/postList")
    public String postList(Model model,
                           @RequestParam(required = false)String search) {
        String searchStr = "%%";
        if(search != null) {
            searchStr = "%" + search + "%";
        }
        List<PostVO> postList = communityService.getPostList(searchStr);
        System.out.println(searchStr);
        for (PostVO post : postList) {
            post.setCountComment(communityService.getCommentCountByPostKey(post.getPostKey()));
        }
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
        vo.setCountComment(communityService.getCommentCountByPostKey(vo.getPostKey()));
        List<CommentVO> commentList = communityService.getAllComment(postId);

        model.addAttribute("post", vo);
        model.addAttribute("comment", commentList);

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

    @PostMapping("/commentWrite")
    @ResponseBody
    public String commentWrite(HttpSession session,
                               @RequestBody CommentVO commentVO) {
        UserVO userVO = (UserVO) session.getAttribute("userInfo");
        commentVO.setUserKey(userVO.getUserKey());

        communityService.writeComment(commentVO);

        return "redirect:community/postDetail?postKey=" + commentVO.getPostKey();

    }

}

