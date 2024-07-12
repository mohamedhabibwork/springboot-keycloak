package cloud.habib.springkeyclock.service;

import cloud.habib.springkeyclock.service.dto.UserDTO;
import cloud.habib.springkeyclock.util.KeycloakProvider;
import jakarta.ws.rs.core.Response;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IKeycloakServiceImpl implements IKeycloakService {
    /**
     * @return
     */
    @Override
    public List<UserRepresentation> findAllUsers() {
        return KeycloakProvider.getUserResource().list();
    }

    /**
     * @param name
     * @return
     */
    @Override
    public List<UserRepresentation> searchUserByUsername(String name) {
        return KeycloakProvider.getUserResource().searchByUsername(name, false).stream().toList();
    }

    /**
     * @param userDTO
     * @return
     */
    @Override
    public UserRepresentation createUser(@NonNull UserDTO userDTO) {
        UsersResource userResource = KeycloakProvider.getUserResource();

        Response response = userResource.create(userDTO.toRepresentation());
        if (response.getStatus() != 201) {
            log.error("Error creating user: {}", response.getStatusInfo());
            throw new RuntimeException("Error creating user");
        }
        UserRepresentation user = response.readEntity(UserRepresentation.class);
        userResource.get(user.getId()).resetPassword(userDTO.toCredentialRepresentation(false));
        RealmResource realmResource = KeycloakProvider.getRealmResource();
        List<RoleRepresentation> roleRepresentations = null;
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            roleRepresentations = List.of(realmResource.roles().get("user").toRepresentation());
        } else {
            roleRepresentations = realmResource.roles().list().stream()
                    .filter(role -> userDTO.getRoles().stream().anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                    .toList();
        }
        realmResource.users().get(user.getId()).roles().realmLevel().add(roleRepresentations);
        user.setRealmRoles(roleRepresentations.stream().map(RoleRepresentation::getName).toList());

        return user;
    }

    /**
     * @param userId
     */
    @Override
    public void deleteUser(String userId) {
        KeycloakProvider.getUserResource().get(userId).remove();
    }

    /**
     * @param userId
     * @param userDTO
     */
    @Override
    public void updateUser(String userId, @NonNull UserDTO userDTO) {
        KeycloakProvider.getUserResource().get(userId).update(userDTO.toRepresentation());
        KeycloakProvider.getUserResource().get(userId).resetPassword(userDTO.toCredentialRepresentation(false));
    }
}
