package springboot.profpilot.model.Gameroom;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.member.Member;

import java.time.LocalDateTime;
import java.util.List;

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


    @OneToMany(mappedBy = "gameRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Gamer> gamers;
//    private Boolean room_isPassword;
//    private Long room_time;
}
