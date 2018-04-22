/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.modules.order.dao.OrderReturnsRefDao;
import com.common.ecmanager.modules.order.entity.OrderReturnsRef;

/**
 * 订单与退货关联Service
 * @author limy
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class OrderReturnsRefService extends CrudService<OrderReturnsRefDao, OrderReturnsRef> {

	public OrderReturnsRef get(String id) {
		return super.get(id);
	}
	
	public List<OrderReturnsRef> findList(OrderReturnsRef orderReturnsRef) {
		return super.findList(orderReturnsRef);
	}
	
	public Page<OrderReturnsRef> findPage(Page<OrderReturnsRef> page, OrderReturnsRef orderReturnsRef) {
		return super.findPage(page, orderReturnsRef);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderReturnsRef orderReturnsRef) {
		super.save(orderReturnsRef);
	}
	
	@Transactional(readOnly = false)
	public void update(OrderReturnsRef orderReturnsRef) {
		super.update(orderReturnsRef);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderReturnsRef orderReturnsRef) {
		super.delete(orderReturnsRef);
	}
	@Transactional(readOnly = false)
	public List<OrderReturnsRef> findReturnsRefList(String orderNo){
		return dao.findReturnsRefByOrderNo(orderNo);
	}

}