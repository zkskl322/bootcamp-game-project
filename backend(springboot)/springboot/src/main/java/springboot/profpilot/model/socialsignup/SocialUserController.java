//package springboot.profpilot.model.socialsignup;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//public class SocialUserController {
//    @Autowired
//    private SocialUserService socialUserService;
//
//    @GetMapping("/gamer/social/signup")
//    public ResponseEntity<String> signupGoogleUser(@AuthenticationPrincipal OAuth2User principal) {
//        String email = principal.getAttribute("email");
//        String username = principal.getAttribute("name");
//
//        Optional<SocialUser> existingUser = socialUserService.findByEmail(email);
//        if(existingUser.isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
//        }
//
//        SocialUser newUser = socialUserService.registerNewSocialUser(username, email);
//
//        return ResponseEntity.ok("User registered successfully: " + newUser.getUsername());
//    }
//}