package es.kgp.chat.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by kgp on 18/01/2014.
 */
@Configuration
@EnableJpaRepositories(value = "es.kgp.chat.server.repository", repositoryImplementationPostfix = "CustomJpa")
@EnableTransactionManagement
@ComponentScan(basePackages = "es.kgp.chat.server.service")
@PropertySource("classpath:/application.properties")
public class ApplicationConfig {

    @Value("${config.showSql}")
    private Boolean showSql;

    @Value("${config.generateDdl}")
    private Boolean generateDdl;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Database database;

    @Bean
    public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator(){
        return new HibernateExceptionTranslator();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(database);
        vendorAdapter.setShowSql(showSql);
        vendorAdapter.setGenerateDdl(generateDdl);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("es.kgp.chat.server.model");
        factoryBean.setMappingResources("META-INF/orm.xml");
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

}
