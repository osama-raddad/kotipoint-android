package cz.koto.misak.kotipoint.android.mobile.viewModel;


import android.content.Context;
import android.databinding.BaseObservable;
import android.text.Html;

import org.ocpsoft.prettytime.PrettyTime;

import cz.koto.misak.kotipoint.android.mobile.R;
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

    public String getCommentText() {
        return Html.fromHtml(event.getmText().trim()).toString();
    }

    public String getCommentAuthor() {
        return context.getResources().getString(R.string.text_comment_author, event.getmHeadline());
    }

    public String getCommentDate() {
        return event.getmEventDate()==null? "N/A":new PrettyTime().format(event.getmEventDate());
    }

//    public int getCommentDepth() {
//        return event.depth;
//    }
//
//    public boolean getCommentIsTopLevel() {
//        return event.isTopLevelComment;
//    }
}

