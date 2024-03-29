package hr.smatijasevic.bachelorproject.security.auth;


import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import hr.smatijasevic.bachelorproject.email.EmailService;
import hr.smatijasevic.bachelorproject.qr.QRCode;
import hr.smatijasevic.bachelorproject.qr.QRCodeService;
import hr.smatijasevic.bachelorproject.security.config.JwtService;
import hr.smatijasevic.bachelorproject.security.user.*;
import hr.smatijasevic.bachelorproject.userdetails.UserDetails;
import hr.smatijasevic.bachelorproject.userdetails.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final AccountRepository repository;
  private final UserDetailsRepository detailsRepository;
  private final UserAuthorityRepository userAuthorityRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final QRCodeService qrCodeService;
  private final EmailService emailService;
  public static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public RegisterResponse register(RegisterRequest request) throws Exception {
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
    repository.save(acc);

    UserAuthorityId userAuthorityId = new UserAuthorityId(acc.getId(), 2);
    UserAuthority userAuthority = UserAuthority.builder()
            .id(userAuthorityId)
            .build();
    userAuthorityRepository.save(userAuthority);

    UserDetails userDetails = UserDetails.builder()
            .account(acc)
            .usage(0)
            .active(false)
            .build();
    detailsRepository.save(userDetails);

    String qrPass = generateQRPassword(8);
    BitMatrix matrix = qrCodeService.generateQRcode(request.getUsername() + qrPass);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
    byte[] qrCodeData = outputStream.toByteArray();
    QRCode qrCode = QRCode.builder()
            .account(acc)
            .qrPass(qrPass)
            .code(qrCodeData)
            .build();
    qrCodeService.saveQRCode(qrCode);

    emailService.sendEmailQRCode("", "Account created",
            """
                    Welcome to our community!\s

                    Here is your QR Code:\s""", qrCodeData);

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

  private String generateQRPassword(int length) {
    final StringBuilder password = new StringBuilder("");

    for (int i = 0; i < length; i++) {
      int index = (int) (Math.random() * ALLOWED_CHARACTERS.length());
      password.append(ALLOWED_CHARACTERS.charAt(index));
    }

    return password.toString();
  }
}
