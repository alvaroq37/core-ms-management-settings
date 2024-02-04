package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "t_material")
public class Material {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long id;

    @Column(name = "description")
    public String description;

    @OneToMany(targetEntity = Jewel.class, fetch = FetchType.EAGER, mappedBy = "material", cascade = CascadeType.ALL)
    public List<Jewel> jewels;

    @ManyToOne(targetEntity = MaterialPrice.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "material_price_id")
    public MaterialPrice materialPrice;
}
