$(document).ready(function() {

    const maxFiles = 5; // 최대 파일 수 제한
    const fileInput = $("#file-upload");
    const thumbnailPreview = $(".thumbnail-preview");
    const photoCount = $(".photo-count");
    let selectedFiles = []; // 메모리상 파일 관리

    // 완료 버튼 클릭 시 제목/내용 유효성 검사
    $(".write-finish-btn").on("click", function () {
        var title = $("input[name='postTitle']").val().trim();
        var content = $("textarea[name='postContent']").val().trim();

        if (title === "") {
            alert("제목을 입력해 주세요");
            return false;
        } else if (content === "") {
            alert("내용을 입력해 주세요");
            return false;
        }
    });

    // 썸네일 미리보기 업데이트 함수
    function updatePreview() {
        thumbnailPreview.empty();
        selectedFiles.forEach((file, index) => {
            const reader = new FileReader();
            reader.onload = function(e) {
                const thumbnailItem = $(`
                    <div class="thumbnail-item">
                        <img src="${e.target.result}" alt="썸네일 이미지" />
                        <button type="button" class="remove-btn" data-index="${index}">x</button>
                    </div>
                `);
                thumbnailPreview.append(thumbnailItem);
            };
            reader.readAsDataURL(file);
        });
        photoCount.text(`${selectedFiles.length}/${maxFiles}`);
    }

    // 파일 선택 시
    fileInput.on("change", function() {
        const files = Array.from(fileInput[0].files);

        if (files.length + selectedFiles.length > maxFiles) {
            alert(`사진은 최대 ${maxFiles}장까지만 업로드할 수 있습니다.`);
            fileInput.val("");
            return;
        }

        selectedFiles = selectedFiles.concat(files);
        fileInput.val(""); // 같은 파일 다시 선택 가능하게 초기화
        updatePreview();
    });

    // 삭제 버튼 클릭 시
    thumbnailPreview.on("click", ".remove-btn", function() {
        const index = $(this).data("index");
        selectedFiles.splice(index, 1); // 해당 인덱스 파일 삭제
        updatePreview(); // 다시 그리기
    });

    $(".write-finish-btn").on("click", function (e) {
        e.preventDefault(); // 기본 form 제출 막기

        let action = $('form[name="writePage"]').attr('action');

        if(action === "postWrite"){
            const title = $("input[name='postTitle']").val().trim();
            const content = $("textarea[name='postContent']").val().trim();

            if (title === "") {
                alert("제목을 입력해 주세요");
                return false;
            } else if (content === "") {
                alert("내용을 입력해 주세요");
                return false;
            }

            const formData = new FormData();
            formData.append("postTitle", title);
            formData.append("postContent", content);

            selectedFiles.forEach((file, index) => {
                formData.append("uploadImages", file); // 서버에서 uploadImages[] 로 받을 수 있음
            });

            // 실제 서버로 전송
            $.ajax({
                url: "/community/postWrite",  // 실제 매핑된 서버 URL
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    alert("게시글이 성공적으로 등록되었습니다.");
                    location.href = "/community/postList"; // 성공 후 목록으로 이동
                },
                error: function (xhr, status, error) {
                    console.error("게시글 등록 실패:", error);
                    alert("게시글 등록 중 오류가 발생했습니다.");
                }
            });
        } else {
            const title = $("input[name='postTitle']").val().trim();
            const content = $("textarea[name='postContent']").val().trim();
            const postKey = $("input[name='postKey']").val();

            if (title === "") {
                alert("제목을 입력해 주세요");
                return false;
            } else if (content === "") {
                alert("내용을 입력해 주세요");
                return false;
            }

            const formData = new FormData();
            formData.append("postTitle", title);
            formData.append("postContent", content);
            formData.append("postKey", postKey);

            selectedFiles.forEach((file, index) => {
                formData.append("uploadImages", file); // 서버에서 uploadImages[] 로 받을 수 있음
            });

            // 실제 서버로 전송
            $.ajax({
                url: "/community/postUpdate",  // 실제 매핑된 서버 URL
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    alert("게시글이 성공적으로 수정되었습니다.");
                    location.href = "/community/postDetail?postKey="+postKey; // 성공 후 목록으로 이동
                },
                error: function (xhr, status, error) {
                    console.error("게시글 수정 실패:", error);
                    alert("게시글 수정 중 오류가 발생했습니다.");
                }
            });
        }


    });


});