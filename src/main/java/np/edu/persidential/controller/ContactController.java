package np.edu.persidential.controller;

import np.edu.persidential.dto.ContactDto;
import np.edu.persidential.exception.NotFoundException;
import np.edu.persidential.model.Contact;
import np.edu.persidential.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/contacts")
public class ContactController {

  private final ContactService contactService;

  public ContactController(ContactService contactService) {
    this.contactService = contactService;
  }

  /**
   * This function returns a list of all contacts in the database
   *
   * @return A list of contacts
   */
  @GetMapping
  public ResponseEntity<List<Contact>> findAll(
      @RequestParam(value = "orderBy", defaultValue = "asc") String orderBy) {
    return ResponseEntity.ok(contactService.findAll(orderBy));
  }

  /**
   * The function takes a JSON object, converts it to a Contact object, saves it to the database,
   * and returns the newly created Contact object
   *
   * @param contact This is the object that will be created.
   * @return The response entity is being returned.
   */
  @PostMapping
  public ResponseEntity<Contact> create(@RequestBody Contact contact) throws URISyntaxException {
    return ResponseEntity.created(new URI("/api/v1/contacts")).body(contactService.save(contact));
  }

  /**
   * We're creating a new contact using the data from the request body, and returning the created
   * contact in the response body
   *
   * @param contactDto The object that will be used to create the contact.
   * @return A ResponseEntity with a status of 201 (created) and a body of the newly created
   *     contact.
   */
  @PostMapping("/dto")
  public ResponseEntity<Contact> create(@Valid @RequestBody ContactDto contactDto)
      throws URISyntaxException {
    Contact contact = new Contact();
    contact.setFirstName(contactDto.getFirstName());
    contact.setLastName(contactDto.getLastName());
    contact.setAddress(contactDto.getAddress());
    contact.setPhoneNumber(contactDto.getPhoneNumber());
    return ResponseEntity.created(new URI("/api/v1/contacts")).body(contactService.save(contact));
  }

  /**
   * This function will return a contact with the given id
   *
   * @param id the id of the contact you want to find
   * @return A ResponseEntity object with a status code of 200 and a body containing the Contact
   *     object with the specified id.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Contact> findOne(@PathVariable Integer id) throws NotFoundException {
    return ResponseEntity.ok(contactService.findById(id));
  }

  /**
   * It updates the contact with the given id.
   *
   * @param id The id of the contact to update
   * @param contact The object that will be updated.
   * @return A ResponseEntity object is being returned.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Contact> update(@PathVariable Integer id, @RequestBody Contact contact) {
    return ResponseEntity.ok(contactService.update(contact));
  }

  /**
   * It deletes a contact from the database and returns a message to the user
   *
   * @param id The id of the contact to be deleted.
   * @return A ResponseEntity object is being returned.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> remove(@PathVariable Integer id)
      throws NotFoundException {
    contactService.remove(id);
    return new ResponseEntity<>(Map.of("message", "Contact deleted successfully."), HttpStatus.OK);
  }
}
