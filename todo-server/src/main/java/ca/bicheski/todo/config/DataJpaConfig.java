package ca.bicheski.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("ca.bicheski.todo.repository")
@EnableTransactionManagement
public class DataJpaConfig {

}
