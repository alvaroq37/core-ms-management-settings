package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Currency;
import core.ms.management.settings.dao.repository.CurrencyRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class CurrencyImpl {
    
    @Inject
    CurrencyRepository currencyRepository;

    public Response currencyListAll() {
        try {
            List<Currency> currencyList = currencyRepository.currencyListAll();
            JsonArray jsoncurrency = new JsonArray(currencyList);
            Response response = Response.ok(jsoncurrency).build();
            if (response.getStatus() == 200) {
                if (currencyList.isEmpty()) {
                    JsonObject jsonResponseCurrencyAll = new JsonObject();
                    jsonResponseCurrencyAll.put("message", "No existe información registrada");
                    response = Response.ok(jsonResponseCurrencyAll).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response currencyFindById(JsonObject jsonDataCurrency) {
        try {
            JsonObject jsonCurrency = new JsonObject();
            long id = Long.parseLong(jsonDataCurrency.getString("id"));
            Currency currency = currencyRepository.currencyFindById(id);

            if (currency == null) {
                jsonCurrency.put("message", "No existe la información solicitada");
                return Response.ok(jsonCurrency).build();
            }
            JsonObject jsonArrayCurrencyById = new JsonObject(Json.encode(currency));
            if (jsonArrayCurrencyById.isEmpty()) {
                jsonCurrency.put("message", "No existe la información solicitada");
                return Response.ok(jsonCurrency).build();
            }
            Response response = Response.ok(jsonArrayCurrencyById).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response currencySave(JsonObject jsonDataCurrency) {
        try {
            Currency currency = new Currency();
            currency.description = jsonDataCurrency.getString("description");
            currency.abbreviation = jsonDataCurrency.getString("abbreviation");
            currency.user_create = jsonDataCurrency.getInteger("user_create");
            currency.dateCreate = new Date();
            currencyRepository.currencySave(currency);
            JsonObject jsonResponseCreateCurrency = new JsonObject();
            jsonResponseCreateCurrency.put("message", "La moneda " + jsonDataCurrency.getString("description") + " ha sido registrada");
            return Response.ok(jsonResponseCreateCurrency).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response currencyDelete(JsonObject jsonDataCurrency) {
        try {
            JsonObject jsonResponseDeleteCurrency = new JsonObject();
            long id = Long.parseLong(jsonDataCurrency.getString("id"));
            long responseDelete = currencyRepository.currencyDelete(id);

            if (responseDelete <= 0) {
                jsonDataCurrency.put("message", "La moneda: " + jsonDataCurrency.getString("description") + " no existe");
                return Response.ok(jsonDataCurrency).build();
            }
            jsonDataCurrency.put("message", "La moneda " + jsonDataCurrency.getString("description") + " ha sido eliminada");
            return Response.ok(jsonDataCurrency).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response currencyUpdate(JsonObject jsonDataCurrency) {
        try {
            JsonObject jsonResponseUpdateCurrency= new JsonObject();
            Currency currency = new Currency();
            currency.id = jsonDataCurrency.getLong("id");
            currency.description = jsonDataCurrency.getString("description");
            currency.abbreviation = jsonDataCurrency.getString("abbreviation");
            currency.user_create = jsonDataCurrency.getInteger("user_create");
            currency.dateCreate = new Date();
            currencyRepository.currencySave(currency);
            jsonResponseUpdateCurrency.put("message", "La moneda " + jsonResponseUpdateCurrency.getString("description").toUpperCase() + " ha sido actualizada");
            Response response = Response.ok(jsonResponseUpdateCurrency).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
