package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.*;
import core.ms.management.settings.dao.repository.CityRepository;
import core.ms.management.settings.dao.repository.OccupationRepository;
import core.ms.management.settings.dao.repository.SexRepository;
import core.ms.management.settings.dao.repository.UserRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class UserImpl {

    @Inject
    UserRepository userRepository;

    @Inject
    OccupationRepository occupationRepository;

    @Inject
    CityRepository cityRepository;

    @Inject
    SexRepository sexRepository;

    public Response userListAll() {
        try {
            List<User> userList = userRepository.agencyListAll();
            JsonArray jsonUser = new JsonArray(userList);
            Response response = Response.ok(jsonUser).build();
            if (response.getStatus() == 200) {
                if (userList.isEmpty()) {
                    JsonObject jsonResponseUserAll = new JsonObject();
                    jsonResponseUserAll.put("message", "No existe información registrada");
                    response = Response.ok(jsonResponseUserAll).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response userFindById(JsonObject jsonDataUser) {
        try {
            JsonObject jsonUser = new JsonObject();
            long id = Long.parseLong(jsonDataUser.getString("id"));
            User user = userRepository.userFindById(id);

            if (user == null) {
                jsonUser.put("message", "No existe la información solicitada");
                return Response.ok(jsonUser).build();
            }
            JsonObject jsonArrayUserById = new JsonObject(Json.encode(user));
            if (jsonArrayUserById.isEmpty()) {
                jsonUser.put("message", "No existe la información solicitada");
                return Response.ok(jsonUser).build();
            }
            Response response = Response.ok(jsonArrayUserById).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response userFindByName(JsonObject jsonDataUser) {
        try {
            JsonObject jsonUserName = new JsonObject();
            String name = jsonDataUser.getString("name");
            User user = userRepository.userFindByName(name);

            if (user == null) {
                jsonUserName.put("message", "No existe la información solicitada");
                return Response.ok(jsonUserName).build();
            }
            JsonObject jsonResponseUserName = new JsonObject(Json.encode(user));
            if (jsonResponseUserName.isEmpty()) {
                jsonUserName.put("message", "No existe la información solicitada");
                return Response.ok(jsonUserName).build();
            }
            Response response = Response.ok(jsonResponseUserName).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response userSave(JsonObject jsonDataUser) {
        try {

            long idOccupation = jsonDataUser.getLong("occ_id");
            long idCity = jsonDataUser.getLong("city_id");
            long idGender = jsonDataUser.getLong("gender_id");

            City city = cityRepository.cityFindById(idCity);
            Occupation occupation = occupationRepository.occupationFindById(idOccupation);
            Gender gender = sexRepository.sexFindById(idGender);

            User user = new User();
            user.ci = jsonDataUser.getString("ci");
            user.names = jsonDataUser.getString("names");
            user.lastNamesPaternal = jsonDataUser.getString("last_name_paternal");
            user.lastNamesMaternal = jsonDataUser.getString("last_name_maternal");
            user.address = jsonDataUser.getString("address");
            user.cellPhone = jsonDataUser.getInteger("cell_phone");
            user.phone = jsonDataUser.getInteger("phone");
            user.email = jsonDataUser.getString("email");
            user.dateBirth = new Date();
            user.city = city;
            user.occupation = occupation;
            user.gender = gender;

            userRepository.userSave(user);

            JsonObject jsonResponseCreateAgency = new JsonObject();
            jsonResponseCreateAgency.put("message", "Usuario " + jsonDataUser.getString("names") + " registrado(a)");
            return Response.ok(jsonResponseCreateAgency).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response userDelete(JsonObject jsonDataUser) {
        try {
            JsonObject jsonResponseDeleteUser = new JsonObject();
            long id = Long.parseLong(jsonDataUser.getString("id"));
            long responseDelete = userRepository.agencyDelete(id);

            if (responseDelete <= 0) {
                jsonResponseDeleteUser.put("message", "Usuario: " + jsonDataUser.getString("names") + " ID: "+ jsonDataUser.getString("id") + " no existe");
                return Response.ok(jsonResponseDeleteUser).build();
            }
            jsonResponseDeleteUser.put("message", "Usuario " + jsonResponseDeleteUser.getString("names") + " ha sido eliminado(a)");
            return Response.ok(jsonResponseDeleteUser).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response userUpdate(JsonObject jsonDataUser) {
        try {
            JsonObject jsonResponseUpdateAgency= new JsonObject();
            User user = new User();
            user.id = jsonDataUser.getLong("id");
            user.ci = jsonDataUser.getString("ci");
            user.names = jsonDataUser.getString("names");
            user.lastNamesPaternal = jsonDataUser.getString("last_name_paternal");
            user.lastNamesMaternal = jsonDataUser.getString("last_name_maternal");
            user.address = jsonDataUser.getString("address");
            user.cellPhone = jsonDataUser.getInteger("cell_phone");
            user.phone = jsonDataUser.getInteger("phone");
            user.email = jsonDataUser.getString("email");
            user.dateBirth = new Date();

            userRepository.userUpdate(user);
            jsonResponseUpdateAgency.put("message", "Usuario " + jsonResponseUpdateAgency.getString("names").toUpperCase() + " ha sido actualizado(a) ");
            Response response = Response.ok(jsonResponseUpdateAgency).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
