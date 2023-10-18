package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_jewel_category")
public class JewelCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "jewel_category_id")
    private long id;

    @Column(name = "description")
    private String description;

}
