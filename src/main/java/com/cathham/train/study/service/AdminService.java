package com.cathham.train.study.service;

import com.cathham.common.exception.BusinessException;
import com.cathham.train.study.entity.Admin;

public interface AdminService {

	/**
	 * 管理员登录
	 * @param username
	 * @param password
	 * @throws BusinessException
	 */
	public Admin adminLogin(String username,String password) throws BusinessException;
}
