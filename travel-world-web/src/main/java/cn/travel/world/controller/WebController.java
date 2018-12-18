package cn.travel.world.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping(value = "web", method = RequestMethod.GET)
public class WebController {

	@RequestMapping(value = "web/*")
	public String home(HttpServletRequest request) {
		System.out.println("redirect to home page!"+request);
		return "src/demo/index.html";
	}

}
