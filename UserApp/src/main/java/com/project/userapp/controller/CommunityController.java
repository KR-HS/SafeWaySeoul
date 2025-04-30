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
                           @RequestParam(value = "search", required = false) String search) {
        String searchStr = "%%";
        if (search != null) {
            searchStr = "%" + search + "%";
        }
        List<PostVO> postList = communityService.getPostList(searchStr);
        System.out.println(searchStr);
        for (PostVO post : postList) {
            post.setCountComment(communityService.getCommentCountByPostKey(post.getPostKey()));
        }
        model.addAttribute("postList", postList);

        return "community/postList";
    }

    @GetMapping("/postDetail")
    public String postDetail(Model model,
                             @RequestParam("postKey") Integer postId) {
        PostVO vo = communityService.getPostById(postId);
        vo.setCountComment(communityService.getCommentCountByPostKey(vo.getPostKey()));
        List<CommentVO> commentList = communityService.getAllComment(postId);

        List<FileVO> fileList = filesMapper.getFilesByPostKey(postId); // ★ 추가: 파일 리스트 조회

        model.addAttribute("post", vo);
        model.addAttribute("comment", commentList);
        model.addAttribute("fileList", fileList); // ★ 추가: 파일 리스트 전달

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
                        // 날짜별 폴더 생성
                        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                        String uploadFolder = "C:/Users/user/Desktop/upload/" + today;
                        File folder = new File(uploadFolder);
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }

                        // 파일 이름: UUID + 원본 파일명
                        String uuid = UUID.randomUUID().toString();
                        String fileName = uuid + "_" + file.getOriginalFilename();

                        // 파일 저장
                        file.transferTo(new File(folder, fileName));

                        // 파일 경로
                        String filePath = "/upload/" + today + "/" + fileName;

                        // 파일 VO 생성 및 DB 저장
                        FileVO fileVO = FileVO.builder()
                                .fileName(fileName)
                                .filePath(filePath)
                                .fileUuid(uuid) // ★ 반드시 UUID 저장
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

}
