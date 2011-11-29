## Using Memcache from Java

Spymemcached is a popular Java Memcache client. In order to use Spymemcached in your project you have to declare the dependency in your build and initialize the client from the environment variables that Heroku provides to your application.

### Add Spymemcached to your pom.xml

Add the following repository and dependency to your pom.xml in order to use Spymemcached to connect to Memcache:

    <repository>
      <id>spy</id>
      <name>Spy Repository</name>
      <layout>default</layout>
      <url>http://files.couchbase.com/maven2/</url>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </repository>
    
    ...

    <dependency>
        <groupId>spy</groupId>
        <artifactId>spymemcached</artifactId>
        <version>2.7.3</version>
    </dependency>

### Use Spymemcached in your application

    :::java
    AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(System.getenv("MEMCACHE_USERNAME"), System.getenv("MEMCACHE_PASSWORD")));
    ConnectionFactoryBuilder factoryBuilder = new ConnectionFactoryBuilder();
    ConnectionFactory cf = factoryBuilder.setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build();
    
    MemcachedClient memcachedClient = new MemcachedClient(cf, Collections.singletonList(new InetSocketAddress(System.getenv("MEMCACHE_SERVERS"), 11211)));
    memcachedClient.add("test", 0, "testData");

### Using Spymemcached with Spring

Use the following Java configuration class to set up a `MemcachedClient` object as a singleton Spring bean:

    :::java
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

or the following XML configuration file:

    :::xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context-3.0.xsd">
    
      <context:annotation-config/>
      <context:property-placeholder/>
      
      <bean id="plainCallbackHandler" class="net.spy.memcached.auth.PlainCallbackHandler">
           <constructor-arg index="0" value="${MEMCACHE_USERNAME}"/>
           <constructor-arg index="1" value="${MEMCACHE_PASSWORD}"/> 
      </bean>
      
      <bean id="authDescriptor" class="net.spy.memcached.auth.AuthDescriptor">
           <constructor-arg index="0">
             <array><value>PLAIN</value></array>
           </constructor-arg>
           <constructor-arg index="1" ref="plainCallbackHandler"/> 
      </bean>
      
      <bean id="memcachedClient" class="net.spy.memcached.spring.MemcachedClientFactoryBean">
        <property name="servers" value="${MEMCACHE_SERVERS}:11211"/>
        <property name="protocol" value="BINARY"/>
        <property name="authDescriptor" ref="authDescriptor"/>
      </bean>
    
    </beans>

### Sample code

To see a complete, working example, check out the [sample code in github](https://github.com/heroku/devcenter-memcache-java). The [readme](https://github.com/heroku/devcenter-memcache-java/blob/master/README.md) explains more about the example.
