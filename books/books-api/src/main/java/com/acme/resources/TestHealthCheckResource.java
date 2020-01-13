package com.acme.resources;

import com.acme.healthcheck.TestHealthCheckBean;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("reset")
@Log(LogParams.METRICS)
public class TestHealthCheckResource {

    @Inject
    @Liveness
    private TestHealthCheckBean checkBean;

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    public Response reset() {
        checkBean.switchFailure();
        return Response.ok("Service resetted successfuly.").build();
    }
}
