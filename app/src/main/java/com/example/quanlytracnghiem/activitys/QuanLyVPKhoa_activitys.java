package com.example.quanlytracnghiem.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.quanlytracnghiem.R;
import com.example.quanlytracnghiem.adapter.QuanLyVPKhoa_adapter;
import com.example.quanlytracnghiem.models.VPKhoa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuanLyVPKhoa_activitys extends AppCompatActivity {
    SearchView searchView;
    ImageButton btnThem, btnSapXep;
    ArrayList<VPKhoa> listKhoa = new ArrayList<>();
    QuanLyVPKhoa_adapter adapter;
    SwipeableRecyclerView swipeableRecyclerView;
    Integer sortCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_vpkhoa_layout);
        setControl();
        loadData();
        setEvent();
    }

    @Override
    protected void onRestart() {
        adapter.notifyDataSetChanged();
        super.onRestart();
    }

    private void loadData() {
        swipeableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuanLyVPKhoa_adapter(QuanLyVPKhoa_activitys.this, listKhoa);
        swipeableRecyclerView.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VPKhoa");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listKhoa.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    VPKhoa vpKhoa = dataSnapshot.getValue(VPKhoa.class);
                    listKhoa.add(vpKhoa);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyVPKhoa_activitys.this, ThemVPKhoa_activitys.class);
                startActivity(intent);
            }
        });
        btnSapXep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SapXep();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Search(newText);
                return true;
            }
        });


        swipeableRecyclerView.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
                VPKhoa vpKhoa = listKhoa.get(position);
                Intent intent = new Intent(QuanLyVPKhoa_activitys.this, CapNhapVPKhoa_activitys.class);
                intent.putExtra("maVP", vpKhoa.getMaKhoa());
                intent.putExtra("tenKhoa", vpKhoa.getTenKhoa());
                intent.putExtra("moTa", vpKhoa.getMoTaKhoa());
                intent.putExtra("diaChi", vpKhoa.getDiaChi());
                intent.putExtra("images", vpKhoa.getImagesKhoa());
                startActivity(intent);
            }

            @Override
            public void onSwipedRight(int position) {
                deleteKhoa(position);
            }
        });
        swipeableRecyclerView.setLeftImage(R.drawable.delete_icon);

        swipeableRecyclerView.setRightImage(R.drawable.edit_con);

    }

    public void deleteKhoa(int position) {
        VPKhoa vpKhoa = listKhoa.get(position);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VPKhoa");
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(vpKhoa.getImagesKhoa());
        storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                reference.child(vpKhoa.getMaKhoa()).removeValue();
            }
        });
    }

    public void SapXep() {
        if (sortCount == 0) {
            sortCount++;
            listKhoa.sort(new Comparator<VPKhoa>() {
                @Override
                public int compare(VPKhoa vp, VPKhoa vp1) {
                    return vp.getTenKhoa().compareTo(vp1.getTenKhoa());
                }
            });
        } else if (sortCount == 1) {
            sortCount++;
            listKhoa.sort(new Comparator<VPKhoa>() {
                @Override
                public int compare(VPKhoa vp, VPKhoa vp1) {
                    return vp1.getTenKhoa().compareTo(vp.getTenKhoa());
                }
            });

        } else {
            loadData();
            sortCount = 0;
        }
        adapter.notifyDataSetChanged();
    }
    public void Search(String text){
        ArrayList<VPKhoa> searchData = new ArrayList<>();
        for (VPKhoa vpKhoa :listKhoa){
            if (vpKhoa.getTenKhoa().toLowerCase().contains(text.toLowerCase())){
                searchData.add(vpKhoa);
            }
        }
        adapter.SearchData(searchData);
    }

    private void setControl() {
        swipeableRecyclerView = findViewById(R.id.SwipRecycleView);
        btnThem = findViewById(R.id.ibtnThem);
        btnSapXep = findViewById(R.id.ibtnSapxep);
        searchView = findViewById(R.id.SearchView);
    }
}