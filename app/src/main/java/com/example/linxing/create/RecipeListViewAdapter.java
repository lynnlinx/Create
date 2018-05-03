package com.example.linxing.create;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxing on 5/1/18.
 */

public class RecipeListViewAdapter extends ArrayAdapter<RecipeItem> {
    private static final String TAG = "IngredientListViewAdapt";
    private List<RecipeItem> mRecipeItems = new ArrayList<>();
    private Context mContext;
    private ListView mListView;
    private FirebaseAuth myAuth;
    private int dailyCalories;

    public RecipeListViewAdapter(Context context, List<RecipeItem> recipeItems, ListView listView, int dailyCalories) {
        super(context, R.layout.recipe_list, recipeItems);
        mContext = context;
        mRecipeItems = recipeItems;
        mListView = listView;
        this.dailyCalories = dailyCalories;
    }


    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        //View view = super.getView(position, convertView, parent);
        Log.d(TAG, "getView: position" + position);

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_item, parent, false);
            viewHolder = new RecipeListViewAdapter.ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.title);
            viewHolder.nutrition = (TextView) convertView.findViewById(R.id.info_nutrition);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.calorie = (TextView) convertView.findViewById(R.id.info_ingredient);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RecipeListViewAdapter.ViewHolder) convertView.getTag();
        }
        if (mRecipeItems.size() > 0) {
            RecipeItem recipeItem = mRecipeItems.get(position);
            viewHolder.name.setText(recipeItem.getTitle());
            StringBuilder sb = new StringBuilder();
            sb.append("Calories: ").append(recipeItem.getCalories()).append(", ")
                    .append("Protein: ").append(recipeItem.getProtein()).append(", ")
                    .append("Fat: ").append(recipeItem.getFat()).append(", ")
                    .append("Carbs: ").append(recipeItem.getCarbs());
            viewHolder.nutrition.setText(sb.toString());
            StringBuilder tmp = new StringBuilder();
            tmp.append("Consuming").append(String.valueOf(dailyCalories)).append("% of daily calories.");
            viewHolder.calorie.setText(tmp.toString());
            Picasso.with(mContext).load(recipeItem.getImage())
                    .error(R.drawable.ic_filter)
                    .placeholder(R.drawable.ic_filter)
                    .into(viewHolder.image);
        }
        final int pos = position;
        /*
        viewHolder.txtv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredient ingredientRemove = mIngredientList.get(pos);
                myAuth = myAuth.getInstance();
                FirebaseUser user = myAuth.getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ingredientRef = database.getReference("ingredient/"
                        + user.getUid() + "/" + ingredientRemove.getUuid());
                ingredientRef.removeValue();
                mIngredientList.remove(pos);
                notifyDataSetChanged();
                mListView.turnNormal();
            }
        });
        */
        return convertView;
    }

    @Override
    public int getCount() {
        return mRecipeItems.size();
    }

    @Nullable
    @Override
    public RecipeItem getItem(int position) {
        /*if (mIngredientList != null && mIngredientList.size() > position) {
            return mIngredientList.get(position);
        }*/
        return mRecipeItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void loadNewData(List<RecipeItem> newRecipe) {
        mRecipeItems = newRecipe;
        notifyDataSetChanged();
    }

    void loadNewRecipe(RecipeItem newRecipe) {
        mRecipeItems.add(newRecipe);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView name;
        TextView nutrition;
        ImageView image;
        TextView calorie;
    }

}
