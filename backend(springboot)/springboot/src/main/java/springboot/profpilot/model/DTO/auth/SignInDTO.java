package springboot.profpilot.model.DTO.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.member.Member;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class SignInDTO implements UserDetails {
    private final Gamer gamer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return gamer.getRole();
            }
        });
        return collection;
    }
    @Override
    public String getPassword() {
        return gamer.getPassword();
    }

    @Override
    public String getUsername() {
        return gamer.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
