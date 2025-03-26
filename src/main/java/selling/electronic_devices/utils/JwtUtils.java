package selling.electronic_devices.utils;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import selling.electronic_devices.model.User;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
    private final String SECRET_KEY = "your-strong-secret-key-with-32-characters"; // Cần ít nhất 32 ký tự
    private final long EXPIRATION_TIME = 86400000; // 1 ngày

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ✅ Tạo token JWT
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // Sử dụng email làm subject
                .claim("name", user.getFullName()) // Lưu tên người dùng vào token
                .setIssuedAt(new Date()) // Thời gian phát hành
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Hạn token
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Ký token
                .compact();
    }

    // ✅ Giải mã token JWT
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Lấy email từ token (Fix lỗi "Cannot resolve method 'extractUsername'")
    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }
}
