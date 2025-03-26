package selling.electronic_devices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selling.electronic_devices.model.User;
import selling.electronic_devices.repository.UserRepository;
import selling.electronic_devices.utils.JwtUtils;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    private final JwtUtils jwtUtils;

    public UserController(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", ""); // Lấy token từ header
        String email = jwtUtils.extractUsername(jwt); // Giải mã token để lấy email

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user.get());
    }
}
