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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ContractImpl {

    @Inject
    ContractRepository contractRepository;

    @Inject
    ClientRepository clientRepository;

    @Inject
    JewelRepository jewelRepository;

    @Inject
    JewelTypeRepository jewelTypeRepository;

    @Inject
    BusinessDiscountRepository businessDiscountRepository;

    @Inject
    AgencyRepository agencyRepository;

    @Inject
    CurrencyRepository currencyRepository;

    @Inject
    MaterialRepository materialRepository;

    @Inject
    LoanTypeRepository loanTypeRepository;


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

            JsonObject jsonClient = jsonDataContract.getJsonObject("client");
            JsonObject jsonAgency = jsonDataContract.getJsonObject("agency");
            JsonObject jsonDiscount = jsonDataContract.getJsonObject("business_discount");
            JsonObject jsonCurrency = jsonDataContract.getJsonObject("currency");
            JsonObject jsonLoanType = jsonDataContract.getJsonObject("loan_type");
            Long idClient = jsonClient.getLong("id");
            Long idAgency = jsonAgency.getLong("id");
            Long idBusinessDiscount = jsonDiscount.getLong("id");
            Long idCurrency = jsonCurrency.getLong("id");
            Long idLoanType= jsonLoanType.getLong("id");

            Date date_expiration = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date_expiration);
            calendar.add(Calendar.DAY_OF_YEAR, 30);
            date_expiration = calendar.getTime();

            Agency agency = agencyRepository.agencyFindById(idAgency);
            Client client = clientRepository.clientFindById(idClient);
            BusinessDiscount businessDiscount = businessDiscountRepository.businessDiscountsFindById(idBusinessDiscount);
            Currency currency =currencyRepository.currencyFindById(idCurrency);
            LoanType loanType = loanTypeRepository.loanTypeFindById(idLoanType);

            Contract contract = new Contract();
            contract.status = jsonDataContract.getBoolean("status");
            contract.rateInterest =Double.parseDouble(jsonDataContract.getString("rate_interest"));
            contract.capitalBalance= Double.parseDouble(jsonDataContract.getString("capital_balance"));
            contract.client = client;
            contract.agency = agency;
            contract.businessDiscount = businessDiscount;
            contract.loanType = loanType;
            contract.currency = currency;
            contract.dateCreate = new Date();
            contract.dateExpiration = date_expiration;
            contract.maximumRange = Double.parseDouble(jsonDataContract.getString("maximum_range"));
            contract.availableCapital = Double.parseDouble(jsonDataContract.getString("available_capital"));
            contract.loanAmount = Double.parseDouble(jsonDataContract.getString("loan_amount"));
            contract.userCreate = 0;
            contract.status = true;
            Contract contractPersist = contractRepository.contractSave(contract);

            JsonArray jsonArrayJewel = jsonDataContract.getJsonArray("jewel");

            jsonArrayJewel.stream().forEach(j -> {
                JsonObject jewel = (JsonObject) j;
                Jewel jewelPersist = new Jewel();
                jewelPersist.jewel = jewel.getString("jewel");
                jewelPersist.grossWeight = Double.parseDouble(jewel.getString("gross_weight"));
                jewelPersist.netWeight = Double.parseDouble(jewel.getString("net_weight"));
                jewelPersist.netWeightLoan = Double.parseDouble(jewel.getString("net_weight_loan"));
                jewelPersist.maximumRange = Double.parseDouble(jewel.getString("maximum_range"));
                jewelPersist.agreedAmount = Double.parseDouble(jewel.getString("agreed_amount"));
                jewelPersist.description = jewel.getString("description");
                jewelPersist.numberParts = Long.parseLong(jewel.getString("number_parts"));

                JsonObject jsonMaterial = jewel.getJsonObject("material");
                JsonObject jsonJewelType = jewel.getJsonObject("jewel_type");
                Long idMaterial = jsonMaterial.getLong("id");
                Long idJewelType = jsonJewelType.getLong("id");
                jewelPersist.material = materialRepository.materialFindById(idMaterial);
                jewelPersist.jewelType = jewelTypeRepository.findByIdJewelType(idJewelType);
                jewelPersist.contract = contractRepository.contractFindById(contractPersist.id);

                jewelRepository.jewelSave(jewelPersist);


            });
            //TODO: Implementar el find by id jewel para el reporte con contractPersit.id
            List<Jewel> jewelListReport = jewelRepository.jewelFindById(contractPersist.id);
            for(Jewel jewel: jewelListReport){
                JsonObject listJewel = JsonObject.mapFrom(jewel);
            }
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

            SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

            JsonObject jsonClient = jsonDataContract.getJsonObject("client");
            JsonObject jsonAgency = jsonDataContract.getJsonObject("agency");
            JsonObject jsonDiscount = jsonDataContract.getJsonObject("business_discount");
            JsonObject jsonCurrency = jsonDataContract.getJsonObject("currency");

            long idClient = jsonClient.getLong("id");
            long idAgency = jsonAgency.getLong("id");
            long idBusinessDiscount = jsonDiscount.getLong("id");
            long idCurrency = jsonCurrency.getLong("id");

            String dExpiration = jsonDataContract.getString("date_expiration");
            Date date_expiration = formatDate.parse(dExpiration);

            Agency agency = agencyRepository.agencyFindById(idAgency);
            Client client = clientRepository.clientFindById(idClient);
            BusinessDiscount businessDiscount = businessDiscountRepository.businessDiscountsFindById(idBusinessDiscount);
            Currency currency =currencyRepository.currencyFindById(idCurrency);

            Contract contract = new Contract();
            contract.status = jsonDataContract.getBoolean("status");
            contract.client = client;
            contract.agency = agency;
            contract.businessDiscount = businessDiscount;
            contract.currency = currency;
            contract.dateUpdate = new Date();
            contract.dateExpiration = date_expiration;
            contract.maximumRange = Double.parseDouble(jsonDataContract.getString("value"));
            contract.userUpdate = 1;
            contract.rateInterest = Double.parseDouble(jsonDataContract.getString("rate_interest"));
            contract.capitalBalance=jsonDataContract.getDouble("balance_capital");

            contractRepository.contractUpdate(contract);

            jsonResponseUpdateContract.put("message", "El contrato con Id: " + jsonResponseUpdateContract.getLong("id") + " ha sido actualizado");
            Response response = Response.ok(jsonResponseUpdateContract).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
