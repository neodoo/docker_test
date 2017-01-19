package com.neodoo.docker.test.service;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:mjobanek@redhat.com">Matous Jobanek</a>
 */
@RunWith(Arquillian.class)
public class GreetingServiceTestCase {

    @Deployment
    public static WebArchive deployService() {
        return ShrinkWrap.create(WebArchive.class,"docker_test.war")
                .addClass(GreetingService.class);
    }

    @Inject
    private GreetingService service;

    @Test
    public void shouldGreetTheWorld() throws Exception {
        System.out.println("shouldGreetTheWorld");
        Assert.assertEquals("Hello, World!", service.greet("World"));
    }
}
