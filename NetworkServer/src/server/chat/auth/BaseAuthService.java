package server.chat.auth;

import server.chat.User;

import java.util.List;

public class BaseAuthService {

    private static final List<User> users = List.of(
            new User("Alexandr", "alex", "111"),
            new User("Daniil", "danil", "222"),
            new User("Eujene", "john", "333")
    );

    public String getUserName(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login)&&user.getPassword().equals(password)) {
                return user.getName();
            }
        }
        return null;
    }
}
