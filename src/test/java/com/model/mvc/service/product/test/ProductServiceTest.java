package com.model.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;



/*
 * 	FileName : ProductServiceTest.java
 * 	- JUnit 4 (Test Framework)와 Spring Framework 통합 Test (Unit Test)
 *  - Spring은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다. 
 *  - @RunWith : Meta-data를 통한 wiring(생성,DI) 할 객체 구현체 지정
 *  - @ContextConfiguration : Meta-data location 지정
 *  - @Test : 테스트 실행 소스 지정
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/commonservice.xml"})
public class ProductServiceTest {

	//==>@RunWith, @ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Test
	public void testAddProduct() throws Exception{
		
		Product product = new Product();
		product.setProdNo(1);
		product.setProdName("testProductName");
		product.setProdDetail("testProductDetail");
		product.setManufDay("20000101");
		product.setPrice(10000);
		product.setImgFile("no image");
		
		productService.addProduct(product);
		
		product = productService.getProduct(1);
		
		//==> console 확인
		//System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals(1, product.getProdNo());
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProductDetail", product.getProdDetail());
		Assert.assertEquals("20000101", product.getManufDay());
		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("no image", product.getImgFile());
		
	}
	
	//@Test
	public void testGetProduct() throws Exception{
		
		Product product = new Product();
		
		product = productService.getProduct(1);
		
		Assert.assertEquals(1, product.getProdNo());
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProductDetail", product.getProdDetail());
		Assert.assertEquals("20000101", product.getManufDay());
		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("no image", product.getImgFile());
		
		Assert.assertNotNull(productService.getProduct(10020));
		//Assert.assertNotNull(productService.getProduct(10035));
		
	}
	
	//@Test
	public void testUpdateProduct() throws Exception{
		
		Product product = productService.getProduct(1);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProductDetail", product.getProdDetail());
		Assert.assertEquals("20000101", product.getManufDay());
		Assert.assertEquals(10000,	product.getPrice());
		Assert.assertEquals("no image", product.getImgFile());
		
		product.setProdName("change");
		product.setProdDetail("change");
		product.setManufDay("222222");
		product.setPrice(2000);
		product.setImgFile("change");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(1);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("change", product.getProdName());
		Assert.assertEquals("change", product.getProdDetail());
		Assert.assertEquals("222222", product.getManufDay());
		Assert.assertEquals( 2000, product.getPrice());
		Assert.assertEquals("change", product.getImgFile());
	}
	
	//@Test
	public void testGetProductListAll() throws Exception{
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(3, list.size());
		
		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);
		
		System.out.println("=========================================");
		
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = productService.getProductList(search);
		
		list = (List<Object>)map.get("list");
		Assert.assertEquals(3, list.size());
		
		totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);
	}
	
	
	//@Test
	public void testGetProductListByProductNo() throws Exception{
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10027");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}
	
	//@Test
		 public void testGetProductListByProductName() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword("연꽃");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }	 
}
