package springboot.profpilot.model.member;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.profpilot.global.Utils.GenerateRandomValue;
import springboot.profpilot.model.emailverfiy.EmailService;
import springboot.profpilot.model.emailverfiy.EmailVerify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import springboot.profpilot.model.emailverfiy.EmailVerifyService;
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerifyService emailVerifyService;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    public Member save(String email, String password, String name, Long studentId) {
        Member member = new Member();
        if (email.contains("khu.ac.kr")) {
            member.setUniversity("경희대학교");
        } else {
            member.setUniversity("타대학교");
        }
        member.setEmail(email);
        member.setName(name);
        member.setStudentId(studentId);
        member.setMajor("NONE");
        member.setPhone("NONE");
        member.setMembership("NONE");
        member.setMembershipExpire("NONE");
        member.setPassword(passwordEncoder.encode(password));
        member.setRole("ROLE_STUDENT");
        member.setStatus("ACTIVE");
        member.setAccountNonExpired(true);
        member.setAccountNonLocked(true);
        member.setCredentialsNonExpired(true);
        member.setEnabled(true);
        member.setCreate_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        member.setAgree_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return memberRepository.save(member);
    }
    public void save(Member member) {
        memberRepository.save(member);
    }
    public String verifyEmail(String email) {
        GenerateRandomValue generateRandomValue = new GenerateRandomValue();
        String code = generateRandomValue.getRandomPassword(10);

        EmailVerify emailVerfiy = new EmailVerify();
        emailVerfiy.setEmail(email);
        emailVerfiy.setCode(code);
        emailVerfiy.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        emailVerfiy.setVerified(false);
        emailVerifyService.save(emailVerfiy);

        return EmailService.sendEmailVerifyCode(email, code);
     }
    public String checkEmailVerifyCode(String email, String code) {
        EmailVerify emailVerfiy = emailVerifyService.findByEmail(email);
        if (emailVerfiy == null) {
            return "notfound";
        }
        if (emailVerfiy.getCode().equals(code)) {
            emailVerfiy.setVerified(true);
            emailVerifyService.save(emailVerfiy);
            return "success";
        } else {
            return "fail";
        }
    }
}
