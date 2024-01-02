package com.example.quanlytracnghiem.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlytracnghiem.R;
import com.example.quanlytracnghiem.models.VPKhoa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThemVPKhoa_activitys extends AppCompatActivity {
    ImageView ivImages;
    EditText edtDiaChi, edtTenKhoa, edtMoTa;
    Button btnThem;
    Uri uri;
    String imagesUrl;
    // auto
    List<String> list_id = new ArrayList<>();
    Integer id_ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_vpkhoa_layout);
        setControl();
        setEvent();

    }

    public void key_auto() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VPKhoa");
        {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list_id.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String key = dataSnapshot.getKey();
                        if (key.isEmpty()){
                            list_id.add("0");
                        }else {
                            list_id.add(key);
                        }
                    }
                    int size = list_id.size()+1 ;
                     id_ = Integer.valueOf(size);
                    System.out.println(id_);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void setEvent() {
        ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            ivImages.setImageURI(uri);
                        } else {
                            Toast.makeText(ThemVPKhoa_activitys.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        ivImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResult.launch(photoPicker);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    xuLyDongBo();
                }
            }
        });
    }

    public void uploaddata() {
        String maKhoa = "VPKhoa"+id_;
        VPKhoa vpKhoa = new VPKhoa(maKhoa, edtTenKhoa.getText().toString(), edtMoTa.getText().toString(),edtDiaChi.getText().toString(), imagesUrl.toString());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VPKhoa");
        reference.child("VPKhoa"+id_).setValue(vpKhoa).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    finish();
                }
            }
        });

    }

    public void xuLyDongBo() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            key_auto();
            saveData();
        }).thenRun(() -> {
            uploaddata();
        });
    }

    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("VPKhoa")
                .child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imagesUrl = urlImage.toString();
            }
        });
    }
    public Boolean validate (){
        if (edtDiaChi.getText().toString().isEmpty() || edtDiaChi.getText().toString().length()>255){
            edtDiaChi.setError("Vui lòng nhập hợp lệ");
            return false;
        }
        if (edtTenKhoa.getText().toString().isEmpty() || edtTenKhoa.getText().toString().length()>50){
            edtTenKhoa.setError("Vui lòng nhập hợp lệ");
            return false;
        }
        if (edtDiaChi.getText().toString().length()>255){
            edtMoTa.setError("Vui lòng nhập hợp lệ");
            return false;
        }
        return true;

    }

    private void setControl() {
        btnThem = findViewById(R.id.btnThem);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtTenKhoa = findViewById(R.id.edtTenKhoa);
        edtMoTa = findViewById(R.id.edtMoTaKhoa);
        ivImages = findViewById(R.id.ivImages);
    }
}