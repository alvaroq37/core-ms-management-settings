package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.BusinessDiscounts;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BusinessDiscountsRepository implements PanacheRepository<BusinessDiscounts> {

    public List<BusinessDiscounts> businessDiscountsListAll(){
        return listAll();
    }

    public BusinessDiscounts businessDiscountsFindById(long id) {return find("id", id).firstResult();}

    public void saveBusinessDiscount(BusinessDiscounts businessDiscounts){
        persist(businessDiscounts);
    }
}
