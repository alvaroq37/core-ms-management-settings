package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "t_material_price")
public class MaterialPrice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long id;

    @Column(name = "description")
    public String description;

    @Column(name = "price")
    public float price;

    @OneToMany(targetEntity = Material.class, fetch = FetchType.EAGER, mappedBy = "materialPrice", cascade = CascadeType.ALL)
    public List<Material> materials;
}
