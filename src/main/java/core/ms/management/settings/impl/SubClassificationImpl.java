package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Classification;
import core.ms.management.settings.dao.entity.SubClassification;
import core.ms.management.settings.dao.repository.ClassificationRepository;
import core.ms.management.settings.dao.repository.SubClassificationRepository;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class SubClassificationImpl {

    @Inject
    SubClassificationRepository subClassificationRepository;

    @Inject
    ClassificationRepository classificationRepository;

    public Response subClassificationListAll(){
        try {
            return Response.ok(subClassificationRepository.SubClassificationListAll()).build();
        } catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response subClassificationSave(JsonObject jsonDataCity) {
        try {
            long id = Long.parseLong(jsonDataCity.getString("id"));
            Classification classification = classificationRepository.ClassificationFindById(id);

            SubClassification subClassification = new SubClassification();
            subClassification.setDescription(jsonDataCity.getString("description"));
            subClassification.setClassification(classification);
            subClassificationRepository.SubClassificationSave(subClassification);

            JsonObject jsonResponseCreateCity = new JsonObject();
            jsonResponseCreateCity.put("message", "SUBCLASSIFICATION " + jsonDataCity.getString("description").toUpperCase() + " CREATED");
            return Response.ok(jsonResponseCreateCity).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }
}
