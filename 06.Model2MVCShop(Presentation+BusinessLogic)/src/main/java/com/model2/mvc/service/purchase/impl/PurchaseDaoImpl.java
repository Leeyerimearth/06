package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession)
	{
		this.sqlSession = sqlSession;
	}
	
	
	public PurchaseDaoImpl()
	{
		System.out.println(this.getClass().getName()+"»ý¼ºÀÚ..");
	}
	
	@Override
	public Purchase findPurchase(int identifyNumber, int sqlDecideNum) {
		// TODO Auto-generated method stub
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("identifyNumber", identifyNumber);
		map.put("sqlDecideNum",sqlDecideNum);
		
		return sqlSession.selectOne("PurchaseMapper.findPurchase", map);
	}

	@Override
	public List<Purchase> getPurchaseList(Search search, String buyerId) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("search", search);
		map.put("buyerId",buyerId);
		
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}

	@Override
	public List<Product> getSaleList(Search search) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("PurchaseMapper.getSaleList", search);
	}

	@Override
	public void insertPurchase(Purchase purchase) {
			System.out.println("paymentOption: "+purchase.getPaymentOption());
		sqlSession.insert("PurchaseMapper.insertPurchase", purchase);
	}

	@Override
	public void updatePurchase(Purchase purchase) {
		
		purchase.setDivyDate(purchase.getDivyDate().substring(0, 10));
		System.out.println(purchase);
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) {
		
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);
		
	}

	@Override
	public int getTotalCount(Search search,String buyerId) {
	
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", map);
	}

}
