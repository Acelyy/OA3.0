package cn.invonate.ygoa3.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.invonate.ygoa3.Entry.Salary;
import cn.invonate.ygoa3.R;

/**
 * Created by liyangyang on 2018/2/27.
 */

public class SalaryAdapter extends RecyclerView.Adapter<SalaryAdapter.ViewHolder> {

    private List<Salary.DataBean> data;
    private LayoutInflater inflater;

    public SalaryAdapter(List<Salary.DataBean> data, Context context) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public SalaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_salary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SalaryAdapter.ViewHolder holder, int position) {
        holder.key.setText(data.get(position).getSailName());
        holder.value.setText(data.get(position).getSail());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.key)
        TextView key;
        @BindView(R.id.value)
        TextView value;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
