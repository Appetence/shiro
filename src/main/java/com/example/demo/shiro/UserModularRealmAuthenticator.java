package com.example.demo.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 多realm认证
 * @author Administrator
 *
 */

public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        logger.info("UserModularRealmAuthenticator:method doAuthenticate() execute ");
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        UserToken userToken = (UserToken) authenticationToken;
        // 登录类型
        String loginType = userToken.getLoginType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        List<Realm> typeRealms = new ArrayList<Realm>();
        for (Realm realm : realms) {
        	String   s =  realm.getName();
            if (realm.getName().contains(loginType)) {
                typeRealms.add(realm);
            }
        }
 
        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            logger.info("doSingleRealmAuthentication() execute ");
            //return doSingleRealmAuthentication(typeRealms.get(0), userToken);
            //Realm s =   typeRealms.iterator().next();
            return doSingleRealmAuthentication(typeRealms.iterator().next(), userToken);
        } else{
            logger.info("doMultiRealmAuthentication() execute ");
            return doMultiRealmAuthentication(typeRealms, userToken);
        }
    }
    
    
}
