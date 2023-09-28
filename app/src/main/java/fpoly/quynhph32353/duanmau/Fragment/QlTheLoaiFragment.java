package fpoly.quynhph32353.duanmau.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Adapter.TheLoaiAdapter;
import fpoly.quynhph32353.duanmau.Dao.TheLoaiDao;
import fpoly.quynhph32353.duanmau.Model.TheLoai;
import fpoly.quynhph32353.duanmau.R;


public class QlTheLoaiFragment extends Fragment {
    View view;
    ListView listView;

    TheLoaiDao theLoaiDao;
    ArrayList<TheLoai> list = new ArrayList<>();

    EditText edt_maLoai, edt_tenloai;

    TheLoaiAdapter theLoaiAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_the_loai, container, false);
        listView = view.findViewById(R.id.ListView_TheLoai);
        theLoaiDao = new TheLoaiDao(getContext());
        theLoaiAdapter = new TheLoaiAdapter(getContext(), list);
        listView.setAdapter(theLoaiAdapter);
        view.findViewById(R.id.fab_TheLoai).setOnClickListener(v -> {
            showAddOrEditDialog_Tl(getContext(), 0, null);
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TheLoai theLoai = list.get(position);
                showAddOrEditDialog_Tl(getContext(), 1, theLoai);
                return false;
            }
        });
        update_list();
        return view;
    }

    protected void showAddOrEditDialog_Tl(Context context, int type, TheLoai theLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_theloai, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        edt_maLoai = view1.findViewById(R.id.edt_maLoai);
        edt_tenloai = view1.findViewById(R.id.edt_tenLoai);
        edt_maLoai.setEnabled(false);

        if (type == 1) {
            edt_maLoai.setText(String.valueOf(theLoai.getMaLoai()));
            edt_tenloai.setText(theLoai.getTenLoai());
        }

        view1.findViewById(R.id.btnCancle_ql_TheLoai).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        view1.findViewById(R.id.btnSave_ql_TheLoai).setOnClickListener(v -> {
            String tenLoai = edt_tenloai.getText().toString().trim();
            if (tenLoai.isEmpty()) {
                Toast.makeText(context, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            } else {
                if (type == 0) {
                    TheLoai newTheLoai = new TheLoai();
                    newTheLoai.setTenLoai(tenLoai);
                    if (theLoaiDao.insertData(newTheLoai)) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        list.add(newTheLoai);
                        theLoaiAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    theLoai.setTenLoai(tenLoai);
                    if (theLoaiDao.Update(theLoai)) {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        update_list();
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

    public void update_list() {
        list.clear();
        list.addAll(theLoaiDao.selectAll());
        theLoaiAdapter.notifyDataSetChanged();
    }
}
