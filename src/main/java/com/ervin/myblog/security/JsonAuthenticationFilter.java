package com.ervin.myblog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JsonCredentials credentials;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (isJsonRequest(request)) {
            try {
                /*
                 * HttpServletRequest can be read only once
                 */
                StringBuffer sb = new StringBuffer();
                String line = null;

                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }

                //json transformation
                ObjectMapper mapper = new ObjectMapper();
                this.credentials = mapper.readValue(sb.toString(), JsonCredentials.class);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.attemptAuthentication(request, response);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        if (isJsonRequest(request)) {
            return credentials.getPassword();
        }
        else {
            return super.obtainPassword(request);
        }
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        if (isJsonRequest(request)) {
            return credentials.getUsername();
}
        else {
            return super.obtainUsername(request);
        }
    }

    private boolean isJsonRequest(HttpServletRequest request) {
        if (request.getHeader("Content-Type").startsWith("application/json")) {
            return true;
        }
        return false;
    }
}
