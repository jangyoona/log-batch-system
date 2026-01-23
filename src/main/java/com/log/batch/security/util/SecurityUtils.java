package com.log.batch.security.util;

import com.log.batch.security.model.WebUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}


    /**
     * userId (PK)  반환
     **/
    public static Long getCurrentUserPk() {
        WebUserDetails principal = getPrincipal();
        return principal != null ? principal.getUser().getId() : null;
    }

    /**
     * userName 반환
     **/
    public static String getCurrentUserName() {
        WebUserDetails principal = getPrincipal();
        return principal != null ? principal.getUser().getUserName() : null;
    }

    public static WebUserDetails getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof WebUserDetails)) {
            return null;
        }

        return (WebUserDetails) principal;
    }


}
