package prgrms.project.post.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prgrms.project.post.controller.response.PageResponse;
import prgrms.project.post.controller.response.UserIdResponse;
import prgrms.project.post.service.user.UserDto;
import prgrms.project.post.service.user.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserIdResponse> registerUser(@RequestBody @Validated UserDto userDto) {
        return ResponseEntity.ok(UserIdResponse.of(userService.registerUser(userDto)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> searchUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.searchById(userId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserDto>> searchAllUser(Pageable pageable) {
        return ResponseEntity.ok(userService.searchAll(pageable));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserIdResponse> updateUser(@PathVariable Long userId, @RequestBody @Validated UserDto userDto) {
        return ResponseEntity.ok(UserIdResponse.of(userService.updateUser(userId, userDto)));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserIdResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);

        return ResponseEntity.ok(UserIdResponse.of(userId));
    }
}
