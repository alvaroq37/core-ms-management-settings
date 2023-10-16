package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.Country;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CountryRepository implements PanacheRepository<Country> {

    public List<Country> countryListAll() {
        return listAll();
    }

    public Country countryFindById(long id) {
        return find("id", id).firstResult();
    }

    public Country countryFindByName(String name) {
        return find("name", name).firstResult();
    }

    public void countrySave(Country country) {
        persist(country);
    }

    public Long countryDelete(long id) {
        return delete("id", id);
    }

    public void countryUpdate(Country country) {
        persist(country);
    }
}
