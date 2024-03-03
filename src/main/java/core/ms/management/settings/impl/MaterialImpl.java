package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Material;
import core.ms.management.settings.dao.repository.MaterialRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class MaterialImpl {

    @Inject
    MaterialRepository materialRepository;

    JsonObject jsonResponseFail = new JsonObject();
    public Response materialListAll(){
        try {
            List<Material> materialList =materialRepository.materialListAll();
            JsonArray jsonArrayMaterial = new JsonArray(materialList);
            if(jsonArrayMaterial.isEmpty()){
                jsonResponseFail.put("message","MATERIAL LIST EMPTY");
                return Response.ok(jsonResponseFail).build();
            }
            Response response = Response.ok(jsonArrayMaterial).build();
            if(response.getStatus() == 200){
                return response;
            }
            jsonResponseFail.put("message","SERVICE NOT AVAILABLE");
            return Response.ok(jsonResponseFail).build();
        }catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response materialSave(JsonObject jsonDataMaterial){
        try {

            Material material = new Material();
            material.description = jsonDataMaterial.getString("description");
            material.karat = jsonDataMaterial.getInteger("karat");
            material.status = jsonDataMaterial.getBoolean("status");

            materialRepository.materialSave(material);

            JsonObject jsonResponseMaterialSave = new JsonObject();
            jsonResponseMaterialSave.put("message", "MATERIAL " + jsonDataMaterial.getString("description").toUpperCase() + " CREATED");
            return Response.ok(jsonResponseMaterialSave).build();
        }catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
    }
}
