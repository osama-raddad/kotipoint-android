package cz.koto.misak.kotipoint.android.mobile.fragment;


import java.util.Arrays;
import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;

public class GalleryFragment extends StatefulPermissionFragment {


    @Override
    StatefulLayout getFragmentView() {
        return null;
    }

    @Override
    public void doWithPermissions() {

    }

    @Override
    public List<AppPermissionEnum> getPermissionList() {
        List<AppPermissionEnum> ret = super.getPermissionList();
        ret.addAll(Arrays.asList(AppPermissionEnum.INTERNET));
        return ret;
    }
}
