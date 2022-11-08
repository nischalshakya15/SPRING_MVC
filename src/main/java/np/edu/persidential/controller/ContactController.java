package np.edu.persidential.controller;

import np.edu.persidential.dao.ContactDao;
import np.edu.persidential.model.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class ContactController {

  private final ContactDao contactDao;

  public ContactController(ContactDao contactDao) {
    this.contactDao = contactDao;
  }

  /**
   * The `findAll()` function is a `@GetMapping` function that returns a `String` and takes a
   * `Model` as a parameter
   *
   * @param model This is the model object that is used to pass data from the controller to the
   *     view.
   * @return The index.html file
   */
  @GetMapping({"/contacts", "/"})
  public String findAll(Model model) throws SQLException {
    model.addAttribute("contacts", contactDao.findAll());
    return "index";
  }

  /**
   * The function `displayContactForm` is a GET request handler that returns the
   * `contact-register-update` view
   *
   * @param model The model is a map that is used to pass data from the controller to the view.
   * @return The return value is a String that represents the name of the view.
   */
  @GetMapping("/contact-form")
  public String displayContactForm(Model model) {
    model.addAttribute("command", new Contact());
    model.addAttribute("action", "Save");
    return "/contact/contact-register";
  }

  /**
   * The function takes a contact object as a parameter, saves it to the database, and then
   * redirects the user to the contacts page
   *
   * @param contact The name of the model attribute.
   * @return A redirect to the contacts page.
   */
  @PostMapping("/save")
  public String save(@ModelAttribute("contact") Contact contact) throws SQLException {
    contactDao.save(contact);
    return "redirect:/contacts";
  }

  /**
   * The function takes in the id of the contact to be updated, and the contact object with the
   * updated values. It then calls the update function in the contactDao class, and redirects to the
   * contacts page
   *
   * @param id The id of the contact to be updated.
   * @param contact The name of the model attribute.
   * @return A redirect to the contacts page.
   */
  @PostMapping("/update")
  public String update(@ModelAttribute("contact") Contact contact) throws SQLException {
    contactDao.update(contact);
    return "redirect:/contacts";
  }

  /**
   * The function takes an id as a parameter, deletes the contact with that id from the database,
   * and then redirects the user to the contacts page
   *
   * @param id The id of the contact to be deleted.
   * @return A redirect to the contacts page.
   */
  @GetMapping("/delete/{id}")
  public String remove(@PathVariable int id) throws SQLException {
    contactDao.delete(id);
    return "redirect:/contacts";
  }

  /**
   * The function takes the id of the contact to be edited as a path variable, finds the contact in
   * the database, and then passes the contact to the view
   *
   * @param id The id of the contact to be edited.
   * @param model This is the model object that is used to pass data from the controller to the
   *     view.
   * @return The editSave method is returning the contact-register-update page.
   */
  @GetMapping("/edit/{id}")
  public String editSave(@PathVariable int id, Model model) throws SQLException {
    Contact contact = contactDao.findOne(id);
    model.addAttribute("command", contact);
    model.addAttribute("action", "Update");
    return "/contact/contact-update";
  }
}
