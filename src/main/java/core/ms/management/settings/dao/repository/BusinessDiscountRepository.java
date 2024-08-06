package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.BusinessDiscount;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BusinessDiscountRepository implements PanacheRepository<BusinessDiscount> {

    public List<BusinessDiscount> businessDiscountsListAll(){
        return listAll();
    }

    public BusinessDiscount businessDiscountsFindById(long id) {return find("id", id).firstResult();}

    public void saveBusinessDiscount(BusinessDiscount businessDiscount){
        persist(businessDiscount);
    }
}
