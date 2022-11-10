package np.edu.persidential.service;

import np.edu.persidential.dto.ContactDto;
import np.edu.persidential.exception.NotFoundException;

import java.util.List;

public interface ContactService {

  ContactDto save(ContactDto contactDto);

  ContactDto update(ContactDto contactDto) throws NotFoundException;

  ContactDto findById(Integer id) throws NotFoundException;

  void remove(Integer id) throws NotFoundException;

  List<ContactDto> findAll(String orderBy);
}
