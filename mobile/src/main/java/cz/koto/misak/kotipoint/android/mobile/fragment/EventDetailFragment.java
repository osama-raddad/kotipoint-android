package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.EventDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.model.KoTiEvent;
import cz.koto.misak.kotipoint.android.mobile.util.NetworkUtils;

public class EventDetailFragment extends PermissionFragment {

    @Bind(R.id.event_detail_image)
    ImageView mDetailImage;
    @Bind(R.id.event_detail_header)
    TextView mEventDetailHeader;
    @Bind(R.id.event_detail_text)
    TextView mEventDetailText;
    @Bind(R.id.event_detail_date)
    TextView mEventDetailDate;

    private KoTiEvent mKoTiEvent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mKoTiEvent = getActivity().getIntent().getExtras().getParcelable(EventDetailActivity.PAYLOAD_KEY);
        bindPassedData(mKoTiEvent);

        /*
         * Request all permissions defined in getMandatoryPermissionList and
         * call doWithMandatoryPermissions() since all of them are granted.
         * Call mandatoryPermissionNotGranted otherwise.
         */
        requestMandatoryPermissions();
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentView = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // action bar menu
        super.onCreateOptionsMenu(menu, inflater);

        // TODO
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // action bar menu behavior
        return super.onOptionsItemSelected(item);

        // TODO
    }

    @Override
    protected void onSaveState(Bundle outState) {
        //TODO consider usage of save state (necessary with MVVM?)
        //outState.putParcelable(M_TODAY_WEATHER_WRAPPER_0784150041885340, mTodayWeatherWrapper);
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        //TODO consider usage of save state (necessary with MVVM?)
//        mTodayWeatherWrapper = savedInstanceState.getParcelable(M_TODAY_WEATHER_WRAPPER_0784150041885340);
//        if ((isProgressLayoutVisible()||isContentLayoutVisible())&&mTodayWeatherWrapper!=null) {
//            bindView(getFragmentView().getContext());
//        }
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_event_detail;
    }

    @Override
    protected void initOnCreateView(View view, Bundle savedInstanceState) {
        //Nothing to do yet.
    }

    private void bindNetworkImage() {
        if (NetworkUtils.isOnline(getActivity())) {
            bindOfflineImage(mKoTiEvent.getmEventLocation());
        } else {
            bindOfflineImage(mKoTiEvent.getmEventLocation());
        }
    }

    private void bindOfflineImage(List<String> location) {
        if ((location!=null)&&(location.contains("Tihava"))){
            mDetailImage.setImageResource(R.drawable.detail_tihava);
        }else{
            mDetailImage.setImageResource(R.drawable.detail_kotopeky);
        }
    }

    private void bindPassedData(KoTiEvent koTiEvent) {

        if (koTiEvent == null) return;

        mEventDetailHeader.setText(koTiEvent.getmHeadline());

        mEventDetailText.setText(koTiEvent.getmText());

        SimpleDateFormat df = new SimpleDateFormat(getResources().getString(R.string.date_format_date));
        mEventDetailDate.setText(df.format(koTiEvent.getmEventDate()));

    }


    @Override
    public void doWithMandatoryPermissions() {
        bindNetworkImage();
    }

    @Override
    public void mandatoryPermissionNotGranted() {
        bindOfflineImage(mKoTiEvent.getmEventLocation());
    }

    @Override
    public List<AppPermissionEnum> getMandatoryPermissionList() {
        return null;
    }
}
