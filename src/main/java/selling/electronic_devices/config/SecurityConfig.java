package selling.electronic_devices.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import selling.electronic_devices.repository.UserRepository;
import selling.electronic_devices.service.OAuth2LoginSuccessHandler;
import selling.electronic_devices.utils.JwtUtils;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserRepository userRepository, JwtUtils jwtUtils) throws Exception {
        http
                .cors(cors -> cors.disable()) // Nếu cần CORS, bật lên
                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF cho API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService())
                        )
                        .successHandler(new OAuth2LoginSuccessHandler(userRepository, jwtUtils)) // Xử lý sau khi đăng nhập thành công
                )

                // logout chạy không được
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout") // URL logout
                        .invalidateHttpSession(true)   // Hủy session
                        .deleteCookies("JSESSIONID")   // Xóa cookie nếu có
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })

                );

        return http.build();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }

    @Bean
    public OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler(UserRepository userRepository, JwtUtils jwtUtils) {
        return new OAuth2LoginSuccessHandler(userRepository, jwtUtils);
    }

}

