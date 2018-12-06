package com.bryanphang.sutd_digital_lock;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SQLiteListAdapter extends RecyclerView.Adapter<SQLiteListAdapter.CharaViewHolder> {

    LayoutInflater mInflater;
    Context context;
    SqliteHelper sqliteHelper;

    public static final String name_entry = "Name";
    public static final String lockid_entry = "Lock ID";
    public static final String fromDate_entry = "From Date";
    public static final String toDate_entry = "To Date";

    public SQLiteListAdapter(Context context, SqliteHelper sqliteHelper) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.sqliteHelper = sqliteHelper;
    }

    @NonNull
    @Override
    //take one row of data, inflate the layout given
    public CharaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.layout, viewGroup, false);
        //passed into constructor of charaviewholder (below)
        return new CharaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharaViewHolder charaViewHolder, int i) {
        //query the layout first - which cardview are we referring to?
        SqliteHelper.CharaData charaData = sqliteHelper.queryOneRow(i);

        final String name = charaData.getName();
        final String lockid = charaData.getLockid();
        final String fromDate = charaData.getFromDate();
        final String toDate = charaData.getToDate();

        charaViewHolder.textViewName.setText(name);
        charaViewHolder.textViewLockid.setText(lockid);
        charaViewHolder.textViewDateFrom.setText(fromDate);
        charaViewHolder.textViewDateTo.setText(toDate);

        charaViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra(SQLiteListAdapter.name_entry, name);
                intent.putExtra(SQLiteListAdapter.lockid_entry, lockid);
                intent.putExtra(SQLiteListAdapter.fromDate_entry, fromDate);
                intent.putExtra(SQLiteListAdapter.toDate_entry, toDate);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) sqliteHelper.queryNumRows();
    }

    class CharaViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewLockid;
        public TextView textViewDateFrom;
        public TextView textViewDateTo;
        Button editButton;

        //going inside instance that you see of a particular card view
        public CharaViewHolder(View view){
            super(view);
            textViewName = view.findViewById(R.id.cardViewTextName);
            textViewLockid = view.findViewById(R.id.cardViewTextLockid);
            textViewDateFrom = view.findViewById(R.id.cardViewTextDateFrom);
            textViewDateTo = view.findViewById(R.id.cardViewTextDateTo);
            editButton = view.findViewById(R.id.buttonedit);
        }
    }
}
