package springAPIServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * APIサーバーのエントリーポイントとなるクラス
 */
@SpringBootApplication
public class SpringApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringApiServerApplication.class, args);
	}

}
