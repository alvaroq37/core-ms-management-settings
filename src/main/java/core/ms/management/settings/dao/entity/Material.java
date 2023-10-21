package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "t_material")
public class Material {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "material_id")
    private long id;

    @Column(name = "description")
    private String description;

    @OneToMany(targetEntity = Jewel.class, fetch = FetchType.EAGER, mappedBy = "material", cascade = CascadeType.ALL)
    private List<Jewel> jewels;

    @ManyToOne(targetEntity = MaterialPrice.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "material_price_id")
    private MaterialPrice materialPrice;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Jewel> getJewels() {
        return jewels;
    }

    public void setJewels(List<Jewel> jewels) {
        this.jewels = jewels;
    }

    public MaterialPrice getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(MaterialPrice materialPrice) {
        this.materialPrice = materialPrice;
    }
}
