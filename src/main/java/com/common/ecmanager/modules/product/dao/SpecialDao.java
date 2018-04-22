/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.dao;

import java.util.List;
import java.util.Map;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.product.entity.Special;

/**
 * 专题信息DAO接口
 * @author limy
 * @version 2016-07-14
 */
@MyBatisDao
public interface SpecialDao extends CrudDao<Special> {
	public Integer getbysort(String sort);
	public void updateFlag(String id);
	/**
	 * 查询isvalid = 1,2的所有数据
	 * @return
	 * @throws Exception
	 */
	public List<Special> querySpecialForSetStatus() throws Exception;
	
	/**
	 * 批量设置isvalid的值
	 * @param setParm map中主要有2个参数：idList-需要设置的主键list；status-需要改变的状态
	 * @throws Exception
	 */
	public void batchSetStatus(Map<String,Object> setParm) throws Exception;
	/**
	 * 查询普通专题  并且有效
	 */
	public List<Special> queryCommonSpecial() throws Exception;
	
	/**
	 * 检验活动排序是否存在，如果是修改活动序号应当排除指定的专题id
	 * @param parm
	 * @return
	 */
	public Integer checkSort(Map<String,String> parm) throws Exception;
}