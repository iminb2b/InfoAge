package nhung.nguyen.infoage.Student;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nhung.nguyen.infoage.Class.ClassActivity;
import nhung.nguyen.infoage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppliedClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppliedClass extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppliedClass() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppliedClass.
     */
    // TODO: Rename and change types and number of parameters
    public static AppliedClass newInstance(String param1, String param2) {
        AppliedClass fragment = new AppliedClass();
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
    ListView listView;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    ArrayList<String> classes = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String name = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        assert name != null;
        DatabaseReference myRef = database.getReference("Student").child(name);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classes.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    StringBuilder sb= new StringBuilder();
                    for(DataSnapshot dat: dataSnapshot.getChildren()){
                        sb.append(dat.getKey()+":  "+dat.getValue().toString()+"\n");
                    }
                    classes.add(sb.toString());

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView= (ListView)view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, classes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ClassActivity.class);
                //intent.putExtra("language",lang);
                startActivity(intent);
            }
        });
        return view;
    }
}