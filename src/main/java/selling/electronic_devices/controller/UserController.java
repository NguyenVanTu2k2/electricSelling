package selling.electronic_devices.controller;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal OidcUser oidcUser) {
        return Map.of(
                "name", oidcUser.getFullName(),
                "email", oidcUser.getEmail(),
                "picture", oidcUser.getPicture()
        );
    }
}
