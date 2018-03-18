package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
import java.util.List;

//import android.widget.SearchView;
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
    private List<Ingredient> realIngredientList;
    private IngredientListViewAdapter adapter;
    private ArrayAdapterSearchView.SearchAutoComplete mSearchAutoComplete;
    private IngredientListViewAdapter ingredientAdapter;
    private ArrayAdapterSearchView mAutoCompleteTextView;
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

        realIngredientList = new ArrayList<Ingredient>();
        adapter = new IngredientListViewAdapter(this, realIngredientList);
        final SideslipListView listView = findViewById(R.id.ingredient_list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        mIngredientList = new ArrayList<Ingredient>();
        int autoCompleteTextViewID = mSearchView.getResources().getIdentifier("android:id/search_src_text", null, null);
        AutoCompleteTextView mAutoCompleteTextView = (AutoCompleteTextView) mSearchView.findViewById(autoCompleteTextViewID);
        mAutoCompleteTextView.setThreshold(0);
        ingredientAdapter = new IngredientListViewAdapter(this, android.R.layout.simple_dropdown_item_1line, mIngredientList);
        mAutoCompleteTextView.setAdapter(ingredientAdapter);
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ingredient ingredient = (Ingredient) parent.getItemAtPosition(position);
                adapter.loadNewIngredient(ingredient);
                Log.d(TAG, "onItemClick: ingre" + realIngredientList);
            }
        });
        

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
            public boolean onQueryTextSubmit(String newText) {
                //if (newText.length() > 1) {
                //    loadData(newText);
                //}
                //mSearchView.clearFocus();
                //finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Cursor cursor = TextUtils.isEmpty(newText) ? null : queryData(newText);
                if (newText.length() > 1) {
                    loadData(newText);
                }
                //mSearchView.clearFocus();
                return true;
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


    @Override
    public void onDataAvailable(List<Ingredient> data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            //adapter.loadNewData(data);
            ingredientAdapter.loadNewData(data);
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



}
