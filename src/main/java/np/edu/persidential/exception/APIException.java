package np.edu.persidential.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class APIException {

  private HttpStatus status;

  private LocalDateTime timestamp;

  private String message;

  private String debugMessage;

  private APIException() {
    timestamp = LocalDateTime.now();
  }

  APIException(HttpStatus status) {
    this();
    this.status = status;
  }

  APIException(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  APIException(HttpStatus status, String message, Throwable ex) {
    this();
    this.status = status;
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }
}
