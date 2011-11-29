## Using MongoDB from Java

This is an example of using Spymemcached to connect to the Membase Memcache service from both a generic Java application and a Spring configured application on Heroku. Read more about how to use Membache in the [add-on article](http://devcenter.heroku.com/articles/memcache).

# Using The Sample Code

Clone the repo with:

    $ git clone https://github.com/heroku/devcenter-memcache-java.git

Start Memcached locally and set environment variables the way that the Heroku add-on by Membase does:

    $ export MEMCACHE_SERVERS=localhost
    $ export MEMCACHE_USERNAME=user
    $ export MEMCACHE_PASSWORD=password

Build the sample:

    $ mvn package
    [INFO] Scanning for projects...
    [INFO]                                                                         
    [INFO] ------------------------------------------------------------------------
    [INFO] Building memcacheSample 0.0.1-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    ...

Run it with foreman:

    $ foreman start
    20:32:39 sample.1        | started with pid 78505
    20:32:39 springsample.1  | started with pid 78506
    20:32:40 springsample.1  | Nov 28, 2011 8:32:40 PM org.springframework.beans.factory.xml.XmlBeanDefinitionReader loadBeanDefinitions
    20:32:40 springsample.1  | INFO: Loading XML bean definitions from class path resource [applicationContext.xml]
    20:32:40 springsample.1  | Nov 28, 2011 8:32:40 PM org.springframework.context.support.AbstractApplicationContext prepareRefresh
    20:32:40 springsample.1  | INFO: Refreshing org.springframework.context.support.GenericXmlApplicationContext@657d5d2a: startup date [Mon Nov 28 20:32:40 PST 2011]; root of context hierarchy
    20:32:40 springsample.1  | Nov 28, 2011 8:32:40 PM org.springframework.beans.factory.support.DefaultListableBeanFactory preInstantiateSingletons
    20:32:40 springsample.1  | INFO: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@296f25a7: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#0,plainCallbackHandler,authDescriptor,memcachedClient]; root of factory hierarchy
    20:32:40 sample.1        | 2011-11-28 20:32:40.520 INFO net.spy.memcached.MemcachedConnection:  Added {QA sa=mc7.ec2.northscale.net/174.129.165.0:11211, #Rops=0, #Wops=0, #iq=0, topRop=null, topWop=null, toWrite=0, interested=0} to connect queue
    20:32:40 sample.1        | 2011-11-28 20:32:40.530 WARN net.spy.memcached.MemcachedConnection:  Could not redistribute to another node, retrying primary node for test.
    20:32:40 springsample.1  | 2011-11-28 20:32:40.531 INFO net.spy.memcached.MemcachedConnection:  Added {QA sa=mc7.ec2.northscale.net/174.129.165.0:11211, #Rops=0, #Wops=0, #iq=0, topRop=null, topWop=null, toWrite=0, interested=0} to connect queue
    20:32:40 springsample.1  | 2011-11-28 20:32:40.539 WARN net.spy.memcached.MemcachedConnection:  Could not redistribute to another node, retrying primary node for testSpring.
    20:32:40 sample.1        | 2011-11-28 20:32:40.581 INFO net.spy.memcached.MemcachedConnection:  Connection state changed for sun.nio.ch.SelectionKeyImpl@78a1d1f4
    20:32:40 springsample.1  | 2011-11-28 20:32:40.603 INFO net.spy.memcached.MemcachedConnection:  Connection state changed for sun.nio.ch.SelectionKeyImpl@506835fb
    20:32:40 sample.1        | 2011-11-28 20:32:40.685 INFO net.spy.memcached.auth.AuthThread:  Authenticated to mc7.ec2.northscale.net/174.129.165.0:11211
    20:32:40 springsample.1  | 2011-11-28 20:32:40.704 INFO net.spy.memcached.auth.AuthThread:  Authenticated to mc7.ec2.northscale.net/174.129.165.0:11211
    20:32:40 sample.1        | testData
    20:32:40 springsample.1  | testDataSpring


You can switch between the Java and XML based configuration by commenting out one of the two lines in `Main.java` in the `spring` sub-package:

    :::java
    public class Main {

        public static void main(String[] args) throws Exception{

            // If you want Java based configuration:
            final ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        
            // If you want XML based configuration:
            //final ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext.xml");
        
            ...

