package springAPIServer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springAPIServer.controller.helper.YearAndMonth;
import springAPIServer.dto.SuccessDto;
import springAPIServer.dto.SuccessDto.SuccessBody;
import springAPIServer.dto.calendar.GetDataByMonthDto;
import springAPIServer.function.ThrowableConsumer;
import springAPIServer.service.CalendarService;

/**
 * CalendarAPIのコントローラークラス
 */

@RestController
@RequestMapping("/api/v1.0/calendar")
public class CalendarAPIContoroller {
	
	@Autowired
	CalendarService calendarService;
	
	/**
	 * URLパラメータで渡された月のカレンダー情報を取得する。
	 * @param month(YYYY-MM)
	 * @return JSON文字列
	 */
	@GetMapping("/data/{month}")
	public ResponseEntity<SuccessDto<GetDataByMonthDto.Output>> getDataByMonth(@PathVariable String month) throws Exception{
		try {
			// パラメータチェック
			checkPathParamater((param) -> {
				SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
				try {
					if(!param.matches("\\d{4}-\\d{2}")) throw new ParseException(param, 0);
					//FIXME 　日付チェックの厳密化のためにLocalDateを検討
					sdf.setLenient(false);
					sdf.parse(param);
				} catch (ParseException e) {
					throw new Exception("リクエストパラメータが不正です。 param=" + param);
				}
			}, month);
			
			// サービス呼び出し
			List<GetDataByMonthDto.Output> outputList =  calendarService.getDataByMonth(
					// InputDto生成
					YearAndMonth.of(month).createInputDto());
			SuccessDto<GetDataByMonthDto.Output> successDto = new SuccessDto<GetDataByMonthDto.Output>(new SuccessBody<GetDataByMonthDto.Output>(outputList));
			HttpStatus status = HttpStatus.OK;
	        return new ResponseEntity<>(successDto, status);
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 *　パスパラメータのチェックを行う
	 *　TODO オーバーロードで他のパラメータタイプのメソッドを作る
	 * @param checker 例外を投げうる関数
	 * @param param チェック対象のパラメータ
	 * @throws Exception
	 */
	public void checkPathParamater(ThrowableConsumer<String> checker, String param) throws Exception{
		// 例外を投げるか投げないかの判断をするだけ
		checker.accept(param);
	}
}
