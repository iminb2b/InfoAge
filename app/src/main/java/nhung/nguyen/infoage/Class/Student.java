package nhung.nguyen.infoage.Class;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nhung.nguyen.infoage.Adapter.AdapterUser;
import nhung.nguyen.infoage.Adapter.ModelUser;
import nhung.nguyen.infoage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Student#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Student extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Student() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Student.
     */
    // TODO: Rename and change types and number of parameters
    public static Student newInstance(String param1, String param2) {
        Student fragment = new Student();
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
    AdapterUser adapterUser;
    List<ModelUser> userList;
    ArrayList<String> std;
    TextView check;
    String username, classid;
    List<String> st;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        recyclerView = view.findViewById(R.id.user_recyclerView);
        recyclerView.setHasFixedSize(true);
        st = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("Class", Context.MODE_PRIVATE);
        classid = sharedPreferences.getString("classid","");
        check = view.findViewById(R.id.check);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userList = new ArrayList<>();

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
//        if(fUser!= null){
//           // Glide.with(this).load(fUser.getPhotoUrl()).into(avatarIv);
//            username = fUser.getDisplayName();
//        }
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Classes").child(classid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String student = ds.child("id").getValue().toString();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(student);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String email = snapshot.child(getString(R.string.discussionEmail)).getValue().toString();
                                String image = snapshot.child(getString(R.string.discussionImage)).getValue().toString();
                                String name = snapshot.child("name").getValue().toString();
                                String phone = snapshot.child(getString(R.string.discussionPhone)).getValue().toString();
                                String uid = snapshot.child(getString(R.string.discussionUid)).getValue().toString();
                                ModelUser modelUser= new ModelUser(email, image, name, phone, uid);
                                userList.add(modelUser);
                                adapterUser = new AdapterUser(getActivity(), userList);
                                recyclerView.setAdapter(adapterUser);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





//        adapterUser = new AdapterUser(getActivity(), userList);
//        recyclerView.setAdapter(adapterUser);
        return view;
    }

    private void getAllUser() {

    }




}