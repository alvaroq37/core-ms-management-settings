package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.SubClassification;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SubClassificationRepository implements PanacheRepository<SubClassification> {

    public List<SubClassification> SubClassificationListAll() {
        return listAll();
    }

    public SubClassification SubClassificationFindById(long id) {
        return find("id", id).firstResult();
    }

    public SubClassification SubClassificationFindByName(String name) {
        return find("name", name).firstResult();
    }

    public List<SubClassification> citiesFindByName(String name){
        return list("SELECT c.name, c.country.name FROM SubClassification c INNER JOIN c.country WHERE c.name = '" + name + "'");
    }

    public void SubClassificationSave(SubClassification SubClassification) {
        persist(SubClassification);
    }

    public Long SubClassificationDelete(long id) {
        return delete("id", id);
    }

    public void SubClassificationUpdate(SubClassification SubClassification) {
        persist(SubClassification);
    }
}
