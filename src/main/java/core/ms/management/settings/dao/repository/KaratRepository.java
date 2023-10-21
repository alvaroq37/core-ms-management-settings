package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Karat;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class KaratRepository implements PanacheRepository<Karat> {

    public List<Karat> karatListAll() {
        return listAll();
    }

    public void karatSave(Karat karat){
        persist(karat);
    }
}
