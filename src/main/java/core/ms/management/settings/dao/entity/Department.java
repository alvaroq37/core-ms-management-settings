package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public long id;

    @Column(name = "name")
    public String name;

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    public Country country;
}
