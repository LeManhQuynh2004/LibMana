package fpoly.quynhph32353.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.quynhph32353.duanmau.Dao.ThuThuDao;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName,edtPassWord;
    CheckBox chkRememberPass;

    String strUserName,strPassWord;
    ThuThuDao thuThuDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserName = findViewById(R.id.edtUsername);
        edtPassWord = findViewById(R.id.edtPassword);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        thuThuDao = new ThuThuDao(this);
        ReadFile();
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            CheckLogin();
        });
        findViewById(R.id.btnCancel).setOnClickListener(v -> {
            edtUserName.setText("");
            edtPassWord.setText("");
            chkRememberPass.setChecked(false);
        });
    }

    private void ReadFile() {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER",MODE_PRIVATE);
        edtUserName.setText(sharedPreferences.getString("USERNAME",""));
        edtPassWord.setText(sharedPreferences.getString("PASSWORD",""));
        chkRememberPass.setChecked(sharedPreferences.getBoolean("REMEMBER",false));
    }

    private void CheckLogin() {
        strUserName = edtUserName.getText().toString().trim();
        strPassWord = edtPassWord.getText().toString().trim();
        if(strUserName.isEmpty() || strPassWord.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else{
            if(thuThuDao.checkLogin(strUserName,strPassWord) || strUserName.equals("admin") && strPassWord.equals("admin")){
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUserName,strPassWord,chkRememberPass.isChecked());
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("username",strUserName);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void rememberUser(String strUserName, String strPassWord, boolean checked) {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!checked){
            //Xóa tình trạng lưu trước đó
            editor.clear();
        }else{
            editor.putString("USERNAME",strUserName);
            editor.putString("PASSWORD",strPassWord);
            editor.putBoolean("REMEMBER",checked);
        }
        editor.commit();
    }
}