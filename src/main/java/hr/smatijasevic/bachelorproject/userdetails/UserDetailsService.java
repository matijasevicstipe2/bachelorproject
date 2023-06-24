package hr.smatijasevic.bachelorproject.userdetails;

import hr.smatijasevic.bachelorproject.security.user.Account;

import java.util.List;

public interface UserDetailsService {
    List<UserDetails> getAllUserDetails();
    UserDetails getUserDetailsById(int id);
    UserDetails getUserDetailsByAccount(Account account);
    void saveUserDetails(UserDetails userDetails);
    void deleteUserDetails(int id);
}

