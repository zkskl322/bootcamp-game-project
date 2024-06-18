package springboot.profpilot.model.Gameroom;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
//    private String GamerNickname;
//    private String room_password;
//    private String room_name;
//    private Long room_size;
//    private Long room_goal;

    GameRoom findByRoomName(String roomName);
   Optional<GameRoom> findById(Long id);
}
