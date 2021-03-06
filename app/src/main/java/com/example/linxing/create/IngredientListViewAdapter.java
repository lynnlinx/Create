package com.example.linxing.create;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by linxing on 3/17/18.
 */

class IngredientListViewAdapter extends ArrayAdapter<Ingredient> {
    private static final String TAG = "IngredientListViewAdapt";
    private List<Ingredient> mIngredientList = new ArrayList<>();
    private Context mContext;
    private SideslipListView listViewfinal;
    private FirebaseAuth myAuth;
    private Set<String> ingredientSet;
    private Activity IngredientActivity;

    public IngredientListViewAdapter(Context context, List<Ingredient> ingredientList, SideslipListView listView, Set<String> set, Activity IngredientActivity) {
        super(context, R.layout.ingredient, ingredientList);
        mContext = context;
        mIngredientList = ingredientList;
        ingredientSet = set;
        listViewfinal = listView;
        this.IngredientActivity = IngredientActivity;
        Log.d(TAG, "IngredientListViewAdapter context: "+ mContext.toString());
    }

    public IngredientListViewAdapter(Context context, int resource, List<Ingredient> ingredientList) {
        super(context, resource, ingredientList);
        mIngredientList = ingredientList;
        mContext = context;
        Log.d(TAG, "IngredientListViewAdapter context: "+ mContext);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        //View view = super.getView(position, convertView, parent);
        Log.d(TAG, "getView: position" + position);

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.title);
            viewHolder.nutrition = (TextView) convertView.findViewById(R.id.info);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.txtv_delete = (TextView) convertView.findViewById(R.id.txtv_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mIngredientList.size() > 0) {
            Ingredient ingredient = mIngredientList.get(position);
            viewHolder.name.setText(ingredient.getFood_name());
            StringBuilder sb = new StringBuilder();
            sb.append("Calories: ").append(ingredient.getNf_calories());
            viewHolder.nutrition.setText(sb.toString());
            //viewHolder.image.setImageResource(ingredient.getImage());
            Picasso.with(mContext).load(ingredient.getImage())
                    .error(R.drawable.ic_filter)
                    .placeholder(R.drawable.ic_filter)
                    .into(viewHolder.image);
        }
        final int pos = position;
        viewHolder.txtv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredient ingredientRemove = mIngredientList.get(pos);
                myAuth = myAuth.getInstance();
                FirebaseUser user = myAuth.getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ingredientRef = database.getReference("ingredient/"
                        + user.getUid() + "/" + ingredientRemove.getNix_item_id());
                ingredientRef.removeValue();
                mIngredientList.remove(pos);
                ingredientSet.remove(ingredientRemove.getNix_item_id());
                notifyDataSetChanged();
                listViewfinal.turnNormal();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return mIngredientList.size();
    }

    @Nullable
    @Override
    public Ingredient getItem(int position) {
        /*if (mIngredientList != null && mIngredientList.size() > position) {
            return mIngredientList.get(position);
        }*/
        return mIngredientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void loadNewData(List<Ingredient> newIngredient) {
        mIngredientList = newIngredient;
        notifyDataSetChanged();
    }

    void loadNewIngredient(Ingredient newIngredient) {
        if (ingredientSet.contains(newIngredient.getNix_item_id())) {

        } else {
            mIngredientList.add(newIngredient);
            ingredientSet.add(newIngredient.getNix_item_id());
        }
        Toast.makeText(IngredientActivity, "Item added!", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView name;
        TextView nutrition;
        ImageView image;
        TextView txtv_delete;
    }
}
