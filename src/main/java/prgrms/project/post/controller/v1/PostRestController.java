package prgrms.project.post.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prgrms.project.post.controller.response.PageResponse;
import prgrms.project.post.controller.response.PostIdResponse;
import prgrms.project.post.service.post.PostDto;
import prgrms.project.post.service.post.PostService;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostIdResponse> uploadPost(@RequestBody @Validated PostDto postDto) {
        return ResponseEntity.ok(PostIdResponse.of(postService.uploadPost(postDto)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> searchPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.searchById(postId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<PostDto>> searchAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.searchAll(pageable));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostIdResponse> updatePost(@PathVariable Long postId, @RequestBody @Validated PostDto postDto) {
        return ResponseEntity.ok(PostIdResponse.of(postService.updatePost(postId, postDto)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostIdResponse> deletePost(@PathVariable Long postId) {
        postService.deleteById(postId);

        return ResponseEntity.ok(PostIdResponse.of(postId));
    }
}
