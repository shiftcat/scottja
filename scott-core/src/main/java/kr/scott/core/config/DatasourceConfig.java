package kr.scott.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * 이 클래스는 데이터베이스 관한 설정을 하는 클래스 이다.
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
@Configuration
@EnableJpaRepositories(basePackages = {"kr.scott.core.repo"}, transactionManagerRef = "txManager")
@EnableTransactionManagement
public class DatasourceConfig
{


    @Bean("server.type")
    public String serverType(Environment environment)
    {
        return environment.getProperty("server.type");
    }


    @Profile("test")
    @Bean("dataSource")
    public DataSource dataSourceForTest(@Qualifier("properties") Properties environment)
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("JDBC-DRIVER"));
        dataSource.setUrl(environment.getProperty("JDBC-URL"));
        dataSource.setUsername(environment.getProperty("JDBC-USER"));
        dataSource.setPassword(environment.getProperty("JDBC-PASSWD"));
        return dataSource;
    }


    @Profile({"local", "real"})
    @Bean("dataSource")
    public DataSource dataSource(Environment environment)
    {
        String dbUrl =
                String.format("jdbc:mysql://%s:%s/%s", environment.getProperty("DATABASE_SERVER"), environment.getProperty("DATABASE_PORT"), environment.getProperty("DATABASE_NAME"));
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(environment.getProperty("DATABASE_USER"));
        dataSource.setPassword(environment.getProperty("DATABASE_PASSWD"));
        return dataSource;
    }



    // 기본 Transaction Manager 이름은 transactionManager 이다.
    @Bean("txManager")
    public PlatformTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource)
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }


    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter()
    {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        return jpaVendorAdapter;
    }



    @Profile({"local", "test"})
    @Bean("jpaProperties")
    public Properties jpsPropertiesForLocal()
    {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect.H2Dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.new_generator_mappings", "true");
        return properties;
    }


    @Profile("real")
    @Bean("jpaProperties")
    public Properties jpsPropertiesForReal()
    {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect.H2Dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.use_sql_comments", "false");
        properties.setProperty("hibernate.new_generator_mappings", "true");
        return properties;
    }


    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            @Qualifier("dataSource") DataSource dataSource
            , @Qualifier("jpaProperties") Properties japProperties
    )
    {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setPersistenceUnitName("sample_persistence");
//        em.setPersistenceXmlLocation("classpath:META-INF/sample_persistence.xml");
        em.setDataSource(dataSource);
        em.setPackagesToScan("kr.scott.core.entities");

        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaProperties(japProperties);
        return em;
    }


// mybatis
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
//        transactionManager.setGlobalRollbackOnParticipationFailure(false);
//        return transactionManager;
//    }
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(BasicDataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        sessionFactoryBean.setTypeAliasesPackage("com.stunstun.spring.repository.entity");
//        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:META-INF/mybatis/mybatis-config.xml"));
//        sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:META-INF/mybatis/mapper/**/*.xml"));
//        return sessionFactoryBean.getObject();
//    }

}
