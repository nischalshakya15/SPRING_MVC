package np.edu.persidential.connectionfactory;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class ConnectionFactory {

  private final DriverManagerDataSource dataSource;

  public ConnectionFactory(DriverManagerDataSource driverManagerDataSource) {
    this.dataSource = driverManagerDataSource;
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
