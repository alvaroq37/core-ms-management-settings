package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "t_type_operation")
public class TypeOperation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name="description")
    public String description;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "user_create")
    public int user_create;
    @Column(name = "user_update")
    public int userUpdate;
}
