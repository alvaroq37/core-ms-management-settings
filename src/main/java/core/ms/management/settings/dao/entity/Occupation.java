package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_occupations")
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "occ_id")
    private long id;
    @Column(name = "occ_description")
    private String description;

    @OneToMany(targetEntity = Client.class, fetch = FetchType.EAGER, mappedBy = "occupation", cascade = CascadeType.ALL)
    private List<Client> clients;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
