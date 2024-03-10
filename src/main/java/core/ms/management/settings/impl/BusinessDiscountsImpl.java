package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.BusinessDiscounts;
import core.ms.management.settings.dao.repository.BusinessDiscountsRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class BusinessDiscountsImpl {

    @Inject
    BusinessDiscountsRepository businessDiscountsRepository;

    public Response businessDiscountListAll(){
        try {
            List<BusinessDiscounts> businessDiscountsList = businessDiscountsRepository.businessDiscountsListAll();
            JsonArray jsonArrayCityAll = new JsonArray(businessDiscountsList);
            Response response = Response.ok(jsonArrayCityAll).build();
            if (response.getStatus() == 200) {
                if (businessDiscountsList.isEmpty()) {
                    JsonObject jsonResponseCityAll = new JsonObject();
                    jsonResponseCityAll.put("message", "Lista de descuentos vacía");
                    response = Response.ok(jsonResponseCityAll).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();

    }

    public Response saveBusinessDiscount(JsonObject jsonBusinessDiscount) {
        try {
            BusinessDiscounts businessDiscounts = new BusinessDiscounts();
            businessDiscounts.description = jsonBusinessDiscount.getString("description").toUpperCase();
            businessDiscounts.value = Float.parseFloat(jsonBusinessDiscount.getString("value"));
            businessDiscounts.status = jsonBusinessDiscount.getBoolean("status");
            businessDiscountsRepository.saveBusinessDiscount(businessDiscounts);
            JsonObject jsonResponseCreateCity = new JsonObject();
            jsonResponseCreateCity.put("message", "Descuento " + jsonBusinessDiscount.getString("description") + " registrado");
            return Response.ok(jsonResponseCreateCity).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }
}
