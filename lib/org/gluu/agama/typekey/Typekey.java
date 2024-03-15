package org.gluu.agama.typekey;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.jwt.*;

import io.jans.as.common.model.common.User;
import io.jans.as.common.service.common.UserService;
import io.jans.as.model.crypto.signature.SignatureAlgorithm;
import io.jans.as.model.crypto.AuthCryptoProvider;
import io.jans.service.cdi.util.CdiUtil;
import io.jans.util.StringHelper;
import io.jans.service.CacheService;

import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.entity.ContentType;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Typekey {

    private static final String SCAN_SCOPE = "https://api.gluu.org/auth/scopes/scan.typekey";
    private static final Logger logger = LoggerFactory.getLogger(Typekey.class);

    private TypekeyConfiguration config;

    public Typekey() {
    }

    public Typekey(TypekeyConfiguration config) {
        this.config = config;
    }

    private String buildServiceAuth() throws Exception {
        String basic = config.getClientId() + ":" + config.getClientSecret();
        basic = new String(Base64.getEncoder().encode(basic.getBytes(UTF_8)), UTF_8);

        StringJoiner joiner = new StringJoiner("&");
        Map.of("grant_type", "client_credentials", "scope", URLEncoder.encode(SCAN_SCOPE, UTF_8))
                .forEach((k, v) -> joiner.add(k + "=" + v));

        String asEndpoint = config.getAuthHost() + "/jans-auth/restv1/token";
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.POST, new URL(asEndpoint));

        request.setAccept(APPLICATION_JSON);
        request.setConnectTimeout(3000);
        request.setReadTimeout(3000);
        request.setQuery(joiner.toString());
        request.setAuthorization("Basic " + basic);

        HTTPResponse r = request.send();
        r.ensureStatusCode(200);

        logger.info("Got a token from {}", asEndpoint);
        return "Bearer " + r.getContentAsJSONObject().getAsString("access_token");
    }

    public void dynamicRegistration(String scanSSA) {
        String asEndpoint = config.getAuthHost() + "/jans-auth/restv1/register";
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.POST, new URL(asEndpoint));
        request.setAccept(APPLICATION_JSON);
        request.setContentType(APPLICATION_JSON);
        request.setConnectTimeout(3000);
        request.setReadTimeout(3000);
        JSONArray redirect_array = new JSONArray();
        redirect_array.put(config.getAuthHost());
        JSONArray grant_array = new JSONArray();
        grant_array.put("client_credentials");
        Map<String, Object> map = new HashMap(Map.of(
                "client_name", "typekey_client",
                "redirect_uris", redirect_array,
                "grant_types", grant_array,
                "software_statement", scanSSA,
                "lifetime", 86400));
        String message = new JSONObject(map).toString();
        request.setQuery(message);
        HTTPResponse r = request.send();
        r.ensureStatusCode(201);
        logger.info("Client registration successful");
        config.setClientId(r.getContentAsJSONObject().getAsString("client_id"));
        config.setClientSecret(r.getContentAsJSONObject().getAsString("client_secret"));
    }

    private String signUid(String uid, String alias) throws Exception {
        AuthCryptoProvider auth = new AuthCryptoProvider(config.getKeystoreName(), config.getKeystorePassword(), null);
        String signedUid = auth.sign(uid, alias, null, SignatureAlgorithm.RS256);
        return signedUid;
    }

    public Map<String, Object> validateKeystrokes(String username, String k_data, String use_case)
            throws Exception {
        String token = buildServiceAuth();
        JSONArray k_data_array = new JSONArray(k_data);
        int useCase = Integer.parseInt(use_case);
        Map<String, Object> map = new HashMap(Map.of(
                "k_data", k_data_array,
                "uid", username,
                "org_id", config.getOrgId(),
                "use_case", useCase));
        String endpointUrl = config.getScanHost() + "/scan/typekey/validate";
        String message = new JSONObject(map).toString();
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.POST, new URL(endpointUrl));
        request.setContentType(APPLICATION_JSON);
        request.setAccept(APPLICATION_JSON);
        request.setConnectTimeout(3000);
        request.setReadTimeout(3000);
        request.setQuery(message);
        request.setAuthorization(token);
        HTTPResponse r = request.send();

        r.ensureStatusCode(200);
        Map<String, Object> responseObject = r.getContentAsJSONObject();

        return responseObject;
    }

    public void notifyKeystrokes(String uid, int track_id, String use_case) {
        int useCase = Integer.parseInt(use_case);
        String token = buildServiceAuth();
        Map<String, Object> map = new HashMap(Map.of(
                "uid", uid,
                "track_id", track_id,
                "org_id", config.getOrgId(),
                "use_case", useCase));
        String endpointUrl = config.getScanHost() + "/scan/typekey/notify";
        String message = new JSONObject(map).toString();
        logger.info(message);
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.POST, new URL(endpointUrl));
        request.setContentType(APPLICATION_JSON);
        request.setAccept(APPLICATION_JSON);
        request.setConnectTimeout(3000);
        request.setReadTimeout(3000);
        request.setQuery(message);
        request.setAuthorization(token);

        HTTPResponse r = request.send();

        r.ensureStatusCode(200);
        logger.info("Notify sent successfully");
    }

    public String getRandomUseCase() {
        Random rand = new Random();
        int result = rand.nextInt(config.getPhrases().size()) + 1;
        return String.valueOf(result);
    }

    public Map<String, Object> generateTypekeyData(String useCase) {
        Map<String, Object> map = new HashMap(Map.of(
                "phrase", config.getPhrases().get(useCase),
                "useCase", useCase));
        return map;
    }

    public boolean isEnrolling(int count) {
        return count < 6;
    }

}
