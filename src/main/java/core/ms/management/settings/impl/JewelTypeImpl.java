package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.JewelType;
import core.ms.management.settings.dao.repository.JewelTypeRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;

@ApplicationScoped
public class JewelTypeImpl {

    @Inject
    JewelTypeRepository jewelTypeRepository;

    JsonObject jsonResponse = new JsonObject();

    JsonArray arrayJewelType = null;

    ObjectMapper mapper = new ObjectMapper();

    public Response listAllJewelType(){

        try{
            arrayJewelType = new JsonArray(jewelTypeRepository.listAllJewelType());
            Response response = Response.ok(arrayJewelType).build();
            if(response.getStatus() == 200){
                return Response.ok(response.getEntity()).build();
            }
        }catch(Exception e){
            Response.accepted(jsonResponse.put("message", e.getMessage())).build();
        }
        return Response.noContent().build();
    }

    public Response findByIdJewelType(JsonObject jsonDataJewelType){
        try{
            Long id = jsonDataJewelType.getLong("id");
            JewelType jewelType = jewelTypeRepository.findByIdJewelType(id);
            JsonObject jsonJewelType = new JsonObject(mapper.writeValueAsString(jewelType));
            Response response = Response.ok(jsonJewelType).build();
            if(response.getStatus()==200){
                return Response.ok(response.getEntity()).build();
            }
        }catch (Exception e){
            Response.accepted(jsonResponse.put("message", e.getMessage())).build();
        }
        return Response.noContent().build();
    }

    public Response saveJewelType(JsonObject jsonDataJewelType){
        try{
            JewelType jewelType = new JewelType();
            jewelType.description = jsonDataJewelType.getString("description");
            jewelType.marketValue = jsonDataJewelType.getLong("marketValue");
            jewelType.dateCreate = new Date();
            jewelType.userCreate = 0;

        }catch(Exception e){
            Response.accepted(jsonResponse.put("message", e.getMessage())).build();
        }
        return Response.noContent().build();
    }
}
