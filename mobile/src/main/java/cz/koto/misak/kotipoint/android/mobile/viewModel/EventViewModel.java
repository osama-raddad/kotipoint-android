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

    private Context context;
    private KoTiEvent event;

    public EventViewModel(Context context, KoTiEvent event) {
        this.context = context;
        this.event = event;
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
        return event.getmEventDate() == null ? "" : new PrettyTime().format(event.getmEventDate());
    }

    public String getEventDate() {
        SimpleDateFormat df = new SimpleDateFormat(context.getResources().getString(R.string.date_format_date));
        return event.getmEventDate() == null ? "" : df.format(event.getmEventDate());
    }

    public int getEventDateVisibility() {
        return (event.getmEventDate() == null || event.getmEventDate().after(new Date())) ? View.GONE : View.VISIBLE;
    }

    public String getEventLocation() {
        String location = (event.getmEventLocation() == null || event.getmEventLocation().size() == 0) ? "-" : event.getmEventLocation().get(0);
//        String author = context.getString(R.string.text_post_author,location);
//        SpannableString content = new SpannableString(author);
//        int index = author.indexOf(location);
//        content.setSpan(new UnderlineSpan(), index,location.length() + index, 0);
//        return content;
        return location;
    }

    public String getHeadline() {
        return event.getmHeadline();
    }


    public View.OnClickListener onClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(EventDetailActivity.newIntent(context));
                Intent i = EventDetailActivity.newIntent(context);
                i.putExtra(EventDetailActivity.PAYLOAD_KEY, event);
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

}

