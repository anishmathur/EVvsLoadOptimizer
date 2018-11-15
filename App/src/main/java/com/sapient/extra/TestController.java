package com.sapient.extra;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

String name;

	@GetMapping("/test")
	public String test(Map<String, Object> model) {
		model.put("name", "Archit");
		return "test";
	}

}