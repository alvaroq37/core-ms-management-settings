package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Classification;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
@ApplicationScoped
public class ClassificationRepository implements PanacheRepository<Classification> {

    public List<Classification> ClassificationListAll() {
        return listAll();
    }

    public Classification ClassificationFindById(long id) {
        return find("id", id).firstResult();
    }

    public Classification ClassificationFindByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Classification> citiesFindByName(String name){
        return list("SELECT c.name, c.country.name FROM Classification c INNER JOIN c.country WHERE c.name = '" + name + "'");
    }

    public void ClassificationSave(Classification Classification) {
        persist(Classification);
    }

    public Long ClassificationDelete(long id) {
        return delete("id", id);
    }

    public void ClassificationUpdate(Classification Classification) {
        persist(Classification);
    }
}
