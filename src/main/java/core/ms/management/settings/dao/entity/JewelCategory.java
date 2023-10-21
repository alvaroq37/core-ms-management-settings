package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "t_jewel_category")
public class JewelCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "jewel_category_id")
    private long id;

    @Column(name = "description")
    private String description;

    @OneToMany(targetEntity = Jewel.class, fetch = FetchType.EAGER, mappedBy = "jewelCategory", cascade = CascadeType.ALL)
    private List<Jewel> jewels ;

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

    public List<Jewel> getJewels() {
        return jewels;
    }

    public void setJewels(List<Jewel> jewels) {
        this.jewels = jewels;
    }
}
