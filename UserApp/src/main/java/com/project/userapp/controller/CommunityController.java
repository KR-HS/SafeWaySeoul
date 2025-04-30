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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/postList")
    public String postList(Model model,
                           HttpSession session,
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
        UserVO userVO = (UserVO) session.getAttribute("userInfo");
        System.out.println(userVO);
        model.addAttribute("userKey", userVO.getUserKey());
        model.addAttribute("postList", postList);

        return "community/postList";
    }

    @GetMapping("/myPostList")
    public String myPostList(Model model,
                             @RequestParam(required = false)String search,
                             @RequestParam("userKey")Integer userKey) {
        String searchStr = "%%";
        if(search != null) {
            searchStr = "%" + search + "%";
        }
        List<PostVO> postList = communityService.getMyPostList(userKey, searchStr);
        System.out.println(searchStr);
        for (PostVO post : postList) {
            post.setCountComment(communityService.getCommentCountByPostKey(post.getPostKey()));
        }

        model.addAttribute("userKey", userKey);
        model.addAttribute("postList", postList);

        return "community/myPostList";
    }

    @GetMapping("/postDetail")
    public String postDetail(Model model,
                             @RequestParam("postKey") Integer postId,
                             HttpSession session) {
        PostVO vo = communityService.getPostById(postId);
        vo.setCountComment(communityService.getCommentCountByPostKey(vo.getPostKey()));
        List<CommentVO> commentList = communityService.getAllComment(postId);

        UserVO userVO = (UserVO) session.getAttribute("userInfo");
        int userKey = userVO.getUserKey();

        model.addAttribute("userKey", userKey);
        model.addAttribute("post", vo);
        model.addAttribute("comment", commentList);

        return "community/postDetail";
    }

    //글쓰기
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

    //글 수정
    @GetMapping("/postUpdate")
    public String postUpdate(@ModelAttribute("postKey") int postKey) {return "community/postWrite";}

    @PostMapping("/postUpdate")
    public String postUpdate(@RequestParam("postKey") int postKey,
                             @RequestParam("postTitle") String postTitle,
                             @RequestParam("postContent") String postContent) {

        communityService.update(postKey, postTitle, postContent);
      return "redirect:/community/postDetail?postKey=" + postKey;
    }

    //글삭제
    @GetMapping("/postDelete")
    public String postDelete(@RequestParam("postKey") int postKey, RedirectAttributes ra) {

        communityService.postDelete(postKey);
        ra.addFlashAttribute("msg", "삭제되었습니다.");
        return "redirect:/community/postList";
    }
}

