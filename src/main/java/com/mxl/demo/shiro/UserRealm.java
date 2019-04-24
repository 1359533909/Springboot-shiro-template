package com.mxl.demo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;

import com.mxl.demo.entity.User;
import com.mxl.demo.service.UserService;

public class UserRealm extends AuthorizingRealm{
	@Autowired
	private  UserService userService;
	/**
	 *执行授权逻辑
	 *
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		System.out.println("执行授权逻辑--------------------");
//		给资源进行授权
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
//		添加授权内容
//		info.addStringPermission("user:add");
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		User user = userService.findUserByName(principal);
		info.addStringPermission(user.getPerms());
		return info;
	}
	/**
	 * 执行认证逻辑
	 *
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("执行认证逻辑----------------------");
		UsernamePasswordToken token=(UsernamePasswordToken) authcToken;
		//数据库数据
		String principal =token.getUsername();
		char[] credentials = token.getPassword();
		User user = userService.findUserByName(principal);
		String username=user.getUsername();
		String password=user.getPassword();
		Md5Hash str=new Md5Hash(credentials, username);
		String string=str.toString();
		token.setPassword(string.toCharArray());
		
		if(!principal.equals(username)) {
			return null;//shiro底层抛出UnknownAccountException异常
		}
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
