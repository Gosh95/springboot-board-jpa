package prgrms.project.post.controller.response;

public record UserIdResponse(
    Long userId
) {

    public static UserIdResponse of(Long userId) {
        return new UserIdResponse(userId);
    }
}
