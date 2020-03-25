package springAPIServer.dto.calendar;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 月指定のカレンダーデータ取得処理で使われるDTOクラス
 * コントローラーとサービス間のデータの受け渡しに使う。
 */
public class GetDataByMonthDto {

	public static class Input {
		public Input(Date firstDate, Date lastDate) {
			this.firstDate = firstDate;
			this.lastDate = lastDate;
		}
		// 初日
		private Date firstDate;
		// 最終日
		private Date lastDate;
		
		public Date getFirstDate() {
			return firstDate;
		}
		public void setFirstDate(Date firstDate) {
			this.firstDate = firstDate;
		}
		public Date getLastDate() {
			return lastDate;
		}
		public void setLastDate(Date lastDate) {
			this.lastDate = lastDate;
		}
	}
	
	public static class Output {
		public Output(Date date, boolean holidayFlag) {
			this.date = date;
			this.holidayFlag = holidayFlag;
		}
		// 日付
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
		private Date date;
		// 祝日フラグ
		private boolean holidayFlag;
		
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public boolean getHolidayFlag() {
			return holidayFlag;
		}
		public void setHolidayFlag(boolean holidayFlag) {
			this.holidayFlag = holidayFlag;
		}
	}
}
