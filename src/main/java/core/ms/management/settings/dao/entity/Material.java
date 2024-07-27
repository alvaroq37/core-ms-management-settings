package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_material")
public class Material {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long id;
    @Column(name = "description")
    public String description;
    @Column(name = "price")
    public float price;
    @Column(name = "karat")
    public int karat;
    @Column(name = "status")
    public boolean status;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "user_create")
    public int userCreate;
    @Column(name = "user_update")
    public int userUpdate;
}
