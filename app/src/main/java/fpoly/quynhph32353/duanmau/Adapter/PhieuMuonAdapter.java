package fpoly.quynhph32353.duanmau.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Dao.PhieuMuonDao;
import fpoly.quynhph32353.duanmau.Dao.SachDao;
import fpoly.quynhph32353.duanmau.Dao.ThanhVienDao;
import fpoly.quynhph32353.duanmau.Model.PhieuMuon;
import fpoly.quynhph32353.duanmau.Model.Sach;
import fpoly.quynhph32353.duanmau.Model.ThanhVien;
import fpoly.quynhph32353.duanmau.R;

public class PhieuMuonAdapter extends BaseAdapter {
    Context context;
    ArrayList<PhieuMuon> list;
    PhieuMuonDao phieuMuonDao;

    SachDao sachDao;
    ThanhVienDao thanhVienDao;

    private static final String TAG = "PhieuMuonAdapter";

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        phieuMuonDao = new PhieuMuonDao(context);
        thanhVienDao = new ThanhVienDao(context);
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

    private class PhieuMuonViewHolder {
        TextView txt_maPM, txt_tenTV, txt_tenSach, txt_ngayThue, txt_tienThue, txt_trangThai;
        ImageView imgDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhieuMuonViewHolder phieuMuonViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_phieumuon, parent, false);
            phieuMuonViewHolder = new PhieuMuonViewHolder();
            phieuMuonViewHolder.txt_maPM = convertView.findViewById(R.id.txt_maPM_item_phieuMuon);
            phieuMuonViewHolder.txt_tenSach = convertView.findViewById(R.id.txt_tenSach_item_phieuMuon);
            phieuMuonViewHolder.txt_ngayThue = convertView.findViewById(R.id.txt_ngayThue_item_phieuMuon);
            phieuMuonViewHolder.txt_trangThai = convertView.findViewById(R.id.txt_trangThai_item_phieuMuon);
            phieuMuonViewHolder.txt_tenTV = convertView.findViewById(R.id.txt_thanhVien_item_phieuMuon);
            phieuMuonViewHolder.txt_tienThue = convertView.findViewById(R.id.txt_giaThue_item_phieuMuon);
            phieuMuonViewHolder.imgDelete = convertView.findViewById(R.id.img_Delete_PhieuMuon);
            convertView.setTag(phieuMuonViewHolder);
        } else {
            phieuMuonViewHolder = (PhieuMuonViewHolder) convertView.getTag();
        }

        PhieuMuon phieuMuon = list.get(position);
        Log.e(TAG, "getView Ma Sach: "+phieuMuon.getMaSach());
        Log.e(TAG, "getView Ma ThanhVien: "+phieuMuon.getMaTV());
        phieuMuonViewHolder.txt_maPM.setText("Mã Phiếu :" + phieuMuon.getMaPM());
        Sach sach = sachDao.selectID(String.valueOf(phieuMuon.getMaSach()));
        Log.e(TAG, "Ma Sach: "+phieuMuon.getMaSach());
        if (sach != null) {
            phieuMuonViewHolder.txt_tenSach.setText("Tên Sách :" + sach.getTenSach());
            Log.e(TAG, "Ten Sach: "+sach.getTenSach());
        }
        Log.e(TAG, "Ma thanh Vien: " + phieuMuon.getMaTV());
        ThanhVien thanhVien = thanhVienDao.selectID(String.valueOf(phieuMuon.getMaTV()));
        if (thanhVien != null) {
            phieuMuonViewHolder.txt_tenTV.setText("Tên Thành Viên :" + thanhVien.getHoTen());
        }
        phieuMuonViewHolder.txt_tienThue.setText("Tiền thuê :" + phieuMuon.getTienThue());
        phieuMuonViewHolder.txt_ngayThue.setText("Ngày thuê :" + phieuMuon.getNgay());

        if (phieuMuon.getTraSach() == 0) {
            phieuMuonViewHolder.txt_trangThai.setTextColor(Color.BLUE);
            phieuMuonViewHolder.txt_trangThai.setText("Đã trả sách");
        } else {
            phieuMuonViewHolder.txt_trangThai.setTextColor(Color.RED);
            phieuMuonViewHolder.txt_trangThai.setText("Chưa trả sách");
        }
        phieuMuonViewHolder.imgDelete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        return convertView;
    }

    public void showDeleteDialog(int position) {
        PhieuMuon phieuMuon = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.baseline_warning_amber_24);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên " + phieuMuon.getMaPM() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (phieuMuonDao.delete(phieuMuon)) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list.remove(phieuMuon);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
