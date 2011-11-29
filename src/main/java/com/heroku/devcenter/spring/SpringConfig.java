package com.heroku.devcenter.spring;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    
    @Bean
    public PlainCallbackHandler getPlainCallbackHandler() {
        return new PlainCallbackHandler(System.getenv("MEMCACHE_USERNAME"), System.getenv("MEMCACHE_PASSWORD"));
    }
    
    @Bean
    public AuthDescriptor getAuthDescriptor() {
        return new AuthDescriptor(new String[]{"PLAIN"},
                getPlainCallbackHandler());
    }
    
    @Bean
    public ConnectionFactory getConnectionFactory() {
        ConnectionFactoryBuilder factoryBuilder = new ConnectionFactoryBuilder();
        return factoryBuilder.setProtocol(Protocol.BINARY).setAuthDescriptor(getAuthDescriptor()).build();        
    }
    
    @Bean
    public InetSocketAddress getServerAddress() {
        return new InetSocketAddress(System.getenv("MEMCACHE_SERVERS"), 11211);
    }
    
    @Bean 
    public MemcachedClient getMemcachedClient() throws IOException{
        MemcachedClient memcachedClient = 
                new MemcachedClient(
                        getConnectionFactory(), 
                        Collections.singletonList(getServerAddress()));
        return memcachedClient;
    }
}
