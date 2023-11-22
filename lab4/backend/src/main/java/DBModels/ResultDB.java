package DBModels;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a result entry in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "results_web_lab4")
public class ResultDB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "coordinate_x")
    private BigDecimal x;

    @Column(name = "coordinate_y")
    private BigDecimal y;

    @Column(name = "coordinate_r")
    private BigDecimal r;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "success")
    private Boolean success;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserDB owner;
}
