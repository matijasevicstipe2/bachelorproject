package hr.smatijasevic.bachelorproject.userdetails;

import java.util.List;

public interface UserDetailsService {
    List<UserDetails> getAllUserDetails();
    UserDetails getUserDetailsById(int id);
    UserDetails getUserDetailsByAccount(int id);
    void saveUserDetails(UserDetails userDetails);
    void deleteUserDetails(int id);
}

