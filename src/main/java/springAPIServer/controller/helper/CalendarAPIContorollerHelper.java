package springAPIServer.controller.helper;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import springAPIServer.dto.calendar.CalendarDto;

/**
 * CalendarAPIContorollerのヘルパークラス
 *
 */
@Component
public class CalendarAPIContorollerHelper {
	
	/**
	 * パラメータのチェック
	 * @param checker
	 * @param input
	 * @return boolean
	 */
	public boolean checkParameter(Predicate<CalendarDto.Input> checker, CalendarDto.Input input){
		return checker.test(input);
	}
}
