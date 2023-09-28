package fpoly.quynhph32353.duanmau.Fragment;

import static java.time.MonthDay.now;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import fpoly.quynhph32353.duanmau.Adapter.PhieuMuonAdapter;
import fpoly.quynhph32353.duanmau.Adapter.SachSpinner;
import fpoly.quynhph32353.duanmau.Adapter.ThanhVienAdapter;
import fpoly.quynhph32353.duanmau.Adapter.ThanhVienSpinner;
import fpoly.quynhph32353.duanmau.Dao.PhieuMuonDao;
import fpoly.quynhph32353.duanmau.Dao.SachDao;
import fpoly.quynhph32353.duanmau.Dao.ThanhVienDao;
import fpoly.quynhph32353.duanmau.Model.PhieuMuon;
import fpoly.quynhph32353.duanmau.Model.Sach;
import fpoly.quynhph32353.duanmau.Model.ThanhVien;
import fpoly.quynhph32353.duanmau.R;

public class QlPhieuMuonFragment extends Fragment {

    private View view;
    private ListView listView;
    private PhieuMuonDao phieuMuonDao;
    private SachDao sachDao;
    private ArrayList<PhieuMuon> list = new ArrayList<>();
    ArrayList<Sach> list_Sach = new ArrayList<>();
    private int maSach, tienThue, maThanhVien;
    private SachSpinner spinner_sach;
    private ThanhVienSpinner thanhVienSpinner;

    private int selectedPositionTV, selectedPositionSach;
    ArrayList<ThanhVien> list_ThanhVien = new ArrayList<>();
    private ThanhVienDao thanhVienDao;
    private EditText edt_maPM;
    private TextView txt_ngayThue_PM, txt_tienThue_PM;
    private CheckBox checkStatus;
    private Spinner spinner_thanhVien, spinner_Sach;
    private PhieuMuonAdapter phieuMuonAdapter;

    private static final String TAG = "QlPhieuMuon";

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ql_phieu_muon, container, false);
        listView = view.findViewById(R.id.ListView_PhieuMuon);
        phieuMuonDao = new PhieuMuonDao(getContext());
        list = phieuMuonDao.selectAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), list);
        listView.setAdapter(phieuMuonAdapter);

        view.findViewById(R.id.fab_phieuMuon).setOnClickListener(v -> {
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

    private void showAddOrEditDialog(Context context, int type, PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_phieumuon, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        edt_maPM = view1.findViewById(R.id.edt_maPM);
        txt_tienThue_PM = view1.findViewById(R.id.txt_tienThue_PM);
        txt_ngayThue_PM = view1.findViewById(R.id.txt_ngayThue_PM);
        checkStatus = view1.findViewById(R.id.chk_status);
        spinner_thanhVien = view1.findViewById(R.id.Spinner_ThanhVien);
        spinner_Sach = view1.findViewById(R.id.Spinner_Sach);
        edt_maPM.setEnabled(false);

        //Spinner Thanh Vien
        thanhVienDao = new ThanhVienDao(getContext());
        list_ThanhVien = thanhVienDao.selectAll();
        thanhVienSpinner = new ThanhVienSpinner(getContext(), list_ThanhVien);
        spinner_thanhVien.setAdapter(thanhVienSpinner);

        //Spinner Sach
        sachDao = new SachDao(getContext());
        list_Sach = sachDao.selectAll();
        spinner_sach = new SachSpinner(getContext(), list_Sach);
        spinner_Sach.setAdapter(spinner_sach);

        if (type != 0) {
            edt_maPM.setText(String.valueOf(phieuMuon.getMaPM()));
            txt_tienThue_PM.setText(String.valueOf(phieuMuon.getTienThue()));
            checkStatus.setChecked(phieuMuon.getTraSach() == 0);

            for (int i = 0; i < list_ThanhVien.size(); i++) {
                if (phieuMuon.getMaTV() == list_ThanhVien.get(i).getMaTV()) {
                    selectedPositionTV = i;
                }
            }
            spinner_thanhVien.setSelection(selectedPositionTV);

            for (int i = 0; i < list_Sach.size(); i++) {
                if (phieuMuon.getMaSach() == list_Sach.get(i).getMaLoai()) {
                    selectedPositionSach = i;
                }
            }
            spinner_Sach.setSelection(selectedPositionSach);

            txt_ngayThue_PM.setText(phieuMuon.getNgay());
            txt_tienThue_PM.setText(String.valueOf(phieuMuon.getTienThue()));
        }

        spinner_thanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = list_ThanhVien.get(position).getMaTV();
                Log.e(TAG, "maThanhVien: " + list_ThanhVien.get(position).getMaTV());
                Toast.makeText(context, "Chọn ThanhVien :" + list_ThanhVien.get(position).getMaTV(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_Sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = list_Sach.get(position).getMaSach();
                Log.e(TAG, "Ma Sach: " + list_Sach.get(position).getMaSach());
                tienThue = list_Sach.get(position).getGiaThue();
                Toast.makeText(context, "Chọn Sach :" + list_Sach.get(position).getMaSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        view1.findViewById(R.id.btnSave_ql_PhieuMuon).setOnClickListener(v -> {
            if (type == 0) {
                PhieuMuon newPhieuMuon = new PhieuMuon();
                newPhieuMuon.setMaSach(maSach);
                newPhieuMuon.setMaTV(maThanhVien);
                newPhieuMuon.setTraSach(checkStatus.isChecked() ? 0 : 1);
                newPhieuMuon.setTienThue(tienThue);
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
                String maTT = sharedPreferences.getString("USERNAME", "");
                newPhieuMuon.setMaTT(maTT);

                Date currentDate = new Date();
                String date = dateFormat.format(currentDate);
                newPhieuMuon.setNgay(date);

                if (phieuMuonDao.insert(newPhieuMuon)) {
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    list.add(newPhieuMuon);
                    updateList();
                    Log.e(TAG, "new MaThanhVien: " + newPhieuMuon.getMaTV());
                    Log.e(TAG, "new MaSach: " + newPhieuMuon.getMaSach());
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                phieuMuon.setTraSach(checkStatus.isChecked() ? 0 : 1);
                phieuMuon.setMaTV(maThanhVien);
                phieuMuon.setMaSach(maSach);

                if(phieuMuonDao.update(phieuMuon)){
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    updateList();
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view1.findViewById(R.id.btnCancle_ql_PhieuMuon).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void updateList() {
        list.clear();
        list.addAll(phieuMuonDao.selectAll());
        phieuMuonAdapter.notifyDataSetChanged();
    }
}
