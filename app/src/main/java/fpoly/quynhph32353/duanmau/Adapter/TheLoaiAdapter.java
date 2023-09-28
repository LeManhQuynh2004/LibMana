package fpoly.quynhph32353.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Dao.TheLoaiDao;
import fpoly.quynhph32353.duanmau.Model.TheLoai;
import fpoly.quynhph32353.duanmau.R;

public class TheLoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<TheLoai> list;

    TheLoaiDao theLoaiDao;

    public TheLoaiAdapter(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
        theLoaiDao = new TheLoaiDao(context);
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
    public class TheLoaiViewHolder{
        TextView txt_maloai,txt_tenLoai;
        ImageView img_Delete_theLoai;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TheLoaiViewHolder theLoaiViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_theloai, parent, false);
            theLoaiViewHolder = new TheLoaiViewHolder();
            theLoaiViewHolder.txt_maloai = convertView.findViewById(R.id.txt_item_maLoai);
            theLoaiViewHolder.txt_tenLoai = convertView.findViewById(R.id.txt_item_tenLoai);
            theLoaiViewHolder.img_Delete_theLoai = convertView.findViewById(R.id.img_Delete_TheLoai);
            convertView.setTag(theLoaiViewHolder);
        } else {
            theLoaiViewHolder = (TheLoaiViewHolder) convertView.getTag();
        }

        TheLoai theLoai = list.get(position);

        theLoaiViewHolder.txt_maloai.setText("Mã loại: " + theLoai.getMaLoai());
        theLoaiViewHolder.txt_tenLoai.setText("Tên Loại: " + theLoai.getTenLoai());

        theLoaiViewHolder.img_Delete_theLoai.setOnClickListener(v -> {
            DeleteItem(position);
        });
        return convertView;
    }
    public void DeleteItem(int position){
        TheLoai theLoai = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.baseline_warning_amber_24);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa thể loại " + theLoai.getTenLoai() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (theLoaiDao.Delete(theLoai)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(theLoai);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }catch (SQLiteConstraintException e){
                    Toast.makeText(context, "Xóa thất bại có liên kết", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
