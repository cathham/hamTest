package com.cathham.common.enums;

public enum StateTypeEnum {

	禁用(0),正常(1),过期(2),拉黑(3);
	 
	 private  int  cid;

	private StateTypeEnum(int cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {		
		return String.valueOf ( this.cid );
	}
}
