package np.edu.persidential.repository;

import np.edu.persidential.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    /**
     * Find all contacts, ordered by id descending.
     *
     * @return A list of contacts ordered by id in descending order.
     */
    List<Contact> findAllByOrderByIdDesc();
}
