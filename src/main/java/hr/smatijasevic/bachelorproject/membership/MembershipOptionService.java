package hr.smatijasevic.bachelorproject.membership;

import java.util.List;
import java.util.Optional;

public interface MembershipOptionService {
    List<MembershipOption> findAll();
    MembershipOption getMembershipOptionById(Long id);
    MembershipOption save(MembershipOption membershipOption);
    Optional<MembershipOption> findById(Long id);
    void deleteById(Long id);
}
