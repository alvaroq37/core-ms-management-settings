package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.JewelCategory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JewelCategoryRepository implements PanacheRepository<JewelCategory> {

    public List<JewelCategory> jewelCategoryListAll(){
        return listAll();
    }

    public void jewelCategorySave(JewelCategory jewelCategory){
        persist(jewelCategory);
    }
}
