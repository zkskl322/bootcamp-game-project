package springboot.profpilot.model.emailverfiy;

import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import springboot.profpilot.model.member.Member;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class EmailService {
    private static String type = "text/html; charset=utf-8";
    private static String emailAdd = "jsilvercastle@gmail.com";
    private static String companyEmail = "profpilotofficial@gmail.com";
    private static String password = "rpgf ezyq gnkg zvlb";

    public static String sendEmailVerifyCode(String email) {
        String code = generateVerificationCode();

        public static String sendEmailVerifyCode (String email){
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");


            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailAdd, password);
                }
            };

            Session session = Session.getInstance(properties, auth);
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(emailAdd, "발신자이름"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject("ProfPilot: 이메일 인증 코드");
                message.setContent("인증 코드는 " + code + "입니다.", type);
                Transport.send(message);
                return code;
            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }
        }
    }

    public static void sendEmailNewPassword(String username, String email, String newPassword) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAdd, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAdd,"발신자이름"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("ProfPilot: 새로운 비밀번호 발급");
            message.setContent("안녕하세요, " + username + "님. 새로운 비밀번호는 " + newPassword + "입니다.", type);
            Transport.send(message);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String sendApplyAuth(String university, Member member) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAdd, password);
            }
        };

        Session session = Session.getInstance(properties, auth);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAdd, "발신자이름"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(companyEmail));
            message.setSubject("ProfPilot: 교수 인증 신청");
            message.setContent("교수 인증 신청이 들어왔습니다. 신청자 정보는 다음과 같습니다.\n" +
                    "아이디: " + member.getId() + "\n" +
                    "이름: " + member.getName() + "\n" +
                    "학번: " + member.getStudentId() + "\n" +
                    "이메일: " + member.getEmail() + "\n" +
                    "대학교: " + university + "\n" +
                    "신청일: " + member.getCreate_at() + "\n" +
                    "인증여부: " + "대기중", type);
            Transport.send(message);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
