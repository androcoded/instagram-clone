package com.example.instagramclone.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;


public class UserTap extends Fragment {

    private ListView listView;
    private ArrayList mArrayList;
    private ArrayAdapter mArrayAdapter;
    private TextView txtUserLoading;
    public UserTap() {
    }

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_tap, container, false);
        listView = view.findViewById(R.id.listView);
        txtUserLoading = view.findViewById(R.id.txtLoadingUser);
        mArrayList = new ArrayList();
        mArrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,mArrayList);
        ParseQuery<ParseUser> queryUser = ParseUser.getQuery();
        queryUser.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername().toString());
        queryUser.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (users.size()>0 && e ==null){
                    for(ParseUser user : users){
                        mArrayList.add(user.getUsername());
                    }
                    txtUserLoading.animate().alpha(0).setDuration(2000);
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(mArrayAdapter);
                }
            }
        });
      return view;
    }
}
