package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.PermissionChecker;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.util.PermissionUtils;
import timber.log.Timber;


public abstract class PermissionFragment extends BaseFragment {

    private Map<AppPermissionEnum, Boolean> mGrantedPermissionMap = new HashMap<>();

    private boolean mPermissionNotGranted;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGrantedPermissionMap.clear();
        if (getPermissionList() == null) return;
        for(AppPermissionEnum appPermissionEnum:getPermissionList()){
            mGrantedPermissionMap.put(appPermissionEnum, Boolean.FALSE);
        }
    }

    public PermissionFragment() {
        super();
    }

    /**
     * Do anything with the specified permissions.
     */
    protected abstract void doWithPermissions();

    /**
     *
     */
    protected abstract void permissionNotGranted();

    /**
     * Define list of permission as precondition for doWithPermissions action.
     * e.g. Manifest.permission.ACCESS_FINE_LOCATION
     *
     * @return
     */
    protected abstract List<AppPermissionEnum> getPermissionList();


    /**
     * Check whether any of rationale request should be shown.
     * @param appPermissionEnum
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(AppPermissionEnum appPermissionEnum){
        if (appPermissionEnum==null) return false;
        for (String permissionRationale:appPermissionEnum.getPermissionArray()){
            if (shouldShowRequestPermissionRationale(permissionRationale)){
                return true;
            }
        }
        return false;
    }

    /**
     * Requests the Contacts permissions.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermissions() {

        mPermissionNotGranted = false;
        List<AppPermissionEnum> permissionEnumList = getPermissionList();

        if (hasPermission(permissionEnumList)) {
            doWithPermissions();
            return;
        }

        for (final AppPermissionEnum permissionEnum : permissionEnumList) {

            // BEGIN_INCLUDE(permission_request)
            if (shouldShowRequestPermissionRationale(permissionEnum)) {

                // Provide an additional rationale to the user if the permission was not granted
                // and the user would benefit from additional context for the use of the permission.
                // For example, if the request has been denied previously.
                Timber.i("Displaying contacts permission rationale to provide additional context.");

                // Display a SnackBar with an explanation and a button to trigger the request.
                Snackbar.make(getView(), R.string.permission_fine_location_rationale,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.manage_permission, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                requestPermissions(permissionEnum.getPermissionArray(), permissionEnum.getRequestId());
                            }
                        })
                        .show();
            } else {
                // Permissions have not been granted yet. Request them directly.
                requestPermissions(permissionEnum.getPermissionArray(), permissionEnum.getRequestId());
            }
            // END_INCLUDE(permission_request)
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        /**
         * If there is at least one not granted permission stop check for the others
         * until requestPermissions() will be invoked again.
         **/
        if (mPermissionNotGranted) return;

        AppPermissionEnum requestedAppPermission = AppPermissionEnum.getAppPermissionEnumById(requestCode);

        if (requestedAppPermission == null){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }else {
            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
            if (PermissionUtils.verifyPermissions(grantResults)) {
                // All required permissions have been granted, do the right thing with these permission...
                Timber.d("%s permissions granted.", requestedAppPermission);
                grantAndDoWithPermissions(requestedAppPermission);
            } else {
                Timber.i("%s permissions NOT granted!",requestedAppPermission);
                Snackbar.make(getView(), R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
                mPermissionNotGranted = true;
                permissionNotGranted();
            }

        }
    }

    /**
     * Grant specified permission.
     * Execute doWithPermissions if there are all permissions granted.
     * @param appPermissionEnum
     */
    private void grantAndDoWithPermissions(AppPermissionEnum appPermissionEnum){
        mGrantedPermissionMap.put(appPermissionEnum, Boolean.TRUE);
        if (mGrantedPermissionMap.values().contains(null)|| mGrantedPermissionMap.values().contains(Boolean.FALSE)) return;
        doWithPermissions();
    }

    /**
     * method that will return whether the permission is accepted. By default it is true if the user is using a device below
     * version 23
     * @param appPermissionEnumList
     * @return
     */
    private boolean hasPermission(List<AppPermissionEnum> appPermissionEnumList) {
        if (canMakeSmores()) {
            if (appPermissionEnumList==null) return true;
            for(AppPermissionEnum permission:appPermissionEnumList) {
                if (PermissionChecker.checkSelfPermission(getContext(), permission.getPermissionArray()[0]) != PackageManager.PERMISSION_GRANTED){
                    return false;
                };
            }
            return true;
        }
        return true;
    }

    /**
     * Just a check to see if we have marshmallows (version 23)
     * @return
     */
    private boolean canMakeSmores() {
        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);
    }

}
