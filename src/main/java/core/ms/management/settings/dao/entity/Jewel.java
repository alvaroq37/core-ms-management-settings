package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_jewel")
public class Jewel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long id;

    @Column(name = "description")
    public String description;

    @ManyToOne(targetEntity = Material.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "material_id")
    public Material material;
}
