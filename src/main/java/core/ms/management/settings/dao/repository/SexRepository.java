package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Sex;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SexRepository implements PanacheRepository<Sex> {

    public List<Sex> sexListAll() {
        return listAll();
    }

    public Sex sexFindById(long id) {
        return find("id", id).firstResult();
    }

    public Sex sexFindByDescription(String description) {
        return find("description", description).firstResult();
    }

    public void sexSave(Sex sex) {
        persist(sex);
    }

    public Long sexDelete(long id) {
        return delete("id", id);
    }

    public void sexUpdate(Sex sex) {
        persist(sex);
    }
}
