package np.edu.persidential.service;

import np.edu.persidential.model.Contact;

import java.util.List;

public interface ContactService {

  Contact save(Contact contact);

  Contact update(Contact contact);

  Contact findById(Integer id);

  void remove(Integer id);

  List<Contact> findAll();

  Contact findContactSortByIdDesc();
}
