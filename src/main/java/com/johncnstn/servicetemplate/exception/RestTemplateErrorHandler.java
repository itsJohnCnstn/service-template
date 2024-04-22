package com.johncnstn.servicetemplate.exception;

import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Component
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        super.handleError(response);
    }
}
