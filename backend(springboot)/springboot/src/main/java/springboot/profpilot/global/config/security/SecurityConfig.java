package springboot.profpilot.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import springboot.profpilot.global.Utils.JwtUtil;
import springboot.profpilot.global.config.token.JwtFilter;
import springboot.profpilot.global.config.token.JwtLogoutFilter;
import springboot.profpilot.global.config.token.LoginFilter;
import springboot.profpilot.model.refresh.RefreshRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil, RefreshRepository refreshRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Bean //AuthenticationManager Bean 등록
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth

                        .requestMatchers( "/game/room/create", "/game/room/delete/{id}", "/chatting/**", "/game/**", "/page/main", "/page/gameroom",
                                "/user/login", "/user/email/test", "/user/signup", "/user/signup/email/verify", "/user/signup/email/verify/check",
                                "/user/email/verify", "/user/email/verify/check", "/user/find-id/email/verify", "/user/find-id/email/verify/check",
                                "/user/find-id/email/verify/check/Id", "/user/reset-password/email/verify/check/reset", "/user/reset-password/email/verify",
                                "/user/reset-password/email/verify/check", "/sendToken/**").permitAll()

                        .requestMatchers( "/hello").hasAnyRole("GAMER", "ADMIN")

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class)
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/gamer/logout"))
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/gamer/login")
                )
                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/gamer/social/signup")
                        .defaultSuccessUrl("/loginSuccess")
//                        .failureUrl("/loginFailure)
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                ;
        return http.build();
    }
}