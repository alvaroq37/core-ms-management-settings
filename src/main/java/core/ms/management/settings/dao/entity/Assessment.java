package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_assessment")
public class Assessment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;



}
