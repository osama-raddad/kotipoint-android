package cz.koto.misak.kotipoint.android.mobile.viewModel;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.EventDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.model.KoTiEvent;

/**
 * Subclassing BaseObservable allows us to use @Bindable on getters, and notifyPropertyChanged() when a @Bindable property changes.
 */
public class EventViewModel extends BaseObservable {

    KoTiEvent mEvent;
    Resources mResources;

    public EventViewModel(KoTiEvent event, Resources resources) {
        this.mEvent = event;
        this.mResources = resources;
    }

//    @BindingAdapter("containerMargin")
//    public static void setContainerMargin(View view, boolean isTopLevelComment) {
//        if (view.getTag() == null) {
//            view.setTag(true);
//            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)
//                    view.getLayoutParams();
//            float horizontalMargin = view.getContext().getResources().getDimension(R.dimen.activity_horizontal_margin);
//            float topMargin = isTopLevelComment
//                    ? view.getContext().getResources().getDimension(R.dimen.activity_vertical_margin) : 0;
//            layoutParams.setMargins((int) horizontalMargin, (int) topMargin, (int) horizontalMargin, 0);
//            view.setLayoutParams(layoutParams);
//        }
//    }
//
//    @BindingAdapter("commentDepth")
//    public static void setCommentIndent(View view, int depth) {
//        if (view.getTag() == null) {
//            view.setTag(true);
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
//                    view.getLayoutParams();
//            float margin = ViewUtils.convertPixelsToDp(depth * 20, view.getContext());
//            layoutParams.setMargins((int) margin, 0, 0, 0);
//            view.setLayoutParams(layoutParams);
//        }
//    }

    public String getEventDateDuration() {
        return mEvent.getmEventDate() == null ? "" : new PrettyTime().format(mEvent.getmEventDate());
    }

    public String getEventDate() {
        if (mEvent.getmEventDate()==null) return "";
        SimpleDateFormat df = new SimpleDateFormat(mResources.getString(R.string.date_format_date));
        return df.format(mEvent.getmEventDate());
    }

    public int getEventDateVisibility() {
        return (mEvent.getmEventDate() == null || mEvent.getmEventDate().after(new Date())) ? View.GONE : View.VISIBLE;
    }

    public String getEventLocation() {
        String ret = "";
        if (mEvent.getmEventLocation()!=null){
            if (mEvent.getmEventLocation().size()>0){
                ret = mEvent.getmEventLocation().get(0);
            }
        }
        return ret;
    }

    public String getEventHeadline(){
        return mEvent.getmHeadline();
    }

    public String getEventText(){
        return "TODOfalskfjas;lkfj;asdfkj;aslfkj;asldfkj;asldfjkjasdhflkasdhflkasddjhfklasdfhlasdjflasdjkflasdjflsadkjflaskdjflaskdfjlaskdjflasdkjflasdkfjlasdfjlasdkj lkfsa l;kas;lfdkjas;lfdkjasd;lf kjas;lfkj;asldfjka;sldfkj a;lskdfj ;laksfjd;lkasfjd";//mEvent.getmText();
    }

    public View.OnClickListener callEventDetailActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(EventDetailActivity.newIntent(context));
                Intent i = EventDetailActivity.newIntent(context);
                i.putExtra(EventDetailActivity.PAYLOAD_KEY, mEvent);
                context.startActivity(i);
            }
        };
    }

    public View.OnClickListener onClickDate() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a SnackBar with an explanation and a button to trigger the request.
                Snackbar.make(v, R.string.calendar_not_implemented,
                        Snackbar.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * https://medium.com/android-news/loading-images-with-data-binding-and-picasso-555dad683fdc#.1ny3d2s2w
     *
     * @param view
     * @param eventLocation
     */
    @BindingAdapter({"bind:imageUrlEvent"})
    public static void imageUrlEvent(ImageView view, String eventLocation) {


        if (false){
//            Timber.e("ImageUrl: %s",imageUrl);
//            Picasso
//                    .with(view.getContext())
//                    .load(imageUrl)
//                    .transform(ImageTransformation.getTransformation(view))
//                    .into(view);

        }else {
            if ((eventLocation != null) && (eventLocation.contains("Tihava"))) {
                view.setImageResource(R.drawable.detail_tihava);
            } else {
                view.setImageResource(R.drawable.detail_kotopeky);
            }
        }
    }

}

