/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.mem.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.modules.mem.dao.WithdrawalApplyForDao;
import com.common.ecmanager.modules.mem.entity.MemMember;
import com.common.ecmanager.modules.mem.entity.WithdrawalApplyFor;

/**
 * 提现管理表Service
 * @author luogc
 * @version 2016-07-28
 */
@Service
@Transactional(readOnly = true)
public class WithdrawalApplyForService extends CrudService<WithdrawalApplyForDao, WithdrawalApplyFor> {

	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private WithdrawalApplyForDao withdrawalApplyForDao;
	
	public WithdrawalApplyFor get(String id) {
		return super.get(id);
	}
	
	public List<WithdrawalApplyFor> findList(WithdrawalApplyFor withdrawalApplyFor) {
		return super.findList(withdrawalApplyFor);
	}
	
	public Page<WithdrawalApplyFor> findPage(Page<WithdrawalApplyFor> page, WithdrawalApplyFor withdrawalApplyFor) {
		return super.findPage(page, withdrawalApplyFor);
	}
	
	public Page<WithdrawalApplyFor> findPages(Page<WithdrawalApplyFor> page, WithdrawalApplyFor withdrawalApplyFor) {
		return super.findPages(page, withdrawalApplyFor);
	}
	@Transactional(readOnly = false)
	public void save(WithdrawalApplyFor withdrawalApplyFor) {
		super.save(withdrawalApplyFor);
	}
	
	@Transactional(readOnly = false)
	public void update(WithdrawalApplyFor withdrawalApplyFor) {
		super.update(withdrawalApplyFor);
			MemMember memMember = new MemMember();
			memMember.setId(withdrawalApplyFor.getMemberId());
			List<MemMember> memMembers = memMemberService.findList(memMember);
			if(memMembers.size()>0 && memMembers != null){
				BigDecimal acount = memMembers.get(0).getBalance();
				acount = acount.subtract(withdrawalApplyFor.getApplyAmount());
				memMember.setBalance(acount);
				memMember.setId(memMembers.get(0).getId());
				memMember.setUpdateDate(new Date());
				memMemberService.updateById(memMember);
			}
			
	}
	
	@Transactional(readOnly = false)
	public void delete(WithdrawalApplyFor withdrawalApplyFor) {
		super.delete(withdrawalApplyFor);
	}
	
	@Transactional(readOnly = false)
	public void updateRemark(WithdrawalApplyFor withdrawalApplyFor) {
		withdrawalApplyForDao.updateRemark(withdrawalApplyFor);
	}
	
}