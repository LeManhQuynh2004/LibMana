package fpoly.quynhph32353.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.quynhph32353.duanmau.Dao.ThuThuDao;
import fpoly.quynhph32353.duanmau.Model.ThuThu;
import fpoly.quynhph32353.duanmau.R;

public class ThemNguoiDungFragment extends Fragment {

    View view;

    EditText edt_username,edt_hoTen,edt_password;

    ThuThuDao thuThuDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);
        thuThuDao = new ThuThuDao(getContext());
        edt_hoTen = view.findViewById(R.id.edt_hoTen_add);
        edt_username = view.findViewById(R.id.edt_username_add);
        edt_password = view.findViewById(R.id.edtPassword_Add);
        view.findViewById(R.id.btnSave_addTT).setOnClickListener(v -> {
            String username = edt_username.getText().toString().trim();
            String hoTen = edt_hoTen.getText().toString().trim();
            String password = edt_password.getText().toString().trim();
            ThuThu thuThu = new ThuThu(username,hoTen,password);
            if(thuThuDao.insertData(thuThu)){
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                resetEditText();
            }else{
                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public void resetEditText() {
        edt_username.setText("");
        edt_hoTen.setText("");
        edt_password.setText("");
    }

}