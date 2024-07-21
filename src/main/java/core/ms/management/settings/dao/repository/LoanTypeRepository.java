package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Client;
import core.ms.management.settings.dao.entity.LoanType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class LoanTypeRepository implements PanacheRepository<LoanType> {

    public List<LoanType> listAllLoanType(){
        return listAll().stream().toList();
    }
    public LoanType loanTypeFindById(long id) {
        return find("id", id).firstResult();
    }
    public void loanTypeSave(LoanType loanType) {
        persist(loanType);
    }
}
