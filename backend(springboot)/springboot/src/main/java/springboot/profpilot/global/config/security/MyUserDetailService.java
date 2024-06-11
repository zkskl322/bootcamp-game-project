package springboot.profpilot.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.DTO.auth.SignInDTO;
import springboot.profpilot.model.member.Member;
import springboot.profpilot.model.member.MemberService;


@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final MemberService memberService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberService.findByEmail(email);
        if (member == null) throw new UsernameNotFoundException("User not found");
        else {
            return new SignInDTO(member);
        }
    }
}