package edu.java.core.util;

import org.springframework.http.HttpStatusCode;

public class ResponseStubber {
    private ResponseStubber() {
    }

    private static final Integer NOT_IMPLEMENTED_CODE = 501;

    public static HttpStatusCode stubNotImplemented() {
        return HttpStatusCode.valueOf(NOT_IMPLEMENTED_CODE);
    }
}
