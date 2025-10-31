package com.app.bluecotton.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    //    applicationContext를 통해 resoruces의 경로를 가져온다.
    private final ApplicationContext applicationContext;

    //    yml 파일을 connection 정보를 가져오기 위한 작업
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    //  DataSource 객체에 따라 설정해 놓은 connection 설정을 넣어준다.
    @Bean
    public DataSource getDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    //  SqlSession을 만들기 위한 SqlSessionFactory를 제작한다.
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDataSource());
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:/mapper/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/config.xml"));
        try {
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
//          스네이크 -> 카멜
            sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
            return sqlSessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
