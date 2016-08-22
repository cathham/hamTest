package com.cathham.train.study.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.cathham.common.enums.StateTypeEnum;

/**
 * The Class 管理员.
 */
@Entity
@Table(name = "sys_admin")
public class Admin implements Serializable{

	/** The Constant serialVersionUID. */
	@Transient
	private static final long	serialVersionUID	= 1L;

	/** The admin id. */
	@Id
    @GeneratedValue(generator="UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator", strategy="org.hibernate.id.UUIDGenerator")
	@Column(name = "admin_id",length = 36)
	private String adminId;
	
	/** The 管理员姓名. */
	@Column(name = "admin_name",length = 20)
	private String adminName;
	
	/** The 管理员密码. */
	@Column(length=100)
	private String password;
	
	/** The 管理员手机号，登录账号. */
	@Column(length=11)
	private String telphone;
	
	/** The 管理员邮箱. */
	@Column(length=50)
	private String email;
	
	/** The 创建时间. */
	@Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();
	
	/** The 状态. */
	@Enumerated(EnumType.ORDINAL)
	private StateTypeEnum state = StateTypeEnum.正常;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public StateTypeEnum getState() {
		return state;
	}

	public void setState(StateTypeEnum state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminName=" + adminName + ", password=" + password + ", telphone=" + telphone
				+ ", email=" + email + ", createTime=" + createTime + ", state=" + state + "]";
	}
}
