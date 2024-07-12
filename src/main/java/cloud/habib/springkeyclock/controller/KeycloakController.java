package cloud.habib.springkeyclock.controller;

import cloud.habib.springkeyclock.service.IKeycloakService;
import cloud.habib.springkeyclock.service.dto.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/keycloak/users")
@PreAuthorize("hasRole('admin_client_role')")
public class KeycloakController {
    @Autowired
    private IKeycloakService keycloakService;


    @GetMapping("/search/{username}")
    public ResponseEntity<?> searchUser(@PathVariable String username) {
        return ResponseEntity.ok(keycloakService.searchUserByUsername(username));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(keycloakService.findAllUsers());
    }

    @GetMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        UserRepresentation user = keycloakService.createUser(userDTO);
        return ResponseEntity.created(URI.create("")).body(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        keycloakService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        keycloakService.updateUser(userId, userDTO);
        return ResponseEntity.noContent().build();
    }

}