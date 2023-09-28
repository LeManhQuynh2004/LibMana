package fpoly.quynhph32353.duanmau.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

public class ChangePassWordFragment extends Fragment {
    View view;
    EditText edt_oldPass, edt_newPass, edt_rePass;
    SharedPreferences sharedPreferences;
    ThuThuDao thuThuDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_re_pass_word, container, false);
        thuThuDao = new ThuThuDao(getContext());
        sharedPreferences = getActivity().getSharedPreferences("LIST_USER", Context.MODE_PRIVATE);
        edt_oldPass = view.findViewById(R.id.edtOldPassword);
        edt_newPass = view.findViewById(R.id.edtnewPassword);
        edt_rePass = view.findViewById(R.id.edtRePassword);
        view.findViewById(R.id.btnCancle_changePass).setOnClickListener(v -> {
            resetEditText();
        });
        view.findViewById(R.id.btnSave_changePass).setOnClickListener(v -> {
            String user = sharedPreferences.getString("USERNAME", "");
            if (Validate()) {
                ThuThu thuThu = thuThuDao.SelectID(user);
                thuThu.setMatKhau(edt_newPass.getText().toString().trim());
                if (thuThuDao.updatePass(thuThu)) {
                    Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    resetEditText();
                } else {
                    Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void resetEditText() {
        edt_oldPass.setText("");
        edt_newPass.setText("");
        edt_rePass.setText("");
    }

    public boolean Validate() {
        boolean check = true;
        String old_pass = edt_oldPass.getText().toString().trim();
        String new_pass = edt_newPass.getText().toString().trim();
        String re_pass = edt_rePass.getText().toString().trim();
        if (old_pass.isEmpty() || new_pass.isEmpty() || re_pass.isEmpty()) {
            Toast.makeText(getContext(), "Quý khách vui lòng cung cấp đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String Pass_old = sharedPreferences.getString("PASSWORD", "");
            if (!old_pass.equals(Pass_old)) {
                Toast.makeText(getContext(), "Mật khẩu cũ không chính sác", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!new_pass.equals(re_pass)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}