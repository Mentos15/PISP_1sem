package by.ermakovich.search_command.service;


import by.ermakovich.search_command.entity.Application;

public interface ApplicationService {

    void create(Application application);

    Application read(long id);

    boolean update(Application game, Long id);

    boolean delete(Long id);
}
