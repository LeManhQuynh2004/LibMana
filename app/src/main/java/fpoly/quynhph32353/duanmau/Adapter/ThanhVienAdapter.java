package fpoly.quynhph32353.duanmau.Adapter;

import android.app.AlertDialog;
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

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Dao.ThanhVienDao;
import fpoly.quynhph32353.duanmau.Model.ThanhVien;
import fpoly.quynhph32353.duanmau.R;

//public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
//    Context context;
//    ArrayList<ThanhVien> list;
//
//    ThanhVienDao thanhVienDao;
//    EditText edt_maNV, edt_hoTen, edt_namsinh;
//
//    QlNhanVienFragment fragment;
//
//    String hoTen, namSinh;
//    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
//        this.context = context;
//        this.list = list;
//        fragment = new QlNhanVienFragment();
//        thanhVienDao = new ThanhVienDao(context);
//    }
//
//    @NonNull
//    @Override
//    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_thanhvien, parent, false);
//        return new ThanhVienViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
//        holder.txt_hoTen.setText("Họ tên NV: " + list.get(position).getHoTen());
//        holder.txt_maNV.setText("Mã NV: " + list.get(position).getMaTV());
//        holder.txt_namSinh.setText("Năm Sinh NV: " + list.get(position).getNamSinh());
//        holder.imgDelete.setOnClickListener(v -> {
//            DeleteThanhVien(position);
//        });
//        holder.itemView.setOnLongClickListener(v -> {
//            UpdateThanhVien(position);
//            return false;
//        });
//    }
//
//    public void DeleteThanhVien(int position) {
//        ThanhVien thanhVien = list.get(position);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setIcon(R.drawable.baseline_warning_amber_24);
//        builder.setTitle("Thông báo");
//        builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên " + thanhVien.getHoTen() + " không ?");
//        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (thanhVienDao.Delete(thanhVien)) {
//                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                    list.remove(thanhVien);
//                    notifyDataSetChanged();
//                } else {
//                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        builder.setNegativeButton("Hủy", null);
//        builder.show();
//    }
//
//    public void UpdateThanhVien(int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_thanhvien, null);
//        builder.setView(view1);
//        AlertDialog alertDialog = builder.create();
//        edt_maNV = view1.findViewById(R.id.edt_maNV);
//        edt_hoTen = view1.findViewById(R.id.edt_hoTen);
//        edt_namsinh = view1.findViewById(R.id.edt_namSinh);
//        edt_maNV.setEnabled(false);
//
//        edt_maNV.setText(String.valueOf(list.get(position).getMaTV()));
//        edt_hoTen.setText(list.get(position).getHoTen());
//        edt_namsinh.setText(String.valueOf(list.get(position).getNamSinh()));
//
//        view1.findViewById(R.id.btnCancle_ql_NhanVien).setOnClickListener(view -> {
//            alertDialog.dismiss();
//        });
//        view1.findViewById(R.id.btnSave_ql_NhanVien).setOnClickListener(view -> {
//            hoTen = edt_hoTen.getText().toString();
//            namSinh = edt_namsinh.getText().toString();
//            if (fragment.validate(hoTen,namSinh)) {
//                ThanhVien thanhVien = new ThanhVien();
//                thanhVien.setHoTen(hoTen);
//                thanhVien.setNamSinh(namSinh);
//                if (thanhVienDao.Update(thanhVien)) {
//                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
//                    list.set(position, thanhVien);
//                    notifyDataSetChanged();
//                    alertDialog.dismiss();
//                } else {
//                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.show();
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class ThanhVienViewHolder extends RecyclerView.ViewHolder {
//
//        TextView txt_maNV, txt_hoTen, txt_namSinh;
//        ImageView imgDelete;
//
//        public ThanhVienViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txt_maNV = itemView.findViewById(R.id.txt_ma_item_nhanVien);
//            txt_hoTen = itemView.findViewById(R.id.txt_hoTen_item_nhanVien);
//            txt_namSinh = itemView.findViewById(R.id.txt_namSinh_item_nhanVien);
//            imgDelete = itemView.findViewById(R.id.img_Delete_ThanhVien);
//        }
//    }
//}
public class ThanhVienAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThanhVien> list;
    ThanhVienDao thanhVienDao;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        thanhVienDao = new ThanhVienDao(context);
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

    private class ThanhVienViewHolder {
        TextView txt_maTV, txt_hoTen, txt_namSinh;
        ImageView imgDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThanhVienViewHolder thanhVienViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_thanhvien, parent, false);
            thanhVienViewHolder = new ThanhVienViewHolder();
            thanhVienViewHolder.txt_maTV = convertView.findViewById(R.id.txt_ma_item_nhanVien);
            thanhVienViewHolder.txt_hoTen = convertView.findViewById(R.id.txt_hoTen_item_nhanVien);
            thanhVienViewHolder.txt_namSinh = convertView.findViewById(R.id.txt_namSinh_item_nhanVien);
            thanhVienViewHolder.imgDelete = convertView.findViewById(R.id.img_Delete_ThanhVien);
            convertView.setTag(thanhVienViewHolder);
        } else {
            thanhVienViewHolder = (ThanhVienViewHolder) convertView.getTag();
        }
        ThanhVien thanhVien = list.get(position);
        thanhVienViewHolder.txt_hoTen.setText("Họ tên NV: " + thanhVien.getHoTen());
        thanhVienViewHolder.txt_maTV.setText("Mã NV: " + thanhVien.getMaTV());
        thanhVienViewHolder.txt_namSinh.setText("Năm Sinh NV: " + thanhVien.getNamSinh());
        thanhVienViewHolder.imgDelete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        return convertView;
    }

    public void showDeleteDialog(int position) {
        ThanhVien thanhVien = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.baseline_warning_amber_24);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên " + thanhVien.getHoTen() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (thanhVienDao.Delete(thanhVien)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(thanhVien);
                        notifyDataSetChanged();
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