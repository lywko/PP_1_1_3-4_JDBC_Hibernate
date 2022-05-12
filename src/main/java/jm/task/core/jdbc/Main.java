package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Иван", "Иванов", (byte) 20);
        userService.saveUser("Генадий", "Генадиев", (byte) 32);
        userService.saveUser("Бутчер", "Пуджов", (byte) 43);
        userService.saveUser("Том", "Томов", (byte) 53);

        userService.removeUserById(1);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
