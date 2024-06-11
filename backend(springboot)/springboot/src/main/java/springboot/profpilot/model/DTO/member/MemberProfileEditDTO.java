package springboot.profpilot.model.DTO.member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberProfileEditDTO {
    private String email;
    private String university;
    private String name;
    private String studentId;
    private String major;
    private String phone;
    private String role;
    private String status;
    private String createAt;
    private String agreeAt;
}
