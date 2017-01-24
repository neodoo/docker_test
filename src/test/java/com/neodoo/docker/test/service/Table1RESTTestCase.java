package com.neodoo.docker.test.service;

import com.neodoo.docker.test.Table1;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import java.util.List;
import java.util.Map;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@UsingDataSet("datasets/com.neodoo.docker.test.dao.PersistenceTestCase.xml")
@Cleanup
public class Table1RESTTestCase {

    @Deployment(testable = false)
    public static WebArchive deployService() {
        return ShrinkWrap.create(WebArchive.class, "docker_test_client.war")
                //                .addPackage(PersistenceTestCase.class.getPackage())
                .addPackages(true, "com.neodoo.docker.test")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("hibernate.cfg.xml", "hibernate.cfg.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @ArquillianResource
    RequestSpecBuilder requestSpecBuilder;

    @BeforeClass
    public static void init() {
//        // http://localhost:58080/docker_test/webresources/com.neodoo.docker.test.table1/
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = 58080;
//        RestAssured.basePath = "/docker_test_client/webresources/";
//        RestAssured.config = RestAssuredConfig.config().redirect(new RedirectConfig().followRedirects(true).allowCircularRedirects(true));
//        //RestAssured.authentication = RestAssured.form("admin", "test", new FormAuthConfig("j_security_check", "j_username", "j_password"));
    }

    @Test
    public void shouldList2Elements() throws Exception {
 
        // Example with JsonPath
        String json = RestAssured
                .given()
                .spec(requestSpecBuilder.build())
                .when()
                .get("/docker_test_client/webresources/com.neodoo.docker.test.table1").asString();
                
        System.out.println(json);
        JsonPath jp = new JsonPath(json);
        List<Map> lstMap = jp.get("");
        Assert.assertEquals("nom1", lstMap.get(0).get("nom"));


    }
    
//    @Test
//    public void shouldList2ElementsWithGson() throws Exception {
// 
//        List<Table1> lstTable1 = RestAssured
//                .given()
//                .spec(requestSpecBuilder.build())
//                .when()
//                .get("/docker_test_client/webresources/com.neodoo.docker.test.table1").as(List.class, ObjectMapperType.GSON);   
//
//        Assert.assertEquals("nom1", lstTable1.get(0).getNom());
//
//
//    }
        
    @Test
    public void shouldList3Elements() throws Exception {
 
        Table1 table1 = new Table1();
        table1.setNom("nom3");
        RestAssured
               .given()
               .spec(requestSpecBuilder.build())
               .contentType("application/json")
               .body(table1)
               .when()
               .post("/docker_test_client/webresources/com.neodoo.docker.test.table1");            
        

        // Example with JsonPath
        String json = RestAssured
                .given()
                .spec(requestSpecBuilder.build())
                .when()
                .get("/docker_test_client/webresources/com.neodoo.docker.test.table1").asString();
                
        System.out.println(json);
        JsonPath jp = new JsonPath(json);
        List<Map> lstMap = jp.get("");
        Assert.assertEquals("nom3", lstMap.get(2).get("nom"));        

    }
        
    
    

}
