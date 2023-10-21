package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.JewelCategory;
import core.ms.management.settings.dao.repository.JewelCategoryRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class JewelCategoryImpl {

    @Inject
    JewelCategoryRepository jewelCategoryRepository;

    public Response jewelCategoryListAll(){
        List<JewelCategory> jewelCategoryList = jewelCategoryRepository.jewelCategoryListAll();
        JsonArray jsonArrayJewelCategory = new JsonArray(jewelCategoryList);
        return Response.ok(jsonArrayJewelCategory).build();
    }

    public Response jewelCategorySave(JsonObject jsonDataJewelCategory){
        try {
            JewelCategory jewelCategory = new JewelCategory();

            jewelCategory.setDescription(jsonDataJewelCategory.getString("description"));
            jewelCategoryRepository.jewelCategorySave(jewelCategory);
            JsonObject jsonResponseJewelCategorySave = new JsonObject();
            jsonResponseJewelCategorySave.put("message", "JEWEL CATEGORY " + jsonDataJewelCategory.getString("description").toUpperCase() + " CREATED");
            return Response.ok(jsonResponseJewelCategorySave).build();
        } catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
    }
}
