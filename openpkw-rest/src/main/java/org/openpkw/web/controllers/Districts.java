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
@Path("districts")
public class Districts {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Districts
     */
    public Districts() {
    }

    /**
     * Retrieves representation of an instance of org.openpkw.web.controllers.Districts
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
