package cloud.habib.springkeyclock.service;

import cloud.habib.springkeyclock.service.dto.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IKeycloakService {
    List<UserRepresentation> findAllUsers();

    List<UserRepresentation> searchUserByUsername(String name);

    UserRepresentation createUser(UserDTO userDTO);

    void deleteUser(String userId);

    void updateUser(String userId, UserDTO userDTO);
}
