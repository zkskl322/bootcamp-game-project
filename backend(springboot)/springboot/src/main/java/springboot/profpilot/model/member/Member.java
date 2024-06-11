package springboot.profpilot.model.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String university;
    private String name;
    private Long studentId;
    private String major;
    private String phone;
    private String membership;
    private String membershipExpire;
    private String password;
    private String role;
    private String status;
    private String create_at;
    private String agree_at;

    private Boolean AccountNonExpired;
    private Boolean AccountNonLocked;
    private Boolean CredentialsNonExpired;
    private Boolean Enabled;

}
