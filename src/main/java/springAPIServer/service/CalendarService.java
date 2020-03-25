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
	
	@Autowired
	CalendarRepository calendarRepository;
	
	/**
	 * 指定された月のデータ取得する。
	 * @param GetDataByMonthDto.Input
	 * @return　GetDataByMonthDto.Output
	 */
	public List<GetDataByMonthDto.Output> getDataByMonth(GetDataByMonthDto.Input input) {
		// DBから取得
		List<CalendarEntity> entities = calendarRepository.findByDateBetween(input.getFirstDate(), input.getLastDate());

		// Output作成
		List<GetDataByMonthDto.Output> outputList = new ArrayList<GetDataByMonthDto.Output>();
		entities.forEach(entity -> {
			GetDataByMonthDto.Output output = new GetDataByMonthDto.Output(entity.getDate(), entity.getHolidayflag());
			System.out.println(entity);
			outputList.add(output);
		});
		
		return outputList;
	}
}
