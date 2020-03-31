package springAPIServer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springAPIServer.dto.calendar.GetDataByMonthDto;
import springAPIServer.entity.CalendarEntity;
import springAPIServer.repository.CalendarRepository;

/**
 * Calendarエンティティを扱うサービスクラス
 */
@Service
public class CalendarService {
	
	private final CalendarRepository calendarRepository;
	
	// コンストラクタインジェクション
	@Autowired
	public CalendarService(CalendarRepository calendarRepository) {
		this.calendarRepository = calendarRepository;
	}
	
	/**
	 * 指定された月のデータ取得する。
	 * @param GetDataByMonthDto.Input
	 * @return　List<GetDataByMonthDto.Output>
	 */
	public List<GetDataByMonthDto.Output> getDataByMonth(GetDataByMonthDto.Input input) {
		List<GetDataByMonthDto.Output> outputList = new ArrayList<GetDataByMonthDto.Output>();
		// DBから取得
		List<CalendarEntity> entities = calendarRepository.findByDateBetween(input.getFirstDate(), input.getLastDate());

		// Output用List作成
		entities.stream()
		.map(entity -> new GetDataByMonthDto.Output(entity.getDate(), entity.getHolidayflag()))
		.forEach(outputList::add);
		
		return outputList;
	}
}
