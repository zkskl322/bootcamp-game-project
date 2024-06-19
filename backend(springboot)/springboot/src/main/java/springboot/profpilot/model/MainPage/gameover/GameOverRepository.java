package springboot.profpilot.model.MainPage.gameover;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.profpilot.model.Gameroom.GameRoom;

public interface GameOverRepository extends JpaRepository<GameRoom, Long> {
}