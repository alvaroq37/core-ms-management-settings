package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.Contract;
import core.ms.management.settings.dao.entity.ContractOperation;
import core.ms.management.settings.dao.repository.ContractOperationRepository;
import core.ms.management.settings.dao.repository.ContractRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.Date;

@ApplicationScoped
public class ContractOperationImpl {

    @Inject
    ContractOperationRepository contractOperationRepository;

    @Inject
    ContractRepository contractRepository;

    public Response listAllContractOperation(){
        try{
        JsonArray arrayOperations = new JsonArray(contractOperationRepository.listAllContractOperation());
        Response response = Response.ok(arrayOperations).build();
        if(response.getStatus() == 200){
            if(arrayOperations.isEmpty()){
                JsonObject jsonResponseOperations = new JsonObject();
                jsonResponseOperations.put("message", "No existe información registrada");
                response = Response.ok(jsonResponseOperations).build();
            }
            return Response.ok(response.getEntity()).build();
        }
    }catch (Exception e){
        return Response.ok(e.getMessage()).build();
    }
        return Response.serverError().build();
    }

    public Response listContractOperationById(JsonObject jsonDataOperation){
        try{
            Long id = jsonDataOperation.getLong("id");
            JsonArray arrayOperations = new JsonArray(contractOperationRepository.listContractOperationById(id));
            Response response = Response.ok(arrayOperations).build();
            if(response.getStatus() == 200){
                if(arrayOperations.isEmpty()){
                    JsonObject jsonResponseOperations = new JsonObject();
                    jsonResponseOperations.put("message", "No existe información registrada");
                    response = Response.ok(jsonResponseOperations).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        }catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response listContractOperationByIdContract(JsonObject jsonDataOperation){
        try{
            Long contract_id = jsonDataOperation.getLong("contract_id");
            JsonArray arrayOperations = new JsonArray(contractOperationRepository.listContractOperationByIdContract(contract_id));
            Response response = Response.ok(arrayOperations).build();
            if(response.getStatus() == 200){
                if(arrayOperations.isEmpty()){
                    JsonObject jsonResponseOperations = new JsonObject();
                    jsonResponseOperations.put("message", "No existe información registrada");
                    response = Response.ok(jsonResponseOperations).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        }catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response saveContractOperation(JsonObject jsonDataOperation){
    try{
        JsonObject jsonContract = jsonDataOperation.getJsonObject("contract");
        Long id = jsonContract.getLong("id");
        Date datePayment = new Date(jsonDataOperation.getLong("date_payment"));
        Date dateNextPayment = new Date(jsonDataOperation.getLong("date_next_payment") + 30);

        Contract contract = contractRepository.contractFindById(id);
        ContractOperation contractOperation = new ContractOperation();
        contractOperation.capitalAvailable = jsonDataOperation.getDouble("capital_available");
        contractOperation.typeOperation = jsonDataOperation.getLong("type_operation");
        contractOperation.capitalBalance = jsonDataOperation.getDouble("capital_available");
        contractOperation.date_next_payment = dateNextPayment;
        contractOperation.datePayment = datePayment;
        contractOperation.contract = contract;
        contractOperationRepository.saveContractOperation(contractOperation);

        JsonObject jsonResponseCreateContract = new JsonObject();
        jsonResponseCreateContract.put("message", "Operación Registrada Correctamente");
        return Response.ok(jsonResponseCreateContract).build();
    } catch (Exception e) {
        return Response.accepted(e.getMessage()).build();
    }
    }

    public Response deleteContractOperation(JsonObject jsonDataOperation) {
        try {
            JsonObject jsonResponseDeleteContract = new JsonObject();
            long id = Long.parseLong(jsonDataOperation.getString("id"));
            long responseDelete = contractOperationRepository.deleteContractOperation(id);

            if (responseDelete <= 0) {
                jsonResponseDeleteContract.put("message", "La operación de contrato con Id: " + jsonDataOperation.getString("id") +  " no existe");
                return Response.ok(jsonResponseDeleteContract).build();
            }
            jsonResponseDeleteContract.put("message", "La operación de contrato con Id: " + jsonDataOperation.getString("id") +  " ha sido eliminada");
            return Response.ok(jsonResponseDeleteContract).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response updateContractOperation(JsonObject jsonDataOperation){
        try{
            JsonObject jsonContract = jsonDataOperation.getJsonObject("contract");
            Long id = jsonContract.getLong("id");
            Date datePayment = new Date(jsonDataOperation.getLong("date_payment"));
            Date dateNextPayment = new Date(jsonDataOperation.getLong("date_next_payment") + 30);
            Contract contract = contractRepository.contractFindById(id);

            ContractOperation contractOperation = new ContractOperation();
            contractOperation.id = jsonDataOperation.getLong("id");
            contractOperation.capitalAvailable = jsonDataOperation.getDouble("capital_available");
            contractOperation.typeOperation = jsonDataOperation.getLong("type_operation");
            contractOperation.capitalBalance = jsonDataOperation.getDouble("capital_available");
            contractOperation.date_next_payment = dateNextPayment;
            contractOperation.datePayment = datePayment;
            contractOperation.contract = contract;
            contractOperationRepository.updateContractOperation(contractOperation);

            JsonObject jsonResponseCreateContract = new JsonObject();
            jsonResponseCreateContract.put("message", "Operación Actualizada Correctamente");
            return Response.ok(jsonResponseCreateContract).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }
}
