package springAPIServer.common;

/**
 * アプリケション共通で使う定数クラス
 */
public class Constants {
	
	/**
	 * APIのresultCode
	 */
	public enum APIRESULT{
		SUCCESS(0),
		ERROR(1);
		
		private final int code;
		
		private APIRESULT(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return this.code;
		}
	}
}
