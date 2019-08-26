/**  
 * All rights Reserved, Designed By jxZhang
 * @Title:  UserToken.java   
 * @Package com.sinosoft.config.shiro   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: jxZhang     
 * @date:   2019年3月12日 上午10:40:50   
 */
package com.example.demo.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**   
 * @ClassName:  UserToken   
 * @Description:继承UsernamePasswordToken 添加loginType属性
 * @author: jxZhang
 * @date:   2019年3月12日 上午10:40:50   
 *   
 */
public class UserToken extends UsernamePasswordToken{
	private static final long serialVersionUID = 1L;
	private String loginType;
	public UserToken(final String username, final String password, 
            final String loginType) {
        super(username, password);
        this.loginType = loginType;
    }
 
    public String getLoginType() {
        return loginType;
    }
 
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
