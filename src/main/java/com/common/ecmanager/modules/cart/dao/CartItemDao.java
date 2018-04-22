/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.cart.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.cart.entity.CartItem;

/**
 * 购物车项DAO接口
 * @author gaven
 * @version 2015-06-30
 */
@MyBatisDao
public interface CartItemDao extends CrudDao<CartItem> {
	
}