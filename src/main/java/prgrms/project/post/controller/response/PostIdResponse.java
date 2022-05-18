package prgrms.project.post.controller.response;

public record PostIdResponse(
    Long postId
) {

    public static PostIdResponse of(Long postId) {
        return new PostIdResponse(postId);
    }
}
