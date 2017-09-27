package com.ervin.myblog.repositories.hibernate;

import com.ervin.myblog.entity.User;
import com.ervin.myblog.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:/test/hibernate-test-config.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserWithIdTest() {
        int id = 1;
        User user = userRepository.getUser(id);
        Assert.assertNotEquals(user, null);
        Assert.assertEquals(user.getId(), id);
        Assert.assertEquals(user.getUsername(), "testuser");
        Assert.assertEquals(user.getPassword(), "test");
    }

    @Test
    public void getUserWithInvalidIdTest() {
        int id = 500;
        User user = userRepository.getUser(id);
        Assert.assertEquals(user, null);
    }

    @Test
    public void getUserWithUsernameTest() {
        String username = "testuser";
        User user = userRepository.getUser(username);
        Assert.assertNotEquals(user, null);
        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getUsername(), username);
        Assert.assertEquals(user.getPassword(), "test");
    }

    @Test
    public void getUserWithInvalidUsernameTest() {
        String username = "invalid";
        User user = userRepository.getUser(username);
        Assert.assertEquals(user, null);
    }
}
