/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.mem.dao.RecommenderLogDao;
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.mem.entity.RecommenderLog;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.service.ProductService;

/**
 * 推广记录Service
 * @author heyh
 * @version 2017-01-03
 */
@Service
@Transactional(readOnly = true)
public class RecommenderLogService extends CrudService<RecommenderLogDao, RecommenderLog> {
	@Autowired
	private RecommenderLogDao recommenderLogDao;
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private ProductService productService;
	
	public RecommenderLog get(String id) {
		return super.get(id);
	}
	
	public List<RecommenderLog> findList(RecommenderLog recommenderLog) {
		return super.findList(recommenderLog);
	}
	
	public Page<RecommenderLog> findPage(Page<RecommenderLog> page, RecommenderLog recommenderLog) {
		 page = super.findPage(page, recommenderLog);
		 List<RecommenderLog> recommenderLogList = page.getList();
		 List<String> rootList = new ArrayList<String>(); 
			for(RecommenderLog recomm :recommenderLogList){
				String parentId = recomm.getParentId().split("-")[0];
				String memberId = recomm.getMemberId().split("-")[0];
				
				//查找推广人和被推广人
				MemMember mem = new MemMember();
				mem.setId(parentId);
				MemMember parentMemMember = memMemberService.get(mem);
				if(parentMemMember!=null){
					recomm.setParentName(parentMemMember.getName());
				}
				mem.setId(memberId);
				MemMember memMember = memMemberService.get(mem);
				if(memMember!=null){
					recomm.setMemMemberName(memMember.getName());
				}
				
				//查找商品名称和商品编码
				Product product = new Product();
				product.setId(recomm.getProductId());
				Product productFind = productService.get(product);
				if(productFind!=null){
					recomm.setProductCode(productFind.getProductNo());
					recomm.setProductName(productFind.getFullName());
				}
				
				boolean flag = true;  //假定是根目录
				for(RecommenderLog r:recommenderLogList){
					//计算点击数和已关注数
					String[] ids = r.getParentIds().split(",");
					for(String id:ids){
						String pp = id+"-"+r.getProductId();
						if(pp.equals(recomm.getParentId())){
							if("1".equals(r.getMemberStatusCode())){
								int n = recomm.getClickCount()+1;
								recomm.setClickCount(n);
							}else if("2".equals(r.getMemberStatusCode())){
								int m = recomm.getLinkCount()+1;
								recomm.setLinkCount(m);
							}
						}
					}
					//查找根目录
					if(flag && recomm.getParentId().equals(r.getMemberId())){
						flag = false;
					}
				}
				if(flag){
					//如果是根目录，则添加
					rootList.add(recomm.getParentId());
				}
			}
			page.setCount2(rootList.size());
			return page;
	}
	
	@Transactional(readOnly = false)
	public void save(RecommenderLog recommenderLog) {
		super.save(recommenderLog);
	}
	
	@Transactional(readOnly = false)
	public void update(RecommenderLog recommenderLog) {
		super.update(recommenderLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(RecommenderLog recommenderLog) {
		super.delete(recommenderLog);
	}
	
	/**
	 * 根据parentId查询下级memberId
	 * @param parentId
	 * @return
	 */
	public List<RecommenderLog> findIdsByParentId(String parentId) {
		return recommenderLogDao.findIdsByParentId(parentId);
	}

	public RecommenderLog getByMemProId(RecommenderLog r) {
		return recommenderLogDao.getByMemProId(r);
	}
	
}