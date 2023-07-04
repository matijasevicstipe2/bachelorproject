package hr.smatijasevic.bachelorproject.visits;

import hr.smatijasevic.bachelorproject.security.user.Account;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class GymVisitServiceImpl implements GymVisitService {
    private final GymVisitRepository gymVisitRepository;

    public GymVisitServiceImpl(GymVisitRepository gymVisitRepository) {
        this.gymVisitRepository = gymVisitRepository;
    }

    @Override
    public GymVisit saveGymVisit(GymVisit gymVisit) {
        return gymVisitRepository.save(gymVisit);
    }

    @Override
    public List<GymVisit> getVisitsByAccount(Integer accountId) {
        return gymVisitRepository.findByUser_Account_IdAndExitTimeNotNull(accountId);
    }
    @Override
    public List<GymVisit> getVisitsByAccountAndGym(Integer accountId, Long gymId) {
        return gymVisitRepository.findByUser_Account_IdAndGym_IdAndExitTimeNotNull(accountId, gymId);
    }

    @Override
    public List<GymVisit> getByAccountAndEnterTimeAfter(Account account, LocalDateTime dateTime) {
        return gymVisitRepository.findByUser_AccountAndEnterTimeAfter(account, dateTime);
    }

    @Override
    public int getCountByAccountAndEnterTimeAfter(Account account, LocalDateTime dateTime) {
        return gymVisitRepository.countByUser_AccountAndEnterTimeAfter(account, dateTime);
    }

    @Override
    public List<GymVisit> getAccountVisits(Integer accountId,Long gymId,  LocalDate date) {
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime startOfNextDay = LocalDateTime.of(date.plusDays(1), LocalTime.MIN);
        Timestamp startOfDayTimestamp = Timestamp.valueOf(startOfDay);
        Timestamp startOfNextDayTimestamp = Timestamp.valueOf(startOfNextDay);
        return gymVisitRepository.findAccountVisits(accountId, gymId, startOfDayTimestamp, startOfNextDayTimestamp);
    }
}
