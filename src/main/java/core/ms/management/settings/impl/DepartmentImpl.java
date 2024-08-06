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

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class DepartmentImpl {

    @Inject
    DepartmentRepository departmentRepository;

    @Inject
    CountryRepository countryRepository;

    JsonObject jsonResponseFail = new JsonObject();

    public Response listAllDepartment(){
        try{
            List<Department> listDepartment = departmentRepository.listAllDepartment();
            if(!listDepartment.isEmpty()){
                JsonArray jsonArrayDepartment = new JsonArray(listDepartment);
                Response response = Response.ok(jsonArrayDepartment).build();
                if(response.getStatus() == 200){
                    return Response.ok(response.getEntity()).build();
                }
            }else{
                return Response.ok(jsonResponseFail.put("message", "No existen departamentos registrados")).build();
            }
        }catch (Exception e){
            return Response.ok(jsonResponseFail.put("message", e.getMessage())).build();
        }
        return Response.noContent().build();
    }

    public Response saveDepartment(JsonObject jsonDepartment){
        try {
            JsonObject jsonCountry;
            if(!jsonDepartment.isEmpty() && jsonDepartment.getJsonObject("country")!=null && !jsonDepartment.getString("name").isEmpty()){
                jsonCountry = jsonDepartment.getJsonObject("country");
                Long country_id = jsonCountry.getLong("id");
                Country country = countryRepository.countryFindById(country_id);

                Department department = new Department();
                department.id = 0;
                department.name = jsonDepartment.getString("name");
                department.country = country;
                department.dateCreate = new Date();
                departmentRepository.saveDepartment(department);
                JsonObject jsonResponseCreateCity = new JsonObject();
                jsonResponseCreateCity.put("message", "Departamento " + jsonDepartment.getString("name") + " registrado correctamente");
                return Response.ok(jsonResponseCreateCity).build();
            }else{
                return Response.ok(jsonResponseFail.put("message", "No se pudo registrar el departamento - Existen datos incompletos")).build();
            }
        }
        catch (Exception e){
            return Response.accepted(jsonResponseFail.put("message",e.getMessage())).build();
        }
    }
}
