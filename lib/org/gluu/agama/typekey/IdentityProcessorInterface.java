package org.gluu.agama.typekey;

import java.util.Map;

public interface IdentityProcessorInterface {

    public Map<String, String> accountFromUsername(String username);

    public boolean authenticate(String username, String password);

    public boolean enrolled(String username);

    public void enroll(String username, Map<String, String> typekeyAttributes);

    public Map<String, String> getTypekeyData(String username);
}
