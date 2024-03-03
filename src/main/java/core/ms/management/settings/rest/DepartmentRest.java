package core.ms.management.settings.rest;

import core.ms.management.settings.impl.DepartmentImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("rest/department")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentRest {

    @Inject
    DepartmentImpl departmentImpl;

    @GET
    @Path("/find/all")
    public Response countryListAll() {
        return departmentImpl.listAllDepartment();
    }

    @POST
    @Path("/save")
    public Response countrySave(JsonObject jsonDepartment) {
        return departmentImpl.saveDepartment(jsonDepartment);
    }
}
