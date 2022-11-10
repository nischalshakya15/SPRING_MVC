package np.edu.persidential.service.impl;

import np.edu.persidential.model.Contact;
import np.edu.persidential.service.ContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

  @PersistenceContext private EntityManager entityManager;

  /**
   * It saves the contact to the database.
   *
   * @param contact The contact object that is being saved.
   * @return The last contact in the database.
   */
  @Override
  public Contact save(Contact contact) {
    entityManager.persist(contact);
    return findContactSortByIdDesc();
  }

  /**
   * Update the contact in the database with the values from the contact object passed in.
   *
   * @param contact The contact to be updated.
   * @return The updated contact.
   */
  @Override
  public Contact update(Contact contact) {
    return entityManager.merge(contact);
  }

  /**
   * Find the Contact object with the given id and return it.
   *
   * @param id The id of the contact to be found.
   * @return The entityManager.find() method returns the entity with the given primary key or null if the entity does not
   * exist.
   */
  @Override
  public Contact findById(Integer id) {
    return entityManager.find(Contact.class, id);
  }

  /**
   * Remove the contact with the given id from the database.
   *
   * @param id The id of the contact to be removed.
   */
  @Override
  public void remove(Integer id) {
    Contact contact = findById(id);
    entityManager.remove(contact);
  }

  /**
   * Return a list of all contacts in the database.
   *
   * @return A list of all the contacts in the database.
   */
  @Override
  public List<Contact> findAll() {
    return entityManager.createQuery("select c from Contact c", Contact.class).getResultList();
  }

  /**
   * Find the contact with the highest id.
   *
   * @return The Contact with the highest id.
   */
  @Override
  public Contact findContactSortByIdDesc() {
    return entityManager
        .createQuery("select c from Contact c order by c.id desc", Contact.class)
        .setMaxResults(1)
        .getSingleResult();
  }
}
