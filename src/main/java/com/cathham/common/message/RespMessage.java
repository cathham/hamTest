package com.cathham.common.message;

/**
 * 返回消息
 * 
 * @author chensihan
 */
public class RespMessage {
	

	/**
	 * 0 操作成功
	 */
	public static final int	SUCCESS	= 0;
	public static final String VERSION = "1.0";

	private int				code = SUCCESS;
	/**
	 * 要返回的消息
	 */
	private String			msg;
	/**
	 * 返回的数据
	 */
	private Object			data;
	/**
	 * 消息版本，保留待用
	 */
	private String			version = VERSION;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
	public RespMessage() {
		super();
	}

	/**
	 * 失败，错误消息
	 * @param code
	 * @param msg
	 * @param version
	 */
	public RespMessage(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 成功消息，数据
	 * @param msg
	 * @param data
	 * @param version
	 */
	public RespMessage(Object data) {
		super();
		this.data = data;
	}

	@Override
	public String toString() {
		return "RespMessage [code=" + code + ", msg=" + msg + ", data=" + data + ", version=" + version + "]";
	}

}
