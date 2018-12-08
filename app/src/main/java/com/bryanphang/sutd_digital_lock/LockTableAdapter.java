package com.bryanphang.sutd_digital_lock;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LockTableAdapter extends RecyclerView.Adapter<LockTableAdapter.LockTableViewHolder> {

    LayoutInflater mInflater;
    Context context;
    SqliteHelper sqliteHelper;


    public static final String KEY_LOCK = "locknum";
    public static final String KEY_LOCK_FROMDATE = "datetimefrom";
    public static final String KEY_LOCK_TODATE = "datetimeto";
    public static final String KEY_PASSWORD = "lock_password";

    public LockTableAdapter(Context context, SqliteHelper sqliteHelper) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.sqliteHelper = sqliteHelper;
    }

    @NonNull
    @Override
    //take one row of data, inflate the layout given
    public LockTableAdapter.LockTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.layout_locktable, viewGroup, false);
        //passed into constructor of LockTableViewHolder (below)
        return new LockTableAdapter.LockTableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LockTableAdapter.LockTableViewHolder lockTableViewHolder, int i) {
        //query the layout first - which cardview are we referring to?
        final SqliteHelper.CharaData charaData = sqliteHelper.queryOneRow(i);

        final String lockid = charaData.getLockid();
        final String fromDate = charaData.getFromDate();
        final String toDate = charaData.getToDate();

        lockTableViewHolder.textViewLockid.setText(lockid);
        lockTableViewHolder.textViewDateFrom.setText(fromDate);
        lockTableViewHolder.textViewDateTo.setText(toDate);

        lockTableViewHolder.unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BluetoothActivity.class);
                intent.putExtra(LockTableAdapter.KEY_PASSWORD, charaData.getPassword());
                context.startActivity(intent);
                Toast.makeText(context, "Password Obtained", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) sqliteHelper.queryNumRows();
    }

    class LockTableViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewLockid;
        public TextView textViewDateFrom;
        public TextView textViewDateTo;

        Button unlockButton;

        //going inside instance that you see of a particular card view
        public LockTableViewHolder(View view){
            super(view);
            textViewLockid = view.findViewById(R.id.cardViewTextLockid_locktable);
            textViewDateFrom = view.findViewById(R.id.cardViewTextDateFrom_locktable);
            textViewDateTo = view.findViewById(R.id.cardViewTextDateTo_locktable);
            unlockButton = view.findViewById(R.id.buttonunlock_locktable);
        }
    }
}
