package cz.koto.misak.kotipoint.android.mobile.viewModel;


import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;

import org.ocpsoft.prettytime.PrettyTime;

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

    public String getPostScore() {
        return event.getmEventDate()==null? "N/A":new PrettyTime().format(event.getmEventDate());
    }

    public Spannable getPostAuthor() {
        String location = (event.getmEventLocation()==null || event.getmEventLocation().size()==0)?"-":event.getmEventLocation().get(0);
        String author = context.getString(R.string.text_post_author,location);
        SpannableString content = new SpannableString(author);
        int index = author.indexOf(location);
        content.setSpan(new UnderlineSpan(), index,location.length() + index, 0);
        return content;
    }

    public String getPostTitle() {
        return event.getmHeadline();
    }

    public String getCommentText() {
        return Html.fromHtml(event.getmText().trim()).toString();
    }

    public String getCommentAuthor() {
        return context.getResources().getString(R.string.text_comment_author, event.getmHeadline());
    }

    public String getCommentDate() {
        return event.getmEventDate()==null? "N/A":new PrettyTime().format(event.getmEventDate());
    }

    public int getCommentsVisibility() {
        return  /*post.postType == Post.PostType.STORY && post.kids == null*/false ? View.GONE : View.VISIBLE;
    }

//    public int getCommentDepth() {
//        return event.depth;
//    }
//
//    public boolean getCommentIsTopLevel() {
//        return event.isTopLevelComment;
//    }

    public View.OnClickListener onClickPost() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Post.PostType postType = post.postType;
//                if (postType == Post.PostType.JOB || postType == Post.PostType.STORY) {
//                    launchStoryActivity();
//                } else if (postType == Post.PostType.ASK) {
//                    launchCommentsActivity();
//                }
                context.startActivity(EventDetailActivity.newIntent(context));
                Intent i = EventDetailActivity.newIntent(context);
                i.putExtra(EventDetailActivity.PAYLOAD_KEY, event);
                context.startActivity(i);
            }
        };
    }

    public View.OnClickListener onClickAuthor() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing yet
            }
        };
    }

    public View.OnClickListener onClickComments() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //do nothing yet
            }
        };
    }
}

