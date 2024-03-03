package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Country;
import core.ms.management.settings.dao.entity.Department;
import core.ms.management.settings.dao.repository.CountryRepository;
import core.ms.management.settings.dao.repository.DepartmentRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class DepartmentImpl {

    @Inject
    DepartmentRepository departmentRepository;

    @Inject
    CountryRepository countryRepository;

    public Response listAllDepartment(){
        List<Department> listDepartment = departmentRepository.listAllDepartment();
        JsonArray jsonArrayDepartment = new JsonArray(listDepartment);
        Response response = Response.ok(jsonArrayDepartment).build();
        if(response.getStatus() == 200){
            return Response.ok(response.getEntity()).build();
        }
        return Response.noContent().build();
    }

    public Response saveDepartment(JsonObject jsonDepartment){
        try {

            JsonObject jsonCountry;
            jsonCountry = jsonDepartment.getJsonObject("country");
            long country_id = jsonCountry.getLong("id");
            Country country = countryRepository.countryFindById(country_id);

            Department department = new Department();
            department.id = 0;
            department.name = jsonDepartment.getString("name");
            department.country = country;

            departmentRepository.saveDepartment(department);
            JsonObject jsonResponseCreateCity = new JsonObject();
            jsonResponseCreateCity.put("message", "CITY " + jsonDepartment.getString("name").toUpperCase() + " CREATED");
            return Response.ok(jsonResponseCreateCity).build();
        }
        catch (Exception e){
            return Response.accepted(e.getMessage()).build();
        }

    }
}
