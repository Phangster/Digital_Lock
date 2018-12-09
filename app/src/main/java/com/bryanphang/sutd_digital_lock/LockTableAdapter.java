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
        final SqliteHelper.LockData lockData = sqliteHelper.queryOneRowLock(i);

        final String ownername = lockData.getName();
        final String property = lockData.getProperty();
        final String fromDate = lockData.getFromDate();
        final String toDate = lockData.getToDate();

        lockTableViewHolder.textViewOwnerName.setText(ownername);
        lockTableViewHolder.textViewProperty.setText(property);
        lockTableViewHolder.textViewLockDateFrom.setText(fromDate);
        lockTableViewHolder.textViewLockDateTo.setText(toDate);

//        lockTableViewHolder.unlockButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < sqliteHelper.queryLockTableNumRows(); i++) {
//                    SqliteHelper.LockData lockData = sqliteHelper.queryLockTableRow(i);
//                    if (lockData.getKeylock().equals(lockid)) {
//                        Intent intent = new Intent(context, BluetoothActivity.class);
//                        intent.putExtra(LockTableAdapter.KEY_PASSWORD, lockData.getKeylockpassword());
//                        Toast.makeText(context, "Password Obtained", Toast.LENGTH_LONG).show();
//                        context.startActivity(intent);
//                    } else {
//                        Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
        lockTableViewHolder.unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BluetoothActivity.class);
                intent.putExtra(LockTableAdapter.KEY_PASSWORD, lockData.getKeylockpassword());
                context.startActivity(intent);
                Toast.makeText(context, "Password Obtained", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) sqliteHelper.queryNumRowsLock();
    }

    class LockTableViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewOwnerName;
        public TextView textViewProperty;
        public TextView textViewLockDateFrom;
        public TextView textViewLockDateTo;

        Button unlockButton;

        //going inside instance that you see of a particular card view
        public LockTableViewHolder(View view){
            super(view);
            textViewOwnerName = view.findViewById(R.id.cardViewTextOwnerName_locktable);
            textViewProperty = view.findViewById(R.id.cardViewTextProperty_locktable);
            textViewLockDateFrom = view.findViewById(R.id.cardViewTextLockDateFrom_locktable);
            textViewLockDateTo = view.findViewById(R.id.cardViewTextLockDateTo_locktable);
            unlockButton = view.findViewById(R.id.buttonunlock_locktable);
        }
    }
}
