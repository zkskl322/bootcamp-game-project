package springboot.profpilot.model.refresh;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    Boolean existsByRefresh(String refresh);
    RefreshEntity findByEmail(String email);
    RefreshEntity findByRefresh(String refresh);
    @Transactional
    void deleteByEmail(String email);

    @Transactional
    void deleteByRefresh(String refresh);
}
