package cloud.habib.springkeyclock.util;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;

public class KeycloakProvider {
    private static final String SERVER_URL = "https://keycloak.habib.cloud";

    private static final String REALM_NAME = "spring";
    private static final String REALM_MASTER_NAME = "master";
    private static final String MAIN_CLIENT_SECRET = "dVNI7lDbNiOnv6YWhEoAbxQTVyFXSUPl";
    private static final String ADMIN_CLIENT_NAME = "admin-cli";
    private static final String USER_CONSOLE = "admin";
    private static final String USER_PASSWORD = "admin";


    public static RealmResource getRealmResource() {
        return KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM_MASTER_NAME)
                .clientId(ADMIN_CLIENT_NAME)
                .username(USER_CONSOLE)
                .password(USER_PASSWORD)
                .clientSecret(MAIN_CLIENT_SECRET)
                .resteasyClient(
                        new ResteasyClientBuilderImpl()
                                .connectionPoolSize(10)
                                .build()
                )
                .build()
                .realm(REALM_NAME);
    }


    public static UsersResource getUserResource() {
        return getRealmResource().users();
    }
}
