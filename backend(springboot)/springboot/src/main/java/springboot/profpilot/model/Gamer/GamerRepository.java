package springboot.profpilot.model.Gamer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GamerRepository extends JpaRepository<Gamer, Long> {
    Gamer findByRealname(String realname);
    Gamer findByEmail(String email);
    Gamer findByNickname(String nickname);
    List<Gamer> findByGameRoomId(Long gameRoomId);

    // 랭크 점수 순위에 따라서 내림차순 정렬, 랭크 점수가 같다면 승수가 높은 순으로 정렬
    List<Gamer> findAllByOrderByRankPointDescWinDesc();
}
