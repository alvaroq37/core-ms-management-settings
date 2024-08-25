package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.City;
import core.ms.management.settings.dao.entity.Department;
import core.ms.management.settings.dao.repository.CityRepository;
import core.ms.management.settings.dao.repository.DepartmentRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class CityImpl {

    @Inject
    CityRepository cityRepository;

    @Inject
    DepartmentRepository departmentRepository;

    JsonObject jsonResponse = new JsonObject();

    public Response cityListAll() {
        try {
            List<City> cityListAll = cityRepository.cityListAll();
            JsonArray jsonArrayCityAll = new JsonArray(cityListAll);
            if (cityListAll.isEmpty()) {
                jsonResponse.put("message", "No existen ciudades registradas");
                return Response.ok(jsonResponse).build();
            }
            Response response = Response.ok(jsonArrayCityAll).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response cityFindById(JsonObject jsonDataCity) {
        try {
            long id = Long.parseLong(jsonDataCity.getString("id"));
            if(id < 0){
                jsonResponse.put("message", "No se ingresó un parámetro de búsqueda");
                return Response.ok(jsonResponse).build();
            }
            City cityById = cityRepository.cityFindById(id);
            if (cityById == null) {
                jsonResponse.put("message", "La ciudad ingresada no se encuentra registrada");
                return Response.ok(jsonResponse).build();
            }
            JsonObject jsonArrayCityById = new JsonObject(Json.encode(cityById));
            Response response = Response.ok(jsonArrayCityById).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response cityFindByName(JsonObject jsonDataCity) {
        try {
            String name = jsonDataCity.getString("name");
            if(name.isEmpty()){
                jsonResponse.put("message", "No se ingresó el nombre de la ciudad a buscar");
                return Response.ok(jsonResponse).build();
            }
            City cityName = cityRepository.cityFindByName(name);
            if (cityName == null) {
                jsonResponse.put("message", "La ciudad: " + jsonDataCity.getString("name") + " no se encuentra registrada" );
                return Response.ok(jsonResponse).build();
            }
            JsonObject jsonResponseCityName = new JsonObject(Json.encode(cityName));
            Response response = Response.ok(jsonResponseCityName).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response citySave(JsonObject jsonDataCity) {
        try {
            if(jsonDataCity.getJsonObject("department")!=null && jsonDataCity.getJsonObject("country")!=null && !jsonDataCity.getString("name").isEmpty()){
                JsonObject jsonDepartment;
                jsonDepartment = jsonDataCity.getJsonObject("department");
                long department_id = Long.parseLong(jsonDepartment.getString("id"));
                Department department = departmentRepository.findDepartmentById(department_id);
                City city = new City();
                city.name = jsonDataCity.getString("name");
                city.dateCreate = new Date();
                city.departament = department;
                cityRepository.citySave(city);
                jsonResponse.put("message", "Ciudad " + jsonDataCity.getString("name") + " registrada");
                return Response.ok(jsonResponse).build();
            }else{
                return Response.ok(jsonResponse.put("message","No se pudo registrar la ciudad - Existen datos incompletos")).build();
            }
        } catch (Exception e) {
            return Response.accepted(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response cityDelete(JsonObject jsonDataCity) {
        try {
            JsonObject jsonResponseDeleteCity = new JsonObject();
            long id = Long.parseLong(jsonDataCity.getString("id"));
            long responseDelete = cityRepository.cityDelete(id);
            if (responseDelete <= 0) {
                jsonResponse.put("message", "Ciudad: " + jsonDataCity.getString("name") + " no se encuentra registrada");
                return Response.ok(jsonResponse).build();
            }
            jsonResponseDeleteCity.put("message", "Ciudad " + jsonDataCity.getString("name") + " eliminada correctamente");
            return Response.ok(jsonResponseDeleteCity).build();
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response cityUpdate(JsonObject jsonDataCity) {
        try {
            if(jsonDataCity.getJsonObject("department")!=null && jsonDataCity.getJsonObject("country")!=null && !jsonDataCity.getString("name").isEmpty()){
                long id = Long.parseLong(jsonDataCity.getString("id"));
                long idDepartment = Long.parseLong(jsonDataCity.getString("idDepartment"));
                if(id > 0 && idDepartment > 0){
                    City city = cityRepository.cityFindById(id);
                    Department department = departmentRepository.findDepartmentById(idDepartment);
                    if (city == null || department == null) {
                        jsonResponse.put("message", "Ciudad " + jsonDataCity.getString("name") + " no registrada");
                        return Response.ok(jsonResponse).build();
                    }
                    city.name = jsonDataCity.getString("name").toUpperCase();
                    city.departament = department;
                    cityRepository.cityUpdate(city);
                    jsonResponse.put("message", "Ciudad " + jsonDataCity.getString("name") + " actualizada correctamente");
                    Response response = Response.ok(jsonResponse).build();
                    return Response.ok(response.getEntity()).build();
                }else{
                    return Response.ok(jsonResponse.put("message","No se pudo actualizar la ciudad - Existen datos incompletos")).build();
                }
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }
}
