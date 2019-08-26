package com.example.demo.shiro;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;

import com.example.demo.shiro.reaml.WebDatabaseRealm;

/**
* @Author: Zhaojiatao
* @Description: Shiro配置类
* @Date: Created in 2018/2/8 13:29
* @param 
*/
@Configuration
public class ShiroConfig {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login.ftl");

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/tologin");

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置记住我或认证通过可以访问的地址(配置不会被拦截的链接 顺序判断)
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/user/testlogin", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");
        filterChainDefinitionMap.put("/drawImage", "anon");

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");


        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
}

    @Bean
    public SecurityManager securityManager() throws NoSuchAlgorithmException{
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //前后端分离使用如下方法    因为前后端分离需要 会话维持     需要jssionid
        securityManager.setAuthenticator(modularRealmAuthenticator());
        List<Realm> realms = new ArrayList<Realm>();
        //设置多realm.
//        realms.add(getDatabaseRealm());
        realms.add(getWebDatabaseRealm());
        
        // 自定义缓存实现 使用redis
//        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
//        securityManager.setSessionManager(sessionManager());
        //注入Cookie(记住我)管理器(remenberMeManager)
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setRealms(realms);
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }
    
    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     
    public RedisManager redisManager() {
    	logger.info("创建shiro redisManager,连接Redis..URL= " + host + ":" + port);
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(6000);// 配置缓存过期时间
        redisManager.setTimeout(timeout);
        // redisManager.setPassword(password)
        return redisManager;
    }
*/
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     
    public RedisCacheManager cacheManager() {
        logger.info("创建RedisCacheManager...");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
*/
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
    
    @Bean
    public RedisSessionDAO redisSessionDAO() {
    	MyredisSessionDAO redisSessionDAO = new MyredisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
 */

/*
    //微信小程序Realm
    @Bean(name="miniPragramDatabaseRealm")
    public miniPragramDatabaseRealm getDatabaseRealm(){
        miniPragramDatabaseRealm myShiroRealm = new miniPragramDatabaseRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }*/
    //webR ealm
    @Bean(name="webDatabaseRealm")
    public WebDatabaseRealm getWebDatabaseRealm(){
    	WebDatabaseRealm webhiroRealm = new WebDatabaseRealm();
    	webhiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
    	webhiroRealm.setCacheManager(ehCacheManager());
        return webhiroRealm;
    }
    
    
    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return
     */
    @Bean
    public RetryLimitCredentialsMatcher hashedCredentialsMatcher(){
    	RetryLimitCredentialsMatcher hashedCredentialsMatcher = new RetryLimitCredentialsMatcher(ehCacheManager(), "shirologin");

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }


    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * Session Manager
     * 	使用的是shiro-redis开源插件
    
    @Bean
    public  DefaultWebSessionManager  sessionManager(){
    	StatelessSessionManager   defaultWebSessionManager   =  new StatelessSessionManager();
    	//禁用设置shiro禁用URL重写   一般web开发 不是前后端分离的会出现这个问题
    	//https://blog.csdn.net/hackerHL/article/details/82035702
    	// tomcat的JESSIONID自动生成模块，shiro直接抄的tomcat的，唉！误导我一直在看tomcat源码
    	defaultWebSessionManager.setSessionDAO(redisSessionDAO());
    	defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
		return defaultWebSessionManager;
    }
     */
    /**
     * 系统自带的Realm管理，主要针对多realm
     * */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
    	List<Realm> realms = new ArrayList<Realm>();
        //设置多realm.
        //添加多个Realm
        //
       
 //       realms.add(getDatabaseRealm());
        realms.add(getWebDatabaseRealm());
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setRealms(realms);
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }
    /**
     * cookie管理器;
     * @return
      * @throws NoSuchAlgorithmException 
     */
     @Bean
     public CookieRememberMeManager rememberMeManager() throws NoSuchAlgorithmException{
         logger.info("注入Shiro的记住我(CookieRememberMeManager)管理器-->rememberMeManager", CookieRememberMeManager.class);
         CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
         //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
         //KeyGenerator keygen = KeyGenerator.getInstance("AES");
         //SecretKey deskey = keygen.generateKey();
         //System.out.println(Base64.encodeToString(deskey.getEncoded()));
         byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
         cookieRememberMeManager.setCipherKey(cipherKey);
         cookieRememberMeManager.setCookie(rememberMeCookie());
         return cookieRememberMeManager;
     }
     @Bean
     public SimpleCookie rememberMeCookie(){
         //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
         SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
         //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
         simpleCookie.setHttpOnly(true);
         //记住我cookie生效时间,默认30天 ,单位秒：60 * 60 * 24 * 30
         simpleCookie.setPath("/WebController/rememberMe");
         simpleCookie.setMaxAge(259200);
         //simpleCookie.setMaxAge(1);
         return simpleCookie;
     }
     //拦截器
     @Bean
     public FormAuthenticationFilter formAuthenticationFilter(){
         FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
         //对应前端的checkbox的name = rememberMe
         formAuthenticationFilter.setRememberMeParam("rememberMe");
         return formAuthenticationFilter;
     }
     //缓存
     /**
      * 缓存管理器
      * @return
      */
     @Bean
     public EhCacheManager ehCacheManager(){
         EhCacheManager cacheManager = new EhCacheManager();
         cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
         return cacheManager;
     }

     /**
      * aop代理
      * @return
      */
     @Bean
    // @DependsOn("lifecycleBeanPostProcessor")
     public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
         DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
         defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
         return defaultAdvisorAutoProxyCreator;
     }
}
