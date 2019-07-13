package com.qishiyi.domain;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
	//��ǰҳ
	int currentPage;
	//ÿҳ��ʾ������
	int countOnePage;
	//������
	long totalCount;
	//��ҳ��
	int totalPage;
	//��ǰҳ��ʾ�����ݼ���
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
