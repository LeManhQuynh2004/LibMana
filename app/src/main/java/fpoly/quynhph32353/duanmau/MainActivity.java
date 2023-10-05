package fpoly.quynhph32353.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import fpoly.quynhph32353.duanmau.Dao.ThuThuDao;
import fpoly.quynhph32353.duanmau.Fragment.QlThanhVienFragment;
import fpoly.quynhph32353.duanmau.Fragment.QlPhieuMuonFragment;
import fpoly.quynhph32353.duanmau.Fragment.QlSachFragment;
import fpoly.quynhph32353.duanmau.Fragment.QlTheLoaiFragment;
import fpoly.quynhph32353.duanmau.Fragment.ChangePassWordFragment;
import fpoly.quynhph32353.duanmau.Fragment.QlThuThuFragment;
import fpoly.quynhph32353.duanmau.Fragment.TKDoanhSo_Fragment;
import fpoly.quynhph32353.duanmau.Fragment.ThemNguoiDungFragment;
import fpoly.quynhph32353.duanmau.Fragment.TkTop10Fragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;

    ThuThuDao thuThuDao;

    TextView txt_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.Toolbar_Main);
        thuThuDao = new ThuThuDao(MainActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thư Viện Phương Nam");
        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.NavigationView);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QlPhieuMuonFragment()).commit();
        Intent intent = getIntent();
        String user = intent.getStringExtra("username");
        DocFile(user);
        txt_user = navigationView.getHeaderView(0).findViewById(R.id.txt_HeaderTextView);
        txt_user.setText("WelCome " + user);
        if (user != null) {
            if (!user.equalsIgnoreCase("admin")) {
                navigationView.getMenu().findItem(R.id.menu_them_nguoi_dung).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_ql_thuThu).setVisible(false);
            }
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = item.getItemId();
                Fragment fragment = null;
                String title = "";

                try {
                    if (position == R.id.menu_ql_phieu_muon) {
                        fragment = new QlPhieuMuonFragment();
                        title = "Quản lý phiếu mượn";
                    } else if (position == R.id.menu_ql_loaiSach) {
                        fragment = new QlTheLoaiFragment();
                        title = "Quản lý thể loại";
                    } else if (position == R.id.menu_ql_Sach) {
                        fragment = new QlSachFragment();
                        title = "Quản lý sách";
                    } else if (position == R.id.menu_ql_thanhVien) {
                        fragment = new QlThanhVienFragment();
                        title = "Quản lý thành viên";
                    } else if (position == R.id.menu_ql_thuThu) {
                        fragment = new QlThuThuFragment();
                        title = "Quản lý thủ thư";
                    } else if (position == R.id.menu_tk_top10) {
                        fragment = new TkTop10Fragment();
                        title = "Top 10 sách bán chạy";
                    } else if (position == R.id.menu_tk_DoanhThu) {
                        fragment = new TKDoanhSo_Fragment();
                        title = "Thống kê doanh số";
                    } else if (position == R.id.menu_them_nguoi_dung) {
                        fragment = new ThemNguoiDungFragment();
                        title = "Thêm người dùng";
                    } else if (position == R.id.menu_doi_mat_khau) {
                        fragment = new ChangePassWordFragment();
                        title = "Đổi mật khẩu";
                    } else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        return true;
                    }
                    if (fragment != null) {
                        drawerLayout.close();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                        getSupportActionBar().setTitle(title);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }
    private void DocFile(String username){
        SharedPreferences sharedPreferences = getSharedPreferences("user_use", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username_user",username);
        editor.apply();
    }
}