package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
public class PurchaseController {

	@Autowired
	@Qualifier("purchasServiceImpl")
	private PurchaseService purchaseService;
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	public PurchaseController() {
		System.out.println(this.getClass()+"컨트롤러 생성자");
	}
	
	@RequestMapping("/addPurchase.do")
	public String addPurchase(HttpSession session,@RequestParam("quantity") int quantity,
								@ModelAttribute("purchase") Purchase purchase,Model model) throws Exception
	{
		System.out.println("/addPurchase.do");
		
		User user = (User) session.getAttribute("user");
		Product product = (Product) session.getAttribute("vo");
		
		product.setQuantity(quantity);
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		
		model.addAttribute("requestPvo", product);
		purchaseService.addPurchase(purchase);
		
		return "forward:/purchase/addPurchaseTable.jsp";
	}
	
	@RequestMapping("/addPurchaseView.do")
	public String addPurchaseView()
	{
		System.out.println("/addPurchaseView.do");
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	@RequestMapping("/getPurchase.do")
	public String getPurchase(@RequestParam("tranNo") int tranNo, Model model) throws Exception
	{
		System.out.println("/getPurchase.do");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model) throws Exception
	{
		System.out.println("/updatePurchaseView.do");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchase.jsp";
	}
	
	@RequestMapping("/updatePurchase.do")
	public String updatePurchase(@RequestParam("tranNo") int tranNo,
							@ModelAttribute("purchase") Purchase purchase,Model model) throws Exception
	{
		System.out.println("/updatePurchase.do");
		
		Purchase sqlPurchase = purchaseService.getPurchase(tranNo);
		sqlPurchase.setPaymentOption(purchase.getPaymentOption());
		sqlPurchase.setReceiverName(purchase.getReceiverName());
		sqlPurchase.setReceiverPhone(purchase.getReceiverPhone());
		sqlPurchase.setDivyAddr(purchase.getDivyAddr());
		sqlPurchase.setDivyRequest(purchase.getDivyRequest());
		sqlPurchase.setDivyDate(purchase.getDivyDate().substring(0, 10));
		
		purchaseService.updatePurcahse(sqlPurchase);
		sqlPurchase = purchaseService.getPurchase(tranNo);
		
		model.addAttribute("purchase", sqlPurchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping("/listPurchase.do")
	public String listPurchase(HttpSession session,
								@ModelAttribute("search") Search search,Model model) throws Exception
	{
		
		System.out.println("/listPurchase.do");
		
		User user = (User) session.getAttribute("user");
		
		if(search.getCurrentPage()==0)
		{
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String,Object> map = purchaseService.getPurchaseList(search, user.getUserId());
		
		Page resultPage = 
				new Page(search.getCurrentPage(),((Integer)map.get("totalCount")).intValue(),pageUnit,pageSize);

		model.addAttribute("list", map.get("list"));
		model.addAttribute("search", search);
		model.addAttribute("resultPage", resultPage);
		
		return "forward:/purchase/listPurchase.jsp"; 
	}
	
	@RequestMapping("/listSale.do")
	public String listSale(@ModelAttribute("search") Search search, HttpSession session,
								@RequestParam("menu") String menu) throws Exception
	{
		System.out.println("/listSale.do");
		
		if(search.getCurrentPage()==0)
		{
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);
		
		Map<String,Object> map = purchaseService.getSaleList(search);
		session.setAttribute("menu", menu);
		
		return "forward:/product/listProduct2.jsp";
	}
	
	@RequestMapping("/updateTranCode.do")
	public String updateTranCode(@RequestParam("prodNo") int prodNo, HttpSession session) throws Exception
	{
		System.out.println("/updateTranCode.do");
		
		Purchase purchase = purchaseService.getPurchase2(prodNo);
		purchaseService.updateTranCode(purchase);
		
		User user = (User) session.getAttribute("user");
		
		if(user.getUserId().equals("admin"))
			return "forward:/listSale.do";
		else
			return "forward:/listPurchase.do";
	}

	//@RequestMapping("/updateTranCodeByProd.do") 아니 왜있냐고?
	//public String updateTranCodeByProd()
	//{
		
	//}
}
