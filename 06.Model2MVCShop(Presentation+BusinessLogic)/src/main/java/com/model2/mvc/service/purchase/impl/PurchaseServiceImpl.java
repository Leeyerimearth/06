package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl  implements PurchaseService{

	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	
	public void setProductDao(ProductDao productDao)  // 어노테이션이 setter를 이용해서 set해주는거
	{
		this.productDao = productDao;
	}
	
	public void setPurchaseDao(PurchaseDao purchaseDao)
	{
		this.purchaseDao = purchaseDao;
	}
	
	public PurchaseServiceImpl() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass().getName()+"생성자...");
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		purchaseDao.insertPurchase(purchase);
		productDao.updateQuantity(purchase.getPurchaseProd());
		
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		
		int sqlDecideNum =1;
		return purchaseDao.findPurchase(tranNo, sqlDecideNum);
	}

	@Override
	public Purchase getPurchase2(int prodNo) throws Exception {
		
		int sqlDecideNum =2;
		return purchaseDao.findPurchase(prodNo, sqlDecideNum);
	}

	@Override
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		// TODO Auto-generated method stub
			List<Purchase> purchaseList = purchaseDao.getPurchaseList(search, buyerId);
			int totalCount = purchaseDao.getTotalCount(search, buyerId);
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("totalCount", totalCount);
			map.put("list", purchaseList);
		
		return map;
	}

	@Override
	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		// TODO Auto-generated method stub
		List<Product> allList =productDao.getProductList(search);
		List<Product> joinList = purchaseDao.getSaleList(search);
		
		Product tempProduct = new Product();
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		for(int i=0; i<allList.size();i++)
		{
			//allList.get(i).setProTranCode("001");
			tempProduct = allList.get(i);
			tempProduct.setProTranCode("001"); // 일단 tempProduct protranCode를 셋
			for(int j=0; j<joinList.size();j++)
			{
				if(joinList.get(j).getProdNo()==tempProduct.getProdNo())
				{
					tempProduct.setProTranCode(joinList.get(j).getProTranCode());
				}
			}
			allList.set(i, tempProduct);
		}
		
		map.put("list",allList);
		
		return map;
	}

	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
		
		purchaseDao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		purchaseDao.updateTranCode(purchase);
	}
	
	
}
