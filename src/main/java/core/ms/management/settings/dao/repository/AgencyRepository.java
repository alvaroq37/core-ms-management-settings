package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Agency;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AgencyRepository implements PanacheRepository<Agency> {

    public List<Agency> agencyListAll(){
        return listAll();
    }

    public Agency agencyFindById(Long id){
        return find("id", id).firstResult();
    }

    public Agency agencyFindByName(String name){
        return find("name", name).firstResult();
    }

    public List<Agency> agencyFindByCity(Long id){
        return list("city_id", id).stream().toList();
    }

    public void agencySave(Agency agency){
        persist(agency);
    }

    public Long agencyDelete(long id) {
        return delete("id", id);
    }

    public void agencyUpdate(Agency agency) {
        persist(agency);
    }
}
