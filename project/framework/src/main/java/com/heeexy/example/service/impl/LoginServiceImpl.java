package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.config.shiro.UserRealm;
import com.heeexy.example.dao.LoginDao;
import com.heeexy.example.service.LoginService;
import com.heeexy.example.service.PermissionService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: hxy
 * @description: 登录service实现类
 * @date: 2017/10/24 11:53
 */
@Service
public class LoginServiceImpl implements LoginService {

	private Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private SessionDAO sessionDAO;
	/**
	 * 登录表单提交
	 */
	@Override
	public JSONObject authLogin(JSONObject jsonObject) {
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
		JSONObject info = new JSONObject();
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			currentUser.login(token);
			info.put("result", "success");
	        // 剔除其他此账号在其它地方登录
	        List<Session> loginedList = getLoginedSession(SecurityUtils.getSubject());
	        for (Session session : loginedList) {
//	            session.stop();
	        	Subject s = new Subject.Builder().session(session).buildSubject();
	        	s.logout();
	        }

		} catch (AuthenticationException e) {
			info.put("result", "fail");
		}
		return CommonUtil.successJson(info);
	}
    private List<Session> getLoginedSession(Subject currentUser) {
        Collection<Session> list =sessionDAO.getActiveSessions();
        List<Session> loginedList = new ArrayList<Session>();
        String loginUser = (String) currentUser.getPrincipal();
        for (Session session : list) {
 
            Subject s = new Subject.Builder().session(session).buildSubject();
 
            if (s.isAuthenticated()) {
            	String user = (String) s.getPrincipal();
 
                if (user.equalsIgnoreCase(loginUser)) {
                    if (!session.getId().equals(
                            currentUser.getSession().getId())) {
                        loginedList.add(session);
                    }
                }
            }
        }
        return loginedList;
    }
	/**
	 * 根据用户名和密码查询对应的用户
	 */
	@Override
	public JSONObject getUser(String username, String password) {
		return loginDao.getUser(username, password);
	}

	/**
	 * 查询当前登录用户的权限等信息
	 */
	@Override
	public JSONObject getInfo() {
		//从session获取用户信息
		Session session = SecurityUtils.getSubject().getSession();
		JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
		String username = userInfo.getString("username");
		JSONObject info = new JSONObject();
		JSONObject userPermission = permissionService.getUserPermission(username);
		logger.info(session.getId().toString());
		session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
		info.put("userPermission", userPermission);
		return CommonUtil.successJson(info);
	}

	/**
	 * 退出登录
	 */
	@Override
	public JSONObject logout() {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
		} catch (Exception e) {
		}
		return CommonUtil.successJson();
	}
}
