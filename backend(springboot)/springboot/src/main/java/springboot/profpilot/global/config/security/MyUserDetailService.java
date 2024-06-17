package springboot.profpilot.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.DTO.auth.SignInDTO;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.Gamer.GamerService;
import springboot.profpilot.model.member.Member;
import springboot.profpilot.model.member.MemberService;


@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final GamerService gamerService;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Gamer gamer = gamerService.findByNickname(nickname);
        if (gamer == null) throw new UsernameNotFoundException("Gamer not found");
        else {
            return new SignInDTO(gamer);
        }
    }





}