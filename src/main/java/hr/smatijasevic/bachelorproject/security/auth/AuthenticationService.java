package hr.smatijasevic.bachelorproject.security.auth;


import hr.smatijasevic.bachelorproject.security.config.JwtService;
import hr.smatijasevic.bachelorproject.security.user.Account;
import hr.smatijasevic.bachelorproject.security.user.AccountRepository;
import hr.smatijasevic.bachelorproject.userdetails.UserDetails;
import hr.smatijasevic.bachelorproject.userdetails.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final AccountRepository repository;
  private  final UserDetailsRepository detailsRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private static final int SALT_LENGTH = 16;

  public RegisterResponse register(RegisterRequest request) throws NoSuchAlgorithmException {
    Optional<Account> account = repository.findByUsername(request.getUsername());
    if (account.isPresent()) {
      return new RegisterResponse();
    }

    Account acc = Account.builder()
            .firstName(request.getFirstname())
            .lastName(request.getLastname())
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .birthdate(request.getBirthdate())
            .sex(request.getSex())
            .address(request.getAddress())
            .cityState(request.getCityState())
            .build();

    UserDetails userDetails = UserDetails.builder()
            .account(acc)
            .usage(0)
            .build();

    repository.save(acc);
    detailsRepository.save(userDetails);

    return new RegisterResponse(acc.getUsername());
  }

  public Optional<ResponseEntity<AuthenticationResponse>> authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );

    Optional<Account> acc = repository.findByUsername(request.getUsername());
    if (acc.isEmpty()) {
      return Optional.empty();
    }
    String jwtToken = jwtService.generateToken(acc.get());

    return Optional.of(ResponseEntity.ok(AuthenticationResponse.builder()
        .jwt(jwtToken)
        .build()));
  }
}
