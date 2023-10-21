package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Karat;
import core.ms.management.settings.dao.repository.KaratRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class KaratImpl {

    @Inject
    KaratRepository karatRepository;

    JsonObject jsonResponseFail = new JsonObject();
    public Response karatListAll(){
        try {
            List<Karat> karatList = karatRepository.karatListAll();
            if(karatList.isEmpty()){
                jsonResponseFail.put("message","LIST KARAT EMPTY");
                return Response.ok(jsonResponseFail).build();
            }
            JsonArray jsonArrayKarat = new JsonArray(karatList);
            Response response = Response.ok(jsonArrayKarat).build();
            if(response.getStatus() == 200){
                return Response.ok(response.getEntity()).build();
            }
            jsonResponseFail.put("message","LIST KARAT EMPTY");
            return Response.ok(jsonResponseFail).build();
        } catch (Exception e){
            jsonResponseFail.put("message", e.getMessage());
            return Response.ok(jsonResponseFail).build();
        }
    }

    public Response karatSave(JsonObject jsonDataKarat){
        String description = jsonDataKarat.getString("description");
        int value = Integer.parseInt(jsonDataKarat.getString("value"));

        Karat karat = new Karat();
        karat.setDescription(description);
        karat.setValue(value);

        karatRepository.karatSave(karat);

        JsonObject jsonResponseKaratSave = new JsonObject();
        jsonResponseKaratSave.put("message", "KARAT " + description.toUpperCase() + " CREATED");
        return Response.ok(jsonResponseKaratSave).build();
    }
}
