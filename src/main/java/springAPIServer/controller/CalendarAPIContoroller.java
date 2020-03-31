package springAPIServer.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springAPIServer.dto.calendar.GetDataByMonthDto;
import springAPIServer.response.SuccessResponse;
import springAPIServer.response.SuccessResponse.SuccessBody;
import springAPIServer.service.CalendarService;

/**
 * CalendarAPIのコントローラークラス
 */

@RestController
@RequestMapping("/api/v1.0/calendar")
public class CalendarAPIContoroller {
	
	private final CalendarService calendarService;
	
	// コンストラクタインジェクション
	@Autowired
	public CalendarAPIContoroller(CalendarService calendarService) {
		this.calendarService = calendarService;
	}
	/**
	 * URLパラメータで渡された月のカレンダー情報を取得する。
	 * @param month(YYYY-MM)
	 * @return JSON文字列
	 */
	@GetMapping("/data/{month}")
	public ResponseEntity<SuccessResponse<GetDataByMonthDto.Output>> getDataByMonth(@PathVariable String month) throws Exception{
		try {
			// 正規表現でYYYY-MM形式化のチェック
			if(!month.matches("^\\d{4}-\\d{2}$")) throw new DateTimeException("");
			
			// YearMonthオブジェクトを利用して月初と最終日を取得する。
			YearMonth yearMonth = YearMonth.of(Integer.parseInt(month.substring(0,4)), Integer.parseInt(month.substring(5,7)));
			Date firstDateOfMonth = localDateToDate(yearMonth.atDay(1));
			Date lastDateOfMonth = localDateToDate(yearMonth.atEndOfMonth());
			
			// InputDtoを作成する。
			GetDataByMonthDto.Input inputDto = new GetDataByMonthDto.Input(firstDateOfMonth, lastDateOfMonth);
			
			// サービス呼び出し
			List<GetDataByMonthDto.Output> outputList = calendarService.getDataByMonth(inputDto);
			
			// OutputDtoのリストからレスポンスを作成
			SuccessResponse<GetDataByMonthDto.Output> successResponse = new SuccessResponse<GetDataByMonthDto.Output>(new SuccessBody<GetDataByMonthDto.Output>(outputList));
			
			// レスポンス返却
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}catch(DateTimeException e) { // 日付フォーマットエラーの場合
			e.printStackTrace();
			throw new DateTimeException("リクエストパラメータが不正です。 パラメータ=" + month);
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception("サーバ処理中にエラーが発生しました。");
		}
	}
	
	public static Date localDateToDate(final LocalDate localDate) {
		  return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
