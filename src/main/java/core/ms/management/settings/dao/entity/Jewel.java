package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_jewel")
public class Jewel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "jewel_id")
    private long id;

    @ManyToOne(targetEntity = Karat.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "karat_id")
    private Karat karat;

    @ManyToOne(targetEntity = Material.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne(targetEntity = Classification.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cla_id")
    private Classification classification;

    @ManyToOne(targetEntity = JewelCategory.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "jewel_category_id")
    private JewelCategory jewelCategory;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Karat getKarat() {
        return karat;
    }

    public void setKarat(Karat karat) {
        this.karat = karat;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public JewelCategory getJewelCategory() {
        return jewelCategory;
    }

    public void setJewelCategory(JewelCategory jewelCategory) {
        this.jewelCategory = jewelCategory;
    }
}
