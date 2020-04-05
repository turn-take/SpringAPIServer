package springAPIServer.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import springAPIServer.error.APIException;
import springAPIServer.response.ErrorResponse;
import springAPIServer.response.ErrorResponse.ErrorBody;


/**
 * エラーのハンドラクラス
 */
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler{
	
	// API内でのエラー発生時
	@ExceptionHandler({APIException.class})
	public ResponseEntity<ErrorResponse> handleAPIException(APIException e) {
		e.printStackTrace();
		// エラーレスポンスの作成
		ErrorResponse response = createErrorResponse(e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	// 想定外のエラー発生時
	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		e.printStackTrace();
		// エラーレスポンスの作成
		ErrorResponse errorDto = new ErrorResponse(new ErrorBody("想定外のエラーが発生しました。"));
		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// handleExceptionInternalの実装
	// ErrorResponseを作成する。
	@Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
		ErrorResponse response = createErrorResponse(ex.getMessage()); // TODO メッセージの内容は検討
		return super.handleExceptionInternal(ex, response, headers, status, request);
    }
	
	private ErrorResponse createErrorResponse(String message) {
		ErrorBody errorBody = new ErrorBody(message);
		ErrorResponse errorResponse = new ErrorResponse(errorBody);
		return errorResponse;
	}
}
