package springAPIServer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import springAPIServer.dto.calendar.GetDataByMonthDto;

/**
 * Calendarエンティティを扱うサービスクラス
 */
@Service
public class CalendarService {
	
	/**
	 * 指定された月のデータ取得する。
	 * @param GetDataByMonthDto.Input
	 * @return　GetDataByMonthDto.Output
	 */
	public List<GetDataByMonthDto.Output> getDataByMonth(GetDataByMonthDto.Input input) {
		List<GetDataByMonthDto.Output> outputList = new ArrayList<GetDataByMonthDto.Output>();
		return outputList;
	}
}
