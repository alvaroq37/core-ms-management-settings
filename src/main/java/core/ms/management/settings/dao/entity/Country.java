package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "t_countrys")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;
    @Column(name = "name")
    public String name;
}
