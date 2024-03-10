package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Currency;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CurrencyRepository implements PanacheRepository<Currency> {
    public List<Currency> currencyListAll(){
        return listAll();
    }
    public Currency currencyFindById(long id){
        return find("id", id).firstResult();
    }
    public void currencySave(Currency currency){
        persist(currency);
    }
    public Long currencyDelete(long id){
        return delete("id", id);
    }
}
