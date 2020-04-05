package springAPIServer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springAPIServer.dto.calendar.CalendarDto;
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
	 * 指定された日付のエンティティを取得する。
	 * @param CaｌendarDto.Input
	 * @return　List<CaｌendarDto.Output>
	 */
//	public List<CaｌendarDto.Output> serchDate(Date date) {
//		List<CaｌendarDto.Output> outputList = new ArrayList<CaｌendarDto.Output>();
//		// DBから取得
//		List<CalendarEntity> entities = calendarRepository.findByDateBetween(input.getFirstDate(), input.getLastDate());
//
//		// Output用List作成
//		entities.stream()
//		.map(entity -> new CaｌendarDto.Output(entity.getDate(), entity.getHolidayflag()))
//		.forEach(outputList::add);
//		
//		return outputList;
//	}
	
	/**
	 * 指定された月のデータ取得する。
	 * @param CaｌendarDto.Input
	 * @return　List<CaｌendarDto.Output>
	 */
	public List<CalendarDto.Output> getDataByMonth(CalendarDto.Input input) {
		List<CalendarDto.Output> outputList = new ArrayList<CalendarDto.Output>();
		// DBから取得
		List<CalendarEntity> entities = calendarRepository.findByDateBetween(input.getFirstDate(), input.getLastDate());

		// Output用List作成
		entities.stream()
		.map(entity -> new CalendarDto.Output(entity.getDate(), entity.getHolidayflag()))
		.forEach(outputList::add);
		
		return outputList;
	}
}
