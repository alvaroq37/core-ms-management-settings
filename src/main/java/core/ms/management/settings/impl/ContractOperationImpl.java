package core.ms.management.settings.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.Client;
import core.ms.management.settings.dao.entity.Contract;
import core.ms.management.settings.dao.entity.ContractOperation;
import core.ms.management.settings.dao.entity.TypeOperation;
import core.ms.management.settings.dao.repository.ClientRepository;
import core.ms.management.settings.dao.repository.ContractOperationRepository;
import core.ms.management.settings.dao.repository.ContractRepository;
import core.ms.management.settings.dao.repository.TypeOperationRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ContractOperationImpl {

    @Inject
    ContractOperationRepository contractOperationRepository;

    @Inject
    TypeOperationRepository typeOperationRepository;

    @Inject
    ClientRepository clientRepository;

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

    public Response searchContracts(JsonObject jsonDataOperation) throws JsonProcessingException {
        String numberDocument = jsonDataOperation.getString("numberDocument");
        String nameClient = jsonDataOperation.getString("nameClient");
        JsonObject jsonResponseOperations = new JsonObject();
        if(!numberDocument.isEmpty()){
            Client client = clientRepository.clientFindByCi(numberDocument);
            List<Contract> contractOperationList = contractRepository.contractFindByClient(client.id);
            JsonArray jsonArrayContractOperations = new JsonArray(contractOperationList);
            Response response = Response.ok(jsonArrayContractOperations).build();
            if(response.getStatus() == 200){
                if(jsonArrayContractOperations.isEmpty()){
                    jsonResponseOperations.put("message", "No existe información registrada");
                    response = Response.ok(jsonResponseOperations).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        }else{
            JsonArray jsonArrayNameClient = new JsonArray(clientRepository.contractFinByLastNamesPaternal(nameClient));
            JsonArray arrayContractsNameClients = new JsonArray();
            for (Object contractNameClient: jsonArrayNameClient){
                Client client = (Client) contractNameClient;
                JsonArray arrayNameClient = new JsonArray(contractRepository.contractFindByClient(client.id));
                for(Object contractClient: arrayNameClient){
                    ObjectMapper objectMapper = new ObjectMapper();
                    String mapperContracts = objectMapper.writeValueAsString(contractClient);
                    JsonObject finalArray = new JsonObject(mapperContracts);
                    arrayContractsNameClients.add(finalArray);
                }
            }

            Response response = Response.ok(arrayContractsNameClients).build();
            if(response.getStatus() == 200){
                if(arrayContractsNameClients.isEmpty()){
                    jsonResponseOperations.put("message", "No existe información registrada");
                    response = Response.ok(jsonResponseOperations).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        }
        return null;
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
        JsonObject jsonResponseCreateContract = new JsonObject();
    try{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JsonObject jsonContract = jsonDataOperation.getJsonObject("contract");
        JsonObject jsonTypeOperation = jsonDataOperation.getJsonObject("typeOperation");
        Long idContract = jsonContract.getLong("id");
        Long idTypeOperation = jsonTypeOperation.getLong("id");
        Date datePayment = new Date();
        Date dateStart = new Date();
        Date dateNextPayment = new Date();//simpleDateFormat.parse(jsonDataOperation.getString("nextExpirationDate"));
        Date dateCreate = new Date();

        Contract contract = contractRepository.contractFindById(idContract);
        TypeOperation typeOperation = typeOperationRepository.findById(idTypeOperation);

        contract.capitalBalance = jsonDataOperation.getDouble("capitalBalance");
        contractRepository.contractUpdate(contract);

        ContractOperation contractOperation = new ContractOperation();
        contractOperation.amount = Double.parseDouble(jsonDataOperation.getString("amount"));
        contractOperation.maximumRange = jsonDataOperation.getDouble("maximumRange");
        contractOperation.capitalAvailable = jsonDataOperation.getDouble("capitalAvailable");
        contractOperation.capitalBalance = jsonDataOperation.getDouble("capitalBalance");
        contractOperation.nextExpirationDate = dateNextPayment;
        contractOperation.datePayment = datePayment;
        contractOperation.dateStart = dateStart;
        contractOperation.daysPassed=jsonDataOperation.getInteger("daysPassed");
        contractOperation.dateCreate = dateCreate;
        contractOperation.foreignCurrencyDebtCustodyExpenses = jsonDataOperation.getDouble("foreignCurrencyDebtCustodyExpenses");
        contractOperation.localCurrencyDebtCustodyExpenses = jsonDataOperation.getDouble("localCurrencyDebtCustodyExpenses");
        contractOperation.foreignCurrencyCapitalAmortization = jsonDataOperation.getDouble("foreignCurrencyCapitalAmortization");
        contractOperation.localCurrencyCapitalAmortization = jsonDataOperation.getDouble("localCurrencyCapitalAmortization");
        contractOperation.foreignCurrencyExpirationServiceCost = jsonDataOperation.getDouble("foreignCurrencyExpirationServiceCost");
        contractOperation.localCurrencyExpirationServiceCost = jsonDataOperation.getDouble("localCurrencyExpirationServiceCost");
        contractOperation.foreignCurrencyInterestOperation = jsonDataOperation.getDouble("foreignCurrencyInterestOperation");
        contractOperation.foreignCurrencyPreviousBalance = jsonDataOperation.getDouble("foreignCurrencyPreviousBalance");
        contractOperation.foreignCurrencyNewCapitalBalance = jsonDataOperation.getDouble("foreignCurrencyNewCapitalBalance");
        contractOperation.foreignCurrencyCapitalAmortization = jsonDataOperation.getDouble("foreignCurrencyCapitalAmortization");
        contractOperation.localCurrencyInterestOperation = jsonDataOperation.getDouble("localCurrencyInterestOperation");
        contractOperation.localCurrencyPreviousBalance = jsonDataOperation.getDouble("localCurrencyPreviousBalance");
        contractOperation.localCurrencyNewCapitalBalance = jsonDataOperation.getDouble("localCurrencyNewCapitalBalance");
        contractOperation.foreignCurrencyInterestGeneral = jsonDataOperation.getDouble("foreignCurrencyInterestGeneral");
        contractOperation.localCurrencyInterestGeneral = jsonDataOperation.getDouble("localCurrencyInterestGeneral");
        contractOperation.contract = contract;
        contractOperation.typeOperation=typeOperation;
        contractOperationRepository.saveContractOperation(contractOperation);
        jsonResponseCreateContract.put("message", "Operación Registrada Correctamente");
        return Response.ok(jsonResponseCreateContract).build();
    } catch (Exception e) {
        return Response.accepted(jsonResponseCreateContract.put("message",e.getMessage())).build();
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
            JsonObject jsonTypeOperation = jsonDataOperation.getJsonObject("typeOperation");
            Long idContract = jsonContract.getLong("id");
            Long idTypeOperation = jsonTypeOperation.getLong("id");
            Date datePayment = new Date(jsonDataOperation.getLong("datePayment"));
            Date dateStart = new Date(jsonDataOperation.getLong("dateStart"));
            Date dateNextPayment = new Date(jsonDataOperation.getLong("dateNextPayment") + 30);

            Contract contract = contractRepository.contractFindById(idContract);
            TypeOperation typeOperation = typeOperationRepository.findById(idTypeOperation);
            ContractOperation contractOperation = new ContractOperation();
            contractOperation.id = jsonDataOperation.getLong("id");
            contractOperation.amount = Double.parseDouble(jsonDataOperation.getString("amount"));
            contractOperation.maximumRange = jsonDataOperation.getDouble("maximumRange");
            contractOperation.capitalAvailable = jsonDataOperation.getDouble("capitalAvailable");
            contractOperation.capitalBalance = jsonDataOperation.getDouble("capitalBalance");
            contractOperation.nextExpirationDate = dateNextPayment;
            contractOperation.datePayment = datePayment;
            contractOperation.dateStart = dateStart;
            contractOperation.daysPassed=jsonDataOperation.getInteger("daysPassed");
            contractOperation.foreignCurrencyDebtCustodyExpenses = jsonDataOperation.getDouble("foreignCurrencyDebtCustodyExpenses");
            contractOperation.localCurrencyDebtCustodyExpenses = jsonDataOperation.getDouble("localCurrencyDebtCustodyExpenses");
            contractOperation.foreignCurrencyCapitalAmortization = jsonDataOperation.getDouble("foreignCurrencyCapitalAmortization");
            contractOperation.localCurrencyCapitalAmortization = jsonDataOperation.getDouble("localCurrencyCapitalAmortization");
            contractOperation.foreignCurrencyExpirationServiceCost = jsonDataOperation.getDouble("foreignCurrencyExpirationServiceCost");
            contractOperation.localCurrencyExpirationServiceCost = jsonDataOperation.getDouble("localCurrencyExpirationServiceCost");
            contractOperation.foreignCurrencyInterestOperation = jsonDataOperation.getDouble("foreignCurrencyInterestOperation");
            contractOperation.foreignCurrencyPreviousBalance = jsonDataOperation.getDouble("foreignCurrencyPreviousBalance");
            contractOperation.foreignCurrencyNewCapitalBalance = jsonDataOperation.getDouble("foreignCurrencyNewCapitalBalance");
            contractOperation.foreignCurrencyCapitalAmortization = jsonDataOperation.getDouble("foreignCurrencyCapitalAmortization");
            contractOperation.localCurrencyInterestOperation = jsonDataOperation.getDouble("localCurrencyInterestOperation");
            contractOperation.localCurrencyPreviousBalance = jsonDataOperation.getDouble("localCurrencyPreviousBalance");
            contractOperation.localCurrencyNewCapitalBalance = jsonDataOperation.getDouble("localCurrencyNewCapitalBalance");
            contractOperation.foreignCurrencyInterestGeneral = jsonDataOperation.getDouble("foreignCurrencyInterestGeneral");
            contractOperation.localCurrencyInterestGeneral = jsonDataOperation.getDouble("localCurrencyInterestGeneral");
            contractOperation.contract = contract;
            contractOperation.typeOperation=typeOperation;
            contractOperationRepository.saveContractOperation(contractOperation);

            JsonObject jsonResponseCreateContract = new JsonObject();
            jsonResponseCreateContract.put("message", "Operación Actualizada Correctamente");
            return Response.ok(jsonResponseCreateContract).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }
}
