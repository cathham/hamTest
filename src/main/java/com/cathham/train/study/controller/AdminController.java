package com.cathham.train.study.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cathham.common.exception.BusinessException;
import com.cathham.common.message.RespMessage;
import com.cathham.common.utils.ResponseUtils;
import com.cathham.train.study.entity.Admin;
import com.cathham.train.study.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	
	@RequestMapping("adminLogin")
	public ModelAndView adminLogin(){
		return new ModelAndView("admin/login");
	}
	
	
	@RequestMapping("validateLogin")
	public void validateLogin(String username,String password,HttpServletResponse response){
		RespMessage message = null;
		try {
			Admin admin = adminService.adminLogin(username, password);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tel", admin.getTelphone());
			map.put("name", admin.getAdminName());
			map.put("email", admin.getEmail());
			map.put("adminId", admin.getAdminId());
			message = new RespMessage(map);
		} catch (BusinessException e) {
			message = new RespMessage(500, e.getMessage());
		}
		ResponseUtils.renderJson(response, JSON.toJSONString(message));
	}
	
	
	@RequestMapping("test/getData")
	@ResponseBody
	public RespMessage testInterface(HttpServletResponse response){
		RespMessage message = null;
		try {
			Admin admin = adminService.adminLogin("15680535079", "111111");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tel", admin.getTelphone());
			map.put("name", admin.getAdminName());
			map.put("email", admin.getEmail());
			map.put("adminId", admin.getAdminId());
			message = new RespMessage(map);
		} catch (BusinessException e) {
			message = new RespMessage(500, e.getMessage());
		}
		return message;
	}
}
