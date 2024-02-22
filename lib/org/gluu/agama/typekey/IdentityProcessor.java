package org.gluu.agama.typekey;

import io.jans.as.common.model.common.User;
import io.jans.as.common.service.common.EncryptionService;
import io.jans.as.common.service.common.UserService;
import io.jans.orm.exception.operation.EntryNotFoundException;
import io.jans.service.cdi.util.CdiUtil;
import io.jans.as.server.service.AuthenticationService;
import io.jans.util.StringHelper;
import io.jans.orm.model.base.CustomObjectAttribute;

import java.io.IOException;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentityProcessor implements IdentityProcessorInterface {

    private static final Logger logger = LoggerFactory.getLogger(IdentityProcessor.class);

    private static final String INUM_ATTR = "inum";
    private static final String UID = "uid";
    private static final String MAIL = "mail";
    private static final String CN = "cn";
    private static final String DISPLAY_NAME = "displayName";
    private static final String GIVEN_NAME = "givenName";
    private static final String SN = "sn";

    public IdentityProcessor() {
    }

    public Map<String, String> accountFromUsername(String username) {

        User user = getUser(UID, username);
        boolean local = user != null;
        logger.debug("There is {} local account for {}", local ? "a" : "no", username);

        if (local) {
            String email = getSingleValuedAttr(user, MAIL);
            String inum = getSingleValuedAttr(user, INUM_ATTR);
            String name = getSingleValuedAttr(user, GIVEN_NAME);

            if (name == null) {
                name = getSingleValuedAttr(user, DISPLAY_NAME);

                if (name == null) {
                    name = email.substring(0, email.indexOf("@"));
                }
            }
            return Map.of(UID, username, INUM_ATTR, inum, "name", name, "email", email);
        }
        return Collections.emptyMap();
    }

    public boolean authenticate(String username, String password) {
        AuthenticationService authenticationService = CdiUtil.bean(AuthenticationService.class);
        logger.info("Validating {}", username);
        boolean result = authenticationService.authenticate(username, password);
        logger.info("Validation status is {}", result);
        return result;
    }

    public void enroll(String username, Map<String, Object> typekeyAttributes) {
        User user = getUser(UID, username);
        logger.info("Adding Typekey claims to UID {}", username);
        UserService userService = CdiUtil.bean(UserService.class);
        user.getCustomAttributes().add(new CustomObjectAttribute("typekeyData", typekeyAttributes));
        userService.updateUser(user);
    }

    public boolean enrolled(String username) {
        User user = getUser(UID, username);
        UserService userService = CdiUtil.bean(UserService.class);
        CustomObjectAttribute typekeyClaims = userService.getCustomAttribute(user, "typekeyData");
        if (typekeyClaims != null) {
            logger.info("{} has enrolled", username);
            return true;
        }
        logger.info("{} has not enrolled", username);
        return false;
    }

    public Map<String, Object> getTypekeyData(String username) {
        User user = getUser(UID, username);
        UserService userService = CdiUtil.bean(UserService.class);
        CustomObjectAttribute typekeyClaims = userService.getCustomAttribute(user, "typekeyData");
        LinkedHashMap<String, Object> data = typekeyClaims.getValue();
        return data;
    }

    private static User getUser(String attributeName, String value) {
        UserService userService = CdiUtil.bean(UserService.class);
        return userService.getUserByAttribute(attributeName, value, true);
    }

    private static String getSingleValuedAttr(User user, String attribute) {

        Object value = null;
        if (attribute.equals(UID)) {
            // user.getAttribute("uid", true, false) always returns null :(
            value = user.getUserId();
        } else {
            value = user.getAttribute(attribute, true, false);
        }
        return value == null ? null : value.toString();

    }

}
