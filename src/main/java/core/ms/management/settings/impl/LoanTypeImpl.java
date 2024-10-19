package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.LoanType;
import core.ms.management.settings.dao.repository.LoanTypeRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class LoanTypeImpl {
    @Inject
    LoanTypeRepository loanTypeRepository;

    public Response listAllLoanType(){
        JsonObject jsonObjectLoanType = new JsonObject();
        try{
            List<LoanType> loanTypeList = loanTypeRepository.listAllLoanType();
            JsonArray arrayLoanType = new JsonArray(loanTypeList);
            Response response = Response.ok(arrayLoanType).build();
            if(response.getStatus() == 200){
                return Response.ok(response.getEntity()).build();
            }
        }catch (Exception e){
            return Response.accepted(jsonObjectLoanType.put("message", e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response saveLoanType (JsonObject jsonDataLoanType){
        JsonObject jsonResponsePersist =  new JsonObject();
        try{
            LoanType loanType =  new LoanType();
            loanType.id = 0;
            loanType.description = jsonDataLoanType.getString("description");
            loanType.rate = Double.parseDouble(jsonDataLoanType.getString("rate"));
            loanType.dateCreate = new Date();
            loanTypeRepository.loanTypeSave(loanType);
            return Response.ok(jsonResponsePersist.put("message", "Tipo de préstamo registrado correctamente")).build();
        }catch (Exception e){
            return Response.accepted(jsonResponsePersist.put("message", e.getMessage())).build();
        }
    }

    public Response findByIdLoanType(JsonObject jsonDataLoanType){
        JsonObject jsonFind = new JsonObject();
        try{
            Long id = jsonDataLoanType.getLong("id");
            LoanType loanType = loanTypeRepository.loanTypeFindById(id);
            if(loanType == null){
                return Response.ok(jsonFind.put("message", "Tipo de préstamo no encontrado")).build();
            }
            Response response = Response.ok(new JsonObject(Json.encode(loanType))).build();
            if(response.getStatus() == 200){
                return Response.ok(response.getEntity()).build();
            }
        }catch (Exception e){
            return Response.accepted(jsonFind.put("message", e.getMessage())).build();
        }
        return Response.serverError().build();
    }
}
