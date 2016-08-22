package com.cathham.common.utils;

import java.util.List;



/**
 * 
 * @author user
 *
 * @param <T>
 *            需查询具体对象 查询分析辅助类
 */
public class PagerList<T> {

	private int  currentPage;// 当前页
	private int totalPage;// 总页数
	private int total;// 总记录数
	private int pageSize=10;// 每页记录数
	private List<T> data;	// 返回查询结果集

	/*----------------------------------------------------------------------------------+--------------------------------------------------------------------------------------------------*/

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;		
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "PagerList [currentPage=" + currentPage + ", totalPage="
				+ totalPage + ", total=" + total + ", pageSize=" + pageSize
				+ ", data=" + data + "]";
	}
	
}
