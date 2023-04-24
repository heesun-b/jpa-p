package shop.mtcoding.board.modules.user;

import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.board.common.exception.Exception400;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getPage(Pageable pageable) {
        Page<User> content = userService.getPage(pageable);

        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty()) {
            throw new Exception400("존재하지 않는 유저입니다.");
        }

        return ResponseEntity.ok(optionalUser.get());
    }

    @PostMapping
    public ResponseEntity<?> saveUser(
            @Valid UserSaveRequest request,
            Errors error) {
        if (error.hasErrors()) {
            throw new Exception400(error.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        }

        User saveUser = userService.save(request);

        return ResponseEntity.ok(saveUser);
    }

}
