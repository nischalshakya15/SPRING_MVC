package np.edu.persidential.service.impl;

import lombok.RequiredArgsConstructor;
import np.edu.persidential.exception.NotFoundException;
import np.edu.persidential.model.Contact;
import np.edu.persidential.repository.ContactRepository;
import np.edu.persidential.service.ContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

  private final ContactRepository contactRepository;

  /**
   * Save the contact to the database.
   *
   * @param contact The contact object that is passed in from the controller.
   * @return The contact object is being returned.
   */
  @Override
  public Contact save(Contact contact) {
    return contactRepository.save(contact);
  }

  /**
   * Update the contact in the database with the given contact.
   *
   * @param contact The contact object that we want to update.
   * @return The updated contact.
   */
  @Override
  public Contact update(Contact contact) {
    return contactRepository.save(contact);
  }

  /**
   * If the contact exists, return it, otherwise throw an exception.
   *
   * @param id The id of the contact to find.
   * @return A contact object.
   */
  @Override
  public Contact findById(Integer id) throws NotFoundException {
    return contactRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Contact not found."));
  }

  /**
   * Delete the contact with the given id.
   *
   * @param id The id of the contact to be deleted.
   */
  @Override
  public void remove(Integer id) throws NotFoundException {
    Contact contact = findById(id);
    contactRepository.delete(contact);
  }

  /**
   * If the orderBy parameter is "desc", then return the contacts in descending order, otherwise
   * return the contacts in ascending order
   *
   * @param orderBy The order in which the contacts should be returned.
   * @return A list of contacts
   */
  @Override
  public List<Contact> findAll(String orderBy) {
    if (orderBy.equalsIgnoreCase("desc")) {
      return contactRepository.findAllByOrderByIdDesc();
    }
    return contactRepository.findAll();
  }
}
