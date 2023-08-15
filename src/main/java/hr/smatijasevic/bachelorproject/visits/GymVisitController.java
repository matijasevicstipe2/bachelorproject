package hr.smatijasevic.bachelorproject.visits;

import hr.smatijasevic.bachelorproject.security.user.Account;
import hr.smatijasevic.bachelorproject.security.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class GymVisitController {
    private final GymVisitService gymVisitService;
    private final AccountRepository accountRepository;

    @GetMapping("/visits")
    public ResponseEntity<List<GymVisitDto>> getGymVisits(@RequestParam String username,
                                                          @RequestParam(required = false) Long gymId) {
        List<GymVisit> gymVisits;
        List<GymVisitDto> gymVisitDtos = new ArrayList<>();
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent()) {
            if (gymId != null) {
                gymVisits = gymVisitService.getVisitsByAccountAndGym(accountOptional.get().getId(), gymId);
            } else {
                gymVisits = gymVisitService.getVisitsByAccount(accountOptional.get().getId());
            }
            for (GymVisit gymVisit : gymVisits) {
                GymVisitDto gymVisitDto = GymVisitDto.builder()
                        .id(gymVisit.getId())
                        .enterTime(gymVisit.getEnterTime())
                        .exitTime(gymVisit.getExitTime())
                        .title(gymVisit.getSessionDetails() != null ? gymVisit.getSessionDetails().getTitle() : null)
                        .notes(gymVisit.getSessionDetails() != null ? gymVisit.getSessionDetails().getNotes() : null)
                        .build();
                gymVisitDtos.add(gymVisitDto);
            }
            return new ResponseEntity<>(gymVisitDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsDto> getGymStats(@RequestParam String username,
                                                          @RequestParam(required = false) Long gymId) {
        List<GymVisit> gymVisits;
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent()) {
            if (gymId != null) {
                gymVisits = gymVisitService.getVisitsByAccountAndGym(accountOptional.get().getId(), gymId);
            } else {
                gymVisits = gymVisitService.getVisitsByAccount(accountOptional.get().getId());
            }
            StatsDto stats = getStatistics(gymVisits);
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-visit")
    public ResponseEntity<GymVisitDto> updateGymVisit(@RequestBody GymVisitDto updatedVisit) {
        try {
            gymVisitService.updateGymVisit(updatedVisit);
            return ResponseEntity.status(HttpStatus.OK).body(updatedVisit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GymVisitDto());
        }
    }

    private StatsDto getStatistics(List<GymVisit> gymVisits) {
        int visitsThisWeek = 0;
        int visitsThisMonth = 0;
        int visitsThisYear = 0;
        int totalVisits = gymVisits.size();

        Duration durationThisWeek = Duration.ZERO;
        Duration durationThisMonth = Duration.ZERO;
        Duration durationThisYear = Duration.ZERO;
        Duration totalDuration = Duration.ZERO;

        for (GymVisit gymVisit : gymVisits) {
            LocalDateTime enterTime = gymVisit.getEnterTime();
            LocalDateTime exitTime = gymVisit.getExitTime();
            Duration duration = Duration.between(enterTime, exitTime);

            if (enterTime.isAfter(LocalDateTime.now().minusWeeks(1))) {
                visitsThisWeek++;
                durationThisWeek = durationThisWeek.plus(duration);
            }

            if (enterTime.isAfter(LocalDateTime.now().minusMonths(1))) {
                visitsThisMonth++;
                durationThisMonth = durationThisMonth.plus(duration);
            }

            if (enterTime.isAfter(LocalDateTime.now().minusYears(1))) {
                visitsThisYear++;
                durationThisYear = durationThisYear.plus(duration);
            }

            totalDuration = totalDuration.plus(duration);
        }

        long hoursThisWeek = durationThisWeek.toHours();
        long minutesThisWeek = durationThisWeek.toMinutesPart();
        long secondsThisWeek = durationThisWeek.toSecondsPart();
        long hoursThisMonth = durationThisMonth.toHours();
        long minutesThisMonth = durationThisMonth.toMinutesPart();
        long secondsThisMonth = durationThisMonth.toSecondsPart();
        long hoursThisYear = durationThisYear.toHours();
        long minutesThisYear = durationThisYear.toMinutesPart();
        long secondsThisYear = durationThisYear.toSecondsPart();
        long totalHours = totalDuration.toHours();
        long totalMinutes = totalDuration.toMinutesPart();
        long totalSeconds = totalDuration.toSecondsPart();

        System.out.println("This Week: " + visitsThisWeek + " visits, " + hoursThisWeek + " hours " + minutesThisWeek + " minutes " + secondsThisWeek + " seconds");
        System.out.println("This Month: " + visitsThisMonth + " visits, " + hoursThisMonth + " hours " + minutesThisMonth + " minutes " + secondsThisMonth + " seconds");
        System.out.println("This Year: " + visitsThisYear + " visits, " + hoursThisYear + " hours " + minutesThisYear + " minutes " + secondsThisYear + " seconds");
        System.out.println("Total: " + totalVisits + " visits, " + totalHours + " hours " + totalMinutes + " minutes " + totalSeconds + " seconds");
        return StatsDto.builder()
                .visitsWeek(visitsThisWeek)
                .visitsMonth(visitsThisMonth)
                .visitsYear(visitsThisYear)
                .visitsTotal(totalVisits)
                .visitsWeekHours(hoursThisWeek)
                .visitsWeekMin(minutesThisWeek)
                .visitsWeekSec(secondsThisWeek)
                .visitsMonthHours(hoursThisMonth)
                .visitsMonthMin(minutesThisMonth)
                .visitsMonthSec(secondsThisMonth)
                .visitsYearHours(hoursThisYear)
                .visitsYearMin(minutesThisYear)
                .visitsYearSec(secondsThisYear)
                .visitsTotalHours(totalHours)
                .visitsTotalMin(totalMinutes)
                .visitsTotalSec(totalSeconds)
                .build();
    }
}
