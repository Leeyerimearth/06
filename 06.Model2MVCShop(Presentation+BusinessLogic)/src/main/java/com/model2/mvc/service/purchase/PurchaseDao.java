package com.model2.mvc.service.purchase;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {

	public Purchase findPurchase(int identifyNumber, int sqlDecideNum);
	
	public List<Purchase> getPurchaseList(Search search, String buyerId);
	
	public List<Product> getSaleList(Search search);
	
	public void insertPurchase(Purchase purchase);
	
	public void updatePurchase(Purchase purchase);
	
	public void updateTranCode(Purchase purchase);
	
	public int getTotalCount(Search search,String buyerId);
}
