import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    @Formula(value = "price * 0.23")
    private double taxAmount;

    @Column(nullable = false)
    private int quantity;

    @ToString.Exclude
    @ManyToOne()
    private Invoice invoice;
}
