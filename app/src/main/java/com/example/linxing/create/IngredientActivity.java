package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by jiana on 2018/2/27.
 * Powered by Nutritionix API
 */

public class IngredientActivity extends AppCompatActivity implements View.OnClickListener,
                                        IngredientJsonData.OnDataAvailable {
    private Button buttonSearch;
    private TextView buttonDelete;
    private ImageButton buttonLeftMenu;
    private DrawerLayout drawerLayout;
    private Button buttonLogout;
    private Button buttonSetting;
    private SearchView mSearchView;
    private TextView textUsername;
    private static final String TAG = "IngredientActivity";
    private List<Ingredient> mIngredientList;
    private IngredientListViewAdapter adapter;
    private FirebaseAuth myAuth;
    UserProfile userInformation;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        buttonLeftMenu = (ImageButton) findViewById(R.id.btn_menu);
        buttonLeftMenu.setOnClickListener(this);

        buttonLogout = (Button) findViewById(R.id.btn_logout);
        buttonLogout.setOnClickListener(this);
        
        buttonSetting = (Button) findViewById(R.id.btn_setting);
        buttonSetting.setOnClickListener(this);

        buttonSearch = (Button) findViewById(R.id.btn_search_recipe);
        buttonSearch.setOnClickListener(this);

        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.onActionViewExpanded();

        mIngredientList = new ArrayList<Ingredient>();
        adapter = new IngredientListViewAdapter(this, mIngredientList);
        SideslipListView listView = findViewById(R.id.ingredient_list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        Log.d(TAG, "onCreate: here create adapter!!!!" + listView.getAdapter());
        /*
        SimpleAdapter adapter = new SimpleAdapter(this, mData,
                R.layout.ingredient_list_item,
                new String[] { "title", "info", "image" },
                new int[] { R.id.title, R.id.info, R.id.image }) {

            @Override
            public View getView (int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                buttonDelete = (TextView) view.findViewById(R.id.txtv_delete);
                final int pos = position;
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mData.remove(pos);
                        notifyDataSetChanged();
                    }
                });
                return view;
            }

        };
*/



        //userinfo
        myAuth = myAuth.getInstance();
        user = myAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(("profile/" + user.getUid()));
        textUsername = findViewById(R.id.txt_username);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInformation = dataSnapshot.getValue(UserProfile.class);
                textUsername.setText(userInformation.getUsername_profile());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            private static final String TAG = "IngredientActivity";
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 1) {
                    loadData(query);
                }
                mSearchView.clearFocus();
                //finish();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Cursor cursor = TextUtils.isEmpty(newText) ? null : queryData(newText);
                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });
    }

/*
    @Override
    protected void onResume() {
        super.onResume();
        IngredientJsonData ingredientJsonData = new IngredientJsonData(this, "https://trackapi.nutritionix.com/v2/search/instant", true);
        Log.d(TAG, "onResume: " + ingredientJsonData);
        ingredientJsonData.execute(mIngredientList);
    }
*/


    @Override
    public void onDataAvailable(List<Ingredient> data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            adapter.loadNewData(data);
            Log.d(TAG, "onDataAvailable: data is" + data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable: failed with status" + status );
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSearch) {
            finish();
            startActivity(new Intent(this, RecipelistActivity.class));
        }
        if (v == buttonLeftMenu) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
        if (v == buttonLogout) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            FirebaseAuth.getInstance().signOut();
        }
        if (v == buttonSetting) {
            finish();
            startActivity(new Intent(this, SettingActivity.class));
        }
    }

    private void loadData(String s) {
        IngredientJsonData ingredientJsonData = new IngredientJsonData(this, "https://trackapi.nutritionix.com/v2/search/instant", true);
        ingredientJsonData.execute(s);
    }


    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map;

        map = new HashMap<String, Object>();
        map.put("title", "Chicken Breast");
        map.put("info", "Protein:37g    Carb:0g    Fat:4g");
        map.put("image", R.mipmap.ic_chickenbreast);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Broccoli");
        map.put("info", "Protein:1g    Carb:3g    Fat:0g");
        map.put("image", R.mipmap.ic_broccoli);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Cheese");
        map.put("info", "Protein:6g    Carb:1g    Fat:9g");
        map.put("image", R.mipmap.ic_cheese);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Egg");
        map.put("info", "Protein:6g    Carb:0g    Fat:5g");
        map.put("image", R.mipmap.ic_egg);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Milk");
        map.put("info", "Protein:8g    Carb:12g    Fat:2g");
        map.put("image", R.mipmap.ic_milk);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Noodle");
        map.put("info", "Protein:7g    Carb:38g    Fat:1g");
        map.put("image", R.mipmap.ic_noodle);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Onion");
        map.put("info", "Protein:1g    Carb:10g    Fat:0g");
        map.put("image", R.mipmap.ic_onion);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Tomato");
        map.put("info", "Protein:1g    Carb:5g    Fat:0g");
        map.put("image", R.mipmap.ic_tomato);
        list.add(map);

        return list;
    }

}
