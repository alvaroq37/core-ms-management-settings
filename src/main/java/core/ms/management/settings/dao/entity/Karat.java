package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

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

    @OneToOne(mappedBy = "karat")
    private Jewel jewel;

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

    public Jewel getJewel() {
        return jewel;
    }

    public void setJewel(Jewel jewel) {
        this.jewel = jewel;
    }
}
