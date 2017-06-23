/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.enums.OrderStatus;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.mem.entity.WithdrawalApplyFor;
import com.uib.ecmanager.modules.mem.service.WithdrawalApplyForService;
import com.uib.ecmanager.modules.order.dao.OrderReturnsRefDao;
import com.uib.ecmanager.modules.order.dao.OrderTableDao;
import com.uib.ecmanager.modules.order.dao.OrderTableItemDao;
import com.uib.ecmanager.modules.order.dao.OrderTableReturnsDao;
import com.uib.ecmanager.modules.order.dao.OrderTableReturnsItemDao;
import com.uib.ecmanager.modules.order.entity.OrderReturnsRef;
import com.uib.ecmanager.modules.order.entity.OrderTable;
import com.uib.ecmanager.modules.order.entity.OrderTableItem;
import com.uib.ecmanager.modules.order.entity.OrderTableReturns;
import com.uib.ecmanager.modules.order.entity.OrderTableReturnsItem;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.service.ProductService;



/**
 * 退货单Service
 * 
 * @author limy
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class OrderTableReturnsService extends
		CrudService<OrderTableReturnsDao, OrderTableReturns> {

	@Autowired
	private OrderReturnsRefDao orderReturnsRefDao;
	@Autowired
	private OrderTableReturnsItemDao orderTableReturnsItemDao;
	@Autowired
	private OrderTableReturnsDao orderTableReturnsDao;
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private OrderTableDao orderTableDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private WithdrawalApplyForService withdrawalApplyForService;
	@Autowired
	private OrderTableItemDao orderTableItemDao;
	@Value("${upload.image.path.aftermarket}")
	private String uploadImagePath;
	@Value("${frontWeb.image.baseUrl.aftermarket}")
	private String baseUrl;
	@Value("${frontweb.image.folder.aftermarket}")
	private String imageFolder;

	public OrderTableReturns get(String id) {
		OrderTableReturns orderTableReturns = super.get(id);
		//根据商品id去拿商品编号
		Product param_product=new Product();
		param_product.setId(orderTableReturns.getProductId());
		Product product=productService.get(param_product);
		orderTableReturns.setProductNo(product.getProductNo());
		//orderTableReturns.setProductNo("商品编号");
		orderTableReturns.setOrderReturnsRefList(orderReturnsRefDao
				.findList(new OrderReturnsRef(orderTableReturns)));
		List<OrderTableReturnsItem> tableReturnsItems = orderTableReturnsItemDao
				.findList(new OrderTableReturnsItem(orderTableReturns));
		for (OrderTableReturnsItem tableReturnsItem : tableReturnsItems) {
//			tableReturnsItem.setImgWebUrl(baseUrl + imageFolder
//					+ tableReturnsItem.getImage());
			tableReturnsItem.setImgWebUrl(baseUrl
					+ tableReturnsItem.getImage());
			tableReturnsItem.setImgDiskUrl(uploadImagePath
					+ tableReturnsItem.getImage());
			//设置商品名称和商品数量
			orderTableReturns.setProductName(tableReturnsItem.getName());
			orderTableReturns.setProductQuantity(tableReturnsItem.getQuantity());
		}
		orderTableReturns.setOrderTableReturnsItemList(tableReturnsItems);
		//设置用户的银行卡相关信息
		//1.根据订单号查询出memberId 2.根据memberId找寻出默认的绑定银行卡信息
		OrderTable orderTable=orderTableService.findOrderTableByOrderNo(orderTableReturns.getOrderNo());
		WithdrawalApplyFor withdrawalApplyFor=new WithdrawalApplyFor();
		withdrawalApplyFor.setMemberId(orderTable.getMemberNo());
		withdrawalApplyFor.setIsDefault("1");
		List<WithdrawalApplyFor> list=withdrawalApplyForService.findList(withdrawalApplyFor);
		if(list.size()!=0){
			orderTableReturns.setWithdrawalApplyFor(list.get(0));	
		}
		return orderTableReturns;
	}

	public List<OrderTableReturns> findList(OrderTableReturns orderTableReturns) {
		return super.findList(orderTableReturns);
	}

	public Page<OrderTableReturns> findPage(Page<OrderTableReturns> page,
			OrderTableReturns orderTableReturns) {
		Page<OrderTableReturns> tmpPage = super.findPage(page,
				orderTableReturns);
		for (OrderTableReturns orderTableReturn : tmpPage.getList()) {
			switch (orderTableReturn.getReturnType()) {
			case 1:
				orderTableReturn.setReturnTypeStr("退款");
				break;
			case 2:
				orderTableReturn.setReturnTypeStr("退货");
				break;
			case 3:
				orderTableReturn.setReturnTypeStr("换货");
				break;
			}
			switch (orderTableReturn.getReturnStatus()) {
			case 1:
				orderTableReturn.setReturnStatusStr("已处理");
				break;
			case 2:
				orderTableReturn.setReturnStatusStr("无法退货");
				break;
			case 3:
				orderTableReturn.setReturnStatusStr("未处理");
				break;
			}
		}
		return tmpPage;
	}

	@Transactional(readOnly = false)
	public void save(OrderTableReturns orderTableReturns) {
		super.save(orderTableReturns);
		for (OrderReturnsRef orderReturnsRef : orderTableReturns
				.getOrderReturnsRefList()) {
			if (orderReturnsRef.getId() == null) {
				continue;
			}
			if (OrderReturnsRef.DEL_FLAG_NORMAL.equals(orderReturnsRef
					.getDelFlag())) {
				if (StringUtils.isBlank(orderReturnsRef.getId())) {
					orderReturnsRef.setOrderTableReturns(orderTableReturns);
					orderReturnsRef.preInsert();
					orderReturnsRefDao.insert(orderReturnsRef);
				}
			} else {
				orderReturnsRefDao.delete(orderReturnsRef);
			}
		}
		for (OrderTableReturnsItem orderTableReturnsItem : orderTableReturns
				.getOrderTableReturnsItemList()) {
			if (orderTableReturnsItem.getId() == null) {
				continue;
			}
			if (OrderTableReturnsItem.DEL_FLAG_NORMAL
					.equals(orderTableReturnsItem.getDelFlag())) {
				if (StringUtils.isBlank(orderTableReturnsItem.getId())) {
					orderTableReturnsItem
							.setOrderTableReturns(orderTableReturns);
					orderTableReturnsItem.preInsert();
					orderTableReturnsItemDao.insert(orderTableReturnsItem);
				}
			} else {
				orderTableReturnsItemDao.delete(orderTableReturnsItem);
			}
		}
	}

	@Transactional(readOnly = false)
	public void update(OrderTableReturns orderTableReturns) {
		super.update(orderTableReturns);
		for (OrderReturnsRef orderReturnsRef : orderTableReturns
				.getOrderReturnsRefList()) {
			if (orderReturnsRef.getId() == null) {
				continue;
			}
			if (OrderReturnsRef.DEL_FLAG_NORMAL.equals(orderReturnsRef
					.getDelFlag())) {

				orderReturnsRef.preUpdate();
				orderReturnsRefDao.update(orderReturnsRef);

			} else {
				orderReturnsRefDao.delete(orderReturnsRef);
			}
		}
		for (OrderTableReturnsItem orderTableReturnsItem : orderTableReturns
				.getOrderTableReturnsItemList()) {
			if (orderTableReturnsItem.getId() == null) {
				continue;
			}
			if (OrderTableReturnsItem.DEL_FLAG_NORMAL
					.equals(orderTableReturnsItem.getDelFlag())) {

				orderTableReturnsItem.preUpdate();
				orderTableReturnsItemDao.update(orderTableReturnsItem);

			} else {
				orderTableReturnsItemDao.delete(orderTableReturnsItem);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(OrderTableReturns orderTableReturns) {
		super.delete(orderTableReturns);
		orderReturnsRefDao.delete(new OrderReturnsRef(orderTableReturns));
		orderTableReturnsItemDao.delete(new OrderTableReturnsItem(
				orderTableReturns));
	}

	/**
	 * @param orderTableReturns
	 */
//	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
//	public void updateStatus(OrderTableReturns orderTableReturns) throws Exception{
//		//每次只要一退货编辑，就去更新订单项里的状态
//			Product param_product=new Product();
//			param_product.setId(orderTableReturns.getProductId());
//			OrderTableItem param_items=new OrderTableItem();
//			param_items.setProduct(param_product);
//			param_items.setOrderNo(orderTableReturns.getOrderNo());
//			param_items.setReturnStatus(String.valueOf(orderTableReturns.getReturnStatus()));
//			param_items.setProductType(orderTableReturns.getOrderType());
//			param_items.setReMemberIdString(orderTableReturns.getRecommendedId());
//			orderTableItemDao.updateReturnStatus(param_items);
//		
//		//当订单里所有商品已处理时，更新订单状态为交易已关闭,为已处理和无法处理时更新订单状态为交易已完成
//				//1.根据订单号扫描所有的商品
//				boolean flag=true;      //标志着是否在退货单表
//				boolean all_Flag=true;  //标志着是否全部为退货成功
//				Integer return_status=0;
//				OrderTable orderTable=new OrderTable();
//				orderTable.setOrderNo(orderTableReturns.getOrderNo());  
//				List<OrderTable> orderTable1=orderTableService.findListItem(orderTable);
//				for(OrderTable o:orderTable1){
//					for(OrderTableItem i:o.getOrderTableItemList()){
//						//2.判断所有的商品是否都在退货单列表里
//						OrderTableReturns orderTableReturns2=new OrderTableReturns();
//						orderTableReturns2.setOrderNo(orderTableReturns.getOrderNo());
//						orderTableReturns2.setProductId(i.getProduct().getId());
//						orderTableReturns2.setOrderType(i.getProductType());
//						orderTableReturns2.setRecommendedId(i.getReMemberIdString());
//						List<OrderTableReturns> returns=orderTableReturnsDao.findList(orderTableReturns2);
//						if(returns.size()==0){
//							//不在退货表中
//							flag=false;
//							break;
//						}else{
//							//退货状态是否为已处理和无法退货
//							if((i.getProduct().getId().equals(orderTableReturns.getProductId())) && (i.getProductType().equals(orderTableReturns.getOrderType()))){
//								if("1".equals(i.getProductType())){
//									if((i.getReMemberIdString().equals(orderTableReturns.getRecommendedId()))){
//										return_status=orderTableReturns.getReturnStatus();	
//									}
//								}else{
//									return_status=orderTableReturns.getReturnStatus();	
//								}	
//							}else{
//								return_status=returns.get(0).getReturnStatus();
//							}
//							if(3==return_status){
//								flag=false;
//								break;
//							}else if(2==return_status){
//								all_Flag=false;
//							}
//						}
//					}
//				}
//				//当订单表里所有的商品都是已处理和无法退货时去更新订单状态
//				if(flag){
//					if(all_Flag){
//						orderTable.setOrderStatus("11");
//						 orderTableDao.updateOrderTable(orderTable);
//					}else{
//						//如果订单状态为已付款待发货时 若无法退货 不改变订单状态
//						for(OrderTable o1:orderTable1){
//							 if("5".equals(o1.getOrderStatus()))	{
//							    	
//							 }else{
//								 orderTable.setOrderStatus("12");
//								 orderTableDao.updateOrderTable(orderTable);
//							 }
//						}
//						 
//					}	
//				}
//				orderTableReturnsDao.updateStatus(orderTableReturns);
//			}
	
	/**
	 * @param orderTableReturns
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateStatus(OrderTableReturns orderTableReturns) throws Exception{
		logger.info("修改【退货表】退货状态参数：退货表id={}，退货状态={}",orderTableReturns.getId(),String.valueOf(orderTableReturns.getReturnStatus()));
		orderTableReturnsDao.updateStatus(orderTableReturns);
		
		Product param_product=new Product();
		param_product.setId(orderTableReturns.getProductId());
		OrderTableItem param_items=new OrderTableItem();
		param_items.setProduct(param_product);
		param_items.setOrderNo(orderTableReturns.getOrderNo());
		param_items.setReturnStatus(String.valueOf(orderTableReturns.getReturnStatus()));
		param_items.setProductType(orderTableReturns.getOrderType());
		param_items.setReMemberIdString(orderTableReturns.getRecommendedId());
		//根据产品id，订单id，订单类型 修改订单项退货状态（1：退货成功；2退货失败）
		logger.info("修改【订单项表】退货状态参数：产品id={}，订单id={}，订单类型={}，退货状态={}",orderTableReturns.getProductId(),orderTableReturns.getOrderNo(),orderTableReturns.getOrderType(),String.valueOf(orderTableReturns.getReturnStatus()));
		orderTableItemDao.updateReturnStatus(param_items);
	
		//根据订单号查询订单项数量
		int orderItemCount = orderTableItemDao.countOrderItemByOrderNo(orderTableReturns.getOrderNo());
		logger.info("订单号={}下的商品数量为={}",orderTableReturns.getOrderNo(),orderItemCount);
		
		//根据订单号查询退货项数量
		int orderReturnCount = orderTableReturnsDao.countOrderReturnByOrderNo(orderTableReturns.getOrderNo());
		logger.info("订单号={}下的商品退货数量为={}",orderTableReturns.getOrderNo(),orderItemCount);
		
		//订单项数量等于退货数量
		if(orderItemCount == orderReturnCount){
			
			//根据订单号查询退货项表退货成功数量
			int orderReturnSuccessCount = orderTableReturnsDao.countOrderReturnSuccessByOrderNo(orderTableReturns.getOrderNo());
			logger.info("订单号={}下的商品退货成功数量为={}",orderTableReturns.getOrderNo(),orderReturnSuccessCount);
			//全部退货成功，订单状态更新为已完成(2)
			if(orderReturnCount == orderReturnSuccessCount){
				OrderTable orderTable=new OrderTable();
				orderTable.setOrderNo(orderTableReturns.getOrderNo());
				//订单状态更新为已完成(2)
				orderTable.setOrderStatus(OrderStatus.completed.getIndex());
				orderTableDao.updateOrderTable(orderTable);
			}else{
				logger.info("订单号={}下部分商品拒绝退货，不更新订单状态。",orderTableReturns.getOrderNo());
			}
			
		}else{
			logger.info("订单号={}下部分商品退货，不更新订单状态。",orderTableReturns.getOrderNo());
		}
		
		
	}

}