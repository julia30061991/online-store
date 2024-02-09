import com.onlinestore.controller.RestController;
import com.onlinestore.model.Order;
import com.onlinestore.model.StatusEnum;
import com.onlinestore.model.User;
import com.onlinestore.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class RestControllerTest {

    @Mock
    private UserServiceImpl service;

    @Mock
    private UserServiceImpl.InvalidException invalidException;

    @InjectMocks
    private RestController restController;

    @Test
    void returnListAllUsers() {
        //given
        List<User> users = new ArrayList<>();
        User user1 = new User(1, UUID.randomUUID(), "Андрей Горячев",
                "89006001020", "and@mail.ru", new ArrayList<>());
        User user2 = new User(2, UUID.randomUUID(), "Ксения Пронина",
                "89155556633", "ksu@mail.ru", new ArrayList<>());
        users.add(user1);
        users.add(user2);
        Mockito.when(service.getUserList()).thenReturn(users);

        //when
        ResponseEntity<List<User>> response = restController.getAllUsers();

        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(users, response.getBody());
    }

    @Test
    void returnUserInfo() {
        //given
        User user = new User(1, UUID.randomUUID(), "Андрей Горячев",
                "89006001020", "and@mail.ru", null);
        List<Order> orders = new ArrayList<>();
        Order order = new Order(1, new ArrayList<>(), StatusEnum.ACCEPTED, 125.0, user);
        orders.add(order);
        user.setOrders(orders);
        Mockito.when(service.getUserInfo(1)).thenReturn(user);

        //when
        ResponseEntity<User> response = restController.getUserInfo(1);

        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(user, response.getBody());
    }

    @Test
    void returnCreatedUser() throws Exception {
        //given
        Mockito.when(service.createUser("Анна Иванова", "89505661212", "ann@mail.ru"))
                .thenReturn(new User(1, null, "Анна Иванова", "89505661212",
                        "ann@mail.ru", null));
        //when
        ResponseEntity<Object> response = restController.createUser("Анна Иванова", "89505661212", "ann@mail.ru");

        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(new User(1, null, "Анна Иванова", "89505661212",
                "ann@mail.ru", null), response.getBody());
    }

    @Test
    void returnCreatedUserException() throws Exception {
        //given
        Mockito.when(service.createUser("Анна Иванова", "89505661212", "4524"))

                .thenThrow(invalidException);

        //when
        ResponseEntity<Object> responseException = restController.createUser("Анна Иванова",
                "89505661212", "4524");

        //then
        Assertions.assertNotNull(responseException);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseException.getStatusCode());
    }

    @Test
    void returnDeleteUser() {
        //when
        ResponseEntity response = restController.deleteUser(1);

        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void returnUpdateUser() {
        //when
        ResponseEntity<Object> response = restController.updateUser(1, "Иван Кротов", "", "");
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
