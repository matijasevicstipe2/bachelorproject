package hr.smatijasevic.bachelorproject.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "First name is required")
  private String firstname;

  @NotBlank(message = "Last name is required")
  private String lastname;

  @NotBlank(message = "Username is required")
  private String username;

  @NotBlank(message = "Password is required")
  private String password;

  @NotNull(message = "Birthdate is required")
  private Date birthdate;

  private String address;

  @NotBlank(message = "City is required")
  private String city;

  @NotBlank(message = "City state is required")
  private String cityState;

  @NotBlank(message = "Country is required")
  private String country;

  @NotBlank(message = "Sex is required")
  private String sex;
}
