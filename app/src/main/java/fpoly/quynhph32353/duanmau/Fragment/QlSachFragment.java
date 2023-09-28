package fpoly.quynhph32353.duanmau.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Adapter.TheLoaiSpinner;
import fpoly.quynhph32353.duanmau.Adapter.SachAdapter;
import fpoly.quynhph32353.duanmau.Dao.SachDao;
import fpoly.quynhph32353.duanmau.Dao.TheLoaiDao;
import fpoly.quynhph32353.duanmau.Model.Sach;
import fpoly.quynhph32353.duanmau.Model.TheLoai;
import fpoly.quynhph32353.duanmau.R;

public class QlSachFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    SachAdapter sachAdapter;
    ArrayList<Sach> list = new ArrayList<>();
    ArrayList<TheLoai> listTheLoai = new ArrayList<>();
    SachDao sachDao;
    TheLoaiDao theLoaiDao;

    TheLoaiSpinner theLoaiSpinner;

    ListView listView;
    EditText edt_maSach, edt_tenSach, edt_giaSach;
    Spinner spn_maLoai;
    int selectedPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sach, container, false);
        listView = view.findViewById(R.id.ListView_Sach);
//        recyclerView = view.findViewById(R.id.RecyclerView_Sach);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(sachAdapter);
        sachDao = new SachDao(getContext());
        list = sachDao.selectAll();
        sachAdapter = new SachAdapter(getContext(), list);
        listView.setAdapter(sachAdapter);
        updateList();
        view.findViewById(R.id.fab_Sach).setOnClickListener(v -> {
            showAddOrEditDialog(getContext(), 0, null);
        });

//        sachAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onSachItemClick(int position) {
//                showAddOrEditDialog(getContext(), 1, list.get(position));
//            }
//        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAddOrEditDialog(getContext(), 1, list.get(position));
                return false;
            }
        });
        return view;
    }

    private void showAddOrEditDialog(Context context, int type, Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_sach, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        edt_maSach = view1.findViewById(R.id.edt_maSach);
        edt_tenSach = view1.findViewById(R.id.edt_tenSach);
        edt_giaSach = view1.findViewById(R.id.edt_giaSach);
        spn_maLoai = view1.findViewById(R.id.Spinner_TheLoai);
        edt_maSach.setEnabled(false);

        theLoaiDao = new TheLoaiDao(getContext());
        listTheLoai = theLoaiDao.selectAll();
        theLoaiSpinner = new TheLoaiSpinner(getContext(), listTheLoai);
        spn_maLoai.setAdapter(theLoaiSpinner);

        if (type != 0) {
            edt_maSach.setText(String.valueOf(sach.getMaSach()));
            edt_giaSach.setText(String.valueOf(sach.getGiaThue()));
            edt_tenSach.setText(String.valueOf(sach.getTenSach()));

            for (int i = 0; i < listTheLoai.size(); i++) {
                if (sach.getMaLoai() == listTheLoai.get(i).getMaLoai()) {
                    selectedPosition = i;
                }
            }

            spn_maLoai.setSelection(selectedPosition);
        }

        view1.findViewById(R.id.btnCancle_ql_Sach).setOnClickListener(v -> alertDialog.dismiss());
        view1.findViewById(R.id.btnSave_ql_Sach).setOnClickListener(v -> {
            String tenSach = edt_tenSach.getText().toString();
            String giaSach = edt_giaSach.getText().toString();
            int maLoaiSach = listTheLoai.get(spn_maLoai.getSelectedItemPosition()).getMaLoai();

            if (type == 0) {
                if (validate(tenSach, giaSach)) {
                    Sach newSach = new Sach();
                    newSach.setTenSach(tenSach);
                    newSach.setGiaThue(Integer.parseInt(giaSach));
                    newSach.setMaLoai(maLoaiSach);

                    if (sachDao.insertData(newSach)) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        list.add(newSach);
                        alertDialog.dismiss();
                        updateList();
                        sachAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                sach.setTenSach(tenSach);
                sach.setGiaThue(Integer.parseInt(giaSach));
                sach.setMaLoai(maLoaiSach);

                if (sachDao.Update(sach)) {
                    updateList();
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    sachAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean validate(String tenSach, String giaSach) {
        if (tenSach.isEmpty() || giaSach.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng cung cấp đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void updateList() {
        list.clear();
        list.addAll(sachDao.selectAll());
        sachAdapter.notifyDataSetChanged();
    }
}
