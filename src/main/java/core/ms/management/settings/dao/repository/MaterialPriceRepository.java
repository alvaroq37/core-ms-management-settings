package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.MaterialPrice;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MaterialPriceRepository implements PanacheRepository<MaterialPrice> {

    public List<MaterialPrice> materialPriceListAll() {
        return listAll();
    }

    public void materialPriceSave(MaterialPrice materialPrice){
        persist(materialPrice);
    }
}
