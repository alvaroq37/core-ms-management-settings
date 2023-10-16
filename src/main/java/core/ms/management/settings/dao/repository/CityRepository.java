package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.City;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CityRepository implements PanacheRepository<City> {

    public List<City> cityListAll() {
        return listAll();
    }

    public City cityFindById(long id) {
        return find("id", id).firstResult();
    }

    public City cityFindByName(String name) {
        return find("name", name).firstResult();
    }

    public List<City> citiesFindByName(String name){
        return list("SELECT c.name, c.country.name FROM City c INNER JOIN c.country WHERE c.name = '" + name + "'");
    }

    public void citySave(City city) {
        persist(city);
    }

    public Long cityDelete(long id) {
        return delete("id", id);
    }

    public void cityUpdate(City city) {
        persist(city);
    }
}
