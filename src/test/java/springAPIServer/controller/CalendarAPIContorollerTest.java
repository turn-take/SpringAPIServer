package springAPIServer.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import springAPIServer.advice.ErrorHandler;
import springAPIServer.controller.helper.CalendarAPIContorollerHelper;
import springAPIServer.dto.calendar.CalendarDto;
import springAPIServer.response.ErrorResponse;
import springAPIServer.response.ErrorResponse.ErrorBody;
import springAPIServer.response.SuccessResponse.SuccessBody;
import springAPIServer.response.SuccessResponse;
import springAPIServer.service.CalendarService;

/**
 * CalendarAPIContorollerクラスのテストケース
 */
@SpringBootTest
// ObjectMapperを使えるようにする
@ContextConfiguration(classes = JacksonAutoConfiguration.class)
@ExtendWith( MockitoExtension.class)
public class CalendarAPIContorollerTest {
	MockMvc mockMvc;
	
	// テスト対象
	@InjectMocks 
	CalendarAPIContoroller target;
	
	@Spy
	CalendarAPIContorollerHelper helper;
	
	@Mock
	CalendarService service;
	
	@Autowired
	ObjectMapper mapper;
	
	/**
	 * テストのセットアップ
	 */
	@BeforeEach
	public void setup() {
		// Mockをテスト対象クラスにインジェクトするように設定
		MockitoAnnotations.initMocks(this);
		// MockMVCの生成
		mockMvc = MockMvcBuilders.standaloneSetup(target)
				// 例外ハンドリングクラスを有効化
				.setControllerAdvice(ErrorHandler.class)
				.build();
		reset(helper);
	}
	
	/**
	 * パラメータ不足時時のテスト
	 * @throws Exception
	 */
	@Test
	public void パラメータ不足時は400エラー() throws Exception {
		// fromがない
		mockMvc.perform(get("/api/v1.0/calendar?to=20200201"))
		.andExpect(status().isBadRequest());
		// toがない
		mockMvc.perform(get("/api/v1.0/calendar?from=2020-01-01"))
		.andExpect(status().isBadRequest());
		// 両方無い
		mockMvc.perform(get("/api/v1.0/calendar"))
		.andExpect(status().isBadRequest());
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void パラメータ不正時は400エラー() throws Exception {
		// パラメータ名不正
		mockMvc.perform(get("/api/v1.0/calendar?fromm=20200101&to=20200201"))
		.andExpect(status().isBadRequest());
		// パラメータが多い
		mockMvc.perform(get("/api/v1.0/calendar?from=20200101&to=20200201&too=20200301"))
		.andExpect(status().isBadRequest());
	}
	
	/**
	 * パラメータの形式のテスト
	 * @throws Exception
	 */
	@Test
	public void YYYYーMM形式では無い場合は400エラー() throws Exception {
		// 両方ダメ
		mockMvc.perform(get("/api/v1.0/calendar?from=20200101&to=20200201"))
		.andExpect(status().isBadRequest());
		// 片方ダメ
		mockMvc.perform(get("/api/v1.0/calendar?from=2020-01-01&to=20200201"))
		.andExpect(status().isBadRequest());
		mockMvc.perform(get("/api/v1.0/calendar?from=20200101&to=2020-02-01"))
		.andExpect(status().isBadRequest());
	}
	
	
	/**
	 * パラメータチェック不正時のテスト
	 * @throws Exception
	 */
	@Test
	public void パラメータチェックがfalseの場合は400エラー() throws Exception {
		
		doReturn(false).when(helper).checkParameter(Mockito.any(), Mockito.any());
		
		// 期待値作成
		ErrorResponse res = new ErrorResponse(new ErrorBody("パラメータが不正です。"));
		String expectedJson = mapper.writeValueAsString(res);
		
		mockMvc.perform(get("/api/v1.0/calendar?from=2020-01-01&to=2020-02-01"))
		.andExpect(status().isBadRequest())
		.andExpect(content().json(expectedJson));
	}
	
	/**
	 * 正常終了時のテスト
	 * @throws Exception
	 */
// TODO CalendarServiceのMockが動作しないのでどうにかしたい
//	@Test
//	public void 正常終了時は200OK() throws Exception {
//		
//		Calendar cal = Calendar.getInstance();
//		List<CalendarDto.Output> list = new ArrayList<>();
//		for(int i = 1; i < 10; i++) {
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			Date date = cal.getTime();
//			CalendarDto.Output output = new CalendarDto.Output(date, true);
//			list.add(output);
//		}
//		
//		doReturn(true).when(helper).checkParameter(Mockito.any(), Mockito.any());
//		when(service.getDataByMonth(Mockito.any())).thenReturn(list);
//		
//		// 期待値作成
//		SuccessResponse<CalendarDto.Output> res = new SuccessResponse<CalendarDto.Output>(new SuccessBody<CalendarDto.Output>(list));
//		String expectedJson = mapper.writeValueAsString(res);
//		
//		mockMvc.perform(get("/api/v1.0/calendar?from=2020-01-01&to=2020-02-01"))
//		.andExpect(status().isOk())
//		.andExpect(content().json(expectedJson));
//	}
}
