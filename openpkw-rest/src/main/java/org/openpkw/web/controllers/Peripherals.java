package org.openpkw.web.controllers;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author kamil
 */
@Path("peripherals")
public class Peripherals {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Peripherals
     */
    public Peripherals() {
    }

    /**
     * Retrieves representation of an instance of org.openpkw.web.controllers.Peripherals
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{districtCommitteeNumber}/{peripheralCommitteeNumber}/{teritorialCode}/{town}/{street}")
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
