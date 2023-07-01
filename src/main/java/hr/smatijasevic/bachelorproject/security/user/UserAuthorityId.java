package hr.smatijasevic.bachelorproject.security.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class UserAuthorityId implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "authority_id")
    private Integer authorityId;

    public UserAuthorityId() {
    }

    public UserAuthorityId(Integer userId, Integer authorityId) {
        this.userId = userId;
        this.authorityId = authorityId;
    }
}

