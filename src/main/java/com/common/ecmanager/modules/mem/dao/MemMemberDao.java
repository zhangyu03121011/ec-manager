/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.mem.dao;

import java.util.List;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.mem.entity.MemMember;

/**
 * 会员表DAO接口
 * @author kevin
 * @version 2015-05-31
 */
@MyBatisDao
public interface MemMemberDao extends CrudDao<MemMember> {
	public void updateVarifyStatus(MemMember memMember);
	
	/**自定义会员审核列表带分页
	 * @param memMember
	 * @return
	 */
	public List<MemMember> findMemApproveList(MemMember memMember);
	/**
	 * 通过id修改commission
	 * @param memMember
	 */
	public void updateById(MemMember memMember);

	public MemMember getMemMemberByName(MemMember memMember);
	
	/**会员购买统计列表带分页
	 * @param memMember
	 * @return
	 */
	public List<MemMember> findPurchaseList(MemMember memMember);

}