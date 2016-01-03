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
@Path("peripheralVotes")
public class PeripheralVotes {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PeripheralVotes
     */
    public PeripheralVotes() {
    }

    /**
     * Retrieves representation of an instance of org.openpkw.web.controllers.PeripheralVotes
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{districtCommitteeNumber}/{teritorialCode}/{peripheralCommitteeNumber}")
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
