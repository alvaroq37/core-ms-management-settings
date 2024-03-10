package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.*;
import core.ms.management.settings.dao.repository.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ClientImpl {

    @Inject
    ClientRepository clientRepository;

    @Inject
    CityRepository cityRepository;

    @Inject
    SexRepository sexRepository;

    @Inject
    OccupationRepository occupationRepository;

    @Inject
    ClientCategoryRepository clientCategoryRepository;

    JsonObject jsonResponseFail = new JsonObject();

    ObjectMapper mapper = new ObjectMapper();
    public Response clientListAll() {
        try {
            List<Client> clientListAll = clientRepository.clientListAll();
            JsonArray jsonArrayClientAll = new JsonArray(clientListAll);
            Response response = Response.ok(jsonArrayClientAll).build();
            if (response.getStatus() == 200) {
                if (clientListAll.isEmpty()) {
                    jsonResponseFail.put("message", "LIST CLIENT EMPTY");
                    response = Response.ok(jsonResponseFail).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response clientFindByCi(JsonObject jsonDataClient) {
        try {
            String ci = jsonDataClient.getString("ci");
            Client clientFindByCi = clientRepository.clientFindByCi(ci);
            JsonObject jsonArrayClientCi = new JsonObject(mapper.writeValueAsString(clientFindByCi));
            Response response = Response.ok(jsonArrayClientCi).build();
            if (response.getStatus() == 200) {
                if (jsonArrayClientCi.isEmpty()) {
                    jsonResponseFail.put("message", "CLIENT  BY ID: " + ci + " NOT EXISTS");
                    return Response.ok(jsonResponseFail).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response clientFindById(JsonObject jsonDataclient) {
        try {
            long id = Long.parseLong(jsonDataclient.getString("id"));
            Client clientFindById = clientRepository.clientFindById(id);
            JsonObject jsonArrayClientById = new JsonObject(mapper.writeValueAsString(clientFindById));
            Response response = Response.ok(jsonArrayClientById).build();
            if (response.getStatus() == 200) {
                if (jsonArrayClientById.isEmpty()) {
                    jsonResponseFail.put("message", "CLIENT  BY ID: " + id + " NOT EXISTS");
                    return Response.ok(jsonResponseFail).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response clientFindByName(JsonObject jsonDataclient) {
        try {
            String name = jsonDataclient.getString("name");
            Client clientFindByName = clientRepository.clientFindByName(name);
            JsonObject jsonArrayClient = new JsonObject(mapper.writeValueAsString(clientFindByName));
            Response response = Response.ok(jsonArrayClient).build();
            if (response.getStatus() == 200) {
                if (jsonArrayClient.isEmpty()) {
                    jsonResponseFail.put("message", "CLIENT  BY NAME: " + name.toUpperCase() + " NOT EXISTS");
                    return Response.ok(jsonResponseFail).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response citySave(JsonObject jsonDataClient) {
        try {

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");

            JsonObject jsonOccupation = jsonDataClient.getJsonObject("occupation");
            JsonObject jsonGender = jsonDataClient.getJsonObject("gender");
            JsonObject jsonCity = jsonDataClient.getJsonObject("city");
            JsonObject jsonClientCategory = jsonDataClient.getJsonObject("client_category");

            long idOccupation = jsonOccupation.getLong("id");
            long idCity = jsonCity.getLong("id");
            long idGender = jsonGender.getLong("id");
            long idCategory = jsonClientCategory.getLong("id");

            String dateHappy = jsonDataClient.getString("dateBirth");

            City city = cityRepository.cityFindById(idCity);
            Gender gender = sexRepository.sexFindById(idGender);
            Occupation occupation = occupationRepository.occupationFindById(idOccupation);
            ClientCategory clientCategory = clientCategoryRepository.clientCategoryFindById(idCategory);

            Client client = new Client();
            client.ci = jsonDataClient.getString("ci");
            client.names = jsonDataClient.getString("names").toUpperCase();
            client.address = jsonDataClient.getString("address").toUpperCase();
            client.email = jsonDataClient.getString("email");
            client.cellPhone = Integer.parseInt(jsonDataClient.getString("number_cell_phone"));
            client.dateBirth = formatDate.parse(dateHappy);
            client.lastNamesMaternal = jsonDataClient.getString("names_maternal").toUpperCase();
            client.lastNamesPaternal = jsonDataClient.getString("names_paternal").toUpperCase();
            client.phone = Integer.parseInt(jsonDataClient.getString("number_phone"));
            client.city = city;
            client.gender = gender;
            client.occupation = occupation;
            client.clientCategory = clientCategory;

            clientRepository.clientSave(client);
            JsonObject jsonResponseClientSave = new JsonObject();
            jsonResponseClientSave.put("message", "Cliente " + jsonDataClient.getString("names") + " registrado");
            return Response.ok(jsonResponseClientSave).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response clientDelete(JsonObject jsonDataClient) {
        try {
            JsonObject jsonResponseClientDelete = new JsonObject();
            long id = Long.parseLong(jsonDataClient.getString("id"));
            long responseDelete = clientRepository.clientDelete(id);

            if (responseDelete <= 0) {
                jsonResponseFail.put("message", "CLIENT " + jsonDataClient.getString("name").toUpperCase() + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            jsonResponseClientDelete.put("message", "CLIENT " + jsonDataClient.getString("name").toUpperCase() + " DELETE");
            return Response.ok(jsonResponseClientDelete).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response clientUpdate(JsonObject jsonDataClient) {
        try {
            JsonObject jsonResponseClientUpdate = new JsonObject();
            long id = Long.parseLong(jsonDataClient.getString("id"));
            String name = jsonDataClient.getString("names");
            if (id <= 0 || name == null) {
                jsonResponseFail.put("message", "CLIENT " + jsonDataClient.getString("names").toUpperCase() + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            long idCity = Long.parseLong(jsonDataClient.getString("idCity"));
            long idSex = Long.parseLong(jsonDataClient.getString("idSex"));
            long idOccupation = Long.parseLong(jsonDataClient.getString("idOccupation"));

            City city = cityRepository.cityFindById(idCity);
            Gender gender = sexRepository.sexFindById(idSex);
            Occupation occupation = occupationRepository.occupationFindById(idOccupation);

            Client client = clientRepository.clientFindById(Long.parseLong(jsonDataClient.getString("id")));
            client.ci = jsonDataClient.getString("ci");
            client.names = jsonDataClient.getString("names").toUpperCase();
            client.address = jsonDataClient.getString("address").toUpperCase();
            client.email = jsonDataClient.getString("email");
            client.cellPhone = Integer.parseInt(jsonDataClient.getString("number_cell_phone"));
            //TODO: Generar la lógica para parsear la fecha que llega en string
            client.dateBirth = new Date();
            client.lastNamesMaternal = jsonDataClient.getString("names_maternal").toUpperCase();
            client.lastNamesPaternal = jsonDataClient.getString("names_paternal").toUpperCase();
            client.phone = Integer.parseInt(jsonDataClient.getString("number_phone"));
            client.city = city;
            client.gender = gender;
            client.occupation = occupation;

            clientRepository.clientUpdate(client);
            jsonResponseClientUpdate.put("message", "CLIENT " + name.toUpperCase() + " HAS UPDATE");
            Response response = Response.ok(jsonResponseClientUpdate).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
