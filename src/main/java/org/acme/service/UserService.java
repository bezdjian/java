package org.acme.service;

import lombok.RequiredArgsConstructor;
import org.acme.client.model.User;
import org.acme.config.RestConfiguration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ApplicationScoped
public class UserService {

    @Inject
    RestConfiguration restConfiguration;

    public List<User> getAllUsers() {
        return restConfiguration.userClient().getAllUsers();
    }

    public Optional<User> getUserById(Long id) {
        return restConfiguration.userClient().getUserById(id);
    }

    public Object searchByName(String name) {
        return restConfiguration.userClient().getAllUsers()
                .stream()
                .filter(user -> user.getName().contains(name))
                .collect(Collectors.toList());
    }
}
