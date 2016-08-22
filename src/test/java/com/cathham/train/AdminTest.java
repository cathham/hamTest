package com.cathham.train;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cathham.common.enums.StateTypeEnum;
import com.cathham.common.message.RespMessage;
import com.cathham.common.utils.HexUtil;
import com.cathham.train.study.dao.AdminDao;
import com.cathham.train.study.entity.Admin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AdminTest extends AbstractJUnit4SpringContextTests{

	@Autowired
	private AdminDao adminDao;
	
	@Test
	public void testInsertObject() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		Admin admin = new Admin();
		admin.setAdminName("普通管理员");
		admin.setCreateTime(new Date());
		admin.setEmail("444444@qq.com");
		admin.setPassword(HexUtil.getEncryptedPwd("444444"));
		admin.setState(StateTypeEnum.正常);
		admin.setTelphone("15680535079");
		adminDao.save(admin);
	}
	
	@Test
	public void testQueryObject() {
		RespMessage message = null;
		List<Admin> admins = adminDao.findList(null, Restrictions.eq("telphone", "15680535079"));
		if(!admins.isEmpty()){
			message = new RespMessage(admins.toString());
		}else{
			message = new RespMessage(500, "未查询到数据");
		}
		System.out.println(message);
	}
	
	@Test
	public void testDelete(){
		List<Admin> admins = adminDao.findList(null, Restrictions.eq("email", "444444@qq.com"));
		if(!admins.isEmpty()){
			for (Admin admin : admins) {
				adminDao.delete(admin.getAdminId());
			}
		}
		System.out.println("删除成功");
	}
}
