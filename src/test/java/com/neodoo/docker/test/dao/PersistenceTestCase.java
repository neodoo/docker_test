package com.neodoo.docker.test.dao;

import com.neodoo.docker.test.Table1;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
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
public class PersistenceTestCase {

//    @Deployment
//    public static Archive<?> deployService() {
//        return ShrinkWrap.create(JavaArchive.class, "docker_test.jar")
////                .addPackage(PersistenceTestCase.class.getPackage())
//                .addPackages(true, "com.neodoo.docker.test") 
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
//                .addAsManifestResource("test-persistence.xml", "persistence.xml");
//    }

    @Deployment
    public static WebArchive deployService() {
        return ShrinkWrap.create(WebArchive.class, "docker_test.war")
//                .addPackage(PersistenceTestCase.class.getPackage())
                .addPackages(true, "com.neodoo.docker.test") 
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("hibernate.cfg.xml", "hibernate.cfg.xml")                
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }
    
    
    @EJB
    private Table1FacadeDAO table1FacadeDAO;

    @Test
    @InSequence(1)
    public void shouldCountEquals2() throws Exception {
        System.out.println("shouldCountEquals2");
        Assert.assertEquals(2, table1FacadeDAO.count());
    }

    @Test
    @InSequence(2)
    public void shouldSelect2() throws Exception {
        System.out.println("shouldSelect2");
        List<Table1> lstTable1 = table1FacadeDAO.findAll();
        Assert.assertEquals(1, lstTable1.get(0).getId().intValue());
        Assert.assertEquals("nom2", lstTable1.get(1).getNom());
    }

    @Test
    @InSequence(3)
    public void shouldInsertAfterSelect3() throws Exception {
        System.out.println("shouldInsertAfterSelect3");

        Table1 table1 = new Table1();
        table1.setNom("nom3");
        table1FacadeDAO.create(table1);

        Assert.assertEquals(3, table1FacadeDAO.count());
    }

    
//    @UsingDataSet("datasets/users.yml")
//    @ShouldMatchDataSet("datasets/expected-users.yml")
}
