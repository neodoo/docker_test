package com.neodoo.docker.test.service;

import com.neodoo.docker.test.Table1;
import com.neodoo.docker.test.dao.Table1FacadeDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author manuel.aznar@neodoo.es
 */
@Stateless
@Path("com.neodoo.docker.test.table1")
public class Table1REST  {

    @EJB
    Table1FacadeDAO table1FacadeDAO;
            
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Table1 entity) {
        table1FacadeDAO.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Table1 entity) {
        table1FacadeDAO.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        table1FacadeDAO.remove(table1FacadeDAO.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Table1 find(@PathParam("id") Integer id) {
        return table1FacadeDAO.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Table1> findAll() {
        return table1FacadeDAO.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Table1> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return table1FacadeDAO.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(table1FacadeDAO.count());
    }
    
}
