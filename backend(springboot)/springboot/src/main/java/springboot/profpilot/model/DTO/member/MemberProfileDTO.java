package springboot.profpilot.model.DTO.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberProfileDTO {
    private String name;
    private String studentId;
    private String email;
    private String membershipGrade;
    private String role;
    private String cloudGrade;
}
