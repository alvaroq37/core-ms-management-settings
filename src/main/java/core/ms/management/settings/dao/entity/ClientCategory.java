package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_client_category")
public class ClientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "descripcion")
    public String description;

    @Column(name = "Value")
    public String value;
}
