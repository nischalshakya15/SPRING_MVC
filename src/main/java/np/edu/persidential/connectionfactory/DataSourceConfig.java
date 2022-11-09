package np.edu.persidential.connectionfactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

  @Bean
  public DriverManagerDataSource getDataSource() {
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
    driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/contact_management");
    driverManagerDataSource.setUsername("root");
    driverManagerDataSource.setPassword("root");
    return driverManagerDataSource;
  }
}
