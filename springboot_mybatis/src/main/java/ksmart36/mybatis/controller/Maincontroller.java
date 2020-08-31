package ksmart36.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Maincontroller {
	// request get방식 http://localhost
		@GetMapping("/")
		public String index(Model model) {
			
			//논리주소-> view resolver -> src/main/resources/template/main.html
			model.addAttribute("title", "main화면");
			return "main";		
		}
		@GetMapping("/main.html")
		public String test(Model model) {
			
			//논리주소-> view resolver -> src/main/resources/template/main.html
			return "main";		
		}
}
