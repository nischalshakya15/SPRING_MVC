package np.edu.persidential.dao;

import np.edu.persidential.model.Contact;

import java.sql.SQLException;
import java.util.List;

public interface ContactDao {

  void save(Contact contact) throws SQLException;

  void update(Contact contact) throws SQLException;

  void delete(int id) throws SQLException;

  List<Contact> findAll() throws SQLException;

  Contact findOne(int id) throws SQLException;
}
