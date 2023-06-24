package hr.smatijasevic.bachelorproject.security.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private byte[] profilePicture;
    private String firstName;
    private String lastName;
    private byte[] qrCode;
    private long daysLeft;
    private String membership;
}
