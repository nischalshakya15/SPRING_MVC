package np.edu.persidential.connectionfactory;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConnectionFactory {

  private final DriverManagerDataSource dataSource;

  public ConnectionFactory(DriverManagerDataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * This function returns a connection to the database.
   *
   * @return A connection to the database.
   */
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}
