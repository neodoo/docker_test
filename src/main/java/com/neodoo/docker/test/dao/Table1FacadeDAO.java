package com.neodoo.docker.test.dao;

import com.neodoo.docker.test.Table1;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author manuel.aznar@neodoo.es
 */
@Stateless
public class Table1FacadeDAO extends AbstractFacade<Table1> {

    @PersistenceContext(unitName = "com.neodoo_docker-test_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Table1FacadeDAO() {
        super(Table1.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
