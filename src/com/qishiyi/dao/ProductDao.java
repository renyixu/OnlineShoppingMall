package com.qishiyi.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.qishiyi.domain.Category;
import com.qishiyi.domain.Order;
import com.qishiyi.domain.OrderItem;
import com.qishiyi.domain.Product;
import com.qishiyi.utils.DataSourceUtils;

public class ProductDao {

	// �������еķ���
	public List<Category> findAllCategory() throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner qr = new QueryRunner(ds, true);
		String sql = "select * from category";
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

	// ������ҳ������Ʒ
	public List<Product> findIndexHotPro() throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner qr = new QueryRunner(ds, true);
		String sql = "select * from product where is_hot=? limit 0,9";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), 1);
	}

	// ������ҳ������Ʒ
	public List<Product> findIndexNewPro() throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner qr = new QueryRunner(ds, true);
		String sql = "select * from product order by pdate desc limit 0,9";
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	// ���������¼
	public Product findPro(String pid) throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner qr = new QueryRunner(ds, true);
		String sql = "select * from product where pid=?";
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	// ��ҳ�˵�����ҳ������Ʒ
	// ��ѯ����������
	public long findTotalCount(String cid) throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner qr = new QueryRunner(ds, true);
		String sql = "select count(*) from product where cid=?";
		return (long) qr.query(sql, new ScalarHandler(), cid);
	}

	// ������Ʒ����
	public List<Product> finrProList(String cid, String currentPage)
			throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner qr = new QueryRunner(ds, true);
		int para = (Integer.parseInt(currentPage) - 1) * 12;
		String sql = "select * from product where cid=? limit ?,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid,para,12);
	}

	// ������Ʒ��ϸ��Ϣ
	public Product findProInfo(String pid) throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner qr = new QueryRunner(ds, true);
		String sql = "select * from product where pid=?";
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	// ͨ����ҳ�����������Ʒ����
	public List<Object> searchInfoMenu(String searchInfo) throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner qr = new QueryRunner(ds, true);
		String sql = "select pname from product where pname like ? limit 0,10";
		return qr.query(sql, new ColumnListHandler("pname"), "%" + searchInfo
				+ "%");
	}

	// ��Ӷ���
	public void addOrder(Order order) throws SQLException {
		Connection conn = DataSourceUtils.getCurrentConnection();
		QueryRunner qr = new QueryRunner(true);
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		qr.update(conn, sql, order.getOrderId(), format.format(order.getDate()),
				order.getTotalPrice(), order.getStates(), order.getAddre(),
				order.getName(), order.getTelephone(), order.getUser().getId());
	}

	// ��Ӷ�����
	public void addOrderItem(Order order) throws SQLException {
		Connection conn = DataSourceUtils.getCurrentConnection();
		QueryRunner qr = new QueryRunner(true);
		String sql = "insert into orderitem values(?,?,?,?,?)";
		for (OrderItem item : order.getItemList()) {
			qr.update(conn, sql, item.getOrderItemId(), item.getProduct()
					.getPid(), item.getItemCount(), item.getTotalPrice(), item
					.getOrder().getOrderId());
		}
	}

	//ȷ�϶���
	public void confirmOrder(Order order) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="update orders set addre=?,name=?,telephone=? where orderId=?";
		qr.update(sql,order.getAddre(),order.getName(),order.getTelephone(),order.getOrderId());
		
	}

	public void changeOrderState(String r6_Order) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="update orders set states=1 where orderId=?";
		qr.update(sql,r6_Order);
	}

	public List<Order> searchAllOrder(String id) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="select * from orders where uid=?";
		return qr.query(sql, new BeanListHandler<Order>(Order.class),id);
	}

	public List<Map<String, Object>> searchAllOrderItem(String orderId) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="select p.pimage,p.pname,p.shop_price,i.itemCount,i.totalPrice from product p,orderitem i where i.orderId=? and p.pid=i.pid";
		return qr.query(sql, new MapListHandler(),orderId);
	}

}
