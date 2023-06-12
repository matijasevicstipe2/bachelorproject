package hr.smatijasevic.bachelorproject.membership;

import hr.smatijasevic.bachelorproject.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membership-options")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MembershipOptionController {
    private final MembershipOptionService membershipOptionService;

    @GetMapping
    public ResponseEntity<List<MembershipOption>> getAllMembershipOptions() {
        List<MembershipOption> options = membershipOptionService.findAll();
        return ResponseEntity.ok(options);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipOption> getMembershipOption(@PathVariable("id") Long id) {
        MembershipOption membershipOption = membershipOptionService.getMembershipOptionById(id);
        if (membershipOption != null) {
            return ResponseEntity.ok(membershipOption);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<MembershipOption> createMembershipOption(@RequestBody MembershipOption membershipOption) {
        MembershipOption createdMembershipOption = membershipOptionService.save(membershipOption);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMembershipOption);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipOption> updateMembershipOption(
            @PathVariable Long id, @RequestBody MembershipOption membershipOption) {
        MembershipOption existingMembershipOption = membershipOptionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership Option not found with id: " + id));

        existingMembershipOption.setName(membershipOption.getName());
        existingMembershipOption.setDescription(membershipOption.getDescription());
        existingMembershipOption.setFee(membershipOption.getFee());

        MembershipOption updatedMembershipOption = membershipOptionService.save(existingMembershipOption);
        return ResponseEntity.ok(updatedMembershipOption);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembershipOption(@PathVariable Long id) {
        membershipOptionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
