package np.edu.persidential.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ContactDto {

  private Integer id;

  @NotNull(message = "firstName should not be null")
  @NotEmpty(message = "firstName should not be empty")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String firstName;

  @NotNull(message = "lastName should not be null")
  @NotEmpty(message = "lastName should not be empty")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String lastName;

  @NotNull(message = "address should not be null")
  @NotEmpty(message = "address should not be empty")
  private String address;

  private Long phoneNumber;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String name;
}
