package hr.smatijasevic.bachelorproject.groupclass;

import hr.smatijasevic.bachelorproject.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/group-classes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class GroupClassController {

    private final GroupClassService groupClassService;
    private final UserGroupClassRepository userGroupClassRepository;
    private  final EmailService emailService;

    @GetMapping
    public ResponseEntity<List<GroupClassDto>> getAllGroupClasses() {

        List<GroupClass> groupClasses = groupClassService.getAllGroupClasses();
        return new ResponseEntity<>(groupClassService.convertToGroupClassDtoList(groupClasses), HttpStatus.OK);
    }

    @GetMapping("/{gymId}")
    public ResponseEntity<List<GroupClassDto>> getGroupClassByGym(@PathVariable Long gymId) {
        List<GroupClass> groupClasses = groupClassService.getGroupClassByGymId(gymId);
        if (!groupClasses.isEmpty()) {
            return new ResponseEntity<>(groupClassService.convertToGroupClassDtoList(groupClasses), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> saveGroupClass(@RequestBody GroupClass groupClass) {
        groupClassService.saveGroupClass(groupClass);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/join-group-class")
    public ResponseEntity<String> joinGroupClass(@RequestParam Long userId, @RequestParam Long groupClassId) throws MessagingException, IOException {
        // Check if the maximum number of people is reached for the group class
        int currentUsers = groupClassService.countUsersByGroupClassId(groupClassId);
        Long maxPeople = groupClassService.findMaxPeopleByGroupClassId(groupClassId);
        if (currentUsers >= maxPeople) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Maximum number of people reached");
        }

        // Save the user-group class association in the database
        UserGroupClass userGroupClass = new UserGroupClass();
        userGroupClass.setUserId(userId);
        userGroupClass.setGroupClassId(groupClassId);
        userGroupClassRepository.save(userGroupClass);

        String trainerEmail = groupClassService.findTrainerEmailByGroupClassId(groupClassId);
        String subject = "Training Session Confirmation";
        String body = "I would like to join this training session.";
        emailService.sendEmail(trainerEmail, subject, body);

        return ResponseEntity.ok("Joined group class successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupClass(@PathVariable Long id) {
        groupClassService.deleteGroupClass(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
