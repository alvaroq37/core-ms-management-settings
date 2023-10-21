package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Material;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MaterialRepository implements PanacheRepository<Material> {

    public List<Material> materialListAll() {
        return listAll();
    }

    public void materialSave(Material material){
        persist(material);
    }
}
