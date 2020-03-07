package com.example.employeeattendance.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.employeeattendance.Activity.MainActivity;
import com.example.employeeattendance.R;
import com.example.employeeattendance.ViewModel.AttendanceViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class AttendanceLogAdapter extends RecyclerView.Adapter<AttendanceLogAdapter.MyViewHolder> {

    private List<AttendanceLogItem> loglist;
    Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,year,time;
        public ConstraintLayout cl;


        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.textView1);
            year =  view.findViewById(R.id.textView2);
            time = view.findViewById(R.id.textView3);
            cl = view.findViewById(R.id.item);
        }
    }


    public AttendanceLogAdapter(List<AttendanceLogItem> loglist) {
        this.loglist = loglist;
        hasStableIds();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AttendanceLogItem TT = loglist.get(position);

        holder.name.setText(TT.getName());
        holder.year.setText(TT.getMode());
        holder.time.setText(TT.getTime());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog builder = new MaterialAlertDialogBuilder(activity)
                        .setTitle("Filter By")
                        .setMessage("Do you want to delete this item?")
                        .setNegativeButton("Cancel",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView time=holder.itemView.findViewById(R.id.textView3);
                                AttendanceViewModel.delete(time.getText().toString());
                                loglist.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if(loglist!=null)
            return loglist.size();
        else
            return 0;
    }

    public void setRows(List<AttendanceLogItem> loglist, Activity activity){
        this.loglist = loglist;
        this.activity=activity;
        notifyDataSetChanged();
    }
}
