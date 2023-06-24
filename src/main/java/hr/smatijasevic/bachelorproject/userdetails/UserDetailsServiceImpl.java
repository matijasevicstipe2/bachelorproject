package hr.smatijasevic.bachelorproject.userdetails;

import hr.smatijasevic.bachelorproject.security.user.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public List<UserDetails> getAllUserDetails() {
        return userDetailsRepository.findAll();
    }

    @Override
    public UserDetails getUserDetailsById(int id) {
        return userDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserDetail not found with id: " + id));
    }

    @Override
    public UserDetails getUserDetailsByAccount(Account account) {
        return userDetailsRepository.findByAccount(account);
    }

    @Override
    public void saveUserDetails(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    @Override
    public void deleteUserDetails(int id) {
        userDetailsRepository.deleteById(id);
    }
}

