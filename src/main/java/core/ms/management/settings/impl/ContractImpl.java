package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.*;
import core.ms.management.settings.dao.repository.*;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@ApplicationScoped
public class ContractImpl {

    @Inject
    ContractRepository contractRepository;

    @Inject
    ClientRepository clientRepository;

    @Inject
    JewelRepository jewelRepository;

    @Inject
    BusinessDiscountsRepository businessDiscountsRepository;

    @Inject
    AgencyRepository agencyRepository;


    public Response contractListAll(){
        try{
            List<Contract> contractList = contractRepository.contractListAll();
            JsonArray jsonContract = new JsonArray(contractList);
            Response response = Response.ok(jsonContract).build();
            if(response.getStatus() == 200){
                if(contractList.isEmpty()){
                    JsonObject jsonResponseCityAll = new JsonObject();
                    jsonResponseCityAll.put("message", "No existe información registrada");
                    response = Response.ok(jsonResponseCityAll).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        }catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response contractFindById(JsonObject jsonDataContract) {
        try {
            JsonObject jsonContract = new JsonObject();
            long id = Long.parseLong(jsonDataContract.getString("id"));
            Contract contract = contractRepository.contractFindById(id);
            if (contract == null) {
                jsonContract.put("message", "No existe la información solicitada");
                return Response.ok(jsonContract).build();
            }
            JsonObject jsonArrayContractById = new JsonObject(Json.encode(contract));
            if (jsonArrayContractById.isEmpty()) {
                jsonContract.put("message", "No existe la información solicitada");
                return Response.ok(jsonContract).build();
            }
            Response response = Response.ok(jsonArrayContractById).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response contractSave(JsonObject jsonDataContract) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

            long idClient = Long.parseLong(jsonDataContract.getString("client_id"));
            long idJewel = Long.parseLong(jsonDataContract.getString("jewel_id"));
            long idAgency = Long.parseLong(jsonDataContract.getString("agency_id"));
            long idBusinessDiscount = Long.parseLong(jsonDataContract.getString("business_discount_id"));
            String dExpiration = jsonDataContract.getString("date_expiration");
            Date date_expiration = formatDate.parse(dExpiration);

            Agency agency = agencyRepository.agencyFindById(idAgency);
            Client client = clientRepository.clientFindById(idClient);
            Jewel jewel = jewelRepository.findById(idJewel);
            BusinessDiscounts businessDiscounts = businessDiscountsRepository.businessDiscountsFindById(idBusinessDiscount);

            Contract contract = new Contract();
            contract.currency = jsonDataContract.getInteger("currency");
            contract.status = jsonDataContract.getBoolean("status");
            contract.client = client;
            contract.agency = agency;
            contract.businessDiscounts = businessDiscounts;
            contract.jewel = jewel;
            contract.dateCreate = new Date();
            contract.dateExpiration = date_expiration;
            contract.value = jsonDataContract.getFloat("value");
            contract.userCreate = 0;

            contractRepository.contractSave(contract);

            JsonObject jsonResponseCreateContract = new JsonObject();
            jsonResponseCreateContract.put("message", "Contrato registrado correctamente");
            return Response.ok(jsonResponseCreateContract).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response contractDelete(JsonObject jsonDataContract) {
        try {
            JsonObject jsonResponseDeleteContract = new JsonObject();
            long id = Long.parseLong(jsonResponseDeleteContract.getString("id"));
            long responseDelete = contractRepository.contractDelete(id);

            if (responseDelete <= 0) {
                jsonResponseDeleteContract.put("message", "El contrato con Id: " + jsonDataContract.getString("id") +  " no existe");
                return Response.ok(jsonResponseDeleteContract).build();
            }
            jsonResponseDeleteContract.put("message", "El Contrato ha sido eliminado");
            return Response.ok(jsonResponseDeleteContract).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response contractUpdate(JsonObject jsonDataContract) {
        try {
            JsonObject jsonResponseUpdateContract = new JsonObject();

            Contract contract = new Contract();
            contract.id = jsonDataContract.getLong("id");
            contract.currency = jsonDataContract.getInteger("currency");
            contract.status = jsonDataContract.getBoolean("status");
            contract.dateUpdate = new Date();
            contract.value = jsonDataContract.getFloat("value");
            contract.userUpdate = 1;

            contractRepository.contractUpdate(contract);

            jsonResponseUpdateContract.put("message", "El contrato con Id: " + jsonResponseUpdateContract.getLong("id") + " ha sido actualizado");
            Response response = Response.ok(jsonResponseUpdateContract).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
