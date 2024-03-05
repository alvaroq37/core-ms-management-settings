package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.ClientCategory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ClientCategoryRepository implements PanacheRepository<ClientCategory> {

    public List<ClientCategory> clientCategoryListAll() {
        return listAll();
    }

    public ClientCategory clientCategoryFindById(long id) {
        return find("id", id).firstResult();
    }

    public ClientCategory clientCategoryFindByDescription(String description) {
        return find("description", description).firstResult();
    }

    public void clientCategorySave(ClientCategory clientCategory) {
        persist(clientCategory);
    }

    public Long clientCategoryDelete(long id) {
        return delete("id", id);
    }

    public void clientCategoryUpdate(ClientCategory clientCategory) {
        persist(clientCategory);
    }
}
