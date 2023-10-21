package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "t_karat")
public class Karat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "karat_id")
    private long Id;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private int value;

    @OneToMany(targetEntity = Jewel.class, fetch = FetchType.EAGER, mappedBy = "karat", cascade = CascadeType.ALL)
    private List<Jewel>jewels ;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Jewel> getJewels() {
        return jewels;
    }

    public void setJewels(List<Jewel> jewels) {
        this.jewels = jewels;
    }
}
