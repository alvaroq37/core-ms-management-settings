package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.JewelType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JewelTypeRepository implements PanacheRepository<JewelType> {
    public List<JewelType> listAllJewelType(){
        return listAll().stream().toList();
    }

    public JewelType findByIdJewelType(Long id){
        return find("id", id).firstResult();
    }

    public void saveJewelType(JewelType jewelType){
        persist(jewelType);
    }

    public void deleteJewelType(Long id){
        delete("id", id);
    }
}
