package hr.smatijasevic.bachelorproject.visits;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsDto {
    private int visitsWeek;
    private int visitsMonth;
    private int visitsYear;
    private int visitsTotal;
    private Long visitsWeekHours;
    private Long visitsMonthHours;
    private Long visitsYearHours;
    private Long visitsTotalHours;
    private Long visitsWeekMin;
    private Long visitsMonthMin;
    private Long visitsYearMin;
    private Long visitsTotalMin;

}
