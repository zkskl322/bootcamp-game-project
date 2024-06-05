package springboot.profpilot.model.DTO.member;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberProfileUpdateDTO {
    private String name;
    private String major;
    private String studentId;
}
