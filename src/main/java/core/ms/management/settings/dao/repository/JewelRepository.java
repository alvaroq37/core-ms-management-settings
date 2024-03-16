package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Jewel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JewelRepository implements PanacheRepository<Jewel> {

    public List<Jewel> jewelList (){
        return listAll();
    }

    public void jewelSave(Jewel jewel){
        persist(jewel);
    }


}
