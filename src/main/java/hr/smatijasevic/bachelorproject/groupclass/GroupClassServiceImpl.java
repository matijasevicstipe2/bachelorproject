package hr.smatijasevic.bachelorproject.groupclass;

import hr.smatijasevic.bachelorproject.gym.GymService;
import hr.smatijasevic.bachelorproject.personaltrainer.PersonalTrainerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupClassServiceImpl implements GroupClassService {

    private final GroupClassRepository groupClassRepository;
    private final UserGroupClassRepository userGroupClassRepository;
    private final PersonalTrainerService personalTrainerService;
    private final GymService gymService;

    public GroupClassServiceImpl(GroupClassRepository groupClassRepository, UserGroupClassRepository userGroupClassRepository, PersonalTrainerService personalTrainerService, GymService gymService) {
        this.groupClassRepository = groupClassRepository;
        this.userGroupClassRepository = userGroupClassRepository;
        this.personalTrainerService = personalTrainerService;
        this.gymService = gymService;
    }

    @Override
    public List<GroupClass> getAllGroupClasses() {
        return groupClassRepository.findAll();
    }

    @Override
    public List<GroupClass> getGroupClassByGymId(Long gymId) {
        return groupClassRepository.findGroupClassByGym_Id(gymId);
    }

    @Override
    public void saveGroupClass(GroupClass groupClass) {
        groupClassRepository.save(groupClass);
    }

    @Override
    public void deleteGroupClass(Long id) {
        groupClassRepository.deleteById(id);
    }

    @Override
    public int countUsersByGroupClassId(Long groupClassId) {
        return userGroupClassRepository.countByGroupClassId(groupClassId);
    }

    @Override
    public Long findMaxPeopleByGroupClassId(Long groupClassId) {
        GroupClass groupClass = groupClassRepository.findById(groupClassId)
                .orElseThrow(() -> new IllegalArgumentException("Group class not found with id: " + groupClassId));
        return groupClass.getMaxPeople();
    }

    @Override
    public String findTrainerEmailByGroupClassId(Long groupClassId) {
        GroupClass groupClass = groupClassRepository.findById(groupClassId)
                .orElseThrow(() -> new IllegalArgumentException("Group class not found with id: " + groupClassId));
        return groupClass.getTrainer().getEmail();
    }

    @Override
    public List<GroupClassDto> convertToGroupClassDtoList(List<GroupClass> groupClasses) {
        return groupClasses.stream()
                .map(this::convertToGroupClassDto)
                .collect(Collectors.toList());
    }

    @Override
    public GroupClassDto convertToGroupClassDto(GroupClass groupClass) {
        return GroupClassDto.builder()
                .id(groupClass.getId())
                .name(groupClass.getName())
                .schedule(groupClass.getSchedule())
                .trainer(personalTrainerService.convertToTrainerDto(groupClass.getTrainer()))
                .gym(gymService.convertToGymDto(groupClass.getGym()))
                .maxPeople(groupClass.getMaxPeople())
                .build();
    }
}
