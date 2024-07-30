package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Enterprise;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EnterpriseRepository implements PanacheRepository<Enterprise> {
    public List<Enterprise> enterpriseListAll(){
        return listAll().stream().toList();
    }
    public Enterprise enterpriseFindById(Long id){
        return find("id", id).firstResult();
    }
    public void enterpriseSave(Enterprise enterprise){
        persist(enterprise);
    }
    public void enterpriseDelete(Long id){
        delete("id", id);
    }
}
