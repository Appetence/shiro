/**  
 * All rights Reserved, Designed By jxZhang
 * @Title:  WebDatabaseRealm.java   
 * @Package com.sinosoft.config.shiro   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: jxZhang     
 * @date:   2019年3月12日 上午10:52:26   
 */
package com.example.demo.shiro.reaml;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.ButtonService;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;


/**   
 * @ClassName:  WebDatabaseRealm   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jxZhang
 * @date:   2019年3月12日 上午10:52:26   
 *   
 */
public class WebDatabaseRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private ButtonService buttonService;

	/**   
	 * <p>Title: web端口登陆认证
	 * <p>Description: </p>   
	 * @param token
	 * @return
	 * @throws AuthenticationException   
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)   
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("WEB端登录认证");
		//获取用户名的账号
		String   webUsername  =String.valueOf(token.getPrincipal());
		User userS = new User();
		userS.setWeb_username(webUsername);
		// 获取数据库中的密码
		User user = userService.selectUserByWebUserName(userS);
		logger.info("WEB端登录认证用户详情："+user);
		if(user ==null){	
			logger.info("WEB端登录认证用户为空");
			throw new AuthenticationException();
		}
		String passwordInDB = user.getPassword();
		String salt = user.getSalt();
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(webUsername,
																					passwordInDB, 
																					ByteSource.Util.bytes(salt),
																					getName());
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute("user_id", user.getId().toString());//?将用户userid存储如session中，用于区分是哪个用户
		logger.info("WEB端登录认证结束");
		return authenticationInfo;
	}


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("web授权逻辑开始");
		// 能进入到这里，表示账号已经通过验证了
		//String webUsername = (String) principals.getPrimaryPrincipal();
		
		//获取用户信息
		Subject subject = SecurityUtils.getSubject();
		String   user_id  =	(String) subject.getSession().getAttribute("user_id");
		User users = new User();
		users.setId(Integer.parseInt(user_id));
		User user = userService.selectUserInfoById(users);
		logger.info("用户详情"+user);
		if(user!=null) {
			Set<String> roleSet =  new HashSet<String>();
			Set<String> permissionSet = new  HashSet<String>();
			Set<Role> roles = roleService.selectRolesByUserId(user);
			if(roles!=null && roles.size()>0){
				for(Role r : roles){
					// 通过service获取角色和权限
					Set<String> permissions = permissionService.selectPermissionsByUserId(r);
					roleSet.add(r.getName());
					permissionSet.addAll(permissions);
				}
				logger.info("该用户角色为:"+roleSet);
				logger.info("目录权限:"+permissionSet);
			}else{
				logger.info("用户查无权限");
			}
			
			// 授权对象
			SimpleAuthorizationInfo simp = new SimpleAuthorizationInfo();
			// 把通过service获取到的角色和权限放进去
			simp.setStringPermissions(permissionSet);
			simp.setRoles(roleSet);
			logger.info("web授权逻辑结束");
			return simp;
		}
		logger.info("web授权逻辑未执行");
		return null;
	}
	

}
