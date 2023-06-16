package hr.smatijasevic.bachelorproject.userdetails;

import hr.smatijasevic.bachelorproject.security.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    UserDetails findByAccountId(int accountId);
}

