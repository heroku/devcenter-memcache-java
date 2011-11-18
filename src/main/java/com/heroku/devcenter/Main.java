package com.heroku.devcenter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import net.spy.memcached.MemcachedClient;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
//		AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
//                new PlainCallbackHandler("app1805387@heroku.com", "k/aoMGr9EcKo4Sa6"));
//		ConnectionFactoryBuilder factoryBuilder = new ConnectionFactoryBuilder();
//		ConnectionFactory cf = factoryBuilder.setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build();
		
        URI base = new URI("http://" + System.getenv("MEMCACHE_SERVERS") + ":11211");
        ArrayList<URI> baseURIs = new ArrayList<URI>();
        baseURIs.add(base);
        MemcachedClient memcachedClient = new MemcachedClient(baseURIs, "default", System.getenv("MEMCACHE_USERNAME"), System.getenv("MEMCACHE_PASSWORD"));
		
//		MemcachedClient memcachedClient = new MemcachedClient(AddrUtil.getAddresses("mc7.ec2.northscale.net:11211"), "app1805387@heroku.com", "k/aoMGr9EcKo4Sa6");
		memcachedClient.add("test", 0, "testData");
		System.out.println(memcachedClient.get("test"));
	}

}
