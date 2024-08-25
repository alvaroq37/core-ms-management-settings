package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Material;
import core.ms.management.settings.dao.repository.MaterialRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class MaterialImpl {

    @Inject
    MaterialRepository materialRepository;

    JsonObject jsonResponse = new JsonObject();
    public Response materialListAll(){
        try {
            List<Material> materialList =materialRepository.materialListAll();
            JsonArray jsonArrayMaterial = new JsonArray(materialList);
            if(jsonArrayMaterial.isEmpty()){
                jsonResponse.put("message","No existen materiales disponibles");
                return Response.ok(jsonResponse).build();
            }
            Response response = Response.ok(jsonArrayMaterial).build();
            if(response.getStatus() == 200){
                return Response.ok(response.getEntity()).build();
            }
            return Response.ok(jsonResponse.put("message", "Servicio no disponible")).build();
        }catch (Exception e){
            return Response.ok(jsonResponse.put("message", e.getMessage())).build();
        }
    }

    public Response materialSave(JsonObject jsonDataMaterial){
        try {
            Material material = new Material();
            material.description = jsonDataMaterial.getString("description");
            material.karat = Integer.parseInt(jsonDataMaterial.getString("karat"));
            material.price = Integer.parseInt(jsonDataMaterial.getString("price"));
            material.status = jsonDataMaterial.getBoolean("status");
            material.dateCreate = new Date();
            material.userCreate = jsonDataMaterial.getInteger("user_create");
            material.userUpdate = jsonDataMaterial.getInteger("user_update");
            materialRepository.materialSave(material);

            jsonResponse.put("message", "Material " + jsonDataMaterial.getString("description") + " registrado correctamente");
            return Response.ok(jsonResponse).build();
        }catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
    }
}
