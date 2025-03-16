package selling.electronic_devices.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import selling.electronic_devices.model.User;
import selling.electronic_devices.repository.UserRepository;
import selling.electronic_devices.utils.JwtUtils;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public OAuth2LoginSuccessHandler(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    @Transactional // Đảm bảo Hibernate cập nhật dữ liệu
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String picture = oauthUser.getAttribute("picture"); // Lấy ảnh đại diện

        Optional<User> existingUser = userRepository.findByEmail(email);
        User user;

        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            user = new User();
            user.setEmail(email);
            user.setFullName(name);
            user.setPicture(picture);
        }

        // Sinh JWT Token
        String token = jwtUtils.generateToken(user);
        user.setJwtToken(token); // Lưu token vào database

        userRepository.save(user); // Cập nhật user với token mới

        // Gửi token về client
        response.sendRedirect("http://localhost:3000?token=" + token);
    }
}

