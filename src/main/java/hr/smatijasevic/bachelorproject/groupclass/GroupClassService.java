package hr.smatijasevic.bachelorproject.groupclass;

import java.util.List;

public interface GroupClassService {

    List<GroupClass> getAllGroupClasses();

    List<GroupClass> getGroupClassByGymId(Long gymId);

    void saveGroupClass(GroupClass groupClass);

    void deleteGroupClass(Long id);
    int countUsersByGroupClassId(Long groupClassId);
    Long findMaxPeopleByGroupClassId(Long groupClassId);
    String findTrainerEmailByGroupClassId(Long groupClassId);
    List<GroupClassDto> convertToGroupClassDtoList(List<GroupClass> groupClasses);
    GroupClassDto convertToGroupClassDto(GroupClass groupClass);
}
