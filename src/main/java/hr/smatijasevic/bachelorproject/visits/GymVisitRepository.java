package hr.smatijasevic.bachelorproject.visits;

import hr.smatijasevic.bachelorproject.security.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GymVisitRepository extends JpaRepository<GymVisit, Long> {
    List<GymVisit> findByAccountId(Long accountId);
    List<GymVisit> findByAccountAndEnterTimeAfter(Account account, LocalDateTime dateTime);
    int countByAccountAndEnterTimeAfter(Account account, LocalDateTime dateTime);
    List<GymVisit> findByAccountAndEnterTime(Account account, LocalDate entryDate);
    @Query("SELECT gv FROM GymVisit gv WHERE gv.account.id = :accountId AND gv.gym.id = :gymId AND gv.enterTime >= :startOfDay AND gv.enterTime < :startOfNextDay AND gv.exitTime IS NULL")
    List<GymVisit> findAccountVisits(@Param("accountId") Integer accountId,
                                     @Param("gymId") Long gymId,
                                     @Param("startOfDay") Timestamp startOfDay,
                                     @Param("startOfNextDay") Timestamp startOfNextDay);


}
