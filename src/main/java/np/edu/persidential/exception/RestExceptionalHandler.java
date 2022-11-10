package np.edu.persidential.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionalHandler extends ResponseEntityExceptionHandler {

  /**
   * If an exception is thrown, the function will return a response entity with the exception
   * message and the exception itself
   *
   * @param exception The exception that was thrown.
   * @return A ResponseEntity<APIException>
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<APIException> handleException(Exception exception) {
    APIException apiException =
        new APIException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
    return buildResponseEntity(apiException);
  }

  /**
   * It takes an exception, creates an APIException object, and returns a ResponseEntity with the
   * APIException object
   *
   * @param exception The exception that was thrown.
   * @return A ResponseEntity object.
   */
  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<APIException> handleNotFoundException(Exception exception) {
    APIException apiException =
        new APIException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    return buildResponseEntity(apiException);
  }

  /**
   * It returns a new ResponseEntity object with the APIException object and the status code of the
   * APIException object
   *
   * @param apiException The exception object that was thrown.
   * @return A ResponseEntity object is being returned.
   */
  private ResponseEntity<APIException> buildResponseEntity(APIException apiException) {
    return new ResponseEntity<>(apiException, apiException.getStatus());
  }
}
