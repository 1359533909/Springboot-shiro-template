package com.mxl.demo.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mxl.demo.service.UserService;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	/**
	 *创建shirofilterfactorybean
	 *
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
		filterFactoryBean.setSecurityManager(securityManager);
		/**
		 * 常用的内置过滤器
		 * 	anon:无需认证(登录)可以访问
		 * 	authc:必须认证才可以访问
		 * 	user:如果使用rememberme功能可以访问
		 * 	perms:该资源必须得到资源权限才可以访问
		 * 	role:该资源必须得到角色权限才可以访问
		 */
		Map<String, String> filterMap =new LinkedHashMap<>();
		filterMap.put("/add","authc");
		filterMap.put("/update","authc");
		filterMap.put("/add", "perms[user:add]");
		filterMap.put("/update", "perms[user:update]");
		filterFactoryBean.setLoginUrl("/toLogin");
		filterFactoryBean.setFilterChainDefinitionMap(filterMap);
		//未授权页面的跳转
		filterFactoryBean.setUnauthorizedUrl("/Unauthor");
		return filterFactoryBean;
		
	}
	/**
	 * 创建defaultwebsecuritymanager
	 *
	 */
	@Bean(name="defaultWebSecurityManager")
	public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		return securityManager;
		
	}
	/**
	 * 创建realm
	 *
	 */
	@Bean(name="userRealm")
	public UserRealm userRealm() {
		UserRealm userRealm=new UserRealm();
		return userRealm;
	}
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
}
