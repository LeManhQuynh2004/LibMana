package fpoly.quynhph32353.duanmau.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Adapter.ThanhVienAdapter;
import fpoly.quynhph32353.duanmau.Dao.ThanhVienDao;
import fpoly.quynhph32353.duanmau.Model.ThanhVien;
import fpoly.quynhph32353.duanmau.R;

public class QlThanhVienFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;

    ListView listView;
    private EditText edt_maNV, edt_hoTen, edt_namSinh;

    private ThanhVienDao thanhVienDao;
    private ThanhVienAdapter thanhVienAdapter;
    private ArrayList<ThanhVien> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ql_nhan_vien, container, false);
//        recyclerView = view.findViewById(R.id.RecyclerView_NhanVien);
        listView = view.findViewById(R.id.ListView_ThanhVien);
        thanhVienDao = new ThanhVienDao(getContext());
        list.addAll(thanhVienDao.selectAll());

        thanhVienAdapter = new ThanhVienAdapter(getContext(), list);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(thanhVienAdapter);
        listView.setAdapter(thanhVienAdapter);

        view.findViewById(R.id.fab_NhanVien).setOnClickListener(v -> {
            showAddOrEditDialog(getContext(), 0, null);
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAddOrEditDialog(getContext(), 1, list.get(position));
                return false;
            }
        });
        return view;
    }

    protected void showAddOrEditDialog(Context context, int type, ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_thanhvien, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();

        edt_maNV = view1.findViewById(R.id.edt_maNV);
        edt_hoTen = view1.findViewById(R.id.edt_hoTen);
        edt_namSinh = view1.findViewById(R.id.edt_namSinh);
        edt_maNV.setEnabled(false);



        if (type != 0) {
            edt_maNV.setText(String.valueOf(thanhVien.getMaTV()));
            edt_hoTen.setText(thanhVien.getHoTen());
            edt_namSinh.setText(thanhVien.getNamSinh());
        }

        view1.findViewById(R.id.btnCancle_ql_NhanVien).setOnClickListener(v -> alertDialog.dismiss());

        view1.findViewById(R.id.btnSave_ql_NhanVien).setOnClickListener(v -> {
            String hoTen = edt_hoTen.getText().toString();
            String namSinh = edt_namSinh.getText().toString();

            if (validate(hoTen, namSinh)) {
                if (type == 0) {
                    ThanhVien newthanhvien = new ThanhVien();
                    newthanhvien.setHoTen(hoTen);
                    newthanhvien.setNamSinh(namSinh);
                    if (thanhVienDao.insertData(newthanhvien)) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        list.add(newthanhvien);
                        updateList();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    thanhVien.setHoTen(hoTen);
                    thanhVien.setNamSinh(namSinh);

                    if (thanhVienDao.Update(thanhVien)) {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        updateList();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public boolean validate(String hoTen, String namSinh) {
        if (hoTen.isEmpty() || namSinh.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng cung cấp đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void updateList() {
        list.clear();
        list.addAll(thanhVienDao.selectAll());
        thanhVienAdapter.notifyDataSetChanged();
    }
}
