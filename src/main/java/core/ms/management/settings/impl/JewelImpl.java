package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Jewel;
import core.ms.management.settings.dao.entity.Material;
import core.ms.management.settings.dao.repository.JewelRepository;
import core.ms.management.settings.dao.repository.MaterialRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class JewelImpl{

    @Inject
    JewelRepository jewelRepository;

    @Inject
    MaterialRepository materialRepository;

    public Response jewelListAll (){
        List<Jewel> jewelList = jewelRepository.jewelList();
        JsonArray jewelArray = new JsonArray(jewelList);
        Response response = Response.ok(jewelArray).build();
        if(response.getStatus() == 200){
            return Response.ok(response.getEntity()).build();
        }
        return Response.noContent().build();
    }

    public Response jewelSave(JsonObject jsonJewel){
        try {
            long material_id = Long.parseLong(jsonJewel.getString("material_id"));
            Material material = materialRepository.findById(material_id);

            Jewel jewel = new Jewel();
            jewel.id = 0L;
            jewel.description = jsonJewel.getString("description");
            jewel.material = material;

            jewelRepository.jewelSave(jewel);

            JsonObject jsonResponseCountrySave = new JsonObject();
            jsonResponseCountrySave.put("message", "JEWEL CREATED");
            return Response.ok(jsonResponseCountrySave).build();
        }
        catch (Exception e){
            return Response.accepted(e.getMessage()).build();
        }
    }
}
