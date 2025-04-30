package com.project.userapp.controller;

import com.project.userapp.command.CommentVO;
import com.project.userapp.command.FileVO;
import com.project.userapp.command.PostVO;
import com.project.userapp.command.UserVO;
import com.project.userapp.community.service.CommunityService;
import com.project.userapp.files.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private FilesMapper filesMapper;

    @GetMapping("/postList")
    public String postList(Model model,
                           HttpSession session,
                           @RequestParam(value = "search", required = false) String search) {
        String searchStr = "%%";
        if (search != null) {
            searchStr = "%" + search + "%";
        }
        List<PostVO> postList = communityService.getPostList(searchStr);
        for (PostVO post : postList) {
            post.setCountComment(communityService.getCommentCountByPostKey(post.getPostKey()));
        }

        UserVO userVO = (UserVO) session.getAttribute("userInfo");
        model.addAttribute("userKey", userVO.getUserKey());
        model.addAttribute("postList", postList);

        return "community/postList";
    }

    @GetMapping("/myPostList")
    public String myPostList(Model model,
                             @RequestParam(required = false) String search,
                             @RequestParam("userKey") Integer userKey) {
        String searchStr = "%%";
        if (search != null) {
            searchStr = "%" + search + "%";
        }
        List<PostVO> postList = communityService.getMyPostList(userKey, searchStr);
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
        List<FileVO> fileList = filesMapper.getFilesByPostKey(postId);

        UserVO userVO = (UserVO) session.getAttribute("userInfo");
        int userKey = userVO.getUserKey();

        model.addAttribute("userKey", userKey);
        model.addAttribute("post", vo);
        model.addAttribute("comment", commentList);
        model.addAttribute("fileList", fileList);

        return "community/postDetail";
    }

    @GetMapping("/postWrite")
    public String postWrite() {
        return "community/postWrite";
    }

    @PostMapping("/postWrite")
    public String postWrite(@RequestParam("postTitle") String postTitle,
                            @RequestParam("postContent") String postContent,
                            @RequestParam(value = "uploadImages", required = false) List<MultipartFile> uploadImages,
                            HttpSession session) {

        UserVO userInfo = (UserVO) session.getAttribute("userInfo");

        PostVO postVO = new PostVO();
        postVO.setPostTitle(postTitle);
        postVO.setPostContent(postContent);
        postVO.setUserKey(userInfo.getUserKey());

        int result = communityService.write(postVO);

        if (uploadImages != null && !uploadImages.isEmpty()) {
            for (MultipartFile file : uploadImages) {
                if (!file.isEmpty()) {
                    try {
                        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                        String uploadFolder = "C:/Users/user/Desktop/upload/" + today;
                        File folder = new File(uploadFolder);
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }

                        String uuid = UUID.randomUUID().toString();
                        String fileName = uuid + "_" + file.getOriginalFilename();
                        file.transferTo(new File(folder, fileName));
                        String filePath = "/upload/" + today + "/" + fileName;

                        FileVO fileVO = FileVO.builder()
                                .fileName(fileName)
                                .filePath(filePath)
                                .fileUuid(uuid)
                                .userKey(userInfo.getUserKey())
                                .postKey(postVO.getPostKey())
                                .build();

                        filesMapper.insertFile(fileVO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return "redirect:/community/postList";
    }

    @PostMapping("/commentWrite")
    @ResponseBody
    public String commentWrite(HttpSession session,
                               @RequestBody CommentVO commentVO) {
        UserVO userVO = (UserVO) session.getAttribute("userInfo");
        commentVO.setUserKey(userVO.getUserKey());

        communityService.writeComment(commentVO);
        return "redirect:/community/postDetail?postKey=" + commentVO.getPostKey();
    }

    @GetMapping("/postUpdate")
    public String postUpdate(@ModelAttribute("postKey") int postKey) {
        return "community/postWrite";
    }

    @PostMapping("/postUpdate")
    public String postUpdate(@RequestParam("postKey") int postKey,
                             @RequestParam("postTitle") String postTitle,
                             @RequestParam("postContent") String postContent) {
        communityService.update(postKey, postTitle, postContent);
        return "redirect:/community/postDetail?postKey=" + postKey;
    }

    @GetMapping("/postDelete")
    public String postDelete(@RequestParam("postKey") int postKey, RedirectAttributes ra) {
        communityService.postDelete(postKey);
        ra.addFlashAttribute("msg", "삭제되었습니다.");
        return "redirect:/community/postList";
    }
}

