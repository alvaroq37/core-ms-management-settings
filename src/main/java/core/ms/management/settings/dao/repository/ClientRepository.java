package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Client;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    public List<Client> clientListAll() {
        return listAll();
    }

    public Client clientFindById(long id) {

        return find("id", id).firstResult();

    }
    public Client clientFindByCi(String ci) {
        return find("ci", ci).firstResult();
    }

    public Client clientFindByName(String names) {

        return find("names", names).firstResult();
    }
    public void clientSave(Client client) {
        persist(client);
    }

    public Long clientDelete(long id) {
        return delete("id", id);
    }

    public void clientUpdate(Client client) {
        persist(client);
    }
}
