<!DOCTYPE html>
<html class="loginHtml">
<head>
	<meta charset="utf-8">
	<title>后台管理系统-用户注册</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<!--
	<link rel="icon" href="${basePath!}/static/logo.png">-->
	<link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${basePath!}/static/css/public.css" media="all" />
</head>
<body class="loginBody">
	<form class="layui-form">
		<div class="login_face"><img src="${basePath!}/static/logo.png" class="userAvatar"></div>
		<div class="layui-form-item input-item">
			<label for="username">用户名</label>
			<input type="text" placeholder="请输入用户名" autocomplete="off" id="username" name="username" class="layui-input" lay-verify="required|username">
		</div>
		<div class="layui-form-item input-item">
			<label for="password">密码</label>
			<input type="password" placeholder="请输入密码" autocomplete="off" id="password"  name="password"  class="layui-input" lay-verify="required|password">
		</div>
		<div class="layui-form-item input-item">
			<label for="phone">手机号</label>
			<input type="number" placeholder="请输入手机号" autocomplete="off" id="phone"  name="mobile"  class="layui-input" lay-verify="required|phone">
		</div>
		<div class="layui-form-item input-item">
			<label for="email">邮箱</label>
			<input type="email" placeholder="请输入邮箱号" autocomplete="off" id="email"  name="email"  class="layui-input" lay-verify="required|email">
		</div>
		<div class="layui-form-item input-item">
			<label for="address">地址</label>
			<input type="text" placeholder="请输入地址" autocomplete="off" id="address"  name="address"  class="layui-input" lay-verify="required|address">
		</div>
		<div class="layui-form-item input-item" id="imgCode">
			<label for="code">验证码</label>
			<input type="text" placeholder="请输入验证码" id="imageCode" name="imageCode"  autocomplete="off" id="code" class="layui-input">
			<#--验证码通过接口获取-->
            <img id="imgObj" title="看不清，换一张" src="${basePath!}/drawImage" onclick="changeImg()"/>
		</div>
		<div class="layui-form-item">
			<button class="layui-btn layui-block" lay-filter="login" lay-submit>登录</button>
		</div>
	</form>
	<script type="text/javascript" src="${basePath!}/static/layui/layui.js"></script>
	<script type="text/javascript" src="${basePath!}/static/page/login/login.js"></script>
	<script type="text/javascript" src="${basePath!}/static/js/cache.js"></script>
    <script type="text/javascript" src="${basePath!}/static/js/verifyCode.js"></script>
	


    <script  type="text/javascript">
        layui.use(['jquery', 'layer', 'form'], function () {
        	 var layer = layui.layer,
             form = layui.form;
        	form.verify({
          	  username: function(value, item){ //value：表单的值、item：表单的DOM对象
          	    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
          	      return '用户名不能有特殊字符';
          	    }
          	    if(/(^\_)|(\__)|(\_+$)/.test(value)){
          	      return '用户名首尾不能出现下划线\'_\'';
          	    }
          	    if(/^\d+\d+\d$/.test(value)){
          	      return '用户名不能全为数字';
          	    }
          	  }  
          	  //我们既支持上述函数式的方式，也支持下述数组的形式
          	  //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
          	  ,password: [
          	    /^[\S]{6,12}$/
          	    ,'密码必须6到12位，且不能出现空格'
          	  ]
          })
            //监听表单提交事件
            form.on('submit(login)', function (data) {
            	console.log("----");
                $.post("${basePath!}/user/register", {
                	  web_username: $("#username").val(),
                      password: $("#password").val(),
                      address: $("#address").val(),
                      mobile: $("#phone").val(),
                      imageCode: $("#imageCode").val(),
                      email: $("#email").val()
                  }, function (result) {
                	 if(result.success){
                		 console.log("登录成功");
                	 }else{
                		 console.log("登录失败");
                	 }
                  }
                );
			

                return false;
            });
		})

    </script>
    <script type="text/javascript">
    
    </script>
</body>
</html>


