package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_sexs")
public class Sex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sex_id")
    public long id;
    @Column(name = "sex_description")
    public String description;

    @OneToMany(targetEntity = Client.class, fetch = FetchType.LAZY, mappedBy = "sexuality", cascade = CascadeType.ALL)
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
