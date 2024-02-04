package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_citys")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;
    @Column(name = "name")
    public String name;

    @ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "departament_id")
    public Department departament;
}
