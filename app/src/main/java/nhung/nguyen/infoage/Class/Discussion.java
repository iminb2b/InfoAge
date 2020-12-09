package nhung.nguyen.infoage.Class;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import nhung.nguyen.infoage.MainActivity.MainActivity;
import nhung.nguyen.infoage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Discussion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Discussion extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Discussion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Discussion.
     */
    // TODO: Rename and change types and number of parameters
    public static Discussion newInstance(String param1, String param2) {
        Discussion fragment = new Discussion();
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
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDbref;
    String hisUid;
    String myUid;
    EditText msg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_discussion, container, false);
    //      RecyclerView recyclerView = view.findViewById(R.id.chat_recyclerView);
        ImageButton sendBtn = view.findViewById(R.id.sendBtn);
      //  Intent intent = getActivity().getIntent();
       // hisUid = intent.getStringExtra("hisUid");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDbref= firebaseDatabase.getReference(getString(R.string.discussionPath));
        msg= view.findViewById(R.id.messageEt);
        Query userQuery = userDbref.orderByChild(getString(R.string.discussionUid)).equalTo(myUid);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String name = ds.child(getString(R.string.discussionName)).getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = msg.getText().toString().trim();
                if(TextUtils.isEmpty(mes)){

                }else{
                    sendMsg(mes);
                }
            }
        });
        return view;
    }
    private void sendMsg(String mesg){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(getString(R.string.discussionSender),myUid);
        //hashMap.put("receiver",hisUid);
        hashMap.put(getString(R.string.discussionMessage),mesg);
        databaseReference.child(getString(R.string.discussionChat)).push().setValue(hashMap);
        msg.setText(getString(R.string.discussionBlank));
    }
    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            myUid = user.getUid();
        }else{
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

}