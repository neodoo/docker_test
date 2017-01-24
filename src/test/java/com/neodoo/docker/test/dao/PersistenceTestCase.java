package com.neodoo.docker.test.dao;

import com.neodoo.docker.test.Table1;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author manuel.aznar@neodoo.es>
 */
@RunWith(Arquillian.class)
@UsingDataSet("datasets/com.neodoo.docker.test.dao.PersistenceTestCase.xml")
@Cleanup
public class PersistenceTestCase {
    
    @Resource(lookup = "java:jboss/datasources/DockerTestDS")
    private DataSource ds;    

    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static WebArchive deployService() {
        return ShrinkWrap.create(WebArchive.class, "docker_test.war")
                //                .addPackage(PersistenceTestCase.class.getPackage())
                .addPackages(true, "com.neodoo.docker.test")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("datasets/expected-com.neodoo.docker.test.dao.PersistenceTestCase#shouldInsertAfterSelect3.xml") // to be loaded by DBUnit on the server side
                .addAsResource("hibernate.cfg.xml", "hibernate.cfg.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @EJB
    private Table1FacadeDAO table1FacadeDAO;

    @Test
//    @InSequence(1)
    public void shouldCountEquals2() throws Exception {
        System.out.println("shouldCountEquals2");
        Assert.assertEquals(2, table1FacadeDAO.count());
    }

    @Test
//    @InSequence(2)
    public void shouldSelect2() throws Exception {
        System.out.println("shouldSelect2");
        List<Table1> lstTable1 = table1FacadeDAO.findAll();
        Assert.assertEquals(1, lstTable1.get(0).getId().intValue());
        Assert.assertEquals("nom2", lstTable1.get(1).getNom());
    }

    @Test
    //@InSequence(3)
    @ShouldMatchDataSet(value = "datasets/expected-com.neodoo.docker.test.dao.PersistenceTestCase#shouldInsertAfterSelect3.xml",
            excludeColumns = {"id"}, orderBy = "id")
    public void shouldInsertAfterSelect3() throws Exception {
        System.out.println("shouldInsertAfterSelect3");

        Table1 table1 = new Table1();
        table1.setNom("nom3");
        table1FacadeDAO.create(table1);

        Assert.assertEquals(3, table1FacadeDAO.count());
    }

    @Test
    public void shouldInsertAfterSelect3InServerSide() throws Exception {
        Table1 table1 = new Table1();
        table1.setNom("nom3");
        table1FacadeDAO.create(table1);
        em.flush(); // force JPA to execute DMLs before assertion

        final IDataSet expectedDataSet = getDataSet("/datasets/expected-com.neodoo.docker.test.dao.PersistenceTestCase#shouldInsertAfterSelect3.xml");
        assertTable(expectedDataSet.getTable("table1"), "select * from table1 order by id");
    }

    private static IDataSet getDataSet(String path) throws DataSetException {
        return new FlatXmlDataSetBuilder().build(PersistenceTestCase.class.getResource(path));
    }

    private void assertTable(ITable expectedTable, String sql) throws SQLException, DatabaseUnitException {
        try (Connection cn = ds.getConnection()) {
            IDatabaseConnection icn = null;
            try {
                icn = new DatabaseConnection(cn);
                final ITable queryTable = icn.createQueryTable(expectedTable.getTableMetaData().getTableName(), sql);
                String[] ignoreCols = {"id"};
                Assertion.assertEqualsIgnoreCols(expectedTable, queryTable, ignoreCols);
            } finally {
                if (icn != null) {
                    icn.close();
                }
            }
        }
    }

}
