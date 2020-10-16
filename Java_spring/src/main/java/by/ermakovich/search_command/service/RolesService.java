package by.ermakovich.search_command.service;

import by.ermakovich.search_command.entity.Roles;

public interface RolesService {

    void create(Roles event);

    Roles read(Long id);

    boolean update(Roles request, Long id);

    boolean delete(Long id);
}
