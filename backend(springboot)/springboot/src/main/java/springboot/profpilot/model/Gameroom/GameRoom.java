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
}
