package com.fly.pay.trade;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cashier")
public class CashierController {

	@RequestMapping("/cashierList.do")
	public String cashierList(ModelMap model, HttpServletRequest request){
		
		return "hello";
	}
}
