package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "t_material_price")
public class MaterialPrice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "material_price_id")
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @OneToMany(targetEntity = Material.class, fetch = FetchType.EAGER, mappedBy = "materialPrice", cascade = CascadeType.ALL)
    private List<Material> materials;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

//    public List<Material> getMaterials() {
//        return materials;
//    }
//
//    public void setMaterials(List<Material> materials) {
//        this.materials = materials;
//    }
}
