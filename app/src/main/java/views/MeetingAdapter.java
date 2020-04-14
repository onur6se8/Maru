package views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.maru.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import events.DeleteMeetingEvent;
import model.Meeting;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    // FOR DATA
    private List<Meeting> mMeetings;

    // CONSTRUCTOR
    public MeetingAdapter(List<Meeting> mMittings) {
        this.mMeetings = mMittings;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_main_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder( final ViewHolder holder, int position) {

        Meeting meeting = mMeetings.get(position);
        holder.mMeetingInfo.setText(meeting.getInfo());
        holder.mMeetingInvite.setText(meeting.getMember());
        Glide.with(holder.mMeetingAvatar.getContext())
                .load(meeting.getCouleurAvatar())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mMeetingAvatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), meeting.getDay() + " / " + meeting.getMonth() + "\n\n" + meeting.getInfo()+ "\n" + meeting.getMember(), Toast.LENGTH_LONG).show();

            }
        });

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.mMeetings.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profil_image)
        public ImageView mMeetingAvatar;
        @BindView(R.id.text_info)
        public TextView mMeetingInfo;                  // les interfaces graphique
        @BindView(R.id.text_invite)
        public TextView mMeetingInvite;
        @BindView(R.id.imageButton_supr)
        public ImageButton mDeleteButton;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

