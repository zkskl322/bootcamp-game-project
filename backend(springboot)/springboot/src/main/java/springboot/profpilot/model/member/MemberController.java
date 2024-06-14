package springboot.profpilot.model.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.profpilot.global.Utils.MakeJsonResponse;
import springboot.profpilot.model.DTO.auth.CheckEmail;
import springboot.profpilot.model.DTO.auth.PasswordDTO;
import springboot.profpilot.model.DTO.auth.VerifyEmail;
import springboot.profpilot.model.DTO.member.MemberProfileDTO;
import springboot.profpilot.model.DTO.member.MemberProfileEditDTO;
import springboot.profpilot.model.DTO.member.MemberProfileUpdateDTO;
import springboot.profpilot.model.Gamer.SignUpDTO;
import springboot.profpilot.model.emailverfiy.EmailVerify;
import springboot.profpilot.model.emailverfiy.EmailVerifyService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final EmailVerifyService emailVerifyService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public @ResponseBody String test() {
        System.out.println("test");
        return "test";
    }

//    @PostMapping("/signup")
//    public @ResponseBody String signup(@RequestBody SignUpDTO member) {
//        if (member.getEmail() == null || member.getPassword() == null || member.getName() == null || member.getStudentId() == null) {
//            return "lack";
//        }
//        if (memberService.findByEmail(member.getEmail()) != null) {
//            return "already";
//        }
//        EmailVerify emailVerfiy = emailVerifyService.findByEmail(member.getEmail());
//        if (emailVerfiy == null || !emailVerfiy.isVerified()) {
//            return "not-Verified";
//        }
//        memberService.save(member.getEmail(), member.getPassword(), member.getName(), member.getStudentId());
//        return "success";
//    }

    @PostMapping("/signup/email/verify")
    public @ResponseBody String verifyEmail(@RequestBody String json_email) {
        String email = json_email.substring(10, json_email.length() - 2);
        EmailVerify emailVerfiy = emailVerifyService.findByEmail(email);

        if (emailVerfiy != null) {
            String sendTime = emailVerfiy.getTime();
            if (emailVerfiy.isVerified()) {
                return "already";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime sendTimeParsed = LocalDateTime.parse(sendTime, formatter);
            LocalDateTime now = LocalDateTime.now();
            String nowString = now.format(formatter);
            LocalDateTime nowParsed = LocalDateTime.parse(nowString, formatter);
            if (nowParsed.isBefore(sendTimeParsed.plusMinutes(5))) {
                return "wait";
            }
            else {
                emailVerifyService.deleteByEmail(email);
            }
        }

        String response = memberService.verifyEmail(email);
        if (response.equals("success")) {
            return "success";
        } else {
            return "fail";
        }
    }

    @PostMapping("/signup/email/verify/check")
    public @ResponseBody String checkEmail(@RequestBody CheckEmail checkEmail) {
        String email = checkEmail.getEmail();
        String code = checkEmail.getVerifyCode();
        return memberService.checkEmailVerifyCode(email, code);
    }

    @PostMapping("/email/verify")
    public @ResponseBody String changeEmailVerify(@RequestBody VerifyEmail verifyCodeEmail) {
        String email = verifyCodeEmail.getEmail();
        EmailVerify emailVerfiy = emailVerifyService.findByEmail(email);

        if (emailVerfiy != null) {
            String sendTime = emailVerfiy.getTime();
            if (emailVerfiy.isVerified()) {
                return "already";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime sendTimeParsed = LocalDateTime.parse(sendTime, formatter);
            LocalDateTime now = LocalDateTime.now();
            String nowString = now.format(formatter);
            LocalDateTime nowParsed = LocalDateTime.parse(nowString, formatter);
            if (nowParsed.isBefore(sendTimeParsed.plusMinutes(5))) {
                return "wait";
            }
            else {
                emailVerifyService.deleteByEmail(email);
            }
        }

        String response = memberService.verifyEmail(email);
        if (response.equals("success")) {
            return "success";
        } else {
            return "fail";
        }
    }

    @PostMapping("/email/verify/check")
    public @ResponseBody String checkEmailVerify(@RequestBody CheckEmail checkEmail) {
        System.out.println(checkEmail.getEmail());
        String email = checkEmail.getEmail();
        String code = checkEmail.getVerifyCode();
        return memberService.checkEmailVerifyCode(email, code);
    }

    @PutMapping("/email/change")
    public @ResponseBody String changeEmail(@RequestBody CheckEmail verifyCodeEmail, Principal principal) {
        String email = verifyCodeEmail.getEmail();
        String code = verifyCodeEmail.getVerifyCode();

        EmailVerify emailVerfiy = emailVerifyService.findByEmail(email);
        if (emailVerfiy == null || !emailVerfiy.isVerified()) {
            return "not-Verified";
        }

        Member member = memberService.findByEmail(principal.getName());
        member.setEmail(email);
        memberService.save(member);
        return "success";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/my-page")
    @ResponseBody
    public Map<String, String> getProfile(Principal principal) {
        String email = principal.getName();
        Member member = memberService.findByEmail(email);
        MemberProfileDTO memberProfile = new MemberProfileDTO();

        memberProfile.setName(member.getName());
        memberProfile.setStudentId(member.getStudentId().toString());
        memberProfile.setEmail(member.getEmail());
        memberProfile.setMembershipGrade(member.getMembership());
        memberProfile.setRole(member.getRole());

        if (member.getRole().equals("professor")) {
            memberProfile.setCloudGrade("professor");
        } else {
            memberProfile.setCloudGrade("NONE");
        }

        return MakeJsonResponse.makeJsonResponse(memberProfile);
    }

    @GetMapping("/my-info")
    @ResponseBody
    public Map<String, String> getMyInfo(Principal principal) {
        String email = principal.getName();
        Member member = memberService.findByEmail(email);
        MemberProfileEditDTO memberProfileEditDTO = new MemberProfileEditDTO();

        memberProfileEditDTO.setEmail(member.getEmail());
        memberProfileEditDTO.setUniversity(member.getUniversity());
        memberProfileEditDTO.setName(member.getName());
        memberProfileEditDTO.setStudentId(member.getStudentId().toString());
        memberProfileEditDTO.setMajor(member.getMajor());
        memberProfileEditDTO.setPhone(member.getPhone());
        memberProfileEditDTO.setRole(member.getRole());
        memberProfileEditDTO.setStatus(member.getStatus());
        memberProfileEditDTO.setCreateAt(member.getCreate_at());
        memberProfileEditDTO.setAgreeAt(member.getAgree_at());

       Map<String, String> response = MakeJsonResponse.makeJsonResponse(memberProfileEditDTO);
        return response;
    }

    @PutMapping("/my-info/update")
    @ResponseBody
    public String updateMyInfo(@RequestBody MemberProfileUpdateDTO memberProfileEditDTO, Principal principal) {
        String email = principal.getName();
        Member member = memberService.findByEmail(email);

        member.setName(memberProfileEditDTO.getName());
        member.setStudentId(Long.parseLong(memberProfileEditDTO.getStudentId()));
        member.setMajor(memberProfileEditDTO.getMajor());

        memberService.save(member);
        return "success";
    }

    @GetMapping("/check")
    public @ResponseBody String check(Principal principal) {
        return "success";
    }

    @PostMapping("/check-password")
    public @ResponseBody boolean checkPassword(@RequestBody PasswordDTO passwordDTO, Principal principal) {
        String email = principal.getName();
        Member member = memberService.findByEmail(email);
        String passwordEncoded = member.getPassword();
        String password = passwordDTO.getPassword();
        return passwordEncoder.matches(password, passwordEncoded);
    }

    @PutMapping("/change-password")
    public @ResponseBody String changePassword(@RequestBody PasswordDTO passwordDTO, Principal principal) {
        String email = principal.getName();
        Member member = memberService.findByEmail(email);
        String passwordEncoded = member.getPassword();
        String password = passwordDTO.getPassword();
        String newPassword = passwordDTO.getNewPassword();
        if (!passwordEncoder.matches(password, passwordEncoded)) {
            return "wrong";
        }
        member.setPassword(passwordEncoder.encode(newPassword));
        memberService.save(member);
        return "success";
    }


}
