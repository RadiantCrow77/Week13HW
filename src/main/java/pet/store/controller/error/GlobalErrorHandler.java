package pet.store.controller.error;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j // logs
public class GlobalErrorHandler {
	
	// Log Status Method
	private enum LogStatus{
		STACK_TRACK, MESSAGE_ONLY
	}
	
	// Exception Msg Method
	@Data
	private class ExceptionMessage{
		private String message;
		private String statusReason;
		private int statusCode;
		private String timestamp;
		private String uri;
	}
	
	// DNE or No Such Element Exception
	// 404 err
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionMessage handleNoSuchElementException(NoSuchElementException ex, WebRequest webRequest) { // exception msg, show URI
		return buildExceptionMessage(ex, HttpStatus.NOT_FOUND, webRequest, LogStatus.MESSAGE_ONLY); // return exception, 404 status that we want, and log the msg to user
	}

	private ExceptionMessage buildExceptionMessage(NoSuchElementException ex, HttpStatus status,
			WebRequest webRequest, LogStatus logStatus) {
	String message = ex.toString();
	String statusReason = status.getReasonPhrase();
	int statusCode = status.value();
	String uri = null;
	String timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
	
	if(webRequest instanceof ServletWebRequest swr) {
		uri = swr.getRequest().getRequestURI();
	}
	
	// logs req
	if(logStatus == LogStatus.MESSAGE_ONLY) {
		log.error("Exception: {}", ex.toString());
	}
	else {
		log.error("Exception: ", ex);
	}
	
	ExceptionMessage excMsg = new ExceptionMessage();
	
	// these are maintained by lombok data annot.
	excMsg.setMessage(message);
	excMsg.setStatusCode(statusCode);
	excMsg.setStatusReason(statusReason);
	excMsg.setTimestamp(timestamp);
	excMsg.setUri(uri);
	
	return excMsg;
	
	}
	
	
}
