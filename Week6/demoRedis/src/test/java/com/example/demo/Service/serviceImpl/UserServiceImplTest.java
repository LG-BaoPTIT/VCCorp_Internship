package com.example.demo.Service.serviceImpl;

import com.example.demo.entity.User;
import com.example.demo.redis.repo.RedisUserRepo;
import com.example.demo.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    RedisUserRepo redisUserRepo;
    User user;
    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(1);
        user.setUserName("luvkll");
        user.setPassword("123123");
        user.setEmail("luvkll@gmail.com");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetUserFromRedis() {
        Mockito.when(redisUserRepo.get(1)).thenReturn(user);
        ResponseEntity<?> response = userService.getUserById(1);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserFromUserRepo() {
        Mockito.when(redisUserRepo.get(1)).thenReturn(null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        ResponseEntity<?> response = userService.getUserById(1);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testUserIsNotPresent(){
        Mockito.when(redisUserRepo.get(1)).thenReturn(null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<?> response = userService.getUserById(1);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User is not present", response.getBody());
    }


}