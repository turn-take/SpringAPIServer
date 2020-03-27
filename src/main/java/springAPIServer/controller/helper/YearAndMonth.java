package springAPIServer.controller.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Supplier;

import springAPIServer.dto.calendar.GetDataByMonthDto;

/**
 * 年と月を扱うためのクラス
 */
public class YearAndMonth {
	final int year;
	final int month;
	
	// FIXME LocalDateを使いたい
	private final Calendar cal;
	
	/**
	 *  YYYY-MM形式の文字列からインスタンスを作成するstaticファクトリ
	 * @param month YYYY-MM形式の文字列
	 * @return YearAndMonthオブジェクト
	 */
	public static YearAndMonth of(String month) {
		return new YearAndMonth(
				Integer.parseInt(month.substring(0,4)), Integer.parseInt(month.substring(5,7)));
	}
	
	private YearAndMonth(int year, int month) {
		this.year = year;
		this.month = month;
		this.cal = Calendar.getInstance();
		// Calendarの年月初期化
		cal.set(Calendar.YEAR, this.year);
		cal.set(Calendar.MONTH, this.month - 1);
	}
	
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	
	/**
	 * フィールドからInputDtoの生成をする
	 */
	public GetDataByMonthDto.Input createInputDto() {
		return this.createInputDto(() -> {
			// 月初を取得
			this.cal.set(Calendar.DAY_OF_MONTH, 1);
			Date firstDate = cal.getTime();
			// 最終日を取得
			this.cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			Date lastDate = cal.getTime();
			// InputDto作成
			GetDataByMonthDto.Input input = new GetDataByMonthDto.Input(firstDate, lastDate);
			return input;
			}
		);
	}
	
	/**
	 * 引数のサプライヤからInputDtoの生成をする
	 * @param 生成を行う関数
	 */
	public GetDataByMonthDto.Input createInputDto(Supplier<GetDataByMonthDto.Input> sup) {
		return sup.get();
	}
}