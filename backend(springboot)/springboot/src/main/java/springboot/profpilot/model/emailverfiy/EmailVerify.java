package springboot.profpilot.model.emailverfiy;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EmailVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private boolean isVerified;
}
