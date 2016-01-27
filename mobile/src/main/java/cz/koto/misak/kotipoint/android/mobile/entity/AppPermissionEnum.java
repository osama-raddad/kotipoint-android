package cz.koto.misak.kotipoint.android.mobile.entity;

import android.Manifest;

/**
 * Definition of all permissions, defined in AndroidManifest.xml for application.
 * Permission list in the manifest must be equal to permissions in this enum!
 */
public enum AppPermissionEnum {

    LOCATION(0, Manifest.permission.ACCESS_FINE_LOCATION),
    NETWORK_STATE(1, Manifest.permission.ACCESS_NETWORK_STATE),
    INTERNET(2, Manifest.permission.INTERNET),
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
