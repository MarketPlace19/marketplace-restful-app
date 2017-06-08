package br.com.marketplace.main;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.collect.Lists;

import br.com.marketplace.config.MarketplaceSecurityConfig;
import br.com.marketplace.entity.Profile;
import br.com.marketplace.entity.User;
import br.com.marketplace.enums.EnumProfile;
import br.com.marketplace.enums.UserStatus;
import br.com.marketplace.repository.ProfileRepository;
import br.com.marketplace.repository.UserRepository;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages = {"br.com.marketplace.repository"}, entityManagerFactoryRef="entityManagerFactory", transactionManagerRef = "transactionManager")
@Import(value = {MarketplaceSecurityConfig.class})
public class Main implements CommandLineRunner {

	private static final String ENTITY_PACKAGE = "br.com.marketplace.entity";
	private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
//	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
//	private static final String SCHEMA_URL = "jdbc:mysql://localhost:3306/bookhub-db";
	private static final String SCHEMA_URL = "jdbc:postgresql://localhost/marketplace-db";
	private static final String DB_USER = "postgres";
//	private static final String DB_USER = "root";
	
	//Senha pode variar de acordo com o ambiente
//	private static final String DB_PASSWORD = "root";
//	private static final String DB_PASSWORD = "1234";
	private static final String DB_PASSWORD = "postgres";
	
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private UserRepository userRepository;

	//Spring JPA
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(this.getDataSource());
		//Produção
//		em.setDataSource(this.dataSource());
		em.setPackagesToScan(new String[] { ENTITY_PACKAGE });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(this.getAdditionalProperties());

		return em;
	}

	//Usado para dev
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DRIVER_CLASS_NAME);
		dataSource.setUrl(SCHEMA_URL);
		dataSource.setUsername(DB_USER);
		dataSource.setPassword(DB_PASSWORD);
		return dataSource;
	}
	
	//Usado para produção
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@DependsOn("entityManagerFactory")
	@Autowired
	@Bean(name = "transactionManager")
	public PlatformTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean(name = "exceptionTranslation")
	public PersistenceExceptionTranslationPostProcessor getExceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties getAdditionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect",	"org.hibernate.dialect.PostgreSQLDialect");
//		properties.setProperty("hibernate.dialect",	"org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		return properties;
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	String[] origins = {"http://localhost:9000"};
				final String userPath = "/users";
				addUserPathRegistry(registry, userPath, origins);
            }
        };
    }

    private void addUserPathRegistry(CorsRegistry registry, String path, String[] origins) {
		addDefaultPathRegistry(registry, path, origins);
		registry.addMapping(path + "/listAllConsumers/*").allowedOrigins(origins);
		registry.addMapping(path + "/listAllProviders/*").allowedOrigins(origins);
	}

	private void addDefaultPathRegistry(CorsRegistry registry, String path, String... origins) {
		registry.addMapping(path + "/*").allowedOrigins(origins);
		registry.addMapping(path + "/get/*").allowedOrigins(origins);
		registry.addMapping(path + "/update/*").allowedOrigins(origins);
		registry.addMapping(path + "/delete/*").allowedOrigins(origins);
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class);
	}

	@Override
	public void run(String... arg0) throws Exception {
		List<User> users = Lists.newArrayList(userRepository.findAll());
		
		if (users.isEmpty()) {
			List<Profile> profiles = Lists.newArrayList(profileRepository
					.findAll());
			if (profiles.isEmpty()) {
				LOGGER.log(Level.INFO, "Gerando os profiles");
				profileRepository.save(new Profile(EnumProfile.SYSTEM_ADMINISTRATOR));
				profileRepository.save(new Profile(EnumProfile.CONSUMER));
				profileRepository.save(new Profile(EnumProfile.PROVIDER));
				profiles = Lists.newArrayList(profileRepository.findAll());
			}
			for (Profile profile : profiles) {
				LOGGER.log(Level.INFO, profile.getDesc());
			}
			String senha = "admin";
			ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
			String cripto = encoder.encodePassword(senha, null);
			LOGGER.log(Level.INFO, senha);
			userRepository.save(new User("Admin Test", "admintest@mail.com", "admin.test", cripto, UserStatus.ACTIVE, profiles));
		}
	}

}
