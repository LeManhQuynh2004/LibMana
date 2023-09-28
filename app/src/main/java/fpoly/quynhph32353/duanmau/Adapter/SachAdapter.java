package fpoly.quynhph32353.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Dao.SachDao;
import fpoly.quynhph32353.duanmau.Interface.OnItemClickListener;
import fpoly.quynhph32353.duanmau.Model.Sach;
import fpoly.quynhph32353.duanmau.R;

//public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
//    private Context context;
//    private ArrayList<Sach> list;
//    private SachDao sachDao;
//
//    private OnItemClickListener itemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.itemClickListener = listener;
//    }
//
//    public SachAdapter(Context context, ArrayList<Sach> list) {
//        this.context = context;
//        this.list = list;
//        sachDao = new SachDao(context);
//    }
//
//    @NonNull
//    @Override
//    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);
//        return new SachViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
//        if (holder != null) {
//            holder.txt_maSach.setText(String.valueOf(list.get(position).getMaSach()));
//            holder.txt_tenSach.setText(list.get(position).getTenSach());
//            holder.txt_giaSach.setText(String.valueOf(list.get(position).getGiaThue()));
//            holder.txt_maLoai.setText(String.valueOf(list.get(position).getMaLoai()));
//            holder.imgDelete.setOnClickListener(v -> {
//                showDeleteDialog(position);
//            });
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clickListener(position);
//                }
//            });
//        }
//    }
//
//    private void clickListener(int position) {
//        if (itemClickListener != null) {
//            itemClickListener.onSachItemClick(position);
//        }
//    }
//

//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    static class SachViewHolder extends RecyclerView.ViewHolder {
//        TextView txt_maSach, txt_tenSach, txt_giaSach, txt_maLoai;
//        ImageView imgDelete;
//
//        public SachViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txt_maSach = itemView.findViewById(R.id.txt_maSach_item_Sach);
//            txt_tenSach = itemView.findViewById(R.id.txt_tenSach_item_Sach);
//            txt_giaSach = itemView.findViewById(R.id.txt_giaSach_item_Sach);
//            txt_maLoai = itemView.findViewById(R.id.txt_maLoai_item_Sach);
//            imgDelete = itemView.findViewById(R.id.img_Delete_Sach);
//        }
//    }
//}

public class SachAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Sach> list;
    private SachDao sachDao;

    //    private OnItemClickListener itemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.itemClickListener = listener;
//    }
    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        sachDao = new SachDao(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class SachViewHolder {
        TextView txt_maSach, txt_tenSach, txt_giaSach, txt_maLoai;
        ImageView imgDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SachViewHolder sachViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);
            sachViewHolder = new SachViewHolder();
            sachViewHolder.txt_maSach = convertView.findViewById(R.id.txt_maSach_item_Sach);
            sachViewHolder.txt_tenSach = convertView.findViewById(R.id.txt_tenSach_item_Sach);
            sachViewHolder.txt_giaSach = convertView.findViewById(R.id.txt_giaSach_item_Sach);
            sachViewHolder.txt_maLoai = convertView.findViewById(R.id.txt_maLoai_item_Sach);
            sachViewHolder.imgDelete = convertView.findViewById(R.id.img_Delete_Sach);
            convertView.setTag(sachViewHolder);
        } else {
            sachViewHolder = (SachViewHolder) convertView.getTag();
        }
        Sach sach = list.get(position);
        sachViewHolder.txt_maSach.setText("Mã sách :" + sach.getMaSach());
        sachViewHolder.txt_tenSach.setText("Tên sách :" + sach.getTenSach());
        sachViewHolder.txt_giaSach.setText("Giá thuê :" + sach.getGiaThue());
        sachViewHolder.txt_maLoai.setText("Mã loại :" + sach.getMaLoai());
        sachViewHolder.imgDelete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        return convertView;
    }

    private void showDeleteDialog(int position) {
        Sach sach = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.baseline_warning_amber_24);
        builder.setTitle("Xác nhận xóa sách");
        builder.setMessage("Bạn có chắc chắn muốn xóa sách " + sach.getTenSach() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (sachDao.Delete(sach)) {
                        list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(context, "Xóa thất bại có liên kết", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
