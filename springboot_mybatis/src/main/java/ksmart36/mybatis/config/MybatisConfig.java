package ksmart36.mybatis.config;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value="ksmart36.mybatis.mapper", sqlSessionFactoryRef = "mybatisSqlSessionFactory")
  // value= "mapper가 있는 패키지", sqlSessionFactoryRef = "Bean의 name"
public class MybatisConfig {
	
	@Bean(name="mybatisSqlSessionFactory")
	public SqlSessionFactory mybatisSqlSessionFactory(DataSource datasource, ApplicationContext context) throws Exception {
		
		SqlSessionFactoryBean mybatisSqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		mybatisSqlSessionFactoryBean.setDataSource(datasource);
		//application.properties == mybatis.mapper-locations
		mybatisSqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:mapper/**/*.xml"));
		//application.properties == mybatis.type-aliases-package
		mybatisSqlSessionFactoryBean.setTypeAliasesPackage("ksmart36.mybatis.domain");
		
		return mybatisSqlSessionFactoryBean.getObject();
		
	}
}
