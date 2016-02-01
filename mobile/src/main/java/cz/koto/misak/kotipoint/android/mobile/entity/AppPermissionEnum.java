package cz.koto.misak.kotipoint.android.mobile.entity;

import android.Manifest;

/**
 * Definition of all permissions, defined in AndroidManifest.xml for application.
 * Permission list in the manifest must be equal to permissions in this enum!
 *
 * http://developer.android.com/reference/android/Manifest.permission.html
 */
public enum AppPermissionEnum {

    /* Allows applications to access information about networks. */
    ACCESS_NETWORK_STATE(0, Manifest.permission.ACCESS_NETWORK_STATE),
    /* Allows applications to open network sockets. */
    INTERNET(1, Manifest.permission.INTERNET),
    /* Allows an app to access precise location. */
    ACCESS_FINE_LOCATION(2, Manifest.permission.ACCESS_FINE_LOCATION),
    /* Allows an app to access approximate location. */
    ACCESS_COARSE_LOCATION(3, Manifest.permission.ACCESS_COARSE_LOCATION);

    AppPermissionEnum(int requestId, String... permissions) {
        this.requestId = requestId;
        this.permissionArray = permissions;
    }

    /**
     * Id to identify a contacts permission request.
     */
    private int requestId;

    /**
     * Permissions required.
     */
    private String[] permissionArray;


    public int getRequestId() {
        return requestId;
    }

    public String[] getPermissionArray() {
        return permissionArray;
    }

    public static AppPermissionEnum getAppPermissionEnumById(int requestId) {
        AppPermissionEnum ret = null;
        for (AppPermissionEnum appEnum : AppPermissionEnum.values()) {
            if (appEnum.getRequestId() == requestId) {
                ret = appEnum;
                break;
            }
        }
        return ret;
    }
}
