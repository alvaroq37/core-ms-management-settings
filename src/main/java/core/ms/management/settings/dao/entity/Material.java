package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_material")
public class Material {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "material_id")
    private long id;

    @OneToOne(mappedBy = "material")
    private Jewel jewel;

    @OneToOne(targetEntity = MaterialPrice.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "material_price_id")
    private MaterialPrice materialPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Jewel getJewel() {
        return jewel;
    }

    public void setJewel(Jewel jewel) {
        this.jewel = jewel;
    }

    public MaterialPrice getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(MaterialPrice materialPrice) {
        this.materialPrice = materialPrice;
    }
}
