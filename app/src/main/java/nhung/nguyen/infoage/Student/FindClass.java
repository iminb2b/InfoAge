package nhung.nguyen.infoage.Student;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import nhung.nguyen.infoage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindClass extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FindClass() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindClass.
     */
    // TODO: Rename and change types and number of parameters
    public static FindClass newInstance(String param1, String param2) {
        FindClass fragment = new FindClass();
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
    ArrayAdapter<String> adapter;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_class, container, false);
        listView= (ListView)view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.language));

        listView.setAdapter(adapter);
        //toolbar=view.findViewById(R.id.toolbar);
        //onCreateOptionsMenu(toolbar.getMenu());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ClassDetail.class);
                intent.putExtra("lang",listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
        return view;
    }

//    public void onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getActivity().getMenuInflater();
//        menuInflater.inflate(R.menu.menu_search,menu);
//        MenuItem menuItem = menu.findItem(R.id.search_view);
//        SearchView searchView = (SearchView)menuItem.getActionView();
//        searchView.setQueryHint(getString(R.string.studentSearch));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return true;
//            }
//        });
//
//    }
}