package es.kgp.chat.server.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;

/**
 * Created by kgp on 18/01/2014.
 */
@Configuration
@PropertySource("classpath:/mysql.properties")
public class MySQLDataSourceConfig {

    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;
    @Value("${database.databaseDriver}")
    private String databaseDriver;
    @Value("${database.url}")
    private String url;
    @Value("${database.databaseType}")
    private String databaseType;

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(databaseDriver);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(url);
        return ds;
    }

    @Bean
    public Database dataBase() {
        return Database.valueOf(databaseType);
    }
}
