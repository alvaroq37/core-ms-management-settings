package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "t_client_category")
public class ClientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "descripcion")
    public String description;

    @Column(name = "value")
    public String value;

    @Column(name = "date_create")
    public Date dateCreate;

    @Column(name = "date_update")
    public Date dateUpdate;

    @Column(name = "user_create")
    public int userCreate;

    @Column(name = "user_update")
    public int userUpdate;
}
