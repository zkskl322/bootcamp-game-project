package springboot.profpilot.model.Gamer.OAuth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.Gamer.GamerRepository;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class MyOAuth2UserService extends DefaultOAuth2UserService {
    private final GamerRepository gamerRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        MySocialUser mySocialUser;

        switch (registrationId) {
            case "google" -> mySocialUser = googleService(user);
            default -> throw new IllegalStateException("Unexpected value: " + registrationId);
        }


        System.out.println("mysocialUser : " + mySocialUser.getEmail());


//        Gamer gamer = gamerRepository.findByLoginId(mySocialUser.getSub()).orElse(null);
//        if (gamer == null) {
//            gamer = new Gamer();
//            gamer.setPassword(mySocialUser.getPass());
//            gamer.setNickname(mySocialUser.getName());
//            gamer.setEmail(mySocialUser.getEmail());
//            gamer.setCreateDate(LocalDateTime.now());
//            gamerRepository.save(gamer);
//        }

        return super.loadUser(userRequest);
    }

    public MySocialUser googleService(OAuth2User user) {
        String sub = user.getAttribute("sub");
        String pass = "";
        String name = user.getAttribute("name");
        String email = user.getAttribute("email");

        return new MySocialUser(sub, pass, name, email);
    }
}