package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Contract;
import core.ms.management.settings.dao.entity.TypeOperation;
import core.ms.management.settings.dao.repository.ContractRepository;
import core.ms.management.settings.dao.repository.TypeOperationRepository;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TypeOperationImpl {

    @Inject
    TypeOperationRepository typeOperationRepository;

    public Response typeOperationSave(JsonObject jsonData){
        try {
            TypeOperation typeOperation = new TypeOperation();
            typeOperation.id=0L;
            typeOperation.description=jsonData.getString("description");

            typeOperationRepository.typeOperationSave(typeOperation);
            JsonObject jsonResponseCreateTypeOperation = new JsonObject();
            jsonResponseCreateTypeOperation.put("message", "Tipo de Operacion registrada correctamente");
            return Response.ok(jsonResponseCreateTypeOperation).build();
        }catch (Exception e){
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response typeOperationDelete(JsonObject jsonData) {
        try {
            JsonObject jsonResponseDeleteTypeOperation = new JsonObject();
            long id = Long.parseLong(jsonData.getString("id"));
            Long responseDelete = typeOperationRepository.typeOperationDelete(id);

            if (responseDelete <= 0) {
                jsonResponseDeleteTypeOperation.put("message", "El tipo de operacion con Id: " + jsonData.getString("id") +  " no existe");
                return Response.ok(jsonResponseDeleteTypeOperation).build();
            }
            jsonResponseDeleteTypeOperation.put("message", "El tipo de operacion ha sido eliminada");
            return Response.ok(jsonResponseDeleteTypeOperation).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response typeOperationUpdate(JsonObject jsonData){
        try {
            TypeOperation typeOperation = new TypeOperation();
            typeOperation.id=jsonData.getLong("id_type_operation");
            typeOperation.description=jsonData.getString("description");

            typeOperationRepository.typeOperationUpdate(typeOperation);
            JsonObject jsonResponseCreateTypeOperation = new JsonObject();
            jsonResponseCreateTypeOperation.put("message", "Tipo de Operacion actualizada correctamente");
            return Response.ok(jsonResponseCreateTypeOperation).build();
        }catch (Exception e){
            return Response.accepted(e.getMessage()).build();
        }
    }

}
