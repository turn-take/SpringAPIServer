package springAPIServer.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springAPIServer.controller.helper.CalendarAPIContorollerHelper;
import springAPIServer.dto.calendar.CalendarDto;
import springAPIServer.error.APIException;
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
	private final CalendarAPIContorollerHelper helper;
	
	// コンストラクタインジェクション
	public CalendarAPIContoroller(CalendarService calendarService, CalendarAPIContorollerHelper helper) {
		this.calendarService = calendarService;
		this.helper = helper;
	}
	/**
	 * URLパラメータで渡された日付のカレンダー情報を取得する。
	 * @param　日付
	 * @return　JSON
	 */
//	@GetMapping("/{date}")
//	public ResponseEntity<SuccessResponse<CaｌendarDto.Output>> getDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
//		//サービス呼び出し
//		calendarService.serchDate(date);
//	}
	
	/**
	 * クエリパラメータで渡された期間のカレンダー情報を取得する。
	 * @param　from
	 * @param to
	 * @return　JSON
	 */
	@GetMapping
	public ResponseEntity<SuccessResponse<CalendarDto.Output>> getDates(
			@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
			@RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to) {
		
			// InputDtoを作成する。
			CalendarDto.Input inputDto = new CalendarDto.Input(from, to);
			
			// パラメータのチェック
			if(!helper.checkParameter(i -> {
				if(i.getFirstDate() == null || i.getLastDate() == null || i.getFirstDate().after(i.getLastDate())) {
					return false;
				}
				return true;
			}, inputDto)) {
				throw new APIException("パラメータが不正です。");
			};
			
			// サービス呼び出し
			List<CalendarDto.Output> outputDtoList = calendarService.getDataByMonth(inputDto);
			
			// OutputDtoのリストからレスポンスを作成
			SuccessResponse<CalendarDto.Output> successResponse = new SuccessResponse<CalendarDto.Output>(new SuccessBody<CalendarDto.Output>(outputDtoList));
			
			// レスポンス返却
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
	}
}
