package fpoly.quynhph32353.duanmau.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import fpoly.quynhph32353.duanmau.Database.Db_Helper;
import fpoly.quynhph32353.duanmau.Model.PhieuMuon;
import fpoly.quynhph32353.duanmau.Model.TheLoai;

public class PhieuMuonDao {
    Db_Helper dbHelper;
    private static final String TABLE_NAME = "PhieuMuon";

    private static final String COLUMN_MA_THU_THU = "maTT";

    private static final String COLUMN_MA_PHIEU_MUON = "maPM";

    private static final String COLUMN_MA_SACH = "maSach";

    private static final String COLUMN_MA_THANH_VIEN = "maTV";

    private static final String COLUMN_NGAY = "ngay";

    private static final String COLUMN_GIA_THUE = "tienThue";

    private static final String COLUMN_TRA_SACH = "traSach";

    public PhieuMuonDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insert(PhieuMuon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_THU_THU, obj.getMaTT());
        contentValues.put(COLUMN_MA_THANH_VIEN, obj.getMaTV());
        contentValues.put(COLUMN_MA_SACH, obj.getMaSach());
        contentValues.put(COLUMN_NGAY, obj.getNgay().getTime());
        contentValues.put(COLUMN_GIA_THUE, obj.getTienThue());
        contentValues.put(COLUMN_TRA_SACH, obj.getTraSach());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return check != -1;
    }

    public boolean delete(PhieuMuon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaPM())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_MA_PHIEU_MUON + "=?", dk);
        return check != -1;
    }

    public boolean update(PhieuMuon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaPM())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_THU_THU, obj.getMaTT());
        contentValues.put(COLUMN_MA_THANH_VIEN, obj.getMaTV());
        contentValues.put(COLUMN_MA_SACH, obj.getMaSach());
        contentValues.put(COLUMN_NGAY, obj.getNgay().getTime());
        contentValues.put(COLUMN_GIA_THUE, obj.getTienThue());
        contentValues.put(COLUMN_TRA_SACH, obj.getTraSach());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_MA_PHIEU_MUON + "=?", dk);
        return check != -1;
    }

    public ArrayList<PhieuMuon> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase(); // Use getReadableDatabase()
        ArrayList<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                int maPM = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_PHIEU_MUON));
                int maTV = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_THANH_VIEN));
                int maSach = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_SACH));
                String maTT = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_THU_THU));
                int tienThue = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GIA_THUE));
                int traSach = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRA_SACH));
                long ngayMillis = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NGAY));
                Date ngay = new Date(ngayMillis);

                PhieuMuon phieuMuon = new PhieuMuon(maPM, maTT, maSach, maTV, tienThue, traSach, ngay);
                list.add(phieuMuon);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public ArrayList<PhieuMuon> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }
    public PhieuMuon selectID(String id) {
        String sql = "SELECT * FROM TheLoai WHERE id = ?";
        ArrayList<PhieuMuon> list = getAll(sql, id);
        return list.get(0);
    }
}
