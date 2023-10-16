package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.City;
import core.ms.management.settings.dao.entity.Client;
import core.ms.management.settings.dao.entity.Occupation;
import core.ms.management.settings.dao.entity.Sex;
import core.ms.management.settings.dao.repository.CityRepository;
import core.ms.management.settings.dao.repository.ClientRepository;
import core.ms.management.settings.dao.repository.OccupationRepository;
import core.ms.management.settings.dao.repository.SexRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

            long idCity = Long.parseLong(jsonDataClient.getString("idCity"));
            long idSex = Long.parseLong(jsonDataClient.getString("idSex"));
            long idOccupation = Long.parseLong(jsonDataClient.getString("idOccupation"));

            City city = cityRepository.cityFindById(idCity);
            Sex sex = sexRepository.sexFindById(idSex);
            Occupation occupation = occupationRepository.occupationFindById(idOccupation);

            Client client = new Client();
            client.setCi(jsonDataClient.getString("ci"));
            client.setNames(jsonDataClient.getString("names").toUpperCase());
            client.setAddress(jsonDataClient.getString("address").toUpperCase());
            client.setEmail(jsonDataClient.getString("email"));
            client.setCellPhone(Integer.parseInt(jsonDataClient.getString("number_cell_phone")));
            //TODO: Generar la lógica para parsear la fecha que llega en string
            client.setDateBirth(new Date());
            client.setLastNamesMaternal(jsonDataClient.getString("names_maternal").toUpperCase());
            client.setLastNamesPaternal(jsonDataClient.getString("names_paternal").toUpperCase());
            client.setPhone(Integer.parseInt(jsonDataClient.getString("number_phone")));
            client.setCity(city);
            client.setSexuality(sex);
            client.setOccupation(occupation);

            clientRepository.clientSave(client);
            JsonObject jsonResponseClientSave = new JsonObject();
            jsonResponseClientSave.put("message", "CLIENT " + jsonDataClient.getString("name").toUpperCase() + " CREATED");
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
            Sex sex = sexRepository.sexFindById(idSex);
            Occupation occupation = occupationRepository.occupationFindById(idOccupation);

            Client client = clientRepository.clientFindById(Long.parseLong(jsonDataClient.getString("id")));
            client.setCi(jsonDataClient.getString("ci"));
            client.setNames(jsonDataClient.getString("names").toUpperCase());
            client.setAddress(jsonDataClient.getString("address").toUpperCase());
            client.setEmail(jsonDataClient.getString("email"));
            client.setCellPhone(Integer.parseInt(jsonDataClient.getString("number_cell_phone")));
            //TODO: Generar la lógica para parsear la fecha que llega en string
            client.setDateBirth(new Date());
            client.setLastNamesMaternal(jsonDataClient.getString("names_maternal").toUpperCase());
            client.setLastNamesPaternal(jsonDataClient.getString("names_paternal").toUpperCase());
            client.setPhone(Integer.parseInt(jsonDataClient.getString("number_phone")));
            client.setCity(city);
            client.setSexuality(sex);
            client.setOccupation(occupation);

            clientRepository.clientUpdate(client);
            jsonResponseClientUpdate.put("message", "CLIENT " + name.toUpperCase() + " HAS UPDATE");
            Response response = Response.ok(jsonResponseClientUpdate).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
