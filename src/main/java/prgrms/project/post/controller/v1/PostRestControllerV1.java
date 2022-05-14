package prgrms.project.post.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prgrms.project.post.controller.response.DefaultApiResponse;
import prgrms.project.post.service.post.PostDto;
import prgrms.project.post.service.post.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostRestControllerV1 {

    private final PostService postService;

    @PostMapping("/posts")
    public DefaultApiResponse<Long> uploadPost(@RequestBody @Validated PostDto postDto) {
        return DefaultApiResponse.ok(postService.uploadPost(postDto));
    }

    @GetMapping("/posts/{postId}")
    public DefaultApiResponse<PostDto> searchPost(@PathVariable Long postId) {
        return DefaultApiResponse.ok(postService.searchById(postId));
    }

    @GetMapping("/posts")
    public DefaultApiResponse<List<PostDto>> searchAllPosts(Pageable pageable) {
        return DefaultApiResponse.ok(postService.searchAll(pageable));
    }

    @PutMapping("/posts/{postId}")
    public DefaultApiResponse<Long> updatePost(@PathVariable Long postId, @RequestBody @Validated PostDto postDto) {
        return DefaultApiResponse.ok(postService.updatePost(postId, postDto));
    }

    @DeleteMapping("/posts/{postId}")
    public DefaultApiResponse<Boolean> deletePost(@PathVariable Long postId) {
        postService.deleteById(postId);

        return DefaultApiResponse.ok(true);
    }

    @DeleteMapping("/posts")
    public DefaultApiResponse<Boolean> deleteAllPosts() {
        postService.deleteAll();

        return DefaultApiResponse.ok(true);
    }
}