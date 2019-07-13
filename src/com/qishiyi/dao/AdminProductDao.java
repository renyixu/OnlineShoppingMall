package com.qishiyi.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.qishiyi.domain.Category;
import com.qishiyi.domain.Order;
import com.qishiyi.domain.Product;
import com.qishiyi.vo.Condition;

public class AdminProductDao {
	public void delPro(String pid) throws SQLException {
		ComboPooledDataSource dataSource =new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(dataSource,true);
		String sql="delete from product where pid=?";
		qr.update(sql,pid);
	}
	public List<Category> findAllCategory() throws SQLException {
		ComboPooledDataSource dataSource=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(dataSource,true);
		String sql="select * from category";
		List<Category> list=qr.query(sql,new BeanListHandler<Category>(Category.class));
		return list;
	}
	public List<Product> findAllPro() throws SQLException {
		//你好
		ComboPooledDataSource dataSource=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(dataSource,true);
		List<Product> list=null;
			String sql="select * from product";
			list=qr.query(sql,new BeanListHandler<Product>(Product.class));
			return list; 
	}

	public void addNewPro(Product pro) throws SQLException {
		ComboPooledDataSource dataSource=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(dataSource,true);
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		Object[] obj={pro.getPid(),pro.getPname(),pro.getMarket_price(),pro.getShop_price(),pro.getPimage(),pro.getPdate(),pro.getIs_hot(),pro.getPdesc(),pro.getPflag(),pro.getCid()};
		qr.update(sql,obj);
	}
	public Product updateProUi(String pid) throws SQLException {
		ComboPooledDataSource dataSource=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(dataSource,true);
		String sql="select * from product where pid=?";
		return qr.query(sql, new BeanHandler<Product>(Product.class),pid);
		
	}

	

	public void updatePro(Product pro) throws SQLException {
		ComboPooledDataSource dataSource=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(dataSource,true);
		String sql="update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
		Object []obj={pro.getPname(),pro.getMarket_price(),pro.getShop_price(),pro.getPimage(),pro.getPdate(),pro.getIs_hot(),pro.getPdesc(),pro.getPflag(),pro.getCid(),pro.getPid()};
		qr.update(sql,obj);
	}
	public List<Product> searchPro(Condition con) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		List<String> list=new ArrayList<String>();
		StringBuffer sql=new StringBuffer("select * from product where 1=1");
		if(con.getName()!=null&&!con.getName().trim().equals("")){
			sql.append(" and pname like ?");
			list.add("%"+con.getName()+"%");
		}
		if(con.getIsHot()!=null&&!con.getIsHot().equals("")){
			sql.append(" and is_hot=?");
			list.add(con.getIsHot());
		}
		if(con.getCategory()!=null&&!con.getCategory().equals("")){
			sql.append(" and cid=?");
			list.add(con.getCategory());
		}
		return qr.query(sql.toString(), new BeanListHandler<Product>(Product.class),list.toArray());
	}
	public List<Order> findAllOrder() throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="select * from orders";
		return qr.query(sql,new BeanListHandler<Order>(Order.class));
	}
	
	public List<Map<String, Object>> findOrderInfoByOid(String orderId) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="select p.pimage,p.pname,p.shop_price,i.itemCount,i.totalPrice from product p,orderitem i where i.orderId=? and p.pid=i.pid";
		return qr.query(sql,new MapListHandler(),orderId);
	}
}
