package springboot.profpilot.model.Gamer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GamerRepository extends JpaRepository<Gamer, Long> {
    Gamer findByRealname(String realname);
    Gamer findByEmail(String email);
    Gamer findByNickname(String nickname);
    List<Gamer> findByGameRoomId(Long gameRoomId);
}
