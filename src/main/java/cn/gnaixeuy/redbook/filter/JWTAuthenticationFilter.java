package cn.gnaixeuy.redbook.filter;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/24
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */

import cn.gnaixeuy.redbook.config.SecurityConfig;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证用户身份是否放行，从请求头中取出token
 *
 * @author gnaixeuy
 */
@Component
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConfig.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConfig.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        if (header != null) {
            String username = JWT.require(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()))
                    .build()
                    .verify(header.replace(SecurityConfig.TOKEN_PREFIX, ""))
                    .getSubject();
            if (username != null) {
                User user = (User) userService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
            }
        }
        return null;
    }

}

