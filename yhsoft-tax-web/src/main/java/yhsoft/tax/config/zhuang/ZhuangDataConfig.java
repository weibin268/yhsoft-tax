package yhsoft.tax.config.zhuang;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.DbAccessorFactory;
import com.zhuang.data.config.JdbcProperties;
import com.zhuang.data.mybatis.MyBatisDbAccessor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by zhuang on 3/7/2018.
 */

@Configuration
public class ZhuangDataConfig {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Bean(name = "dbAccessor")
    public DbAccessor dbAccessor(){
        DbAccessor dbAccessor = new MyBatisDbAccessor(sqlSessionFactory,true);
        return dbAccessor;
    }

}
