package fpoly.quynhph32353.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpoly.quynhph32353.duanmau.R;


public class QlTheLoaiFragment extends Fragment {
    View view;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_the_loai, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView_TheLoai);

        return view;
    }
}