/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.order.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.common.ecmanager.common.enums.Deposit_Type;
import com.common.ecmanager.common.enums.Log_Type;
import com.common.ecmanager.common.enums.OrderStatus;
import com.common.ecmanager.common.enums.PaymentStatus;
import com.common.ecmanager.common.enums.Refunds_Method;
import com.common.ecmanager.common.enums.ShippingStatus;
import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.common.utils.DateUtils;
import com.common.ecmanager.common.utils.OrderNoGenerateUtil;
import com.common.ecmanager.common.utils.StringUtils;
import com.common.ecmanager.modules.coupon.dao.CouponCodeDao;
import com.common.ecmanager.modules.coupon.entity.Coupon;
import com.common.ecmanager.modules.coupon.entity.CouponCode;
import com.common.ecmanager.modules.delivery.entity.DeliveryCorporation;
import com.common.ecmanager.modules.delivery.service.DeliveryCorporationService;
import com.common.ecmanager.modules.mem.dao.DepositDao;
import com.common.ecmanager.modules.mem.dao.MemMemberDao;
import com.common.ecmanager.modules.mem.dao.MemRankDao;
import com.common.ecmanager.modules.mem.entity.Deposit;
import com.common.ecmanager.modules.mem.entity.MemMember;
import com.common.ecmanager.modules.mem.entity.MemRank;
import com.common.ecmanager.modules.method.dao.ShippingMethodDao;
import com.common.ecmanager.modules.method.entity.ShippingMethod;
import com.common.ecmanager.modules.method.service.ShippingMethodService;
import com.common.ecmanager.modules.order.dao.OrderShippingRefDao;
import com.common.ecmanager.modules.order.dao.OrderTableDao;
import com.common.ecmanager.modules.order.dao.OrderTableItemDao;
import com.common.ecmanager.modules.order.dao.OrderTableLogDao;
import com.common.ecmanager.modules.order.dao.OrderTablePaymentDao;
import com.common.ecmanager.modules.order.dao.OrderTableRefundsDao;
import com.common.ecmanager.modules.order.dao.OrderTableReturnsDao;
import com.common.ecmanager.modules.order.dao.OrderTableReturnsItemDao;
import com.common.ecmanager.modules.order.dao.OrderTableShippingDao;
import com.common.ecmanager.modules.order.entity.OrderShippingRef;
import com.common.ecmanager.modules.order.entity.OrderTable;
import com.common.ecmanager.modules.order.entity.OrderTableItem;
import com.common.ecmanager.modules.order.entity.OrderTableLog;
import com.common.ecmanager.modules.order.entity.OrderTablePayment;
import com.common.ecmanager.modules.order.entity.OrderTableRefunds;
import com.common.ecmanager.modules.order.entity.OrderTableReturns;
import com.common.ecmanager.modules.order.entity.OrderTableReturnsItem;
import com.common.ecmanager.modules.order.entity.OrderTableShipping;
import com.common.ecmanager.modules.order.entity.OrderTableShippingItem;
import com.common.ecmanager.modules.product.dao.ProductDao;
import com.common.ecmanager.modules.product.entity.Product;
import com.common.ecmanager.modules.sys.entity.Area;
import com.common.ecmanager.modules.sys.entity.User;
import com.common.ecmanager.modules.sys.service.AreaService;
import com.common.ecmanager.modules.sys.service.SystemService;

/**
 * 订单Service
 * @author limy
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class OrderTableService extends CrudService<OrderTableDao, OrderTable> {

	@Autowired
	private OrderTableItemDao orderTableItemDao;
	@Autowired
	private OrderTablePaymentDao orderTablePaymentDao;
	@Autowired
	private OrderTableRefundsDao orderTableRefundsDao;
	@Autowired
	private OrderTableDao orderTableDao;
	@Autowired
	private OrderTableLogDao orderTableLogDao;
	@Autowired
	private MemMemberDao memMemberDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private MemRankDao memRankDao;
	@Autowired
	private DepositDao depositDao;
	@Autowired
	private OrderTableShippingDao orderTableShippingDao;
	@Autowired
	private OrderTableReturnsDao orderTableReturnsDao;
	@Autowired
	private OrderShippingRefDao orderShippingRefDao;
	@Autowired
	private AreaService areaService;
	@Autowired
	private OrderTableReturnsItemDao orderTableReturnsItemDao;
	@Autowired
	private ShippingMethodService shippingMethodService;
	// 用户
	@Autowired
	private SystemService systemService;
	// 发货
	@Autowired
	private OrderTableShippingService orderTableShippingService;
	// 配送方式
	@Autowired
	private ShippingMethodDao shippingMethodDao;
	// 物流公司
	@Autowired
	private DeliveryCorporationService deliveryCorporationService;
	
	public OrderTable get(String id) {
		OrderTable orderTable = super.get(id);
		orderTable.setOrderTableItemList(orderTableItemDao.findList(new OrderTableItem(orderTable)));
		OrderTablePayment payment = new OrderTablePayment(orderTable);
		orderTable.setOrderTablePaymentList(orderTablePaymentDao.findList(payment));
		orderTable.setOrderTableRefundsList(orderTableRefundsDao.findList(new OrderTableRefunds(orderTable)));
		if(StringUtils.isNotBlank(orderTable.getArea())){
			String[] areaCodes = orderTable.getArea().split(",");
			String nameOfArea = "";
			for(String areaCode:areaCodes){
				Area area =	areaService.get(areaCode);
				nameOfArea += area==null?"":area.getName();
			}
			orderTable.setNameOfArea(nameOfArea);
		}
		return orderTable;
	}
	
	public List<OrderTable> findList(OrderTable orderTable) {
		return super.findList(orderTable);
	}
	
	public Page<OrderTable> findPage(Page<OrderTable> page, OrderTable orderTable) {
		return super.findPage(page, orderTable);
	}
	public Page<OrderTable> findPages(Page<OrderTable> page, OrderTable orderTable) {		
		OrderTableItem orderTableItem=new OrderTableItem();
		orderTable.setPage(page);
		List<OrderTable> orderList=orderTableDao.findListItem(orderTable);
		try {
			if (orderList!=null && orderList.size()!=0) {				
				for (OrderTable o:orderList) {
					orderTableItem.setOrderTable(o);			
					List<OrderTableItem> oIList=orderTableItemDao.findByTableId(orderTableItem);
					if (oIList!=null && oIList.size()!=0) {
						o.setOrderTableItemList(oIList);
					}
				} 
				page.setList(orderList);
			}
		}catch (Exception e) {
				logger.info("分页异常========"+e);
			}
		return page;
	}
	@Transactional(readOnly = false)
	public void save(OrderTable orderTable) {
		super.save(orderTable);
		for (OrderTableItem orderTableItem : orderTable.getOrderTableItemList()){
			if (orderTableItem.getId() == null){
				continue;
			}
			if (OrderTableItem.DEL_FLAG_NORMAL.equals(orderTableItem.getDelFlag())){
				if (StringUtils.isBlank(orderTableItem.getId())){
					orderTableItem.setOrderTable(orderTable);
					orderTableItem.preInsert();
					orderTableItemDao.insert(orderTableItem);
				}
			}else{
				orderTableItemDao.delete(orderTableItem);
			}
		}
//		OrderTableShipping ots =  orderTableShippingDao.findOrderShippingByOrderNo(orderTable.getOrderNo());
		for (OrderTablePayment orderTablePayment : orderTable.getOrderTablePaymentList()){
			if (orderTablePayment.getId() == null){
				continue;
			}
			if (OrderTablePayment.DEL_FLAG_NORMAL.equals(orderTablePayment.getDelFlag())){
				if (StringUtils.isBlank(orderTablePayment.getId())){
					orderTablePayment.setOrderTable(orderTable);
					orderTablePayment.setIsRemarks(orderTable.getIsRemarks());
					orderTablePayment.setExceptionRemarks(orderTable.getExceptionRemarks());
					orderTablePayment.preInsert();
					orderTablePaymentDao.insert(orderTablePayment);
				}
			}else{
				orderTablePaymentDao.delete(orderTablePayment);
			}
		}
		for (OrderTableRefunds orderTableRefunds : orderTable.getOrderTableRefundsList()){
			if (orderTableRefunds.getId() == null){
				continue;
			}
			if (OrderTableRefunds.DEL_FLAG_NORMAL.equals(orderTableRefunds.getDelFlag())){
				if (StringUtils.isBlank(orderTableRefunds.getId())){
					orderTableRefunds.setOrderTable(orderTable);
					orderTableRefunds.preInsert();
					orderTableRefundsDao.insert(orderTableRefunds);
				}
			}else{
				orderTableRefundsDao.delete(orderTableRefunds);
			}
		}
	}
	
	
	@Transactional(readOnly = false)
	public void update(OrderTable orderTable) {
		if(orderTable.getExceptionRemarks().length()!=0){
			orderTable.setIsRemarks("1");
		}
		super.update(orderTable);
		for (OrderTableItem orderTableItem : orderTable.getOrderTableItemList()){
			if (orderTableItem.getId() == null){
				continue;
			}
			if (OrderTableItem.DEL_FLAG_NORMAL.equals(orderTableItem.getDelFlag())){
				
					orderTableItem.preUpdate();
					orderTableItemDao.update(orderTableItem);
				
			}else{
				orderTableItemDao.delete(orderTableItem);
			}
		}
		OrderTableShipping ots = orderTableShippingDao.findOrderShippingByOrderNo(orderTable.getOrderNo());
		if(ots!=null){
			ots.setIsRemarks(orderTable.getIsRemarks());
			ots.setExceptionRemarks(orderTable.getExceptionRemarks());
			orderTableShippingDao.update(ots);
		}
		for (OrderTablePayment orderTablePayment : orderTable.getOrderTablePaymentList()){
			if (orderTablePayment.getId() == null){
				continue;
			}
			if (OrderTablePayment.DEL_FLAG_NORMAL.equals(orderTablePayment.getDelFlag())){
					orderTablePayment.setIsRemarks(orderTable.getIsRemarks());
					orderTablePayment.setExceptionRemarks(orderTable.getExceptionRemarks());
					orderTablePayment.preUpdate();
					orderTablePaymentDao.update(orderTablePayment);
				
			}else{
				orderTablePaymentDao.delete(orderTablePayment);
			}
		}
		for (OrderTableRefunds orderTableRefunds : orderTable.getOrderTableRefundsList()){
			if (orderTableRefunds.getId() == null){
				continue;
			}
			if (OrderTableRefunds.DEL_FLAG_NORMAL.equals(orderTableRefunds.getDelFlag())){
				
					orderTableRefunds.preUpdate();
					orderTableRefundsDao.update(orderTableRefunds);
				
			}else{
				orderTableRefundsDao.delete(orderTableRefunds);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderTable orderTable) {
		super.delete(orderTable);
		orderTableItemDao.delete(new OrderTableItem(orderTable));
		orderTablePaymentDao.delete(new OrderTablePayment(orderTable));
		orderTableRefundsDao.delete(new OrderTableRefunds(orderTable));
	}




	/**
	 * 订单确认
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	@Transactional(readOnly = false)
	public void confirm(OrderTable order, User operator) {
		order.setOrderStatus(OrderStatus.confirmed.getIndex());
		System.out.println(order.getOrderStatus());
		Assert.notNull(order);
		
		orderTableDao.update(order);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(new Date());
		OrderTableLog orderTableLog = new OrderTableLog();
		orderTableLog.setId(str);
		orderTableLog.setType(Log_Type.confirm.getIndex());
		orderTableLog.setOperator(operator != null ? operator.getName() : null);
		orderTableLog.setOrderNo(order.getOrderNo());
		orderTableLog.setCreateBy(operator);
		orderTableLog.setCreateDate(new Date());
		orderTableLog.setUpdateBy(operator.getUpdateBy());
		orderTableLog.setUpdateDate(new Date());
		orderTableLogDao.insert(orderTableLog);
	}
	
	/**
	 * 订单完成
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	@Transactional(readOnly = false)
	public void complete(OrderTable order, User operator){
		Assert.notNull(order);
		
		MemMember member =  order.getMemMember();
		member = memMemberDao.get(member);
		MemRank memrank =  member.getMemRank();
		memrank = memRankDao.get(memrank);
		

		if (order.getShippingStatus()== ShippingStatus.partialShipment.getIndex() || order.getShippingStatus()==ShippingStatus.shipped.getIndex()) {
			member.setPoint(member.getPoint() + order.getPoint());
		for (Coupon coupon : order.getCouponList()) {
				couponCodeDao.build(coupon);
			}
		}

		if (order.getShippingStatus()==ShippingStatus.unshipped.getIndex() || order.getShippingStatus()==ShippingStatus.returned.getIndex()) {
			CouponCode couponCode = order.getCouponCode();
			if (couponCode != null) {
				couponCode.setIsUsed(1);
				couponCode.setUsedDate(null);
				couponCodeDao.update(couponCode);

				order.setCouponCode(null);
				orderTableDao.update(order);
			}
		}
		member.setAmount(member.getAmount().add(order.getAmountPaid()));

		if ("0".equals(memrank.getIsSpecial())) {
			MemRank memberRank = memRankDao.findByAmount(member.getAmount());
			if (memberRank != null && memberRank.getAmount().compareTo(member.getMemRank().getAmount()) > 0) {
				member.setMemRank(memberRank);
			}
		}
		memMemberDao.update(member);

		if (order.getIsAllocatedStock()==0) {
			for (OrderTableItem orderTableItem : order.getOrderTableItemList()) {
				if (orderTableItem != null) {
					Product product = orderTableItem.getProduct();
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() - (orderTableItem.getQuantity() - orderTableItem.getShippedQuantity()));
						productDao.update(product);
					}
				}
			}
			order.setIsAllocatedStock(1);
		}

		for (OrderTableItem orderTableItem : order.getOrderTableItemList()) {
			if (orderTableItem != null) {
				Product product = orderTableItem.getProduct();
				product = productDao.get(product);
				if (product != null) {
					Integer quantity = orderTableItem.getQuantity();
					Calendar nowCalendar = Calendar.getInstance();
					Calendar weekSalesCalendar = DateUtils.toCalendar(product.getWeekSalesDate());
					Calendar monthSalesCalendar = DateUtils.toCalendar(product.getMonthSalesDate());
					if (nowCalendar.get(Calendar.YEAR) != weekSalesCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.WEEK_OF_YEAR) > weekSalesCalendar.get(Calendar.WEEK_OF_YEAR)) {
						product.setWeekSales( quantity);
					} else {
						product.setWeekSales(product.getWeekSales() + quantity);
					}
					if (nowCalendar.get(Calendar.YEAR) != monthSalesCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.MONTH) > monthSalesCalendar.get(Calendar.MONTH)) {
						product.setMonthSales( quantity);
					} else {
						product.setMonthSales(product.getMonthSales() + quantity);
					}
					product.setSales(product.getSales() + quantity);
					product.setWeekSalesDate(new Date());
					product.setMonthSalesDate(new Date());
					productDao.update(product);
				}
			}
		}

		order.setOrderStatus(OrderStatus.completed.getIndex());
		order.setExpire(null);
		orderTableDao.update(order);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(new Date());
		OrderTableLog orderTableLog = new OrderTableLog();
		orderTableLog.setId(str);
		orderTableLog.setType(Log_Type.complete.getIndex());
		orderTableLog.setOperator(operator != null ? operator.getName() : null);
		orderTableLog.setOrderNo(order.getOrderNo());
		orderTableLog.setCreateBy(operator);
		orderTableLog.setCreateDate(new Date());
		orderTableLog.setUpdateBy(operator.getUpdateBy());
		orderTableLog.setUpdateDate(new Date());
		orderTableLogDao.insert(orderTableLog);
	}

	/**
	 * 订单取消
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	@Transactional(readOnly = false)
	public void cancel(OrderTable order, User operator){
		Assert.notNull(order);

		CouponCode couponCode = order.getCouponCode();
		if (couponCode != null) {
			couponCode.setIsUsed(1);
			couponCode.setUsedDate(null);
			couponCodeDao.update(couponCode);

			order.setCouponCode(null);
			orderTableDao.update(order);
		}

		if (order.getIsAllocatedStock()==0) {
			for (OrderTableItem orderTableItem : order.getOrderTableItemList()) {
				if (orderTableItem != null) {
					Product product = orderTableItem.getProduct();
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() - (orderTableItem.getQuantity() - orderTableItem.getShippedQuantity()));
						productDao.update(product);
					}
				}
			}
			order.setIsAllocatedStock(1);
		}

		order.setOrderStatus(OrderStatus.cancelled.getIndex());
		order.setExpire(null);
		orderTableDao.update(order);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(new Date());
		OrderTableLog orderTableLog = new OrderTableLog();
		orderTableLog.setId(str);
		orderTableLog.setType(Log_Type.cancel.getIndex());
		orderTableLog.setOperator(operator != null ? operator.getName() : null);
		orderTableLog.setOrderNo(order.getOrderNo());
		orderTableLog.setCreateBy(operator);
		orderTableLog.setCreateDate(new Date());
		orderTableLog.setUpdateBy(operator.getUpdateBy());
		orderTableLog.setUpdateDate(new Date());
		orderTableLogDao.insert(orderTableLog);
	}
	//退款
	@Transactional(readOnly = false)
	public void refunds(OrderTable order, OrderTableRefunds refunds, User operator) {
		Assert.notNull(order);
		Assert.notNull(refunds);
		refunds.setOrderTable(order);
		if (refunds.getMethod() == Refunds_Method.deposit.getIndex()) {
			MemMember member = order.getMemMember();
			member = memMemberDao.get(member);
			member.setBalance(member.getBalance().add(refunds.getAmount()));
			memMemberDao.update(member);

			Deposit deposit = new Deposit();
			deposit.setType(Deposit_Type.adminRefunds.getIndex());
			deposit.setCredit(refunds.getAmount());
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(member.getBalance());
			deposit.setOperator(operator != null ? operator.getName() : null);
			deposit.setMemMember(member);
			deposit.setOrderTable(order);
			depositDao.update(deposit);
		}

		order.setAmountPaid(order.getAmountPaid().subtract(refunds.getAmount()));
		order.setExpire(null);
		if (order.getAmountPaid().compareTo(new BigDecimal(0)) == 0) {
			order.setPaymentStatus(PaymentStatus.refunded.getIndex());
		} else if (order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
			order.setPaymentStatus(PaymentStatus.partialRefunds.getIndex());
		}
		orderTableDao.update(order);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(new Date());
		OrderTableLog orderTableLog = new OrderTableLog();
		orderTableLog.setId(str);
		orderTableLog.setType(Log_Type.refunds.getIndex());
		orderTableLog.setOperator(operator != null ? operator.getName() : null);
		orderTableLog.setOrderNo(order.getOrderNo());
		orderTableLog.setCreateBy(operator);
		orderTableLog.setCreateDate(new Date());
		orderTableLog.setUpdateBy(operator.getUpdateBy());
		orderTableLog.setUpdateDate(new Date());
		orderTableLogDao.insert(orderTableLog);
	}
	//发货
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	private void shippingDetail(OrderTable order, OrderTableShipping shipping, User operator) throws Exception{
		Assert.notNull(order);
		Assert.notNull(shipping);
		if (order.getIsAllocatedStock()== 1 ) {
		order.setIsAllocatedStock(0);
		shipping.setOrderNo(order.getOrderNo());
		orderTableShippingDao.update(shipping);
		for (OrderTableShippingItem orderTableShippingItem : shipping.getOrderTableShippingItemList()) {
			OrderTableItem orderItem = new OrderTableItem();
			if(orderTableShippingItem.getProductType().equals("0")|| orderTableShippingItem.getProductType().equals("2")){
				//平台
				orderItem = order.getOrderItem(orderTableShippingItem.getProductNo(),orderTableShippingItem.getProductType());
			}else if(orderTableShippingItem.getProductType().equals("1")){
				//推荐
				if(orderTableShippingItem.getReMemberIdString()!=null){
					orderItem = order.getOrderItem(orderTableShippingItem.getProductNo(),orderTableShippingItem.getProductType(),orderTableShippingItem.getReMemberIdString());
				}else{
					orderItem = order.getOrderItem(orderTableShippingItem.getProductNo(),orderTableShippingItem.getProductType());
				}
				
			}
			if (orderItem != null) {
				Product product = orderItem.getProduct();
				product = productDao.get(product);
				if (product != null) {
					if (product.getStock() != null && product.getStock()>0 && product.getAllocatedStock()>0) {
						product.setStock(product.getStock() - orderTableShippingItem.getQuantity());
						if (order.getIsAllocatedStock()==0) {
							product.setAllocatedStock(product.getAllocatedStock() - orderTableShippingItem.getQuantity());
						}
					}
					Calendar nowCalendar = Calendar.getInstance();
					if(product.getWeekSalesDate()!=null || product.getMonthSalesDate()!=null || product.getWeekSales()!=null || product.getMonthSales()!=null){
						Calendar weekSalesCalendar = DateUtils.toCalendar(product.getWeekSalesDate());
						Calendar monthSalesCalendar = DateUtils.toCalendar(product.getMonthSalesDate());
						if (nowCalendar.get(Calendar.YEAR) != weekSalesCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.WEEK_OF_YEAR) > weekSalesCalendar.get(Calendar.WEEK_OF_YEAR)) {
							product.setWeekSales(orderTableShippingItem.getQuantity());
						} else {
							product.setWeekSales(product.getWeekSales() + orderTableShippingItem.getQuantity());
						}
						if (nowCalendar.get(Calendar.YEAR) != monthSalesCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.MONTH) > monthSalesCalendar.get(Calendar.MONTH)) {
							product.setMonthSales(orderTableShippingItem.getQuantity());
						} else {
							product.setMonthSales(product.getMonthSales() + orderTableShippingItem.getQuantity());
						}
						product.setSales(product.getSales() + orderTableShippingItem.getQuantity());
						product.setWeekSalesDate(new Date());
						product.setMonthSalesDate(new Date());
					}
					productDao.update(product);
				}
				orderItem.setShippedQuantity(orderItem.getShippedQuantity() + orderTableShippingItem.getQuantity());
				orderTableItemDao.update(orderItem);
			}
		}
		}
		if (order.getShippedQuantity() >= order.getQuantity()) {
			order.setShippingStatus(ShippingStatus.shipped.getIndex());
			order.setIsAllocatedStock(1);
		}
		shipping.setOrderNo(order.getOrderNo());
		shipping.setShippingDate(new Date());
		orderTableShippingDao.update(shipping);
		order.setShippingMethodName(shipping.getShippingMethod());
		ShippingMethod shippingMethod = shippingMethodService.getShippingMethodByName(shipping.getShippingMethod());
		order.setShippingMethod(shippingMethod);
		order.setShippingDate(shipping.getShippingDate());
		order.setExpire(null);
		order.setOrderStatus(OrderStatus.shipped.getIndex());
//		orderTableDao.update(order);
		//发货时更新订单发货相关信息
		orderTableDao.updateShippingInfo(order);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(new Date());
		OrderTableLog orderTableLog = new OrderTableLog();
		orderTableLog.setId(str);
		orderTableLog.setType(Log_Type.shipping.getIndex());
		orderTableLog.setOperator(operator != null ? operator.getName() : null);
		orderTableLog.setOrderNo(order.getOrderNo());
		orderTableLog.setCreateBy(operator);
		orderTableLog.setCreateDate(new Date());
		orderTableLog.setUpdateBy(operator.getUpdateBy());
		orderTableLog.setUpdateDate(new Date());
		orderTableLogDao.insert(orderTableLog);
	}
	//退货
	@Transactional(readOnly = false)
	public void returns(OrderTable order, OrderTableReturns returns, User operator) throws Exception {
		Assert.notNull(order);
		Assert.notNull(returns);
		System.out.println(returns);
		returns.setOrderNo(order.getOrderNo());
		orderTableReturnsDao.update(returns);
		for (OrderTableReturnsItem returnsItem : returns.getOrderTableReturnsItemList()) {
			OrderTableItem orderItem = order.getOrderItem(returnsItem.getReturnNo());
			if (orderItem != null) {
				orderItem.setReturnQuantity(orderItem.getReturnQuantity() + returnsItem.getQuantity());
			}
		}
		if (order.getReturnQuantity() >= order.getShippedQuantity()) {
			order.setShippingStatus(ShippingStatus.returned.getIndex());
		} else if (order.getReturnQuantity() > 0) {
			order.setShippingStatus(ShippingStatus.partialReturns.getIndex());
		}
		order.setExpire(null);
		orderTableDao.update(order);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(new Date());
		OrderTableLog orderTableLog = new OrderTableLog();
		orderTableLog.setId(str);
		orderTableLog.setType(Log_Type.returns.getIndex());
		orderTableLog.setOperator(operator != null ? operator.getName() : null);
		orderTableLog.setOrderNo(order.getOrderNo());
		orderTableLog.setCreateBy(operator);
		orderTableLog.setCreateDate(new Date());
		orderTableLog.setUpdateBy(operator.getUpdateBy());
		orderTableLog.setUpdateDate(new Date());
		orderTableLogDao.insert(orderTableLog);
	}
	/**
	 * 根据订单编号查询订单信息
	 * @param orderNo
	 * @return
	 */
	@Transactional(readOnly = false)
	public OrderTable findOrderTableByOrderNo(String orderNo){
		return orderTableDao.findOrderTableByOrderNo(orderNo);
	}
	
	/**
	 * 根据购物车商品项编号查询出B端分享人当前等级的商品价格
	 * @param cartItemId
	 * @return
	 */
	public java.util.Map<String, Object> queryRecommendMeberByOrderNoAndProductId(String orderNo,String productId,String reMemberIdString) throws Exception {
		return orderTableDao.queryRecommendMeberByOrderNoAndProductId(orderNo,productId,reMemberIdString);
	}
	/**
	 * 根据优惠码查询订单号
	 * @param c
	 * @return
	 */
	public List<OrderTable> findOrderNoByCouponCode(CouponCode c) {
		return orderTableDao.findOrderNoByCouponCode(c);
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean shipping(String orderId, String shippingMethodId,
			String deliveryCorpId, String areaName, OrderTableShipping shipping) throws Exception{
		try {
			User user = systemService.getCurrent();
			String shippingId = UUID.randomUUID().toString().replaceAll("-", "");
			shipping.setShippingNo("E"+OrderNoGenerateUtil.getOrderNo());
			shipping.setId(shippingId);
			OrderTable order = get(orderId);
			if (order == null) {
				logger.error("订单不存在 id = {}",orderId);
				throw new RuntimeException();
			}
			List<OrderShippingRef> orderShippingRefList = new ArrayList<OrderShippingRef>();
			List<OrderTableShippingItem> orderTableShippingItemList = new ArrayList<OrderTableShippingItem>();
			for (OrderTableItem oti : order.getOrderTableItemList()) {
				OrderTableShippingItem otsi = new OrderTableShippingItem();
				otsi.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				otsi.setOrderTableShipping(shipping);
				otsi.setProductNo(oti.getProduct().getId());
				otsi.setProductType(oti.getProductType());
				otsi.setReMemberIdString(oti.getReMemberIdString());
				otsi.setName(oti.getName());
				otsi.setQuantity(oti.getQuantity());
				otsi.setShippingNo(shipping.getShippingNo());
				otsi.setCreateBy(user);
				otsi.setUpdateBy(user);
				otsi.setCreateDate(new Date());
				otsi.setUpdateDate(new Date());
				otsi.setDelFlag("0");
				orderTableShippingService.saveShippingItem(otsi);
				orderTableShippingItemList.add(otsi);
			}
			shipping.setOrderShippingRefList(orderShippingRefList);
			shipping.setOrderTableShippingItemList(orderTableShippingItemList);
			for (Iterator<OrderTableShippingItem> iterator = shipping.getOrderTableShippingItemList().iterator(); iterator.hasNext();) {
				OrderTableShippingItem shippingItem = iterator.next();
				if (shippingItem == null
						|| StringUtils.isEmpty(shippingItem.getShippingNo())
						|| shippingItem.getQuantity() == null
						|| shippingItem.getQuantity() <= 0) {
					iterator.remove();
					continue;
				}
				OrderTableItem orderTableItem=new OrderTableItem();
				if(shippingItem.getProductType().equals("0") || shippingItem.getProductType().equals("2")){
					//平台购买
					orderTableItem = order.getOrderItem(shippingItem.getProductNo(),shippingItem.getProductType());
				}else if(shippingItem.getProductType().equals("1")){
					//分享购买
					if(shippingItem.getReMemberIdString()!=null){
						orderTableItem = order.getOrderItem(shippingItem.getProductNo(),shippingItem.getProductType(),shippingItem.getReMemberIdString());	
					}else{
						orderTableItem = order.getOrderItem(shippingItem.getProductNo(),shippingItem.getProductType());
					}
					
				}
				if (orderTableItem == null || shippingItem.getQuantity() > orderTableItem.getQuantity()- orderTableItem.getShippedQuantity()) {
					logger.error("订单项不存在 ,数量不一致 = {}",shippingItem.getQuantity());
					throw new RuntimeException();
				}
				if (orderTableItem.getProduct() != null && orderTableItem.getProduct().getStock() != null && shippingItem.getQuantity() > orderTableItem.getProduct().getStock()) {
					logger.error("订单项中产品不存在 ,库存不一致 = {}",orderTableItem.getProduct().getStock());
					throw new RuntimeException();
				}
				shippingItem.setName(orderTableItem.getFullName());
				shippingItem.setOrderTableShipping(shipping);
			}
			shipping.setOrderNo(order.getOrderNo());
			ShippingMethod shippingMethod = shippingMethodDao.get(shippingMethodId);
			shipping.setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
			DeliveryCorporation deliveryCorp = deliveryCorporationService.get(deliveryCorpId);
			shipping.setDeliveryCorp(deliveryCorp != null ? deliveryCorp.getName() : null);
			shipping.setDeliveryCorpUrl(deliveryCorp != null ? deliveryCorp.getUrl() : null);
			shipping.setDeliveryCorpCode(deliveryCorp != null ? deliveryCorp.getCode() : null);
			shipping.setArea(areaName);
			if (order.getShippingStatus() != ShippingStatus.unshipped.getIndex()) {
				logger.error("订单项中发货状态不对= {}",order.getShippingStatus());
				throw new RuntimeException();
			}
			if (order.isLocked(user)) {
				logger.error("订单被锁定= {}",order.isLocked(user));
				throw new RuntimeException();
			}
			shipping.setOperator(user.getName());
			shipping.setUpdateBy(user);
			shipping.setUpdateDate(new Date());
			shipping.setCreateBy(user);
			shipping.setCreateDate(new Date());
			orderTableShippingService.saveShipping(shipping);
			OrderShippingRef osf = new OrderShippingRef();
			osf.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			osf.setOrderTabelId(orderId);
			osf.setOrderTableShipping(shipping);
			orderTableShippingService.saveShippingRef(osf);
			orderShippingRefList.add(osf);
			shippingDetail(order, shipping, user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发货service报错={}"+e.fillInStackTrace());
			return false;
		}
		return true;
	}
}