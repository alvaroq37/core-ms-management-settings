package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "t_jewel_type")
public class JewelType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    public Long id;
    @Column(name="description")
    public String description;
    @Column(name = "market_value")
    public Long marketValue;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "user_create")
    public int userCreate;
    @Column(name = "user_update")
    public int userUpdate;
}
