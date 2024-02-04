package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="t_business_discounts")
public class BusinessDiscounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "desription")
    public String description;

    @Column(name = "value")
    public float value;
}
