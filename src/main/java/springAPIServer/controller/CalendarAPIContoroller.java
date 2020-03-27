package springAPIServer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			checkParamater((param) -> {
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
					// YearAndMonth生成して
					YearAndMonth.of(month)
					// Calendarの調節をして
					.adjustCalendar((cal, yAndM) -> {
						cal.set(Calendar.YEAR, yAndM.getYear());
						cal.set(Calendar.MONTH, yAndM.getMonth() - 1);})
					// InputDtoの生成をする
					.createInputDto(cal -> {
						// 月初を取得
						cal.set(Calendar.DAY_OF_MONTH, 1);
						Date firstDate = cal.getTime();
						// 最終日を取得
						cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
						Date lastDate = cal.getTime();
						// InputDto作成
						GetDataByMonthDto.Input input = new GetDataByMonthDto.Input(firstDate, lastDate);
						return input;
					}));
			SuccessDto<GetDataByMonthDto.Output> successDto = new SuccessDto<GetDataByMonthDto.Output>(new SuccessBody<GetDataByMonthDto.Output>(outputList));
			HttpStatus status = HttpStatus.OK;
	        return new ResponseEntity<>(successDto, status);
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 *　パラメータのチェックを行う
	 *　TODO オーバーロードで他のパラメータタイプのメソッドを作る
	 * @param checker 例外を投げうる関数
	 * @param param チェック対象のパラメータ
	 * @throws Exception
	 */
	public void checkParamater(ThrowableConsumer<String> checker, String param) throws Exception{
		// 例外を投げるか投げないかの判断をするだけ
		checker.accept(param);
	}
	
	/**
	 * 月と日付を扱うためのクラス
	 */
	static class YearAndMonth {
		int year;
		int month;
		
		// FIXME LocalDateを使いたい
		Calendar cal;
		
		/**
		 *  YYYY-MM形式の文字列からインスタンスを作成するstaticファクトリ
		 * @param month YYYY-MM形式の文字列
		 * @return YearAndMonthオブジェクト
		 */
		public static YearAndMonth of(String month) {
			return new YearAndMonth(
					Integer.parseInt(month.substring(0,4)), Integer.parseInt(month.substring(5,7)));
		}
		
		public YearAndMonth(int year, int month) {
			this.year = year;
			this.month = month;
			this.cal = Calendar.getInstance();
		}
		
		public int getYear() {
			return year;
		}
		public int getMonth() {
			return month;
		}
		
		/**
		 * インスタンス内で保持するCalendarの調節を行う
		 * @param Calendarの調節を行う関数
		 */
		public YearAndMonth adjustCalendar(BiConsumer<Calendar, YearAndMonth> con) {
			con.accept(this.cal, this);
			return this;
		}
		
		/**
		 * InputDtoの生成をする
		 * @param 生成を行う関数
		 */
		public GetDataByMonthDto.Input createInputDto(Function<Calendar, GetDataByMonthDto.Input> func) {
			return func.apply(this.cal);
		}
	}
}
