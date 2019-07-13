package com.qishiyi.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.qishiyi.domain.Cart;
import com.qishiyi.domain.CartItem;
import com.qishiyi.domain.Category;
import com.qishiyi.domain.Order;
import com.qishiyi.domain.OrderItem;
import com.qishiyi.domain.PageBean;
import com.qishiyi.domain.Product;
import com.qishiyi.domain.User;
import com.qishiyi.service.ProductService;
import com.qishiyi.utils.CommonUtils;
import com.qishiyi.utils.DoubleChangeUtils;
import com.qishiyi.utils.JedisUtils;
import com.qishiyi.utils.PaymentUtil;
@SuppressWarnings("all")
public class ProductServlet extends HttpServlet {
//2017-12-12 12:12:12
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		ProductService productService = new ProductService();
		// ��ȡmethodName
		String methodName = request.getParameter("methodName");
		
		switch (methodName) {
		case "findIndexPro":
			findIndexPro(request, response, productService);
			break;
		case "findAllCategory":
			findAllCategory(request, response, productService);
			break;
		case "findProByMenuPage":
			findProByMenuPage(request, response, productService);
			break;
		case "searchInfoMenu":
			searchInfoMenu(request, response, productService);
			break;
		case "lookRecord":
			lookRecord(request, response, productService);
			break;
		case "findProInfo":
			findProInfo(request, response, productService);
			break;
		case "addCart":
			addCart(request, response);
			break;
		case "showCartList":
			showCartList(request, response);
			break;
		case "deleteOneCartItem":
			deleteOneCartItem(request, response);
			break;
		case "deleteAllCartItem":
			deleteAllCartItem(request, response);
			break;
		case "submitOrder":
			submitOrder(request, response, productService);
			break;
		case "confirmOrder":
			confirmOrder(request,response,productService);
			break;
		case "showOrderList":
			showOrderList(request, response, productService);
			break;
		default:
			break;
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// �������еķ�����ʾ�ڲ˵�����
	public void findAllCategory(HttpServletRequest request,
			HttpServletResponse response, ProductService productService)
			throws IOException {
		List<Category> categoryList = null;
		// ����redis�����в������ݣ���������ݣ��Ͳ�ȥ���ݿ��в��ң������ȥ���ݿ��в���
		Jedis jedis = JedisUtils.getJedis();
		String categoryListJson = jedis.get("categoryListJson");// ��redis�в�������
		System.out.println(categoryListJson);
		// ���redis�е�����Ϊ�գ���ȥ���ݿ��в��Ҳ����ҵ������ݻ��浽redis
		if (categoryListJson == null) {
			System.out.println("redis������û�����ݣ������ݿ��в���...");
			try {
				categoryList = productService.findAllCategory(categoryList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Gson gson = new Gson();
			categoryListJson = gson.toJson(categoryList);// ��ֵ����categoryListJson�����仺�浽redis
			jedis.set("categoryListJson", categoryListJson);
		}
		// ����Ϊ�գ���redis��������д��ҳ����
		response.getWriter().write(categoryListJson);
	}

	// ������ҳproduct����
	public void findIndexPro(HttpServletRequest request,
			HttpServletResponse response, ProductService productService)
			throws IOException, ServletException {
		// ����������Ʒ
		List<Product> indexHotProList = null;
		try {
			indexHotProList = productService.findIndexHotPro();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ����������Ʒ������ʱ�併������
		List<Product> indexNewProList = null;
		try {
			indexNewProList = productService.findIndexNewPro();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ��request���д洢
		request.setAttribute("hotPro", indexHotProList);
		request.setAttribute("newPro", indexNewProList);
		// ת����indexҳ��
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	// ͨ���˵�����İѲ˵�����product
	public void findProByMenuPage(HttpServletRequest request,
			HttpServletResponse response, ProductService productService)
			throws ServletException, IOException {
		// ��ȡ����cid,currentPage
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");
		PageBean<Product> pageBean = null;
		pageBean = productService.findProByMenu(cid, currentPage);
		// �洢������
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		// ת����product_listҳ��
		request.getRequestDispatcher("/product_list.jsp").forward(request,
				response);
	}

	// ����product����ϸ��Ϣ
	public void findProInfo(HttpServletRequest request,
			HttpServletResponse response, ProductService productService)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");// ��ǰ���ʵ�product��pid
		Product pro = null;
		try {
			pro = productService.findProInfo(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String cookiePid = pid;
		// ��ȡ����Я���Ĳ���
		Cookie[] cookies = request.getCookies();
		// �ж�cookies�Ƿ�Ϊ��
		if (cookies != null) {
			// cookie��Ϊ��
			// ѭ��cookie����
			for (Cookie cookie : cookies) {
				if ((cookie.getName()).equals("lookrecordcookie")) {
					String val = cookie.getValue();// ��ȡƥ���cookie��ֵ,����1������1;2;3;4;5
					String[] valArray = val.split("#");// ���val
					List<String> list = Arrays.asList(valArray);// ���ַ�������ת����List����
					// ѡ��ʹ��LinkedList����ΪLinkedLIst����һ������ΪaddFirst()
					LinkedList<String> linkedList = new LinkedList<String>(list);
					if (linkedList.contains(pid)) {
						linkedList.remove(pid);
					}
					linkedList.addFirst(pid);
					// ��LinkedListƴ�ӳ��ַ���
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < linkedList.size(); i++) {
						sb.append(linkedList.get(i));
						sb.append("#");
					}
					cookiePid = sb.substring(0, sb.length() - 1);
				}
			}
		}
		Cookie cookie = new Cookie("lookrecordcookie", cookiePid);
		cookie.setPath(request.getContextPath());// ����cookie��Я��·��
		// ��cookie����ʱ��
		response.addCookie(cookie);
		request.setAttribute("product", pro);// ��ͻ��˷���cookie
		request.getRequestDispatcher("/product_info.jsp").forward(request,
				response);
	}

	// �鿴�����¼
	public void lookRecord(HttpServletRequest request,
			HttpServletResponse response, ProductService productService)
			throws ServletException, IOException {
		// ��ȡrequestЯ����cookie
		Cookie[] cookies = request.getCookies();
		String pid = null;// product��id
		List<Product> list = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("lookrecordcookie")) {
					pid = cookie.getValue();// pid����1;2;3;4;5
					// ��pid���Ϊ����
					String[] pidArray = pid.split("#");
					list = productService.findLookRecordProduct(pidArray);// list���������product�ļ���
				}
			}
		}
		request.setAttribute("lookRecord", list);
		// ͨ��ת������ʾ��������ҳ��
		request.getRequestDispatcher("/lookrecord.jsp").forward(request,
				response);
	}

	// �������������product
	public void searchInfoMenu(HttpServletRequest request,
			HttpServletResponse response, ProductService productService)
			throws IOException {
		String searchInfo = request.getParameter("searchContent");
		List<Object> list = null;
		try {
			list = productService.searchInfoMenu(searchInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��json���߽�����JSON��ʽ���ַ���
		Gson gson = new Gson();
		String returnStr = gson.toJson(list);
		response.getWriter().write(returnStr);
	}

	// ���빺�ﳵ
	public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��ȡ�������������
		Map<String, String[]> map = request.getParameterMap();
		// ����Cart����
		// 1.����Product
		Product product = new Product();
		product.setPid((map.get("pid"))[0]);
		product.setPname((map.get("pname"))[0]);
		product.setPimage((map.get("pimage"))[0]);
		double shop_price = Double.parseDouble(map.get("shop_price")[0]);
		DecimalFormat format=new DecimalFormat("#0.00");
		String shop_price_str=format.format(shop_price);
		shop_price=Double.parseDouble(shop_price_str);
		// ��Ʒ����
		product.setShop_price(shop_price);
		// 2.����CartItem
		CartItem cartItem = new CartItem();
		cartItem.setItemId(CommonUtils.setId());//��cartItem����id
		cartItem.setProduct(product);
		String buyNum=map.get("buyNum")[0];
		if("".equals(buyNum)){
			buyNum="1";
		}
		int buyNumber = Integer.parseInt(buyNum);//��������
		cartItem.setBuyNumber(buyNumber);
		double totalPrice=shop_price*buyNumber;
		String totalPrice_str=format.format(totalPrice);
		totalPrice=Double.parseDouble(totalPrice_str);
		cartItem.setTotalPrice(totalPrice);//����С��
		// ����Cart
		Cart cart = null;
		List<CartItem> cartItemList = null;

		// ����seeeion
		HttpSession session = request.getSession();// ���д���ִ�к�sessionһ����Ϊ�գ����Բ���Ҫ�ж�session�Ƿ�Ϊ��
		// ��session�л�ȡCart���󣬵�һ�μ��빺�ﳵCart����Ϊ��
		if (session.getAttribute("cart") != null) {
			cart = (Cart) session.getAttribute("cart");// ��ȡԭ�ȵ�cart����
			cartItemList = cart.getCartItemList();
		}
		// ��session�л�ȡ����cart�����ǿյģ�����һ�μ��빺�ﳵ
		else {
			cart = new Cart();
			cartItemList = new ArrayList<CartItem>();
		}
		cartItemList.add(cartItem);
		cart.setCartItemList(cartItemList);//1.1����cart�����е�CartItemList
		// �������е�cartList�е�cartItem����ȡ���е���ƷС�ƣ����������ƷС�Ƶ��ܺ�
		double total = 0;
		for (CartItem item : cartItemList) {
			total = total + item.getTotalPrice();
		}
		String str_total=format.format(total);
		total=Double.parseDouble(str_total);
		cart.setTotalPrice(total);//1.2���ù��ﳵ��������Ʒ���ܽ��
		session.setAttribute("cart", cart);//��cart����session
		//��cartItemList�ĳ��ȷ��ص�ҳ��
		String str=cartItemList.size()+"";
		String returnData="{\"cartItemList\":"+str+"}";
		response.getWriter().write(returnData);
	}
	
	//��ʾ���ﳵ�б�
	public void showCartList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session=request.getSession();
		Cart cart=(Cart) session.getAttribute("cart");
		if(cart!=null){
		List<CartItem> list=cart.getCartItemList();
		request.setAttribute("cartItemList", list);
		request.setAttribute("totalPrice", cart.getTotalPrice());
		//ת��
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
	}
	
	//ɾ�����ﳵ�еĵ���cartItem
	public void deleteOneCartItem(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		//��ȡ��������е�cartItemId
		String cartItemId=request.getParameter("cartItemId");
		HttpSession session=request.getSession();
		//��session�л�ȡcart����
		Cart cart=(Cart) session.getAttribute("cart");
		List<CartItem> cartItemList=cart.getCartItemList();
		//����listɾ��ָ��id��cartItem
		for(int i=0;i<cartItemList.size();i++){
			if(cartItemId.equals(cartItemList.get(i).getItemId())){
				cartItemList.remove(cartItemList.get(i));
				i--;//��Ϊλ�÷����ı䣬����Ҫ����i�Ĵ�С
			}
		}
		//�����ܽ��
		double total = 0;
		for (CartItem item : cartItemList) {
			total = total + item.getTotalPrice();
		}
		cart.setTotalPrice(total);//���ù��ﳵ��������Ʒ���ܽ��
		session.setAttribute("cart",cart);
		request.setAttribute("cartItemList",cartItemList);
		request.setAttribute("totalPrice", cart.getTotalPrice());
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}
	
	//ɾ�����ﳵ�е�������Ʒ
	public void deleteAllCartItem(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session=request.getSession();
		//��session�л�ȡcart����
		Cart cart=(Cart) session.getAttribute("cart");
		List<CartItem> cartItemList=cart.getCartItemList();
		for(int i=0;i<cartItemList.size();i++){
			cartItemList.remove(i);
			i--;
		}
		session.setAttribute("cart", cart);
		request.setAttribute("cartItemList",cartItemList);
		request.getRequestDispatcher("/cart.jsp").forward(request,response);
	}
	
	//�ύ����
	public void submitOrder(HttpServletRequest request,HttpServletResponse response,ProductService productService) throws IOException{
		//1.�ж��û��Ƿ��¼
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("login");
		if(user==null){
			try {
				//���û�е�¼���ض�����ת����¼ҳ��
				response.sendRedirect(request.getContextPath()+"/login.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return; //û�е�¼����ת����¼ҳ���Ͳ�ִ����
		}
		
		//2.��װ����Order
		Order order=new Order();
		
		//private String orderId; //id��--->sessionId+�µ�ʱ��
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHH:mm:ss"); //HH��ʾ24Сʱ��
		Date date=new Date();
		String sessionId=session.getId();
		String orderTime=format.format(date);
		order.setOrderId(sessionId+orderTime);
		//317B8F9011A64B18B8C406151526EEC22017092222:33:56
		//private Date date; //�µ�ʱ��
		order.setDate(new Date());
		
		//private double totalPrice; //�������ܼ�
		Cart cart=(Cart)session.getAttribute("cart"); //��session�л�ȡCart����
		order.setTotalPrice(cart.getTotalPrice());
		
		//private User user; //�˶��������ĸ��û�
		order.setUser(user);
		
		//private int state; //����״̬
		order.setStates(0);
		//private String address; //�ջ��ַ
		order.setAddre("");
		//private String name; //�ջ�������
		order.setName("");
		//private String telephone; //�ջ��˵绰
		order.setTelephone("");
		//private List<OrderItem> itemList; //�˶����еĶ�����
		
		List<CartItem> cartItemList= cart.getCartItemList();
		List<OrderItem> orderItemList=new ArrayList<OrderItem>();
		int i=1;
		for(CartItem cartItem:cartItemList){
			OrderItem orderItem=new OrderItem();
			//	private String orderItemId; //�������id
			orderItem.setOrderItemId(sessionId+orderTime+i);
			//System.out.println("���-->"+sessionId+orderTime+i);
			//private Product product; //Product����
			orderItem.setProduct(cartItem.getProduct());
			//private int itemCount; //��������
			orderItem.setItemCount(cartItem.getBuyNumber());
			//private double totalPrice; //С��
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			//private Order order; //�˶����������ĸ�����
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
			i++;
		}
		order.setItemList(orderItemList);
		//��service�ύOrder����,��order��Ϣ�������ݿ�
		productService.submitOrder(order);
		
		//��Order��Ϣ�浽session�У���Ϊ������תҳ��ʱ��Ҫʹ���ض���Ҫʹ��ַ���仯��
		session.setAttribute("order", order);
		//��ת����ϸ����ҳ�棬��Ϊ����Ҫ��ȡ�ջ��˵���Ϣ
		response.sendRedirect(request.getContextPath()+"/order_info.jsp");
	}
	
	//ȷ�϶���
	public void confirmOrder(HttpServletRequest request,HttpServletResponse response,ProductService productService) throws IOException{
		//1.�����ջ�����Ϣ
		//��ȡ����
		Map<String,String[]> map=request.getParameterMap();
		//��װOrder
		Order order=new Order();
		try {
			BeanUtils.populate(order, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //����ȡ��������ӳ�䵽Order��
		
		try {
			productService.confirmOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//2.����֧��,֧���ɹ���Ӧ��ȥ���ݿ��ｫ������state�޸ĳ�1
		// ��� ֧�������������
				//String orderid = request.getParameter("orderid");
		//��ȡorderId�ӷ�װ�е�Order�л�ȡ��Ϊ��ȫ����Ϊ�����ҳ���л�ȡ����ô���б��۸ĵĿ��ܣ�����ȫ
				String orderid=order.getOrderId(); 
				//String money = request.getParameter("money");
				String money="0.01"; //�˴���Ϊ�ǲ��ԣ����Խ�Ǯ�趨Ϊ0.01
				// ��ȡ��ѡ������
				String pd_FrpId = request.getParameter("pd_FrpId");

				// ����֧����˾��Ҫ��Щ����
				String p0_Cmd = "Buy";
				String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
				String p2_Order = orderid;
				String p3_Amt = money;
				String p4_Cur = "CNY";
				String p5_Pid = "";
				String p6_Pcat = "";
				String p7_Pdesc = "";
				// ֧���ɹ��ص���ַ ---- ������֧����˾����ʡ��û�����
				// ������֧�����Է�����ַ
				String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
				String p9_SAF = "";
				String pa_MP = "";
				String pr_NeedResponse = "1";
				// ����hmac ��Ҫ��Կ
				String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
						"keyValue");
				String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
						p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
						pd_FrpId, pr_NeedResponse, keyValue);
				
				
				String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
								"&p0_Cmd="+p0_Cmd+
								"&p1_MerId="+p1_MerId+
								"&p2_Order="+p2_Order+
								"&p3_Amt="+p3_Amt+
								"&p4_Cur="+p4_Cur+
								"&p5_Pid="+p5_Pid+
								"&p6_Pcat="+p6_Pcat+
								"&p7_Pdesc="+p7_Pdesc+
								"&p8_Url="+p8_Url+
								"&p9_SAF="+p9_SAF+
								"&pa_MP="+pa_MP+
								"&pr_NeedResponse="+pr_NeedResponse+
								"&hmac="+hmac;

				//�ض��򵽵�����֧��ƽ̨
				response.sendRedirect(url);
	}
	
	//��ѯ�����б�
	public void showOrderList(HttpServletRequest request,HttpServletResponse response,ProductService productService) throws ServletException, IOException{
		//����û���û�е�¼
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("login");
		List<Order> orderList=null;
		//1.�������ڵ�ǰ�û�������order������orders��
		if(user!=null){
			try {
				orderList=productService.searchAllOrder(user.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//�û�δ��¼
		else{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		
		List<Map<String,Object>> mapList=null;
		//2.��orderѭ������������order�µ�����orderitem
		for(Order order:orderList){
			try {
				mapList=productService.searchAllOrderItem(order.getOrderId());
				List<OrderItem> itemList=new ArrayList<OrderItem>();
				for(Map<String,Object> map:mapList){
					//��map��ȡ��pimage pname shop_price��װProduct
					Product product=new Product();
					//��װProduct����
					BeanUtils.populate(product, map);
					//��map��ȡ�� count totalPrice��װOrderItem,���������Product��װ��OrderItem��
					OrderItem orderItem=new OrderItem();
					orderItem.setItemCount(Integer.parseInt(map.get("itemCount").toString()));
					//double����ת��
					double price=Double.parseDouble(map.get("shop_price").toString());
					orderItem.setTotalPrice(DoubleChangeUtils.doubleChange(price));
					orderItem.setProduct(product);
					itemList.add(orderItem);
				}
				order.setItemList(itemList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//�����ѷ�װ��ϣ��洢������
		request.setAttribute("orderList", orderList);
		//ת���������б�ҳ��
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}
}