package fpoly.quynhph32353.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fpoly.quynhph32353.duanmau.Dao.ThongKeDao;
import fpoly.quynhph32353.duanmau.R;


public class TKDoanhSo_Fragment extends Fragment {

    View view;

    EditText edt_tuNgay, edt_denNgay;

    TextView txt_DoanhThu;

    int day, month, year;

    ThongKeDao thongKeDao;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_t_k_doanh_so_, container, false);
        edt_tuNgay = view.findViewById(R.id.edt_TuNgay);
        edt_denNgay = view.findViewById(R.id.edt_DenNgay);
        txt_DoanhThu = view.findViewById(R.id.txt_DoanhThu);
        thongKeDao = new ThongKeDao(getContext());
        view.findViewById(R.id.btn_find).setOnClickListener(v -> {
            String tuNgay = edt_tuNgay.getText().toString();
            String denNgay = edt_denNgay.getText().toString();
            txt_DoanhThu.setText(thongKeDao.DoanhThu(tuNgay, denNgay) + "VND");
        });
        return view;
    }
}