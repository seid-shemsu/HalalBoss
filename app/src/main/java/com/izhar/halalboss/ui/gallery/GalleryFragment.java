package com.izhar.halalboss.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.izhar.halalboss.R;
import com.izhar.halalboss.models.Order;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    RecyclerView recyclerView;
    OrderListAdapter ola;
    List<Order> orders;
    TextView empty;
    ProgressBar progressBar;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        orders = new ArrayList<>();
        recyclerView = root.findViewById(R.id.recycler);
        progressBar = root.findViewById(R.id.progress);
        empty = root.findViewById(R.id.empty);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference pending = FirebaseDatabase.getInstance().getReference().child("orders").child("pending");
        pending.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    empty.setVisibility(View.GONE);
                    orders.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        orders.add(dataSnapshot.getValue(Order.class));
                    }
                    ola = new OrderListAdapter(getContext(), orders);
                    recyclerView.setAdapter(ola);
                }
                else
                    empty.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}