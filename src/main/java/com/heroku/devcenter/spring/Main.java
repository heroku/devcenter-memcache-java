package com.heroku.devcenter.spring;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.Collections;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		//ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext.xml");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		MemcacheConfig config = ctx.getBean(MemcacheConfig.class);
		
		AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                new PlainCallbackHandler(config.getUsername(), config.getPassword()));
		ConnectionFactoryBuilder factoryBuilder = new ConnectionFactoryBuilder();
		ConnectionFactory cf = factoryBuilder.setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build();
		
		MemcachedClient memcachedClient = new MemcachedClient(cf, Collections.singletonList(new InetSocketAddress(config.getServers(), 11211)));
		memcachedClient.add("testSpring", 0, "testDataSpring");
		System.out.println(memcachedClient.get("testSpring"));
	}

}
