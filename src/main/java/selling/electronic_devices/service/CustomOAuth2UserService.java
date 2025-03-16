package selling.electronic_devices.service;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import selling.electronic_devices.model.User;
import selling.electronic_devices.repository.UserRepository;
import selling.electronic_devices.utils.JwtUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends OidcUserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public CustomOAuth2UserService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        // Lấy thông tin từ Google
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String picture = oidcUser.getPicture();

        // Kiểm tra xem user đã tồn tại chưa
        Optional<User> existingUser = userRepository.findByEmail(email);
        User user;

        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            // Nếu chưa có thì tạo user mới
            user = new User();
            user.setEmail(email);
            user.setFullName(name);
            user.setPicture(picture);
            userRepository.save(user);
        }

        // Tạo JWT token
        String jwtToken = jwtUtils.generateToken(user);

        // Lưu token vào user
        user.setJwtToken(jwtToken);
        userRepository.save(user);

        return oidcUser;
    }
}

