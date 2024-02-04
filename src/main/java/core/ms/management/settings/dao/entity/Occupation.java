package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;
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
}
