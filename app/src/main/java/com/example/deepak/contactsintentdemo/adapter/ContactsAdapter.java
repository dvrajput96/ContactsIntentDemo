package com.example.deepak.contactsintentdemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepak.contactsintentdemo.R;
import com.example.deepak.contactsintentdemo.activity.MainActivity;
import com.example.deepak.contactsintentdemo.model.ClsContacts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Deepak on 07-Apr-18.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<ClsContacts> clsContactsList;
    private Context context;
    private MainActivity mainActivity;

    public ContactsAdapter(List<ClsContacts> clsContactsList, Context context) {
        this.clsContactsList = clsContactsList;
        this.context = context;
        this.mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ClsContacts clsContacts = clsContactsList.get(position);
        if (clsContacts.getContactName() != null) {
            holder.txtPersonName.setText(clsContacts.getContactName());
        } else {
            holder.txtPersonName.setText("No Name");
        }
        if (clsContacts.getContactNumber() != null) {
            holder.txtPersonContactNumber.setText(clsContacts.getContactNumber());
        } else {
            holder.txtPersonContactNumber.setText("");
        }
        holder.txtPersonContactNumber.setText(clsContacts.getContactNumber());
        if (clsContacts.getPic() != null) {
            holder.ivPersonImage.setImageURI(Uri.parse(clsContacts.getPic()));
        } else {
            holder.ivPersonImage.setImageResource(R.drawable.ic_person_black_24dp);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mainActivity.onLongClick(position,clsContacts.getUid());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return clsContactsList.size();
    }

    public void add(List<ClsContacts> clsContactsList) {
        this.clsContactsList = clsContactsList;
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        clsContactsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, clsContactsList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPersonImage)
        ImageView ivPersonImage;

        @BindView(R.id.txtPersonName)
        TextView txtPersonName;

        @BindView(R.id.txtPersonContactNumber)
        TextView txtPersonContactNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
