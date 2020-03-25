package springAPIServer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import springAPIServer.dto.calendar.GetDataByMonthDto;
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
	public String getDataByMonth(@PathVariable String month) {
		try {
			
			boolean isNormalParam = checkParamater((param) -> {
				SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
				try {
					sdf.parse(param);
				} catch (ParseException e) {
					return false;
				}
				return true;
			}, month);
			
			if(isNormalParam) {
				// 中間生産物を操作して最終的にoutputを取得する
				List<GetDataByMonthDto.Output> outputList =  calendarService.getDataByMonth(YearAndMonth
						.of(month)
						.calendarSetting((cal, yAndM) -> {
							cal.set(Calendar.YEAR, yAndM.getYear());
							cal.set(Calendar.MONTH, yAndM.getMonth() - 1);})
						.createInput(cal -> {
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
				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.writeValueAsString(outputList);
			} else {
				return "えらー";
			}
		} catch (JsonProcessingException je) {
			StringBuilder sb = new StringBuilder();
			sb.append("JSONフォーマットエラーです。コードを確認してください。 \r\n");
			sb.append(je.toString());
			return sb.toString();
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder();
			sb.append("想定外のエラーが発生しました。リクエストとコードを確認してください。 \r\n");
			sb.append(e.toString());
			return sb.toString();
		}
	}
	
	public boolean checkParamater(Predicate<String> checker, String param) {
		return checker.test(param);
	}
	
	// 中間生産物
	static class YearAndMonth {
		int year;
		int month;
		
		Calendar cal;
		
		// static
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
		
		// 中間操作
		public YearAndMonth calendarSetting(BiConsumer<Calendar, YearAndMonth> con) {
			con.accept(this.cal, this);
			return this;
		}
		
		// 終端操作
		public GetDataByMonthDto.Input createInput(Function<Calendar, GetDataByMonthDto.Input> func) {
			return func.apply(this.cal);
		}
	}
}
