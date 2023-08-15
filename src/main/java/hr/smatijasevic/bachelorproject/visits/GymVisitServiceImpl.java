package hr.smatijasevic.bachelorproject.visits;

import hr.smatijasevic.bachelorproject.security.user.Account;
import hr.smatijasevic.bachelorproject.visits.session.SessionDetails;
import hr.smatijasevic.bachelorproject.visits.session.SessionDetailsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class GymVisitServiceImpl implements GymVisitService {
    private final GymVisitRepository gymVisitRepository;
    private final SessionDetailsRepository sessionDetailsRepository;

    public GymVisitServiceImpl(GymVisitRepository gymVisitRepository, SessionDetailsRepository sessionDetailsRepository) {
        this.gymVisitRepository = gymVisitRepository;
        this.sessionDetailsRepository = sessionDetailsRepository;
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
        return gymVisitRepository.findAccountVisits(accountId, gymId, startOfDay, startOfNextDay);
    }

    @Override
    public void updateGymVisit(GymVisitDto updatedVisit) {
        Optional<GymVisit> gv = gymVisitRepository.findById(updatedVisit.getId());
        Optional<SessionDetails> sd = sessionDetailsRepository.findByGymVisit_Id(updatedVisit.getId());
        if (sd.isPresent()) {
            sd.get().setTitle(updatedVisit.getTitle());
            sd.get().setNotes(updatedVisit.getNotes());
            sessionDetailsRepository.save(sd.get());
        } else {
            SessionDetails sessionDetails = SessionDetails.builder()
                    .gymVisit(gv.get())
                    .title(updatedVisit.getTitle())
                    .notes(updatedVisit.getNotes())
                    .build();
            sessionDetailsRepository.save(sessionDetails);
        }
    }
}
