## Using Memcache from Java

Spymemcached is a popular Java Memcache client. In order to use Spymemcached in your project you have to declare the dependency in your build and initialize the client from the environment variables that Heroku provides to your application.

### Add Spymemcached to Your Pom.xml

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

### Use Spymemcached in Your Application

    :::java
    AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(System.getenv("MEMCACHE_USERNAME"), System.getenv("MEMCACHE_PASSWORD")));
    ConnectionFactoryBuilder factoryBuilder = new ConnectionFactoryBuilder();
    ConnectionFactory cf = factoryBuilder.setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build();
	
    MemcachedClient memcachedClient = new MemcachedClient(cf, Collections.singletonList(new InetSocketAddress(System.getenv("MEMCACHE_SERVERS"), 11211)));
    memcachedClient.add("test", 0, "testData");

### Using Spymemcached with Spring

When using Spymemcached with Spring you can create a bean that will hold your Memcache configuration and then use Spring to initialize that bean:

Memcache Configuration Bean:

    public class MemcacheConfig {
        private String servers;
        private String username;
        private String password;

        //getters and setters ommitted
    }

This bean can be initialized with either Java or XML based spring configuration:

Java Configuration:

    :::java
    @Configuration
    public class SpringConfig {
        @Bean
        public MemcacheConfig getMemcachedConfig() {
            MemcacheConfig mcConfig = new MemcacheConfig();
            mcConfig.setServers(System.getenv("MEMCACHE_SERVERS"));
            mcConfig.setUsername(System.getenv("MEMCACHE_USERNAME"));
            mcConfig.setPassword(System.getenv("MEMCACHE_PASSWORD"));
            return mcConfig;
        }
    }

or XML Configuration:

    <bean class="com.heroku.devcenter.spring.MemcacheConfig">
      <property name="servers" value="#{systemEnvironment['MEMCACHE_SERVERS'] }"/>
      <property name="username" value="#{systemEnvironment['MEMCACHE_USERNAME'] }"/>
      <property name="password" value="#{systemEnvironment['MEMCACHE_PASSWORD'] }"/>
    </bean>

Client Creation:

    :::java
    // For xml based config use GenericXmlApplicationContext
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
    MemcacheConfig config = ctx.getBean(MemcacheConfig.class);
		
    AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(config.getUsername(), config.getPassword()));
    ConnectionFactoryBuilder factoryBuilder = new ConnectionFactoryBuilder();
    ConnectionFactory cf = factoryBuilder.setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build();
    MemcachedClient memcachedClient = new MemcachedClient(cf, Collections.singletonList(new InetSocketAddress(config.getServers(), 11211)));

You can also download the [sample code](http://github.com/heroku/devcenter-memcache-java.git)
