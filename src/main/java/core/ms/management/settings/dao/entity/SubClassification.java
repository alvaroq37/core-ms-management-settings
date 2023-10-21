package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_sub_classification")
public class SubClassification {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="sub_classification_id")
    private long id;

    @Column(name = "description")
    private String description;

    @ManyToOne(targetEntity = Classification.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cla_id")
    private Classification classification;

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

//    public Classification getClassification() {
//        return classification;
//    }
//
    public void setClassification(Classification classification) {
        this.classification = classification;
    }
}
