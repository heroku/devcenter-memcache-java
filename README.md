## Using Memcache from Java

This is an example of using Spymemcached to connect to the Couchbase Memcache service from both a generic Java application and a Spring configured application on Heroku. Read more about how to use Memcache in the [add-on article](http://devcenter.heroku.com/articles/memcache).

# Using The Sample Code

Clone the repo with:

    $ git clone https://github.com/heroku/devcenter-memcache-java.git

Start Memcached locally and set environment variables the way that the Heroku add-on by Couchbase does:

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
            
# Test on Heroku

Assuming you already have a [Heroku account](http://heroku.com/signup) and have installed the [Heroku command line tool](http://devcenter.heroku.com/articles/java), you can test this sample on Heroku in a few steps.

## Create Heroku App

    $ heroku create -s cedar
    Creating quiet-waterfall-6274... done, stack is cedar
    http://quiet-waterfall-6274.herokuapp.com/ | git@heroku.com:quiet-waterfall-6274.git
    Git remote heroku added

## Add Memcache Add-on

    $ heroku addons:add memcache:5mb
    -----> Adding memcache:5mb to quiet-waterfall-6274... done, v1 (free)

## Deploy Sample Using Git

    $ git push heroku master
    Counting objects: 56, done.
    Delta compression using up to 8 threads.
    Compressing objects: 100% (38/38), done.
    Writing objects: 100% (46/46), 9.16 KiB, done.
    Total 46 (delta 14), reused 0 (delta 0)
    
    -----> Heroku receiving push
    -----> Java app detected
    -----> Installing settings.xml..... done
    -----> executing /app/tmp/repo.git/.cache/.maven/bin/mvn -B -Duser.home=/tmp/build_g3p4ujxwspkf -Dmaven.repo.local=/app/tmp/repo.git/.cache/.m2/repository -s /app/tmp/repo.git/.cache/.m2/settings.xml -DskipTests=true clean install
           [INFO] Scanning for projects...
           [INFO]                                                                         
           [INFO] ------------------------------------------------------------------------
           [INFO] Building memcacheSample 0.0.1-SNAPSHOT
           [INFO] ------------------------------------------------------------------------
           Downloading: http://files.couchbase.com/maven2/org/springframework/spring-core/3.0.6.RELEASE/spring-core-3.0.6.RELEASE.pom
           Downloading: http://s3pository.heroku.com/jvm/org/springframework/spring-core/3.0.6.RELEASE/spring-core-3.0.6.RELEASE.pom
           Downloaded: http://s3pository.heroku.com/jvm/org/springframework/spring-core/3.0.6.RELEASE/spring-core-3.0.6.RELEASE.pom (3 KB at 3.9 KB/sec)
           Downloading: http://files.couchbase.com/maven2/org/springframework/spring-parent/3.0.6.RELEASE/spring-parent-3.0.6.RELEASE.pom
    ...

## Execute the Sample Code as One-Off Processes

The two sample apps are listed as two process types, "sample" and "springsample". They are designed to be executed as one-off processes, so you execute them with

    $ heroku run sample
    Running sample attached to terminal... up, run.1
    2011-12-01 23:51:07.122 INFO net.spy.memcached.MemcachedConnection:  Added {QA sa=mc7.ec2.northscale.net/10.82.127.172:11211, #Rops=0, #Wops=0, #iq=0, topRop=null, topWop=null, toWrite=0, interested=0} to connect queue
    2011-12-01 23:51:07.153 INFO net.spy.memcached.MemcachedConnection:  Connection state changed for sun.nio.ch.SelectionKeyImpl@33bfc93a
    2011-12-01 23:51:07.567 INFO net.spy.memcached.auth.AuthThread:  Authenticated to mc7.ec2.northscale.net/10.82.127.172:11211
    testData
    2011-12-01 23:51:07.575 INFO net.spy.memcached.MemcachedClient:  Shut down memcached client

and

    $ heroku run springsample
    Running springsample attached to terminal... up, run.2
    Dec 1, 2011 11:52:09 PM org.springframework.context.support.AbstractApplicationContext prepareRefresh
    INFO: Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@50a9ae05: startup date [Thu Dec 01 23:52:09 UTC 2011]; root of context hierarchy
    Dec 1, 2011 11:52:09 PM org.springframework.beans.factory.support.DefaultListableBeanFactory preInstantiateSingletons
    INFO: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@5f989f84: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,springConfig,getPlainCallbackHandler,getAuthDescriptor,getConnectionFactory,getServerAddress,getMemcachedClient]; root of factory hierarchy
    2011-12-01 23:52:09.494 INFO net.spy.memcached.MemcachedConnection:  Added {QA sa=mc7.ec2.northscale.net/10.82.127.172:11211, #Rops=0, #Wops=0, #iq=0, topRop=null, topWop=null, toWrite=0, interested=0} to connect queue
    2011-12-01 23:52:09.499 INFO net.spy.memcached.MemcachedConnection:  Connection state changed for sun.nio.ch.SelectionKeyImpl@2802bd3a
    2011-12-01 23:52:09.638 INFO net.spy.memcached.auth.AuthThread:  Authenticated to mc7.ec2.northscale.net/10.82.127.172:11211
    testDataSpring
    2011-12-01 23:52:09.646 INFO net.spy.memcached.MemcachedClient:  Shut down memcached client


