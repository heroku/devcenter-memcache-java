package com.heroku.devcenter.spring;

import net.spy.memcached.MemcachedClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    /**
     * @param args
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        //ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext.xml");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        MemcachedClient memcachedClient = ctx.getBean(MemcachedClient.class);

        memcachedClient.add("testSpring", 0, "testDataSpring");
        System.out.println(memcachedClient.get("testSpring"));
        memcachedClient.shutdown();
    }

}
