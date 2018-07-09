package com.jobdetails.shant.getjobdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


//Adapter Class for recyclerview for displaying Test Details on main screen
class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder>{

    private ArrayList<PrintResponse> printResponses;
    private Context context;

    ResponseAdapter(Context context, ArrayList<PrintResponse> printResponses)
    {
        this.context=context;
        this.printResponses=printResponses;
    }

    @Override
    public ResponseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.test_response,parent, false);
        return  new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ResponseAdapter.ViewHolder holder, int position) {

        PrintResponse printResponse = printResponses.get(position);
        holder.tv_ex_id.setText(String.valueOf(printResponse.getDisplay_ex_id()));
        holder.tv_test_id.setText(String.valueOf(printResponse.getDisplay_test_id()));
        holder.tv_test_name.setText(printResponse.getDisplay_test_name());
    }

    @Override
    public int getItemCount() {
        return printResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ex_id,tv_test_id,tv_test_name;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_ex_id = itemView.findViewById(R.id.ex_id);
            tv_test_id = itemView.findViewById(R.id.testcase_id);
            tv_test_name = itemView.findViewById(R.id.testcase_name);

        }
    }
}
