package com.qishiyi.domain;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
	//当前页
	int currentPage;
	//每页显示的条数
	int countOnePage;
	//总条数
	long totalCount;
	//总页数
	int totalPage;
	//当前页显示的数据集合
	List<T> list=new ArrayList<T>();
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCountOnePage() {
		return countOnePage;
	}
	public void setCountOnePage(int countOnePage) {
		this.countOnePage = countOnePage;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
