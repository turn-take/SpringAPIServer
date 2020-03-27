package springAPIServer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * APIエラー時のレスポンスの基となるDtoクラス
 */
public class ErrorDto {

	@JsonProperty("error")
	private ErrorBody errorBody;
	
	public ErrorDto(ErrorBody errorBody) {
		this.errorBody = errorBody;
	}
	
	public ErrorBody getErrorBody() {
		return errorBody;
	}

	public void setErrorBody(ErrorBody errorBody) {
		this.errorBody = errorBody;
	}

	/**
	 * エラー本文
	 * メッセージを表示するだけ
	 */
	public static class ErrorBody {
		private String message;
		
		public ErrorBody(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
