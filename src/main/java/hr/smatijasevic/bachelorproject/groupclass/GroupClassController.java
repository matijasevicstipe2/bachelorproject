package hr.smatijasevic.bachelorproject.groupclass;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-classes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class GroupClassController {

    private final GroupClassService groupClassService;

    @GetMapping
    public ResponseEntity<List<GroupClass>> getAllGroupClasses() {
        List<GroupClass> groupClasses = groupClassService.getAllGroupClasses();
        return new ResponseEntity<>(groupClasses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupClass> getGroupClassById(@PathVariable Long id) {
        GroupClass groupClass = groupClassService.getGroupClassById(id);
        if (groupClass != null) {
            return new ResponseEntity<>(groupClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> saveGroupClass(@RequestBody GroupClass groupClass) {
        groupClassService.saveGroupClass(groupClass);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupClass(@PathVariable Long id) {
        groupClassService.deleteGroupClass(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
