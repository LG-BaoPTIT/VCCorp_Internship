package com.ite.authservice.constants;

import java.util.Arrays;
import java.util.List;

public class JobQueue {
    public static final String QUEUE_DEV = "rabbit-queue-dev";
    public static final String APPROVED_ACCOUNT_QUEUE = "approved-account";//send mail with qr 2fa
    public static final String CREATE_ADMIN_ACCOUNT_QUEUE = "create-admin-account";
    public static final String UPDATE_ADMIN_ACCOUNT_QUEUE = "update-admin-account";
    public static final String LOCK_ACCOUNT_NOTICE_QUEUE = "lock account notice";
    public static final String ADD_ROLE_GROUP_QUEUE = "add-role-group";
    public static final String UPDATE_ROLE = "update-roleGroup";
    public static final String DELETE_ROLE = "delete-roleGroup";
    public static final String APPROVED_ACCOUNT_NOTICE_QUEUE = "approved-account-notice";
    public static final String RESET_PASSWORD_NOTICE_QUEUE = "reset-password-notice";
}
