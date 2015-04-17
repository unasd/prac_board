package mybatis;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatis {
	private final static String RESOURCES = "mybatis/mybatis_config.xml";
	private static SqlSessionFactory sqlMapper = null;
	
	public static SqlSessionFactory getSqlSessionFactory(){
		if(sqlMapper==null){
			Reader reader;
			try{
				reader = Resources.getResourceAsReader(RESOURCES);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			} catch (Exception err){
				err.printStackTrace();
			}
		}
		return sqlMapper;
	}
}
