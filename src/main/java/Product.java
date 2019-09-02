import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double taxAmount;

    @Column(nullable = false)
    private int quantity;

    @ToString.Exclude
    @ManyToOne()
    private Invoice invoice;

}
