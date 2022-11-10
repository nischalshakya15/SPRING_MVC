package np.edu.persidential.service;

import np.edu.persidential.exception.NotFoundException;
import np.edu.persidential.model.Contact;

import java.util.List;

public interface ContactService {

  Contact save(Contact contact);

  Contact update(Contact contact);

  Contact findById(Integer id) throws NotFoundException;

  void remove(Integer id) throws NotFoundException;

  List<Contact> findAll(String orderBy);
}
