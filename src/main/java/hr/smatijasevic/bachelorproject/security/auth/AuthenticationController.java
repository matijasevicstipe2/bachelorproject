package hr.smatijasevic.bachelorproject.security.auth;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/account")
  public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) throws Exception {
    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
    return service.authenticate(request)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials"));

  }
}
