package com.example.quanlytracnghiem.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlytracnghiem.R;
import com.example.quanlytracnghiem.models.VPKhoa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.CompletableFuture;

public class CapNhapVPKhoa_activitys extends AppCompatActivity {
    Bundle bundle;
    EditText edtDiaChi, edtTenKhoa, edtMoTa;
    ImageView ivImages;
    Button btnCapNhap;
    VPKhoa vpKhoa = new VPKhoa();
    String imagesURL;
    Uri uri;
    VPKhoa khoa ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhap_vpkhoa_layout);
        bundle = getIntent().getExtras();
        setControl();
        getData();
        setEvent();

    }


    private void getData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("").setMessage("Đang tải dữ liệu...");
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        if (bundle != null) {
            Glide.with(CapNhapVPKhoa_activitys.this).load(bundle.getString("images")).into(ivImages);
            vpKhoa.setTenKhoa(bundle.getString("tenKhoa"));
            vpKhoa.setMaKhoa(bundle.getString("maVP"));
            vpKhoa.setDiaChi(bundle.getString("diaChi"));
            vpKhoa.setMoTaKhoa(bundle.getString("moTa"));
            vpKhoa.setImagesKhoa(bundle.getString("images"));
            // show data
            edtDiaChi.setText(vpKhoa.getDiaChi());
            edtTenKhoa.setText(vpKhoa.getTenKhoa());
            edtMoTa.setText(vpKhoa.getMoTaKhoa());
        }
        dialog.dismiss();
    }

    public void saveData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VPKhoa");

        if (imagesURL == null){
            khoa = new VPKhoa(vpKhoa.getMaKhoa(), edtTenKhoa.getText().toString(), edtMoTa.getText().toString(),edtDiaChi.getText().toString(), vpKhoa.getImagesKhoa());
        }else {
            khoa = new VPKhoa(vpKhoa.getMaKhoa(), edtTenKhoa.getText().toString(), edtMoTa.getText().toString(),edtDiaChi.getText().toString(),imagesURL);
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imagesURL);
            storageReference.delete();
        }
        reference.child(vpKhoa.getMaKhoa()).setValue(khoa).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                   finish();
                }
            }
        });
    }

    public void setEvent() {
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
                            Toast.makeText(CapNhapVPKhoa_activitys.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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
        });btnCapNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                  if (uri== null){

                      saveData();
                  }else {
                      UploadData();
                  }
                }
            }
        });
    }
    public void UploadData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("VPKhoa")
                .child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imagesURL = urlImage.toString();
                saveData();
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
        btnCapNhap = findViewById(R.id.btnCapNhap);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtTenKhoa = findViewById(R.id.edtTenKhoa);
        edtMoTa = findViewById(R.id.edtMoTaKhoa);
        ivImages = findViewById(R.id.ivImages);
    }
}