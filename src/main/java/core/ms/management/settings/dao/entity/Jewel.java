package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_jewel")
public class Jewel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "jewel_id")
    private long id;

    @OneToOne(targetEntity = Karat.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "karat_id")
    private Karat karat;

    @OneToOne(targetEntity = Material.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "material_id")
    private Material material;

    @OneToOne(targetEntity = Classification.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "classification_id")
    private Classification classification;

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
}
