package springboot.profpilot.model.Gamer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import springboot.profpilot.model.Gameroom.GameRoom;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Gamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;
    private String password;
    private String realname;
    private String role;

    @Column(unique = true)
    private String email;

    private String loginId;

    private LocalDateTime createDate;

    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;

    private int win;
    private int lose;
    private int draw;
    private int total;
    private int rankPoint;
    private String tier;

    @ManyToOne
    private GameRoom gameRoom;

}