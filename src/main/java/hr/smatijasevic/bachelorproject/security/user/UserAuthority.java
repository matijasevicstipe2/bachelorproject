package hr.smatijasevic.bachelorproject.security.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_authority")
public class UserAuthority {

    @EmbeddedId
    private UserAuthorityId id;
}

