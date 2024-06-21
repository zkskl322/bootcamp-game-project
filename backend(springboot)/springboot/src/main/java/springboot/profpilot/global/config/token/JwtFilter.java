package springboot.profpilot.global.config.token;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;
import springboot.profpilot.global.Utils.JwtUtil;
import springboot.profpilot.model.DTO.auth.SignInDTO;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.Gamer.GamerRepository;
import springboot.profpilot.model.Gamer.GamerService;
import springboot.profpilot.model.member.Member;

import javax.swing.plaf.synth.SynthUI;
import java.io.PrintWriter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final FirebaseAuth firebaseAuth;
    private final GamerRepository gamerRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // 헤더에서 access키에 담긴 토큰을 꺼냄
        if (request.getHeader("Social") != null) {
            FirebaseToken decodedToken;
            try{
                String header = request.getHeader("Authorization").substring(7);
                decodedToken = firebaseAuth.verifyIdToken(header);//디코딩한 firebase 토큰을 반환
            } catch (FirebaseAuthException | IllegalArgumentException e) {
                System.out.println("Firebase Token Decode Error");
                // ErrorMessage 응답 전송
                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
                return;
            }

            // User를 가져와 SecurityContext에 저장한다.
            try{
                BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
                Gamer gamer = gamerRepository.findByNickname(decodedToken.getName());

                if (gamer == null) {
                    gamer = new Gamer();
                    gamer.setEmail(decodedToken.getEmail());
                    gamer.setNickname(decodedToken.getName());
                    gamer.setPassword(BCryptPasswordEncoder.encode("firebase"));
                    gamer.setRealname(decodedToken.getName());
                    gamer.setCreateDate(java.time.LocalDateTime.now());
                    gamer.setRole("ROLE_USER");
                    gamer.setWin(0);
                    gamer.setLose(0);
                    gamer.setDraw(0);
                    gamer.setTier("Bronze");
                    gamerRepository.save(gamer);
                }


                gamer.setNickname(decodedToken.getName());
                gamer.setRole("ROLE_USER");
                SignInDTO signInDTO = new SignInDTO(gamer);
                Authentication authentication = new UsernamePasswordAuthenticationToken(signInDTO, null, signInDTO.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);//securityContextHolder 에 인증 객체 저장

            } catch(UsernameNotFoundException e){
                // ErrorMessage 응답 전송
                response.setStatus(HttpStatus.SC_NOT_FOUND);
                response.setContentType("application/json");
                response.getWriter().write("{\"code\":\"USER_NOT_FOUND\"}");
                return;
            }
            filterChain.doFilter(request, response);
        } else {


            String accessToken = request.getHeader("access");

            // 토큰이 없다면 다음 필터로 넘김
            if (accessToken == null) {

                filterChain.doFilter(request, response);

                return;
            }

            // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
            try {
                jwtUtil.isExpired(accessToken);
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 토큰이 access인지 확인 (발급시 페이로드에 명시)
            String category = jwtUtil.getCategory(accessToken);

            if (!category.equals("access")) {

                //response body
                PrintWriter writer = response.getWriter();
                //response status code
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // username, role 값을 획득
            String username = jwtUtil.getUsername(accessToken);
            String role = jwtUtil.getRole(accessToken);

            Gamer gamer = new Gamer();
            gamer.setNickname(username);
            gamer.setRole(role);
            SignInDTO signInDTO = new SignInDTO(gamer);

            Authentication authToken = new UsernamePasswordAuthenticationToken(signInDTO, null, signInDTO.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        }
    }
}
