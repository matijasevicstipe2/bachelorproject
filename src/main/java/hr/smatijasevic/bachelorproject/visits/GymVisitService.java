package hr.smatijasevic.bachelorproject.visits;

import hr.smatijasevic.bachelorproject.security.user.Account;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GymVisitService {

    GymVisit saveGymVisit(GymVisit gymVisit);
    List<GymVisit> getVisitsByAccount(Integer accountId);
    List<GymVisit> getVisitsByAccountAndGym(Integer accountId, Long gymId);
    List<GymVisit> getByAccountAndEnterTimeAfter(Account account, LocalDateTime dateTime);
    int getCountByAccountAndEnterTimeAfter(Account account, LocalDateTime dateTime);
    List<GymVisit> getAccountVisits(Integer accountId, Long gymId, LocalDate date);

}
