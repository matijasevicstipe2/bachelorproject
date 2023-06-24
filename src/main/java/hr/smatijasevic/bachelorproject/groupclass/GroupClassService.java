package hr.smatijasevic.bachelorproject.groupclass;

import java.util.List;

public interface GroupClassService {

    List<GroupClass> getAllGroupClasses();

    GroupClass getGroupClassById(Long id);

    void saveGroupClass(GroupClass groupClass);

    void deleteGroupClass(Long id);
}