package io.github.javialc.java.labs.domain.utils;

public class RequestSourceContextHolder {

    private static final ThreadLocal<RequestSource> REQUEST_SOURCE_HOLDER = new ThreadLocal<>();

    private RequestSourceContextHolder() {
    }

    public static void setRequestSource(final RequestSource requestSource) {
        REQUEST_SOURCE_HOLDER.set(requestSource);
    }

    public static RequestSource getRequestSource() {
        return REQUEST_SOURCE_HOLDER.get();
    }

    public static void clearRequestSource() {
        REQUEST_SOURCE_HOLDER.remove();
    }
}
