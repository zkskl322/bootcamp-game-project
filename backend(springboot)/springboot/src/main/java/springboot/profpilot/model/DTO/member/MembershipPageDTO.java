package springboot.profpilot.model.DTO.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembershipPageDTO {
    private String isProfessor;
    private String role;
    private String professorAuthapply;
    private String professorUniversity;
    private String membershipGrade;
    private String membershipExpirationDate;
    private String cloudCapacity;

    public MembershipPageDTO(String isProfessor, String role, String professorAuthapply, String professorUniversity, String membershipGrade, String membershipExpirationDate, String cloudCapacity) {
        this.isProfessor = isProfessor;
        this.role = role;
        this.professorAuthapply = professorAuthapply;
        this.professorUniversity = professorUniversity;
        this.membershipGrade = membershipGrade;
        this.membershipExpirationDate = membershipExpirationDate;
        this.cloudCapacity = cloudCapacity;
    }

    public MembershipPageDTO() {
        this.isProfessor = "";
        this.role = "";
        this.professorAuthapply = "";
        this.professorUniversity = "";
        this.membershipGrade = "";
        this.membershipExpirationDate = "";
        this.cloudCapacity = "";
    }
}
