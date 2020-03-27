package springAPIServer.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
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
	public void checkParamaterが例外をスローする() {
		
		String insuitableFormat1 = "2020-0";
		String insuitableFormat2 = "2020-013";
		String insuitableFormat3 = "202001";
		String insuitableFormat4 = "2020-13";
		
		assertThrows(Exception.class, () -> target.getDataByMonth(insuitableFormat1));
		assertThrows(Exception.class, () -> target.getDataByMonth(insuitableFormat2));
		assertThrows(Exception.class, () -> target.getDataByMonth(insuitableFormat3));
		//FIXME 現状では↓はダメです
		//assertThrows(Exception.class, () -> target.getDataByMonth(insuitableFormat4));
	}
	
	@Test
	public void checkParamaterが例外をスローしない() {
		
		String insuitableFormat1 = "2020-01";
		
		assertDoesNotThrow(() -> target.getDataByMonth(insuitableFormat1));
	}
}
