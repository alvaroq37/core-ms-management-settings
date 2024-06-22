package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_type_operation")
public class TypeOperation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="description")
    public String description;
}
