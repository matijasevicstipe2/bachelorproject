package hr.smatijasevic.bachelorproject.membership;

import org.springframework.stereotype.Service;

import hr.smatijasevic.bachelorproject.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembershipOptionServiceImpl implements MembershipOptionService {
    private final MembershipOptionRepository membershipOptionRepository;

    @Override
    public List<MembershipOption> findAll() {
        return membershipOptionRepository.findAll();
    }

    @Override
    public MembershipOption getMembershipOptionById(Long id) {
        return membershipOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership Option not found with id: " + id));
    }

    @Override
    public MembershipOption save(MembershipOption membershipOption) {
        return membershipOptionRepository.save(membershipOption);
    }

    @Override
    public Optional<MembershipOption> findById(Long id) {
        return membershipOptionRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        membershipOptionRepository.deleteById(id);
    }
}
