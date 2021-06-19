package com.izhar.halalboss.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.izhar.halalboss.adapters.FoodListAdapter;
import com.izhar.halalboss.models.Food;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    FoodListAdapter fla;
    List<Food> foodlist;
    DatabaseReference foods;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        foodlist = new ArrayList<>();
        foods = FirebaseDatabase.getInstance().getReference().child("foods");
        foods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodlist.clear();
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        foodlist.add(dataSnapshot.getValue(Food.class));
                    }
                    fla = new FoodListAdapter(getContext(), foodlist);
                    recyclerView.setAdapter(fla);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}