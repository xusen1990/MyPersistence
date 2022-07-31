package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;

import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void testTraditionalQuery() throws Exception{

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //调用
        User user = new User();
        user.setId(1);
        user.setUsername("Lily");
        User user2 = sqlSession.selectOne("com.lagou.dao.IUserDao.findByCondition", user);
        System.out.println(user2);
    }



    @Test
    public void testQuery() throws Exception{

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        //调用
        User user = new User();
        user.setId(1);
        user.setUsername("Lily");
        User user2 = userDao.findByCondition(user);
        System.out.println(user2);
        System.out.println("--------");
        List<User> users = userDao.findAll();
        for (User user1 : users) {
            System.out.println(user1);
        }

    }


    @Test
    public void testInsert() throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        //调用
        User user = new User();
        user.setUsername("Mike");
        userDao.saveUser(user);

    }


    @Test
    public void testUpdate() throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        //调用
        User user = new User();
        user.setId(6);
        user.setUsername("Tony");
        userDao.updateUser(user);
    }


    @Test
    public void testDelete() throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        //调用
        User user = new User();
        user.setId(6);
        userDao.deleteUser(user);

    }
}
