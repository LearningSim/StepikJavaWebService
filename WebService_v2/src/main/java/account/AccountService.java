package account;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, UserProfile> userByLogin = new HashMap<>();
    private final Map<String, UserProfile> userBySessionId = new HashMap<>();

    public void addUser(UserProfile user) {
        userByLogin.put(user.getLogin(), user);
    }

    public UserProfile getUserByLogin(String login) {
        return userByLogin.get(login);
    }

    public void addSession(String sessionId, UserProfile user) {
        userBySessionId.put(sessionId, user);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return userBySessionId.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        userBySessionId.remove(sessionId);
    }
}
