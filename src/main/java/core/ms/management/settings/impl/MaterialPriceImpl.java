package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.MaterialPrice;
import core.ms.management.settings.dao.repository.MaterialPriceRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class MaterialPriceImpl {

    @Inject
    MaterialPriceRepository materialPriceRepository;

    JsonObject jsonResponseFail = new JsonObject();
    public Response materialPriceListAll(){
        try {
            List<MaterialPrice> materialPriceListAll = materialPriceRepository.materialPriceListAll();
            JsonArray jsonArrayPrice = new JsonArray(materialPriceListAll);
            if(jsonArrayPrice.isEmpty()){
                jsonResponseFail.put("message","MATERIAL PRICE LIST EMPTY");
                return Response.ok(jsonResponseFail).build();
            }
            Response response = Response.ok(jsonArrayPrice).build();
            if(response.getStatus() == 200){
                return Response.ok(response.getEntity()).build();
            }
            jsonResponseFail.put("message","MATERIAL PRICE LIST EMPTY");
            return Response.ok(jsonResponseFail).build();
        }catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response materialPriceSave(JsonObject jsonDataMaterialPrice){
        try {
            MaterialPrice materialPrice = new MaterialPrice();
            materialPrice.setPrice(Float.parseFloat(jsonDataMaterialPrice.getString("price")));
            materialPrice.setDescription(jsonDataMaterialPrice.getString("description"));

            materialPriceRepository.materialPriceSave(materialPrice);

            JsonObject jsonResponseMaterialPriceSave = new JsonObject();
            jsonResponseMaterialPriceSave.put("message", "MATERIAL PRICE " + jsonDataMaterialPrice.getString("description").toUpperCase() + " CREATED");
            return Response.ok(jsonResponseMaterialPriceSave).build();
        } catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
    }
}
