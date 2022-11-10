package np.edu.persidential.mapper;

import np.edu.persidential.dto.ContactDto;
import np.edu.persidential.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactMapper {

  ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

  ContactDto toDto(Contact contact);

  Contact toEntity(ContactDto contactDto);

  List<Contact> toEntity(List<ContactDto> contactDtos);

  List<ContactDto> toDto(List<Contact> contacts);
}
