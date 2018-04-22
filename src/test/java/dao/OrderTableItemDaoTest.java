package dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;

import com.common.ecmanager.modules.order.dao.OrderTableItemDao;

public class OrderTableItemDaoTest extends BaseTest {
	
	@Autowired
	private OrderTableItemDao orderTableItemDao;
	
	@Test
	public void countOrderReturnByOrderNoTest() throws Exception{
		String orderNo = "2017062114240001";
		int orderItemCount = orderTableItemDao.countOrderItemByOrderNo(orderNo);
		System.out.println("====>orderItemCount="+ orderItemCount);
	}
	

}
