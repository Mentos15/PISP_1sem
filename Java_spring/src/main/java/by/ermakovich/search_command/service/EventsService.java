package by.ermakovich.search_command.service;

import by.ermakovich.search_command.entity.Events;

public interface EventsService {

    void create(Events event);

    Events read(Long id);

    boolean update(Events request, Long id);

    boolean delete(Long id);

}
