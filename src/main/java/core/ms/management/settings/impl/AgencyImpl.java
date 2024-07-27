package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Agency;
import core.ms.management.settings.dao.entity.City;
import core.ms.management.settings.dao.entity.User;
import core.ms.management.settings.dao.repository.AgencyRepository;
import core.ms.management.settings.dao.repository.CityRepository;
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
public class AgencyImpl {

    @Inject
    AgencyRepository agencyRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CityRepository cityRepository;

    public Response agencyListAll() {
        try {
            List<Agency> agencyList = agencyRepository.agencyListAll();
            JsonArray jsonAgency = new JsonArray(agencyList);
            Response response = Response.ok(jsonAgency).build();
            if (response.getStatus() == 200) {
                if (agencyList.isEmpty()) {
                    JsonObject jsonResponseAgencyAll = new JsonObject();
                    jsonResponseAgencyAll.put("message", "No existe información registrada");
                    response = Response.ok(jsonResponseAgencyAll).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response agencyFindById(JsonObject jsonDataAgency) {
        try {
            JsonObject jsonAgency = new JsonObject();
            long id = Long.parseLong(jsonDataAgency.getString("id"));
            Agency agency = agencyRepository.agencyFindById(id);

            if (agency == null) {
                jsonAgency.put("message", "No existe la información solicitada");
                return Response.ok(jsonAgency).build();
            }
            JsonObject jsonArrayAgencyById = new JsonObject(Json.encode(agency));
            if (jsonArrayAgencyById.isEmpty()) {
                jsonAgency.put("message", "No existe la información solicitada");
                return Response.ok(jsonAgency).build();
            }
            Response response = Response.ok(jsonArrayAgencyById).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response agencyFindByName(JsonObject jsonDataAgency) {
        try {
            JsonObject jsonAgencyName = new JsonObject();
            String name = jsonDataAgency.getString("name");
            Agency agency = agencyRepository.agencyFindByName(name);

            if (agency == null) {
                jsonAgencyName.put("message", "No existe la información solicitada");
                return Response.ok(jsonAgencyName).build();
            }
            JsonObject jsonResponseAgencyName = new JsonObject(Json.encode(agency));
            if (jsonResponseAgencyName.isEmpty()) {
                jsonAgencyName.put("message", "No existe la información solicitada");
                return Response.ok(jsonAgencyName).build();
            }
            Response response = Response.ok(jsonResponseAgencyName).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response agencySave(JsonObject jsonDataAgency) {
        try {

            JsonObject jsonCity = jsonDataAgency.getJsonObject("city");
            long idCity = jsonCity.getLong("id");
            City city = cityRepository.cityFindById(idCity);

            Agency agency = new Agency();
            agency.name = jsonDataAgency.getString("name");
            agency.address = jsonDataAgency.getString("address");
            agency.userCreate = jsonDataAgency.getInteger("user_create");
            agency.dateCreate = new Date();
            agency.city = city;
            agencyRepository.agencySave(agency);
            JsonObject jsonResponseCreateAgency = new JsonObject();
            jsonResponseCreateAgency.put("message", "Agencia " + jsonDataAgency.getString("name") + " registrada");
            return Response.ok(jsonResponseCreateAgency).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response agencyDelete(JsonObject jsonDataAgency) {
        try {
            JsonObject jsonResponseDeleteAgency = new JsonObject();
            long id = Long.parseLong(jsonDataAgency.getString("id"));
            long responseDelete = agencyRepository.agencyDelete(id);

            if (responseDelete <= 0) {
                jsonResponseDeleteAgency.put("message", "Agencia: " + jsonDataAgency.getString("name") + " ID: "+ jsonDataAgency.getString("id") + " no existe");
                return Response.ok(jsonResponseDeleteAgency).build();
            }
            jsonResponseDeleteAgency.put("message", "Agencia " + jsonResponseDeleteAgency.getString("name") + " ha sido eliminado");
            return Response.ok(jsonResponseDeleteAgency).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response agencyUpdate(JsonObject jsonDataAgency) {
        try {
            JsonObject jsonResponseUpdateAgency= new JsonObject();
            Agency agency = new Agency();
            agency.id = jsonDataAgency.getLong("id");
            agency.name = jsonDataAgency.getString("name");
            agency.address = jsonDataAgency.getString("address");
            agencyRepository.agencyUpdate(agency);
            jsonResponseUpdateAgency.put("message", "Agencia " + jsonResponseUpdateAgency.getString("name").toUpperCase() + " ha sido actualizada");
            Response response = Response.ok(jsonResponseUpdateAgency).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
