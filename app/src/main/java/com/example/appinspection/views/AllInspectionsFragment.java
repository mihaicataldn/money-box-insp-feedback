package com.example.appinspection.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinspection.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllInspectionsFragment extends Fragment {

    private View allView;
    private RecyclerView myAnswersList;
    private LinearLayoutManager mLayoutManager;
    private DatabaseReference answersRef;




    public AllInspectionsFragment()
    {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.all_inspection_fragment, container, false);

        allView = inflater.inflate(R.layout.all_inspection_fragment, container,false);

        myAnswersList = (RecyclerView) allView.findViewById(R.id.recyclerVIew);
        myAnswersList.setLayoutManager(new LinearLayoutManager(getContext()));
        mLayoutManager = new LinearLayoutManager(getContext());

        answersRef = FirebaseDatabase.getInstance().getReference().child("Inspection");
        return allView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseRecyclerOptions.Builder<Inspect> inspectBuilder = new FirebaseRecyclerOptions.Builder<Inspect>();
        inspectBuilder.setQuery(answersRef, Inspect.class);
        FirebaseRecyclerOptions options = inspectBuilder //two parameters contact ref and the contacts getters and setters
                .build();// build


        FirebaseRecyclerAdapter<Inspect, InspectionsViewHolder> adapter
                = new FirebaseRecyclerAdapter<Inspect, InspectionsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final InspectionsViewHolder InspectionsViewHolder, int i, @NonNull Inspect inspections) {
                String eventsID = getRef(i).getKey();
                answersRef.child(eventsID).addValueEventListener(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            final String title = (String) dataSnapshot.child("title").getValue();
                            final String ans1 = (String) dataSnapshot.child("q1").getValue();
                            final String ans2 = (String) dataSnapshot.child("q2").getValue();
                            final String ans3 = (String) dataSnapshot.child("q3").getValue();
                            final String rating = (String) dataSnapshot.child("rating").getValue();


                            //now will display
                            InspectionsViewHolder.title.setText(title);
                            InspectionsViewHolder.q1.setText(ans1);
                            InspectionsViewHolder.q2.setText(ans2);
                            InspectionsViewHolder.q3.setText(ans3);
                            InspectionsViewHolder.rating.setText(rating);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public InspectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                InspectionsViewHolder viewHolder = new InspectionsViewHolder(view);
                return viewHolder;

            }
        };

        myAnswersList.setAdapter(adapter);
        adapter.startListening();

    }
    public static class InspectionsViewHolder extends RecyclerView.ViewHolder //create the holder for the Recycler adaptor
    {
        public TextView q1,q2,q3,title, rating;


        public InspectionsViewHolder(@NonNull View itemView)// create constructor
        {
            super(itemView);
            title = itemView.findViewById(R.id.title1ViewDBS);
            q1 = itemView.findViewById(R.id.question1ViewDBS);
            q2 = itemView.findViewById(R.id.question2ViewDBS);
            q3 = itemView.findViewById(R.id.question3ViewDBS);
            rating = itemView.findViewById(R.id.rating1ViewDBS);
            {
            }
        }
    }



}
