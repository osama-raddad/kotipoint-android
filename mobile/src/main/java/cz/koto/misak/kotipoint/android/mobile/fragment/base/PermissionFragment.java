package cz.koto.misak.kotipoint.android.mobile.fragment.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.fragment.base.BaseFragment;
import cz.koto.misak.kotipoint.android.mobile.util.PermissionUtils;
import timber.log.Timber;


public abstract class PermissionFragment extends BaseFragment {

    private Map<AppPermissionEnum, Boolean> mGrantedMandatoryPermissionMap = new HashMap<>();

    private boolean mPermissionNotGranted;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGrantedMandatoryPermissionMap.clear();
        if (getMandatoryPermissionList() == null) return;
        for(AppPermissionEnum appPermissionEnum: getMandatoryPermissionList()){
            mGrantedMandatoryPermissionMap.put(appPermissionEnum, Boolean.FALSE);
        }
    }

    public PermissionFragment() {
        super();
    }

    /**
     * Do anything with the specified permissions.
     */
    protected abstract void doWithMandatoryPermissions();

    /**
     *
     */
    protected abstract void mandatoryPermissionNotGranted();

    /**
     * Define list of permission as precondition for doWithMandatoryPermissions action.
     * e.g. Manifest.permission.ACCESS_FINE_LOCATION
     *
     * @return
     */
    protected abstract List<AppPermissionEnum> getMandatoryPermissionList();


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
    protected void requestMandatoryPermissions() {

        mPermissionNotGranted = false;
        List<AppPermissionEnum> permissionEnumList = getMandatoryPermissionList();

        if (PermissionUtils.hasPermission(getContext(),permissionEnumList)) {
            doWithMandatoryPermissions();
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
         * until requestMandatoryPermissions() will be invoked again.
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
                grantAndDoWithMandatoryPermissions(requestedAppPermission);
            } else {
                Timber.i("%s permissions NOT granted!",requestedAppPermission);
                Snackbar.make(getView(), R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
                mPermissionNotGranted = true;
                mandatoryPermissionNotGranted();
            }

        }
    }

    /**
     * Execute doWithMandatoryPermissions if there are all MANDATORY permissions granted.
     * @param appPermissionEnum
     */
    private void grantAndDoWithMandatoryPermissions(AppPermissionEnum appPermissionEnum){
        mGrantedMandatoryPermissionMap.put(appPermissionEnum, Boolean.TRUE);
        if (mGrantedMandatoryPermissionMap.values().contains(null)|| mGrantedMandatoryPermissionMap.values().contains(Boolean.FALSE)) return;
        doWithMandatoryPermissions();
    }

}
