package hr.smatijasevic.bachelorproject.security.auth;


import hr.smatijasevic.bachelorproject.security.config.JwtService;
import hr.smatijasevic.bachelorproject.security.user.Account;
import hr.smatijasevic.bachelorproject.security.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final AccountRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  public static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public RegisterResponse register(RegisterRequest request) {
    Optional<Account> account = repository.findByUsername(request.getUsername());
    if (account.isPresent()) {
      return new RegisterResponse();
    }

    String password = generatePassword(8);

    Account acc = Account.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(password))
            .build();

    repository.save(acc);

    return new RegisterResponse();
  }

  private String generatePassword(int length) {
    final StringBuilder password = new StringBuilder("");

    for (int i = 0; i < length; i++) {
      int index = (int) (Math.random() * ALLOWED_CHARACTERS.length());
      password.append(ALLOWED_CHARACTERS.charAt(index));
    }

    return password.toString();
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
        .token(jwtToken)
        .build()));
  }
}
