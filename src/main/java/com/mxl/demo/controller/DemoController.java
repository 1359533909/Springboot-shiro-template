package com.mxl.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxl.demo.entity.User;

@Controller
public class DemoController {
	@RequestMapping(value="/index")
	public String toIndex(Model model) {
		User user=new User();
		user.setUsername("mxl");
		model.addAttribute("name",user.getUsername());
		return "index";
	}
	@RequestMapping(value="/toLogin")
	public String toLogin(Model model) {
		return "login";
	}
	@RequestMapping(value="/add")
	public String toAdd(Model model) {
		return "user/add";
	}
	@RequestMapping(value="/update")
	public String toUpdate(Model model) {
		return "user/update";
	}
	@RequestMapping(value="/login")
	public String Login(String username,String password,Model model) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		try {
			subject.login(token);
			return "/user/demo";
		} catch (UnknownAccountException e) {
			// 用户名不存在
			model.addAttribute("msg", "用户名不存在!");
			return "login";
		}catch (IncorrectCredentialsException e) {
			// TODO: handle exception
			model.addAttribute("msg", "密码不正确!");
			return "login";
		}
	}
	@RequestMapping(value="/Unauthor")
	public String unauthor() {
		return "Unauthor";
		
	}
}
