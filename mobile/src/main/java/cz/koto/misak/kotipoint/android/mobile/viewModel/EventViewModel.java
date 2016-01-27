package cz.koto.misak.kotipoint.android.mobile.viewModel;


import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.EventDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.entity.KoTiEvent;

public class EventViewModel extends BaseObservable {

    private Context mContext;
    private KoTiEvent mEvent;

    public EventViewModel(Context context, KoTiEvent event) {
        this.mContext = context;
        this.mEvent = event;
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
        SimpleDateFormat df = new SimpleDateFormat(mContext.getResources().getString(R.string.date_format_date));
        return mEvent.getmEventDate() == null ? "" : df.format(mEvent.getmEventDate());
    }

    public int getEventDateVisibility() {
        return (mEvent.getmEventDate() == null || mEvent.getmEventDate().after(new Date())) ? View.GONE : View.VISIBLE;
    }

    public String getEventLocation() {
        String location = (mEvent.getmEventLocation() == null || mEvent.getmEventLocation().size() == 0) ? "-" : mEvent.getmEventLocation().get(0);
//        String author = mContext.getString(R.string.text_post_author,location);
//        SpannableString content = new SpannableString(author);
//        int index = author.indexOf(location);
//        content.setSpan(new UnderlineSpan(), index,location.length() + index, 0);
//        return content;
        return location;
    }

    public String getHeadline() {
        return mEvent.getmHeadline();
    }


    public View.OnClickListener onClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(EventDetailActivity.newIntent(mContext));
                Intent i = EventDetailActivity.newIntent(mContext);
                i.putExtra(EventDetailActivity.PAYLOAD_KEY, mEvent);
                mContext.startActivity(i);
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

}

