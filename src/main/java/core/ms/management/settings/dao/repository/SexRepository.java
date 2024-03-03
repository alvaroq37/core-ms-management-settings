package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Gender;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SexRepository implements PanacheRepository<Gender> {

    public List<Gender> sexListAll() {
        return listAll();
    }

    public Gender sexFindById(long id) {
        return find("id", id).firstResult();
    }

    public Gender sexFindByDescription(String description) {
        return find("description", description).firstResult();
    }

    public void sexSave(Gender gender) {
        persist(gender);
    }

    public Long sexDelete(long id) {
        return delete("id", id);
    }

    public void sexUpdate(Gender gender) {
        persist(gender);
    }
}
