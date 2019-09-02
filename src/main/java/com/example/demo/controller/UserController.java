package com.example.demo.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.service.ButtonService;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.util.RandomUtil;
import com.example.demo.util.SendSMSUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ButtonService buttonService;
	@Autowired
	RoleService roleService;
	@Autowired
	PermissionService permissionService;
	@Autowired
	UserService userService;
	
	@ResponseBody
	@PostMapping("/login")
	public Map<String,Object> testlogin(String imageCode, @Valid User user, BindingResult bindingResult , HttpSession session ) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isBlank(imageCode)){
    		map.put("success", false);
    		map.put("errorInfo", "请输入验证码！");
    		return map;
    	}
    	
    	if(!session.getAttribute("checkcode").equals(imageCode)){
    		map.put("success", false);
    		map.put("errorInfo", "验证码输入错误！");
    		return map;
    	}
    	/*
    	if(bindingResult.hasErrors()){
    		//验证@Validated后面bean 里是否有不符合注解条件的错误信息
    		map.put("success", false);
    		map.put("errorInfo", bindingResult.getFieldError().getDefaultMessage());
    		return map;
    	}*/
		map = userService.userLogin(user, session);
		
		return map;
	}

	/**
	 * 安全退出
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/logout")
	public String logout() throws Exception {
//		logService.save(new Log(Log.LOGOUT_ACTION,"用户注销"));
		SecurityUtils.getSubject().logout();
		return "redirect:/tologin";
	}
	@ResponseBody
	@PostMapping("/register")
	public Map<String,Object> userRegister(String imageCode, @Valid User user, BindingResult bindingResult , HttpSession session ) throws Exception {
		String pass = user.getPassword();
		Map<String,Object> map = new HashMap<String,Object>();
		int result = userService.insertUserInfo(user);
		if(result>0){
			user = userService.selectUserInfoById(user);
			user.setPassword(pass);
			map.put("success", true);
			map = userService.userLogin(user, session);
		}else{
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 保存角色信息
	 * @param roleId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@PostMapping("/saveRole")
	public Map<String,Object> saveRole(HttpSession session)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		String userId = (String)session.getAttribute("user_id");
		User user = new User();
		Integer big = new Integer(userId);
		user.setId(big);
		Set<Role> currentRole=roleService.selectRolesByUserId(user);
		if(currentRole != null && !currentRole.isEmpty()){
			session.setAttribute("currentRole", currentRole); // 保存当前角色信息
			map.put("success", true);
		}else{
			logger.info("用户角色信息为空");
			map.put("success", false);
			map.put("errorInfo", "用户角色信息为空");
		}
		return map;
	}
	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/userManage")
    @RequiresPermissions(value = {"userManage"})
    public String userManage(Model model) {
		logger.info("用户管理页面");
		//查询所有用户，提供管理配置用户权限级别
		List<User> users = userService.selectUserList();
		model.addAttribute("users",users);
        return "power/user";
    }
	
	@RequestMapping("/sendMessage")
	public void sendMessage() {
		SendSMSUtil ssu = new SendSMSUtil();
		String result = RandomUtil.createRandomInt(6);
		ssu.sendTemplateSingleSMS("86","15834260040", result);
		System.out.println("完成");
    }
	@RequestMapping("/sendSingleMessage")
	public void sendSingleMessage() {
		SendSMSUtil ssu = new SendSMSUtil();
		String result = RandomUtil.createRandomInt(6);
		ssu.sendSingleSMS("86","15834260040",result);
		System.out.println("完成");
    }
	@RequestMapping("/sendVoiceVerificationCode")
	public void sendVoiceVerificationCode() {
		SendSMSUtil ssu = new SendSMSUtil();
		String result = RandomUtil.createRandomInt(6);
		ssu.sendVoiceVerificationCode("86","15834260040", result);
		System.out.println("验证码："+createRandomString());
    }
	public String createRandomString() {
		String result = RandomUtil.createRandomInt(6);
		return result;
    }
	
	
	
	

	

}
