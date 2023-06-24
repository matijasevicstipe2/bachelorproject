package hr.smatijasevic.bachelorproject.groupclass;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupClassServiceImpl implements GroupClassService {

    private final GroupClassRepository groupClassRepository;

    public GroupClassServiceImpl(GroupClassRepository groupClassRepository) {
        this.groupClassRepository = groupClassRepository;
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
}
