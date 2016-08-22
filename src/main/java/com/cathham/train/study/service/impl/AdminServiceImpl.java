package com.cathham.train.study.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cathham.common.exception.BusinessException;
import com.cathham.common.utils.HexUtil;
import com.cathham.train.study.dao.AdminDao;
import com.cathham.train.study.entity.Admin;
import com.cathham.train.study.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Log logger = LogFactory.getLog(AdminServiceImpl.class);
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public Admin adminLogin(String username, String password) throws BusinessException {
		
		List<Admin> admins = adminDao.findList(null, Restrictions.eq("telphone", username));
		if(!admins.isEmpty()){
			Admin admin = admins.get(0);
			try {
				if(HexUtil.validPasswd(password, admin.getPassword())){
					return admin;
				}else{
					throw new BusinessException("用户名密码错误");
				}
			} catch (NoSuchAlgorithmException e) {
				logger.error("管理员登录密码验证异常：",e.fillInStackTrace());
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				logger.error("管理员登录密码验证异常：",e.fillInStackTrace());
				e.printStackTrace();
			}
			return null;
		}else{
			throw new BusinessException("用户名密码错误");
		}
	}

}
