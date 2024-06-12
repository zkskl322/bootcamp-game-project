package springboot.profpilot.model.Gameroom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import springboot.profpilot.model.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String room_password;
    private String room_name;
    private Long room_size;
    private Long room_goal;
    private Boolean room_isPassword;

    private Long room_time;

    private int score;

    public void setScore(int i) {
        this.score = score;
    }

    public void resetState() {
        this.room_password = null;
        this.room_name = null;
        this.room_size = 0L;
        this.room_goal = 0L;
        this.room_isPassword = false;
        this.room_time = 0L;
        this.score = 0;
    }
}
