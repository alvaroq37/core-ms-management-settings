package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Jewel;
import core.ms.management.settings.dao.entity.JewelType;
import core.ms.management.settings.dao.entity.Material;
import core.ms.management.settings.dao.repository.JewelRepository;
import core.ms.management.settings.dao.repository.JewelTypeRepository;
import core.ms.management.settings.dao.repository.MaterialRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class JewelImpl{

    @Inject
    JewelRepository jewelRepository;

    @Inject
    MaterialRepository materialRepository;

    @Inject
    JewelTypeRepository jewelTypeRepository;

    public Response jewelListAll (){
        List<Jewel> jewelList = jewelRepository.jewelList();
        JsonArray jewelArray = new JsonArray(jewelList);
        Response response = Response.ok(jewelArray).build();
        if(response.getStatus() == 200){
            return Response.ok(response.getEntity()).build();
        }
        return Response.noContent().build();
    }

    public Response jewelFindById(JsonObject jsonJewel){
        try{
            Long id = Long.parseLong(jsonJewel.getString("id"));
            List<Jewel> jewel = jewelRepository.jewelFindById(id);
            JsonArray jewelArray = new JsonArray(jewel);
            Response response = Response.ok(jewelArray).build();
            if(response.getStatus() == 200){
                return Response.ok(response.getEntity()).build();
            }
            return Response.noContent().build();

        }catch (Exception e){
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response jewelSave(JsonObject jsonJewel){
        try {
            JsonObject jsonMaterial = jsonJewel.getJsonObject("material");
            JsonObject jsonJewelType = jsonJewel.getJsonObject("jewel_type");
            Long material_id = jsonMaterial.getLong("id");
            Long jewel_type_id = jsonJewelType.getLong("id");
            Material material = materialRepository.materialFindById(material_id);
            JewelType jewelType = jewelTypeRepository.findByIdJewelType(jewel_type_id);

            Jewel jewel = new Jewel();
            jewel.jewelType = jewelType;
            jewel.jewel = jsonJewel.getString("jewel");
            jewel.grossWeight = Double.parseDouble(jsonJewel.getString("gross_weight"));
            jewel.netWeight = Double.parseDouble(jsonJewel.getString("net_weight"));
            jewel.netWeightLoan = Double.parseDouble(jsonJewel.getString("net_weight_loan"));
            jewel.maximumRange = Double.parseDouble(jsonJewel.getString("maximum_range"));
            jewel.agreedAmount = Double.parseDouble(jsonJewel.getString("agreed_amount"));
            jewel.description = jsonJewel.getString("description");
            jewel.numberParts = Long.parseLong(jsonJewel.getString("number_parts"));
            jewel.dateCreate = new Date();
            jewel.userCreate = 1;
            jewel.material = material;

            jewelRepository.jewelSave(jewel);

            JsonObject jsonResponseCountrySave = new JsonObject();
            jsonResponseCountrySave.put("message", "Joya registrada");
            return Response.ok(jsonResponseCountrySave).build();
        }
        catch (Exception e){
            return Response.accepted(e.getMessage()).build();
        }
    }
}
