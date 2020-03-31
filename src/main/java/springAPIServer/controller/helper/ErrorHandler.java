package springAPIServer.controller.helper;

import java.time.DateTimeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import springAPIServer.response.ErrorResponse;
import springAPIServer.response.ErrorResponse.ErrorBody;


/**
 * エラーのハンドラクラス
 */
@RestControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler({DateTimeException.class})
	public ResponseEntity<ErrorResponse> handleDateTimeException(DateTimeException e) {
		e.printStackTrace();
		// エラーレスポンスの作成
		ErrorResponse errorDto = new ErrorResponse(new ErrorBody(e.getMessage()));
		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		e.printStackTrace();
		// エラーレスポンスの作成
		ErrorResponse errorDto = new ErrorResponse(new ErrorBody(e.getMessage()));
		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
