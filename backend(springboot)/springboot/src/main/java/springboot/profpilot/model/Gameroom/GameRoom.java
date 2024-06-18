package springboot.profpilot.model.Gameroom;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import springboot.profpilot.model.Gamer.Gamer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class GameRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String room_GamerNickname;

    private String room_password;
    private String roomName;
    private Long room_size;
    private Long room_goal;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Gamer> gamers = new ArrayList<>();
//    private Boolean room_isPassword;
//    private Long room_time;
}
