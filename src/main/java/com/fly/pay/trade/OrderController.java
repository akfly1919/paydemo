package com.fly.pay.trade;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fly.pay.trade.util.IdUtil;

@Controller
public class OrderController {
	
	@RequestMapping("/createOrder.do")
	public String createOrder(ModelMap model, HttpServletRequest request){
		String orderId=IdUtil.generateOrderIdByDate(new Date());
		String productId=request.getParameter("productId");
		String productName=request.getParameter("productName");
		
		model.put("orderId", orderId);
		model.put("productId", productId);
		model.put("productName", productName);
		return "order";
	}
}
