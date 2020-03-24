package springAPIServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 「Hello world!」を返すコントローラークラス
 *　簡易的なテストに使う
 */

@RestController
public class HelloContoroller {
	
	@Autowired
	private Environment env;
	
	/**
	 * URL : localhost:8080/Hello
	 */
	@GetMapping("/Hello")
	String greet() {
		System.out.println(env.getProperty("spring.datasource.username"));
		System.out.println(env.getProperty("spring.datasource.password"));
		return "Hello world!";
	}
}
