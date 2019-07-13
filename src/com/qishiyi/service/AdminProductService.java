package com.qishiyi.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.qishiyi.dao.AdminProductDao;
import com.qishiyi.domain.Category;
import com.qishiyi.domain.Order;
import com.qishiyi.domain.PageBean;
import com.qishiyi.domain.Product;
import com.qishiyi.vo.Condition;

public class AdminProductService {
	private AdminProductDao adminProductDao = null;

	public AdminProductService() {
		adminProductDao = new AdminProductDao();
	}

	public void delPro(String pid) throws SQLException {
		adminProductDao.delPro(pid);
	}

	public List<Category> findAllCategory() throws SQLException {
		return adminProductDao.findAllCategory();
	}

	public List<Product> findAllPro() throws SQLException {
		return adminProductDao.findAllPro();

	}

	public void addNewPro(Product pro) throws SQLException {
		adminProductDao= new AdminProductDao();
		adminProductDao.addNewPro(pro);
	}

	public Product updateProUi(String pid) throws SQLException {
		adminProductDao= new AdminProductDao();
		return adminProductDao.updateProUi(pid);

	}

	public List<Category> updateProCateUi() throws SQLException {
		adminProductDao = new AdminProductDao();
		return adminProductDao.findAllCategory();
	}

	public void updatePro(Product pro) throws SQLException {
		adminProductDao = new AdminProductDao();
		adminProductDao.updatePro(pro);

	}

	public List<Product> searchPro(Condition con) throws SQLException {
		adminProductDao= new AdminProductDao();
		return adminProductDao.searchPro(con);

	}

	public List<Order> findAllOrder() throws SQLException {
		adminProductDao=new AdminProductDao();
		return adminProductDao.findAllOrder();
	}

	public List<Map<String, Object>> findOrderInfoByOid(String orderId) throws SQLException {
		adminProductDao=new AdminProductDao();
		return adminProductDao.findOrderInfoByOid(orderId);
	}
}
