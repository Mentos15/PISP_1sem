package by.ermakovich.search_command.service;

import by.ermakovich.search_command.entity.Users;

public interface UsersService {

    void create(Users request);

    Users read(Long id);

    boolean update(Users request, Long id);

    boolean delete(Long id);
}
