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

	//查找所有的分类
	public List<Category> findAllCategory(List<Category> categoryList) throws SQLException {
		return productDao.findAllCategory();	
	}
	
	//查找首页的热门商品
	public List<Product> findIndexHotPro() throws SQLException {
		return productDao.findIndexHotPro();
	}

	//查找首页最新商品
	public List<Product> findIndexNewPro() throws SQLException {
		return productDao.findIndexNewPro();
	}
	
	//查找浏览记录
	public List<Product> findLookRecordProduct(String[] pidArray) {
		//因为要根据pid查询很多的product对象，所以只能一次查询一个product，
		//要通过循环pid数组来查询数据库，并将每一次的查询结果添加到一个list集合中
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
	
	//通过首页菜单栏查找商品
	public PageBean<Product> findProByMenu(String cid, String currentPage) {
		PageBean<Product> pb=new PageBean<Product>();
		pb.setCurrentPage(Integer.parseInt(currentPage));//1.设置当前页码
		pb.setCountOnePage(12);//2.设置每页显示的数据量
		long totalCount=findTotalCount(cid);
		pb.setTotalCount(totalCount);//3.设置总数据量，要去数据库中查询
		int totalPage=(int) Math.ceil(1.0*totalCount/12);
		pb.setTotalPage(totalPage);//4.设置总页数
		pb.setList(fintProList(cid,currentPage));//5.设置当前页的数据集合
		return pb;
	}
	
	//分页查找商品集合
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

	//分页查找商品总条数
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
	
	//查找商品详细信息
	public Product findProInfo(String pid) throws SQLException {
		return productDao.findProInfo(pid);
	}
	
	//首页搜索框查找商品
	public List<Object> searchInfoMenu(String searchInfo) throws SQLException {
		return productDao.searchInfoMenu(searchInfo);
	}

	public void submitOrder(Order order) {
		try {
			//开启事务，此处开启事务的目的是要保证Order和OrderItem同时提交或同时不提交
			DataSourceUtils.startTransaction();
			productDao.addOrder(order);
			productDao.addOrderItem(order);
		} catch (Exception e) {
			//回滚
			try {
				DataSourceUtils.rollBack();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			//提交事务、释放资源
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//确认订单
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
