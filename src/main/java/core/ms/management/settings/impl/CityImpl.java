package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.City;
import core.ms.management.settings.dao.entity.Country;
import core.ms.management.settings.dao.repository.CityRepository;
import core.ms.management.settings.dao.repository.CountryRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class CityImpl {

    @Inject
    CityRepository cityRepository;

    @Inject
    CountryRepository countryRepository;

    public Response cityListAll() {
        try {
            List<City> cityListAll = cityRepository.cityListAll();
            JsonArray jsonArrayCityAll = new JsonArray(cityListAll);
            Response response = Response.ok(jsonArrayCityAll).build();
            if (response.getStatus() == 200) {
                if (cityListAll.isEmpty()) {
                    JsonObject jsonResponseCityAll = new JsonObject();
                    jsonResponseCityAll.put("message", "LIST CITY IS EMPTY");
                    response = Response.ok(jsonResponseCityAll).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response cityFindById(JsonObject jsonDataCity) {
        try {
            JsonObject jsonCityFindById = new JsonObject();
            long id = Long.parseLong(jsonDataCity.getString("id"));
            City cityById = cityRepository.cityFindById(id);
            if (cityById == null) {
                jsonCityFindById.put("message", "FIND CITY BY ID NOT EXISTS");
                return Response.ok(jsonCityFindById).build();
            }
            JsonObject jsonArrayCityById = new JsonObject(Json.encode(cityById));
            if (jsonArrayCityById.isEmpty()) {
                jsonCityFindById.put("message", "FIND CITY BY NAME IS EMPTY");
                return Response.ok(jsonCityFindById).build();
            }
            Response response = Response.ok(jsonArrayCityById).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response cityFindByName(JsonObject jsonDataCity) {
        try {
            JsonObject jsonCityName = new JsonObject();
            String name = jsonDataCity.getString("name");
            City cityName = cityRepository.cityFindByName(name);
            if (cityName == null) {
                jsonCityName.put("message", "FIND CITY BY NAME NOT EXISTS");
                return Response.ok(jsonCityName).build();
            }
            JsonObject jsonResponseCityName = new JsonObject(Json.encode(cityName));
            if (jsonResponseCityName.isEmpty()) {
                jsonCityName.put("message", "REQUEST CITY BY NAME IS EMPTY");
                return Response.ok(jsonCityName).build();
            }
            Response response = Response.ok(jsonResponseCityName).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response citySave(JsonObject jsonDataCity) {
        try {
            long id = Long.parseLong(jsonDataCity.getString("id"));
            Country country = countryRepository.countryFindById(id);
            City city = new City();
            city.setName(jsonDataCity.getString("name").toUpperCase());
            city.setCountry(country);
            cityRepository.citySave(city);
            JsonObject jsonResponseCreateCity = new JsonObject();
            jsonResponseCreateCity.put("message", "CITY " + jsonDataCity.getString("name").toUpperCase() + " CREATED");
            return Response.ok(jsonResponseCreateCity).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response cityDelete(JsonObject jsonDataCity) {
        try {
            JsonObject jsonResponseDeleteCity = new JsonObject();
            long id = Long.parseLong(jsonDataCity.getString("id"));
            long responseDelete = cityRepository.cityDelete(id);

            if (responseDelete <= 0) {
                jsonResponseDeleteCity.put("message", "CITY: " + jsonDataCity.getString("name").toUpperCase() + " ID: "+ jsonDataCity.getString("id") + " NOT EXISTS");
                return Response.ok(jsonResponseDeleteCity).build();
            }
            jsonResponseDeleteCity.put("message", "CITY " + jsonDataCity.getString("name").toUpperCase() + " DELETE");
            return Response.ok(jsonResponseDeleteCity).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response cityUpdate(JsonObject jsonDataCity) {
        try {
            JsonObject jsonResponseCityUpdate = new JsonObject();
            long id = Long.parseLong(jsonDataCity.getString("id"));
            long idCountry = Long.parseLong(jsonDataCity.getString("idCountry"));
            City city = cityRepository.cityFindById(id);
            Country country = countryRepository.countryFindById(idCountry);
            if (city == null || country == null) {
                jsonResponseCityUpdate.put("message", "CITY " + jsonDataCity.getString("name").toUpperCase() + " NOT EXISTS");
                return Response.ok(jsonResponseCityUpdate).build();
            }
            city.setName(jsonDataCity.getString("name").toUpperCase());
            city.setCountry(country);
            cityRepository.cityUpdate(city);
            jsonResponseCityUpdate.put("message", "CITY " + jsonDataCity.getString("name").toUpperCase() + " HAS UPDATE");
            Response response = Response.ok(jsonResponseCityUpdate).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
