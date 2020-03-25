package springAPIServer.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import springAPIServer.dto.calendar.GetDataByMonthDto;
import springAPIServer.dto.calendar.GetDataByMonthDto.Input;
import springAPIServer.service.CalendarService;

/**
 * CalendarAPIContorollerクラスのテストケース
 */
@SpringBootTest
public class CalendarAPIContorollerTest {
	// テスト対象
	@InjectMocks 
	CalendarAPIContoroller target;
	
	// モック
	@Mock
	CalendarService service;
	
	//　モックにテスト用の値を設定
	@BeforeEach
	public void setup() {
		Calendar cal = Calendar.getInstance();
		List<GetDataByMonthDto.Output> list = new ArrayList<>();
		for(int i = 1; i < 10; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			Date date = cal.getTime();
			GetDataByMonthDto.Output output = new GetDataByMonthDto.Output(date, true);
			list.add(output);
		}
		when(service.getDataByMonth((Input) any(GetDataByMonthDto.Input.class))).thenReturn(list);
	}
	
	@Test
	public void getDataByMonthのテスト() {
		System.out.println(target.getDataByMonth("2020-03"));
	}
}
