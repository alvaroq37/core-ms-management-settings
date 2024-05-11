package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.ContractOperation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ContractOperationRepository implements PanacheRepository<ContractOperation> {

    public List<ContractOperation> listAllContractOperation() {
        return listAll().stream().toList();
    }

    public List<ContractOperation> listContractOperationById(Long id){
        return find("id", id).stream().toList();
    }

    public List<ContractOperation> listContractOperationByIdContract(Long contract_id){
        return find("contract_id", contract_id).stream().toList();
    }

    public void saveContractOperation(ContractOperation contractOperation){
        persist(contractOperation);
    }

    public Long deleteContractOperation(Long id){return delete("id", id);}

    public void updateContractOperation(ContractOperation contractOperation){persist(contractOperation);}
}
