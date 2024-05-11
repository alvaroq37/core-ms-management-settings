package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "t_city")
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

    @Column(name = "date_create")
    public Date dateCreate;

    @Column(name = "date_update")
    public Date dateUpdate;

    @Column(name = "user_create")
    public int user_create;

    @Column(name = "user_update")
    public int userUpdate;
}
