package dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;

import com.common.ecmanager.modules.order.dao.OrderTableReturnsDao;

public class OrderTableReturnsDaoTest extends BaseTest {
	
	@Autowired
	private OrderTableReturnsDao orderTableReturnsDao;
	
	@Test
	public void countOrderReturnByOrderNoTest() throws Exception{
		String orderNo = "2016111514030747330493";
		int orderReturnCount = orderTableReturnsDao.countOrderReturnByOrderNo(orderNo);
		System.out.println("====>orderReturnCount="+ orderReturnCount);
	}
	
	@Test
	public void countOrderReturnSuccessByOrderNoTest() throws Exception{
		String orderNo = "2016111514030747330493";
		int orderReturnSuccessCount = orderTableReturnsDao.countOrderReturnSuccessByOrderNo(orderNo);
		System.out.println("====>orderReturnSuccessCount="+ orderReturnSuccessCount);
	}

}
