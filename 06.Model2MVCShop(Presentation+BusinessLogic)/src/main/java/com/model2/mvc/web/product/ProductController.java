package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	public ProductController() {
		System.out.println(this.getClass().getName()+"��Ʈ�ѷ� ������");
	}

	
	@RequestMapping("/addProduct.do")
	public ModelAndView addProduct(@ModelAttribute("product") Product product) throws Exception
	{
		System.out.println("/addProduct.do");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/product/addProductResultView.jsp");
		
		productService.addProduct(product);
		
		return mv;
	}
	
	@RequestMapping("/getProduct.do")
	public ModelAndView getProduct(@RequestParam("prodNo") String prodNo,HttpSession session,HttpServletRequest request,
									HttpServletResponse response) throws Exception
	{
		System.out.println("/getProduct.do");
		
		Product vo = productService.getProduct(Integer.parseInt(prodNo));
		ModelAndView mv = new ModelAndView();
		
		String cookieString = "";
		int count=0;
		
		if(request.getCookies()!=null) // cookie�� ���� �ƴҶ� �ϸ�ȵȴ�. cookie�� null�� �ƴϴ�.
		{
			Cookie[] cookieJar = request.getCookies();
			for(int i=0 ; i<cookieJar.length; i++)
			{
				Cookie cookie = cookieJar[i];
				if(cookie.getName().equals("history")) // history cookie�� ������,
				{
					cookieString = cookie.getValue()+","+prodNo;
					
				}
				else // cookie�� ������, history cookie�� ������.
				{
 					count++;
				}
			}
			
			if(count==cookieJar.length)
			{
				cookieString =prodNo;
			}
			
		}
		else // history�� ���� �ƿ� ��Ű�� 0�϶�.
		{
			cookieString = prodNo; // cookieString���ٰ� ù prodNo�� ���Ѵ�.
		}
		Cookie cookie = new Cookie("history",cookieString);
		cookie.setMaxAge(-1);
		response.addCookie(cookie);

		System.out.println(cookieString);
		session.setAttribute("vo", vo);
		String menu = (String) session.getAttribute("menu");
		System.out.println(menu);
		
		if(menu.equals("manage"))
			mv.setViewName("forward:/updateProductView.do");
		else
			mv.setViewName("forward:/product/readProduct.jsp");
		
		return mv;
	}
	
	@RequestMapping("/updateProductView.do")
	public ModelAndView updateProductView()
	{
		System.out.println("/updateProductView.do ����");
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("forward:/product/updateProductView.jsp");
		return mv;
	}
	
	@RequestMapping("/updateProduct.do")
	public ModelAndView updateProduct(@ModelAttribute("product") Product product, HttpSession session) throws Exception
	{
		System.out.println("/updateProduct.do ���� �ߴٸ�!");
		
		ModelAndView mv = new ModelAndView();
		Product sessionProduct = (Product) session.getAttribute("vo");
		
		product.setProdNo(sessionProduct.getProdNo());
		product.setRegDate(sessionProduct.getRegDate());
		
		productService.updateProduct(product);
		
		//model.addAttribute("vo", product);
		mv.addObject("vo", product);
		mv.setViewName("forward:/product/readProduct2.jsp");
		
		return mv;
	}
	
	@RequestMapping("/listProduct.do") //���� listProduct trancode�����ΰͰ����� �ʿ����
	public ModelAndView listProduct(@ModelAttribute("search") Search search,HttpSession session,
									@RequestParam("menu") String menu) throws Exception
	{
		System.out.println("/listProduct.do");
		
		ModelAndView mv = new ModelAndView();
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String,Object> map = productService.getProductList2(search);
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		//model.addAttribute("list", map.get("list"));
		//model.addAttribute("resultPage", resultPage);
		//model.addAttribute("search", search);
		
		mv.addObject("list", map.get("list"));
		mv.addObject("resultPage",resultPage);
		mv.addObject("search",search);
		mv.setViewName("forward:/product/listProduct.jsp");
		
		session.setAttribute("menu", menu);
		
		return mv;
	}
	
	@RequestMapping("/listProduct2.do") //���� listProduct
	public ModelAndView listProduct2(@ModelAttribute("search") Search search,HttpSession session,
			@RequestParam("menu") String menu) throws Exception
	{
			ModelAndView mv = new ModelAndView();
			System.out.println("/listProduct2.do");

			if(search.getCurrentPage() ==0 ){
				search.setCurrentPage(1);
			}
			search.setPageSize(pageSize);

			Map<String,Object> map = productService.getProductList(search);
			Page resultPage	= 
					new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);

			//model.addAttribute("list", map.get("list"));
			//model.addAttribute("resultPage", resultPage);
			//model.addAttribute("search", search);

			mv.addObject("list", map.get("list"));
			mv.addObject("resultPage",resultPage);
			mv.addObject("search",search);
			mv.setViewName("forward:/product/listProduct2.jsp");
			
			session.setAttribute("menu", menu);

			return mv; //�ǸŻ�ǰ������ forward
	}
	
}
