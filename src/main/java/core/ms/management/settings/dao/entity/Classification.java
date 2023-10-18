package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_classification")
public class Classification {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "classification_id")
    private long id;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "classification")
    private Jewel jewel;

    @OneToOne(targetEntity = SubClassification.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sub_classification_id")
    private SubClassification subClassification;
}
