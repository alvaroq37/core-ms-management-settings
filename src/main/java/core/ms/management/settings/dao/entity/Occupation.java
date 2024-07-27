package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_occupations")
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;
    @Column(name = "description")
    public String description;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "user_create")
    public int userCreate;
    @Column(name = "user_update")
    public int userUpdate;
}
