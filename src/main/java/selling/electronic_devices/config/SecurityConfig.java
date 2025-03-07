package selling.electronic_devices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/oauth2/**").permitAll() // Cho phép các request này
                        .anyRequest().authenticated() // Cần xác thực cho tất cả các request khác
                )
                .oauth2Login(oauth2 -> oauth2 // Cấu hình OAuth2 Login mới
                        .loginPage("/oauth2/authorization/google") // Điều hướng đến Google OAuth2
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll()); // Xử lý logout

        return http.build();
    }
}

