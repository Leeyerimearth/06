package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;


@Repository("productDaoImpl") // 로 정의 한다 . 안해도 디폴트!
public class ProductDaoImpl implements ProductDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession)
	{
		this.sqlSession = sqlSession;
	}
	
	public ProductDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Product findProduct(int prodNo) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectOne("ProductMapper.findProduct", prodNo);
	}

	@Override
	public List<Product> getProductList2(Search search) {
		// TODO Auto-generated method stub
		System.out.println("얘가 사실 갔다오는게 없다.");
		return null;
	}

	@Override
	public List<Product> getProductList(Search search) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("ProductMapper.getProductList", search);
	}

	@Override
	public void insertProduct(Product product) {
		// TODO Auto-generated method stub
		System.out.println(product +"productDAOIMPL");
		sqlSession.insert("ProductMapper.insertProduct",product);
	}

	@Override
	public void updateQuantity(Product product) { // HashMap map) {
		
		Product getProduct=this.findProduct(product.getProdNo());
		int prodQuantity = getProduct.getQuantity() - product.getQuantity();
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("prodQuantity", prodQuantity);
		map.put("prodNo", product.getProdNo());
		// TODO Auto-generated method stub
		// 여기서 findProduct부르고 map으로 데이터 전달. product랑 ,prodQuantity -> serviceImlp에서 한댜아
		//product대산에 Hashmap을 전달받고 그걸 다시 전달한다. Hashmap에는, findProduct를 불러 얻은 product랑,quantity를 string이 저장되어있다.
		sqlSession.update("ProductMapper.updateQuantity", map); //purchase할때 사용된다.
															//map
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		sqlSession.update("ProductMapper.updateProduct", product);
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ProductMapper.getTotalCount",search);
	}

}
