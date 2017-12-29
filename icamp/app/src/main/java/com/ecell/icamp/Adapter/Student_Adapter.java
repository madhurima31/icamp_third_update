package com.ecell.icamp.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecell.icamp.Activity.Dashboard_Admin;
import com.ecell.icamp.Activity.Dashboard_Student;
import com.ecell.icamp.Fragment.Student_Details;
import com.ecell.icamp.R;

import java.util.List;

/**
 * Created by 1505560 on 09-Dec-17.
 */

public class Student_Adapter extends RecyclerView.Adapter<Student_Adapter.ViewHolder> {

    private LinearLayout st_card;
    private TextView st_id, st_name;
    private List<String> li_id, li_name;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            st_card = (LinearLayout)view.findViewById(R.id.st_card);
            st_id = (TextView)view.findViewById(R.id.st_id);
            st_name = (TextView)view.findViewById(R.id.st_name);
        }
    }

    public Student_Adapter(List<String> li_id, List<String> li_name) {
        this.li_id = li_id;
        this.li_name = li_name;
    }

    @Override
    public Student_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_student, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        st_id.setText(li_id.get(position));
        st_name.setText(li_name.get(position));

        st_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dashboard_Admin.fragmentChange(3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return li_id.size();
    }
}
