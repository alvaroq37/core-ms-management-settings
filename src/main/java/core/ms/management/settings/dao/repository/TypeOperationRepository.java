package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Gender;
import core.ms.management.settings.dao.entity.TypeOperation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TypeOperationRepository implements PanacheRepository<TypeOperation> {

    public void typeOperationSave(TypeOperation typeOperation) {
        persist(typeOperation);
    }

    public Long typeOperationDelete(long id) {
        return delete("id", id);
    }

    public void typeOperationUpdate(TypeOperation typeOperation) {
        persist(typeOperation);
    }
}
