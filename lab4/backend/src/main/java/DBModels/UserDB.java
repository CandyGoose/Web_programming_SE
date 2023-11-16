package DBModels;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_web_lab4")
@NamedQuery(
        name = "UserDB.findByUsername",
        query = "SELECT u FROM UserDB u WHERE u.username=:username")
@NamedQuery(
        name = "UserDB.findByRefreshToken",
        query = "SELECT u FROM UserDB u JOIN FETCH u.results WHERE u.refreshToken=:refreshToken")
@NamedQuery(
        name = "UserDB.findByID",
        query = "SELECT u FROM UserDB u WHERE u.id=:id")
@NamedQuery(
        name = "UserDB.findByIDWithResults",
        query = "SELECT u FROM UserDB u JOIN FETCH u.results WHERE u.id=:id")

public class UserDB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "owner")
    private Set<ResultDB> results;
}
