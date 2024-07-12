package cloud.habib.springkeyclock.service.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

@Builder
@RequiredArgsConstructor
@Value
public class UserDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private java.util.Set<String> roles;


    public UserRepresentation toRepresentation() {
        return new UserRepresentation() {{
            setUsername(username);
            setEmail(email);
            setFirstName(firstName);
            setLastName(lastName);
            setEnabled(true);
        }};
    }

    public CredentialRepresentation toCredentialRepresentation(boolean temporary) {
        return new CredentialRepresentation() {{
            setType(OAuth2Constants.PASSWORD);
            setValue(password);
            setTemporary(temporary);
        }};
    }
}
