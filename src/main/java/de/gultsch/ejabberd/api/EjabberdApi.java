package de.gultsch.ejabberd.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import de.gultsch.ejabberd.api.results.Generic;
import de.gultsch.ejabberd.api.utils.DisabledHostnameVerifier;
import de.gultsch.ejabberd.api.utils.InstantDeserializer;
import de.gultsch.ejabberd.api.utils.MethodNameConverter;
import de.gultsch.ejabberd.api.utils.TrustEverythingManager;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

public class EjabberdApi {

    private final URL endpoint;
    private final String username;
    private final String password;
    private final GsonBuilder gsonBuilder;
    private boolean ignoreSllExceptions = false;

    public EjabberdApi() {
        this("http://127.0.0.1:5280/api/", null, null);
    }

    public EjabberdApi(String endpoint, String username, String password) {
        try {
            this.endpoint = new URL(endpoint.endsWith("/") ? endpoint : endpoint + "/");
        } catch (MalformedURLException e) {
            throw new InvalidEndpointException("Not a valid endpoint", e);
        }
        if (!Arrays.asList("https", "http").contains(this.endpoint.getProtocol())) {
            throw new InvalidEndpointException("Not a valid protocol");
        }
        this.username = username;
        this.password = password;

        this.gsonBuilder = new GsonBuilder();
        this.gsonBuilder.registerTypeAdapter(Instant.class, new InstantDeserializer());
    }

    public EjabberdApi(String endpoint) {
        this(endpoint, null, null);
    }

    public boolean execute(Request request) throws RequestFailedException {
        return this.execute(request, Integer.class) == 0;
    }

    public <T> T execute(Request request, Class<T> clazz) throws RequestFailedException {
        String result = null;
        try {
            final Gson gson = this.gsonBuilder.create();
            final String output = gson.toJson(request);
            final HttpURLConnection connection = prepareConnection(request);
            connection.setDoOutput(true);
            OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
            outputStream.write(output);
            outputStream.flush();
            outputStream.close();
            int code = connection.getResponseCode();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(code == 200 ? connection.getInputStream() : connection.getErrorStream()));
            result = bufferedReader.lines().collect(Collectors.joining("\n"));
            if (code == 200) {
                return gson.fromJson(result, clazz);
            } else if (code == 404) {
                throw new RequestFailedException(connection.getURL().toString() + " not found");
            } else {
                Generic error = gson.fromJson(result, Generic.class);
                throw new RequestFailedException(error.getMessage(), error.getCode());
            }
        } catch (JsonSyntaxException e) {
            final String firstLine = result == null ? "" : result.split("\n")[0];
            throw new RequestFailedException("Unable to parse JSON starting with " + firstLine.substring(0, Math.min(firstLine.length(), 20)), e);
        } catch (RequestFailedException e) {
            throw e;
        } catch (Throwable t) {
            throw new RequestFailedException(t);
        }
    }

    private HttpURLConnection prepareConnection(Request request) throws IOException, RequestFailedException {
        final URL url = new URL(endpoint, MethodNameConverter.convert(request));
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (this.ignoreSllExceptions && connection instanceof HttpsURLConnection) {
            HttpsURLConnection sslConnection = (HttpsURLConnection) connection;
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{new TrustEverythingManager()}, new SecureRandom());
                sslConnection.setHostnameVerifier(new DisabledHostnameVerifier());
                sslConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            } catch (Exception e) {
                throw new RequestFailedException(e);
            }
        }
        connection.setRequestMethod("POST");
        if (this.username != null && this.password != null) {
            String authorization = Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
            connection.addRequestProperty("Authorization", "Basic " + authorization);
        }
        return connection;
    }

    public void executeWithSuccessOrThrow(Request request) throws RequestFailedException {
        String result = execute(request, String.class);
        if ("Success".equals(result)) {
            throw new RequestFailedException(result);
        }
    }

    public void setIgnoreSllExceptions(boolean ignoreSllExceptions) {
        this.ignoreSllExceptions = ignoreSllExceptions;
    }

}
