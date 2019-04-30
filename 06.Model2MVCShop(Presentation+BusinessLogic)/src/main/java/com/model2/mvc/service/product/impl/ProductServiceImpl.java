package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier("productDaoImpl")
	ProductDao productDao;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	public ProductServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public void setProductDao(ProductDao productDao)
	{
		System.out.println("setProductDao Ω««‡");
		this.productDao = productDao;
	}
	public void setPurchaseService(PurchaseService purchaseService)
	{
		this.purchaseService = purchaseService;
	}

	@Override
	public void addProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		productDao.insertProduct(product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		return productDao.findProduct(prodNo);
	}

	@Override
	public HashMap<String, Object> getProductList2(Search search) throws Exception {
		// TODO Auto-generated method stub
				//productDao.getProductList2(search); -> null¿”
		
		
		//purchaseDao.getSaleList(search);
		return	purchaseService.getSaleList(search);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		productDao.updateProduct(product);
	}

}
