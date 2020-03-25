package springAPIServer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
			//　日付フォーマットチェック
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
			sdf.parse(month);
			
			// 年・月を取得
			int y = Integer.parseInt(month.substring(0,4));
			int m = Integer.parseInt(month.substring(5,7));
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, y);
			cal.set(Calendar.MONTH, m - 1);
			
			// 取得した月の月初を取得
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date firstDate = cal.getTime();
			
			// 取得した月の最終日を取得
			cal.set(y, m, cal.getActualMaximum(Calendar.DATE), 0, 0, 0);
			Date lastDate = cal.getTime();
			
			// InputDto作成
			GetDataByMonthDto.Input input = new GetDataByMonthDto.Input(firstDate, lastDate);
			
			// サービス呼び出し
			List<GetDataByMonthDto.Output> outputList =  calendarService.getDataByMonth(input);
			
			// JSON返却
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(outputList);
			
		} catch(ParseException pe) {
			StringBuilder sb = new StringBuilder();
			sb.append("パラメータのフォーマットが違います！YYYY-MM形式で指定してください。 \r\n");
			sb.append(pe.toString());
			return sb.toString();
		} catch (JsonProcessingException je) {
			StringBuilder sb = new StringBuilder();
			sb.append("JSONフォーマットエラーです。コードを確認してください。 \r\n");
			sb.append(je.toString());
			return sb.toString();
		}
	}
}
