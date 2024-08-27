package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Agency;
import core.ms.management.settings.dao.entity.City;
import core.ms.management.settings.dao.entity.Enterprise;
import core.ms.management.settings.dao.entity.User;
import core.ms.management.settings.dao.repository.AgencyRepository;
import core.ms.management.settings.dao.repository.CityRepository;
import core.ms.management.settings.dao.repository.EnterpriseRepository;
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
    CityRepository cityRepository;

    @Inject
    EnterpriseRepository enterpriseRepository;

    JsonObject jsonResponse = new JsonObject();

    public Response agencyListAll() {
        try {
            JsonArray jsonAgency = new JsonArray(agencyRepository.agencyListAll());
            if (jsonAgency.isEmpty()) {
                jsonResponse.put("message", "No existen agencias registradas");
                return Response.ok(jsonResponse).build();
            }
            Response response = Response.ok(jsonAgency).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response agencyFindById(JsonObject jsonDataAgency) {
        try {
            long id = Long.parseLong(jsonDataAgency.getString("id"));
            if(id > 0){
                Agency agency = agencyRepository.agencyFindById(id);
                if (agency == null) {
                    jsonResponse.put("message", "No existe la agencia solicitada");
                    return Response.ok(jsonResponse).build();
                }
                JsonObject jsonArrayAgencyById = new JsonObject(Json.encode(agency));
                Response response = Response.ok(jsonArrayAgencyById).build();
                if (response.getStatus() == 200) {
                    return Response.ok(response.getEntity()).build();
                }
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response agencyFindByName(JsonObject jsonDataAgency) {
        try {
            String name = jsonDataAgency.getString("name");
            if(!name.isEmpty()){
                Agency agency = agencyRepository.agencyFindByName(name);
                if (agency == null) {
                    jsonResponse.put("message", "No existe la agencia solicitada");
                    return Response.ok(jsonResponse).build();
                }
                JsonObject jsonResponseAgencyName = new JsonObject(Json.encode(agency));
                Response response = Response.ok(jsonResponseAgencyName).build();
                if (response.getStatus() == 200) {
                    return Response.ok(response.getEntity()).build();
                }
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response agencySave(JsonObject jsonDataAgency) {
        try {
            if(jsonDataAgency.getJsonObject("city")!=null && !jsonDataAgency.getString("name").isEmpty()){
                JsonObject jsonCity = jsonDataAgency.getJsonObject("city");
                JsonObject jsonEnterprise = jsonDataAgency.getJsonObject("enterprise");
                Long idCity = jsonCity.getLong("id");
                Long idEnterprise = jsonEnterprise.getLong("id");
                if(idCity > 0 && idEnterprise > 0){
                    City city = cityRepository.cityFindById(idCity);
                    Enterprise enterprise = enterpriseRepository.enterpriseFindById(idEnterprise);

                    Agency agency = new Agency();
                    agency.name = jsonDataAgency.getString("name");
                    agency.address = jsonDataAgency.getString("address");
                    agency.phone = Integer.parseInt(jsonDataAgency.getString("phone"));
                    agency.userCreate = jsonDataAgency.getInteger("user_create");
                    agency.dateCreate = new Date();
                    agency.city = city;
                    agency.enterprise = enterprise;
                    agencyRepository.agencySave(agency);

                    jsonResponse.put("message", "Agencia " + jsonDataAgency.getString("name") + " registrada correctamente");
                    return Response.ok(jsonResponse).build();
                }else{
                    return Response.ok(jsonResponse.put("message","No se pudo registrar la agencia - Existen datos incompletos")).build();
                }

            }else{
                return Response.ok(jsonResponse.put("message","No se pudo registrar la agencia - Existen datos incompletos")).build();
            }
        } catch (Exception e) {
            return Response.accepted(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response agencyDelete(JsonObject jsonDataAgency) {
        try {
            if(jsonDataAgency.isEmpty()){
                return Response.ok(jsonResponse.put("message","No se cuentan con datos para realizar la transacción")).build();
            }
            long id = Long.parseLong(jsonDataAgency.getString("id"));
            if(id > 0){
                long responseDelete = agencyRepository.agencyDelete(id);
                if (responseDelete <= 0) {
                    jsonResponse.put("message", "Agencia: " + jsonDataAgency.getString("name") + " ID: "+ jsonDataAgency.getString("id") + " no existe");
                    return Response.ok(jsonResponse).build();
                }
                jsonResponse.put("message", "Agencia " + jsonResponse.getString("name") + " ha sido eliminado");
                return Response.ok(jsonResponse).build();
            }else{
                return Response.ok(jsonResponse.put("message","No se pudo eliminar la agencia")).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response agencyUpdate(JsonObject jsonDataAgency) {
        try {
            if(jsonDataAgency.isEmpty()){
                return Response.ok(jsonResponse.put("message","No se cuentan con datos para realizar la transacción")).build();
            }
            Agency agency = new Agency();
            agency.id = jsonDataAgency.getLong("id");
            agency.name = jsonDataAgency.getString("name");
            agency.address = jsonDataAgency.getString("address");
            agency.phone = jsonDataAgency.getInteger("phone");
            agencyRepository.agencyUpdate(agency);
            jsonResponse.put("message", "Agencia " + jsonResponse.getString("name") + " actualizada correctamente");
            Response response = Response.ok(jsonResponse).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
    }
}
