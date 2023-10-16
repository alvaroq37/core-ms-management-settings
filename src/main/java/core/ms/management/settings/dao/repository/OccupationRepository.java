package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Occupation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OccupationRepository implements PanacheRepository<Occupation> {
    public List<Occupation> occupationListAll() {
        return listAll();
    }


    public Occupation occupationFindById(long id) {
        return find("id", id).firstResult();
    }

    public Occupation occupationFindByName(String description) {
        return find("description", description).firstResult();
    }

    public void occupationSave(Occupation occupation) {
        persist(occupation);
    }

    public Long occupationDelete(long id) {
        return delete("id", id);
    }

    public void occupationUpdate(Occupation occupation) {
        persist(occupation);
    }
}
