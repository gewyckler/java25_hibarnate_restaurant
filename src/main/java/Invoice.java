import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private LocalDate dateAdded;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private boolean paid;

    @Column
    @CreationTimestamp
    private LocalDate dateRelease;

    @Column
    @CreationTimestamp
    private LocalDateTime dateAndHourOfPayment;

    private double amountOnBill;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER)
    private Set<Product> pruductSet;
}
