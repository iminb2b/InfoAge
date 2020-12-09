package nhung.nguyen.infoage.Class;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nhung.nguyen.infoage.Adapter.AdapterPost;
import nhung.nguyen.infoage.Adapter.ModelPost;
import nhung.nguyen.infoage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    RecyclerView recyclerView;
    AdapterPost adapterPost;
    List<ModelPost> postList;
    String cid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = view.findViewById(R.id.db_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final String classid = getActivity().getIntent().getStringExtra("classid");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Class", Context.MODE_PRIVATE);
        cid = sharedPreferences.getString("classid","");
        postList = new ArrayList<>();
        FloatingActionButton fab = view.findViewById(R.id.fab_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), NewPost.class);
                intent.putExtra("classid",cid);
                startActivity(intent);
            }
        });
     final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
       DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ClassPost").child(cid);
       ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    String tit = ds.child("title").getValue().toString();
                    String con = ds.child("content").getValue().toString();
                    String auth = ds.child("author").getValue().toString();
                    String postid = ds.child("postid").getValue().toString();
                    ModelPost modelPost= new ModelPost(tit,con,auth,postid);
                    postList.add(modelPost);
                    adapterPost = new AdapterPost(getActivity(), postList);
                    recyclerView.setAdapter(adapterPost);
                }


            }
//
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
       });
//        adapterPost = new AdapterPost(getActivity(), postList);
//        recyclerView.setAdapter(adapterPost);
        return view;
    }

}