package springAPIServer.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import springAPIServer.dto.ErrorDto;
import springAPIServer.dto.ErrorDto.ErrorBody;


/**
 * エラーのハンドラ
 */
@RestControllerAdvice
public class ErrorHandler {
	
	// StringのJSON文字列を返す
	@ExceptionHandler({Exception.class})
	public ResponseEntity<String> handleException(Exception e) {
		e.printStackTrace();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ObjectMapper objectMapper = new ObjectMapper();
		ErrorDto errorDto = new ErrorDto(new ErrorBody(e.getMessage()));
		try {
			String errorBody = objectMapper.writeValueAsString(errorDto);
	        return new ResponseEntity<>(errorBody, status);
		} catch (JsonProcessingException je) {
			// どうしようもないのでerrorだけ返しておく
			return new ResponseEntity<>("{\"error\":\"\"}", status);
		}
	}
	
}
