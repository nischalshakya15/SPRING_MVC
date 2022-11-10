package np.edu.persidential.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ContactDto {

  private Integer id;

  @NotNull private String firstName;

  @NotNull private String lastName;

  @NotNull private String address;

  @NotNull private Long phoneNumber;
}
