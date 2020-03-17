package com.example.tahubakso.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahubakso.R;
import com.example.tahubakso.tahuproject.HeaderNotaModel;

import java.util.List;

public class adapter_list_item_nota extends RecyclerView.Adapter<adapter_list_item_nota.Holder> {
    private List<HeaderNotaModel> arrayList;
    private Context context;
    private CustgroupListener listener;
    public adapter_list_item_nota(Context context, List<HeaderNotaModel> arrayList , CustgroupListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener=listener;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_spa, parent, false);
        return new Holder(view);
    }
    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final HeaderNotaModel data_item = arrayList.get(position);
        holder.textview_item_spa.setText(data_item.getNamacustomer());
        holder.textview_description_item.setText("kode nota : "+data_item.getCodenota()+"");
//        holder.txtsatuan.setText("harga satuan : "+data_item.getHargabarang()+"");
        //holder.textview_cost_item.setText("kode barang : "+data_item.getId());
//        Log.d("kalian", "onBindViewHolder: "+(Integer.parseInt(data_item.getHargabarang())*Integer.parseInt(data_item.getJumlahbarang())));
//        holder.textview_cost_item.setText("subtotal : "+(Integer.parseInt(data_item.getHargabarang())*Integer.parseInt(data_item.getJumlahbarang())));
    }

    @Override
    public int getItemCount() {
        return arrayList == null? 0 : arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView textview_item_spa,textview_description_item,textview_cost_item,txtsatuan;
        CardView cardku;
        public Holder(View itemView) {
            super(itemView);
            textview_item_spa = (TextView) itemView.findViewById(R.id.textview_name_item);
            cardku = (CardView) itemView.findViewById(R.id.cardku);
            textview_description_item = (TextView)itemView.findViewById(R.id.textview_description_item);
            textview_cost_item = (TextView)itemView.findViewById(R.id.textview_cost_item);
            textview_cost_item.setVisibility(View.GONE);
            txtsatuan = (TextView)itemView.findViewById(R.id.txtsatuan);
            txtsatuan.setVisibility(View.GONE);
            cardku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickListener(getAdapterPosition());
                }
            });
        }
    }

    public interface CustgroupListener {
        void onClickListener(int position);
        void onInfoClickListener(int position);
    }

}
