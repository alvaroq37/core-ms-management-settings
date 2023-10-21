package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Classification;
import core.ms.management.settings.dao.repository.ClassificationRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class ClassificationImpl {

    @Inject
    ClassificationRepository classificationRepository;


    public Response classificationListAll(){
        List<Classification> classificationList = classificationRepository.ClassificationListAll();
        JsonArray jsonClassification = new JsonArray(classificationList);
        return Response.ok(jsonClassification).build();
    }

    public Response classificationSave(JsonObject jsonDataClassification) {
        try {

            Classification classification = new Classification();
            classification.setDescription(jsonDataClassification.getString("description"));
            classificationRepository.ClassificationSave(classification);
            JsonObject jsonResponseCreateCity = new JsonObject();
            jsonResponseCreateCity.put("message", "CLASSIFICATION " + jsonDataClassification.getString("description").toUpperCase() + " CREATED");
            return Response.ok(jsonResponseCreateCity).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }
}
