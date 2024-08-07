package core.ms.management.settings.dao.repository;

import core.ms.management.settings.dao.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public List<User> agencyListAll(){
        return listAll();
    }

    public User userFindById(Long id){
        return find("id", id).firstResult();
    }

    public User userFindByName(String email, String password){
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        return find("email=:email and password=:password", params).firstResult();
    }

    public List<User> userFindByOccupation(Long id){
        return list("occ_id", id).stream().toList();
    }

    public List<User> userFindByCity(Long id){
        return list("id", id).stream().toList();
    }
    public List<User> userFindByGender(Long id){
        return list("gender_id", id).stream().toList();
    }

    public void userSave(User user){
        persist(user);
    }

    public Long agencyDelete(long id) {
        return delete("id", id);
    }

    public void userUpdate(User user) {
        persist(user);
    }
}
