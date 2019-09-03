import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dateOfCreation", nullable = false)
    private LocalDate dateAdded;

    @Column(nullable = false)
    private String clientName;

    @Column(name = "ifPaid")
    private boolean ifPaid = false;

    @Column
    @CreationTimestamp
    private LocalDate dateRelease;

    @Column
    @CreationTimestamp
    private LocalDateTime dateAndHourOfPayment;


    @Formula(value = "(select sum(p.price) from Product p where p.invoice_id = id)")
    private Double amountOnBill;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER)
    private List<Product> pruductList;
}
