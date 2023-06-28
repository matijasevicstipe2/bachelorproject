package hr.smatijasevic.bachelorproject.groupclass;

import hr.smatijasevic.bachelorproject.personaltrainer.PersonalTrainer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupClassServiceImpl implements GroupClassService {

    private final GroupClassRepository groupClassRepository;
    private final UserGroupClassRepository userGroupClassRepository;

    public GroupClassServiceImpl(GroupClassRepository groupClassRepository, UserGroupClassRepository userGroupClassRepository) {
        this.groupClassRepository = groupClassRepository;
        this.userGroupClassRepository = userGroupClassRepository;
    }

    @Override
    public List<GroupClass> getAllGroupClasses() {
        return groupClassRepository.findAll();
    }

    @Override
    public GroupClass getGroupClassById(Long id) {
        return groupClassRepository.findById(id).orElse(null);
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
}
