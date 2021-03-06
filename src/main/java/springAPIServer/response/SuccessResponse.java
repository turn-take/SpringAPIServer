package springAPIServer.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * API成功時のレスポンスの基となるDtoクラス
 */
public class SuccessResponse <T> {
	
	@JsonProperty("success")
	private SuccessBody<T> successBody;
	
	public SuccessResponse(SuccessBody<T> successBody) {
		this.successBody = successBody;
	}

	public SuccessBody<T> getSuccessBody() {
		return successBody;
	}

	public void setSuccessBody(SuccessBody<T> successBody) {
		this.successBody = successBody;
	}

	/**
	 * 成功本文
	 * 内容を表示させる
	 * ResponseEntityで返す想定なのでListでいい
	 */
	public static class SuccessBody<T> {
		private List<T> contents;
		
		public SuccessBody(List<T> contents) {
			this.contents = contents;
		}

		public List<T> getContents() {
			return contents;
		}

		public void setContents(List<T> contents) {
			this.contents = contents;
		}
		
		
	}
}
