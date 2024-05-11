package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Contract;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ContractRepository implements PanacheRepository<Contract> {

    public List<Contract> contractListAll(){
        return listAll().stream().toList();
    }

    public Contract contractFindById(long id){
        return find("id", id).firstResult();
    }

    public List<Contract> contractFindByAgency(long id){
        return list("agency_id", id).stream().toList();
    }
    public List<Contract> contractFindByClient(long id){
        return list("client_id", id).stream().toList();
    }

   public Contract contractSave(Contract contract){
        persist(contract);
        return contract;
    }

    public Long contractDelete(long id){return delete("id", id);}

    public void contractUpdate(Contract contract){persist(contract);}
}
