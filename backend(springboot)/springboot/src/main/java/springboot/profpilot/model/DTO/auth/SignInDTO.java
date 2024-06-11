package springboot.profpilot.model.DTO.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springboot.profpilot.model.member.Member;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class SignInDTO implements UserDetails {
    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collection;
    }
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return member.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return member.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return member.getEnabled();
    }
}
