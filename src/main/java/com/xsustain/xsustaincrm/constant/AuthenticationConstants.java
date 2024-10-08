package com.xsustain.xsustaincrm.constant;


import com.xsustain.xsustaincrm.model.enume.RoleType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public final class AuthenticationConstants {
    public static final Set<RoleType> ADMINTATION_ROLES = new HashSet<>(
            Arrays.asList(RoleType.ADMIN, RoleType.SUPPORTSTAFF));
    public static final long EXPIRE_TOKEN_AFTER_MINUTES = 99999;
}
