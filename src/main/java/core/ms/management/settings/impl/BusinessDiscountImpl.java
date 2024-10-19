package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.BusinessDiscount;
import core.ms.management.settings.dao.repository.BusinessDiscountRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;

@ApplicationScoped
public class BusinessDiscountImpl {

    @Inject
    BusinessDiscountRepository businessDiscountRepository;

    JsonObject jsonResponse = new JsonObject();

    public Response businessDiscountListAll(){
        try {
            JsonArray jsonArrayCityAll = new JsonArray(businessDiscountRepository.businessDiscountsListAll());
            if (jsonArrayCityAll.isEmpty()) {
                JsonObject jsonResponseCityAll = new JsonObject();
                jsonResponseCityAll.put("message", "No existen descuentos registrados");
                return Response.ok(jsonResponseCityAll).build();
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

    public Response saveBusinessDiscount(JsonObject jsonBusinessDiscount) {
        try {
            if(!jsonBusinessDiscount.isEmpty() && !jsonBusinessDiscount.getString("description").isEmpty() && Float.parseFloat(jsonBusinessDiscount.getString("value")) <= 0){
                BusinessDiscount businessDiscount = new BusinessDiscount();
                businessDiscount.description = jsonBusinessDiscount.getString("description");
                businessDiscount.value = Float.parseFloat(jsonBusinessDiscount.getString("value"));
                businessDiscount.status = jsonBusinessDiscount.getBoolean("status");
                businessDiscount.dateCreate = new Date();
                businessDiscountRepository.saveBusinessDiscount(businessDiscount);
                jsonResponse.put("message", "Descuento " + jsonBusinessDiscount.getString("description") + " registrado correctamente");
                return Response.ok(jsonResponse).build();
            }else{
                return Response.ok(jsonResponse.put("message","No se cuenta con datos para realizar el registro del descuento")).build();
            }
        } catch (Exception e) {
            return Response.accepted(jsonResponse.put("message",e.getMessage())).build();
        }
    }
}
