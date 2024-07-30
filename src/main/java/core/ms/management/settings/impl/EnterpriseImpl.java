package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.Country;
import core.ms.management.settings.dao.entity.Enterprise;
import core.ms.management.settings.dao.entity.JewelType;
import core.ms.management.settings.dao.repository.CountryRepository;
import core.ms.management.settings.dao.repository.EnterpriseRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class EnterpriseImpl {
    @Inject
    EnterpriseRepository enterpriseRepository;

    @Inject
    CountryRepository countryRepository;

    JsonObject jsonEnterpriseResponse = new JsonObject();

    ObjectMapper mapper = new ObjectMapper();

    public Response enterpriseListAll(){
        try{
            JsonArray arrayEnterprise = new JsonArray(enterpriseRepository.enterpriseListAll());
            if(!arrayEnterprise.isEmpty()){
                Response response = Response.ok(arrayEnterprise).build();
                if(response.getStatus() == 200){
                    return Response.ok(response.getEntity()).build();
                }
            }
        }catch (Exception e){
            return Response.accepted(jsonEnterpriseResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response enterpriseFindById(JsonObject jsonDataEnterprise){
        try{
            Long id = jsonDataEnterprise.getLong("id");
            Enterprise enterprise = enterpriseRepository.enterpriseFindById(id);
            JsonObject jsonJewelType = new JsonObject(mapper.writeValueAsString(enterprise));
            Response response = Response.ok(jsonJewelType).build();
            if(response.getStatus()==200){
                return Response.ok(response.getEntity()).build();
            }
        }catch (Exception e){
            Response.accepted(jsonEnterpriseResponse.put("message", e.getMessage())).build();
        }
        return Response.noContent().build();
    }

    public Response enterpriseSave(JsonObject jsonDataEnterprise){
        try{
            JsonObject jsonCountry = jsonDataEnterprise.getJsonObject("country");
            Long countryId = jsonCountry.getLong("id");
            Country country = countryRepository.countryFindById(countryId);

            Enterprise enterprise = new Enterprise();
            enterprise.name = jsonDataEnterprise.getString("name");
            enterprise.description = jsonDataEnterprise.getString("description");
            enterprise.address = jsonDataEnterprise.getString("address");
            enterprise.nit = Long.parseLong(jsonDataEnterprise.getString("nit"));
            enterprise.legalRepresentative = jsonDataEnterprise.getString("legal_representative");
            enterprise.dateCreate = new Date();
            enterprise.userCreate = 0;
            enterprise.country = country;

            enterpriseRepository.enterpriseSave(enterprise);
            JsonObject jsonResponseJewelTypeResponse = new JsonObject();
            jsonResponseJewelTypeResponse.put("message", "Empresa " + jsonDataEnterprise.getString("name") + " registrada correctamente");
            return Response.ok(jsonResponseJewelTypeResponse).build();
        }catch(Exception e){
            Response.accepted(jsonEnterpriseResponse.put("message", e.getMessage())).build();
        }
        return Response.noContent().build();
    }
}
