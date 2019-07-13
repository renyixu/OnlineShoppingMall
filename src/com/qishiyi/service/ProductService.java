package com.qishiyi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qishiyi.dao.ProductDao;
import com.qishiyi.domain.Category;
import com.qishiyi.domain.Order;
import com.qishiyi.domain.OrderItem;
import com.qishiyi.domain.PageBean;
import com.qishiyi.domain.Product;
import com.qishiyi.utils.DataSourceUtils;

public class ProductService {
	private ProductDao productDao=null;
	public ProductService(){
		productDao=new ProductDao();
	}

	//�������еķ���
	public List<Category> findAllCategory(List<Category> categoryList) throws SQLException {
		return productDao.findAllCategory();	
	}
	
	//������ҳ��������Ʒ
	public List<Product> findIndexHotPro() throws SQLException {
		return productDao.findIndexHotPro();
	}

	//������ҳ������Ʒ
	public List<Product> findIndexNewPro() throws SQLException {
		return productDao.findIndexNewPro();
	}
	
	//���������¼
	public List<Product> findLookRecordProduct(String[] pidArray) {
		//��ΪҪ����pid��ѯ�ܶ��product��������ֻ��һ�β�ѯһ��product��
		//Ҫͨ��ѭ��pid��������ѯ���ݿ⣬����ÿһ�εĲ�ѯ�����ӵ�һ��list������
		List<Product> proList=new ArrayList<Product>();
		for(String pid:pidArray){
			try {
				proList.add(productDao.findPro(pid));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return proList;
	}
	
	//ͨ����ҳ�˵���������Ʒ
	public PageBean<Product> findProByMenu(String cid, String currentPage) {
		PageBean<Product> pb=new PageBean<Product>();
		pb.setCurrentPage(Integer.parseInt(currentPage));//1.���õ�ǰҳ��
		pb.setCountOnePage(12);//2.����ÿҳ��ʾ��������
		long totalCount=findTotalCount(cid);
		pb.setTotalCount(totalCount);//3.��������������Ҫȥ���ݿ��в�ѯ
		int totalPage=(int) Math.ceil(1.0*totalCount/12);
		pb.setTotalPage(totalPage);//4.������ҳ��
		pb.setList(fintProList(cid,currentPage));//5.���õ�ǰҳ�����ݼ���
		return pb;
	}
	
	//��ҳ������Ʒ����
	private List<Product> fintProList(String cid, String currentPage)  {
		List<Product> listPro=null;
		try {
			listPro= productDao.finrProList(cid,currentPage);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listPro;
	}

	//��ҳ������Ʒ������
	public long findTotalCount(String cid){
		long count=0;
		try {
			count=productDao.findTotalCount(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	//������Ʒ��ϸ��Ϣ
	public Product findProInfo(String pid) throws SQLException {
		return productDao.findProInfo(pid);
	}
	
	//��ҳ�����������Ʒ
	public List<Object> searchInfoMenu(String searchInfo) throws SQLException {
		return productDao.searchInfoMenu(searchInfo);
	}

	public void submitOrder(Order order) {
		try {
			//�������񣬴˴����������Ŀ����Ҫ��֤Order��OrderItemͬʱ�ύ��ͬʱ���ύ
			DataSourceUtils.startTransaction();
			productDao.addOrder(order);
			productDao.addOrderItem(order);
		} catch (Exception e) {
			//�ع�
			try {
				DataSourceUtils.rollBack();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			//�ύ�����ͷ���Դ
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//ȷ�϶���
	public void confirmOrder(Order order) throws SQLException {
		productDao.confirmOrder(order);
		
	}

	public void changeOrderState(String r6_Order) throws SQLException {
		productDao.changeOrderState(r6_Order);
		
	}

	public List<Order> searchAllOrder(String id) throws SQLException {
		return productDao.searchAllOrder(id);
		
	}

	public List<Map<String, Object>> searchAllOrderItem(String orderId) throws SQLException {
		return productDao.searchAllOrderItem(orderId);
	}
}
