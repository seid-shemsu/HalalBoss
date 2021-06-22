package com.izhar.halalboss.ui.slideshow;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.izhar.halalboss.R;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class SlideshowFragment extends Fragment {

    ImageView image;
    DatabaseReference categories;
    StorageReference storage;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        Button trend = root.findViewById(R.id.trend);
        trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.category);
                dialog.show();
                image = dialog.findViewById(R.id.image);
                EditText category = dialog.findViewById(R.id.cat);
                TextInputLayout hint = dialog.findViewById(R.id.t1);
                hint.setHint("Trending");
                Button add = dialog.findViewById(R.id.add);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFileChooser();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = System.currentTimeMillis() + "";
                        storage = FirebaseStorage.getInstance().getReference().child("trends").child(id);
                        storage.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        categories = FirebaseDatabase.getInstance().getReference("trends").child(id);
                                        categories.child("image").setValue(uri.toString());
                                        categories.child("desc").setValue(category.getText().toString());
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        Button cat = root.findViewById(R.id.cat);
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.category);
                dialog.show();
                TextInputLayout hint = dialog.findViewById(R.id.t1);
                hint.setHint("Category");
                image = dialog.findViewById(R.id.image);
                EditText category = dialog.findViewById(R.id.cat);
                Button add = dialog.findViewById(R.id.add);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFileChooser();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = System.currentTimeMillis() + "";
                        storage = FirebaseStorage.getInstance().getReference().child("categories").child(id);
                        storage.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        categories = FirebaseDatabase.getInstance().getReference("categories").child(id);
                                        categories.child("image").setValue(uri.toString());
                                        categories.child("category").setValue(category.getText().toString());
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        return root;
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    Uri imgUri;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imgUri = data.getData();
            Picasso.Builder builder = new Picasso.Builder(getContext());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    Toast.makeText(getContext(), ""+exception.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            builder.build().load(imgUri).into(image);
        }
        else {
            Toast.makeText(getContext(), "" + resultCode, Toast.LENGTH_SHORT).show();
        }
    }
}