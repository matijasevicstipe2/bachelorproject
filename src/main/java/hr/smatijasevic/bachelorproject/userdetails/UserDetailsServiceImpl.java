package hr.smatijasevic.bachelorproject.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
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
    public UserDetails getUserDetailsByAccount(int id) {
        return userDetailsRepository.findByAccountId(id);
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

