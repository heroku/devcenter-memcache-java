package com.heroku.devcenter;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.Collections;

public class Main {

    /**
     * @param args
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                new PlainCallbackHandler(System.getenv("MEMCACHE_USERNAME"), System.getenv("MEMCACHE_PASSWORD")));
        ConnectionFactoryBuilder factoryBuilder = new ConnectionFactoryBuilder();
        ConnectionFactory cf = factoryBuilder.setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build();

        MemcachedClient memcachedClient = new MemcachedClient(cf, Collections.singletonList(new InetSocketAddress(System.getenv("MEMCACHE_SERVERS"), 11211)));
        memcachedClient.add("test", 0, "testData");
        System.out.println(memcachedClient.get("test"));
        memcachedClient.shutdown();
    }

}
