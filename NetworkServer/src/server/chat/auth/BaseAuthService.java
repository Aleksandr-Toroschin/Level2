package server.chat.auth;

import server.chat.User;

import java.util.List;

public class BaseAuthService {

    private static final List<User> users = List.of(
            new User("Александр", "user1", "111", "nik1"),
            new User("Даниил", "user2", "222", "nik2"),
            new User("Евгений", "user3", "333", "nik3")
    );

    public String getUserName(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login)&&user.getPassword().equals(password)) {
                return user.getName();
            }
        }
        return null;
    }

    public String getNickNameByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user.getNickName();
            }
        }
        return null;
    }
}
