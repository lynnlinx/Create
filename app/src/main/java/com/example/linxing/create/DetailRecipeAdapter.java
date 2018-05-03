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

public class DetailRecipeAdapter extends ArrayAdapter<RecipeDetailItem> {
    private static final String TAG = "DetailRecipeAdapt";
    private List<RecipeDetailItem> mRecipeItem;
    private Context mContext;
    private ListView mListView;
    private FirebaseAuth myAuth;

    public DetailRecipeAdapter(Context context, List<RecipeDetailItem> recipeItem,ListView listView) {
        super(context, R.layout.detailrecipe, recipeItem);
        mContext = context;
        mRecipeItem = recipeItem;
        mListView = listView;

    }
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        //View view = super.getView(position, convertView, parent);
        Log.d(TAG, "getView: position" + position);

        DetailRecipeAdapter.ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.detailrecipe, parent, false);
            viewHolder = new DetailRecipeAdapter.ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.recipe_name);
            viewHolder.ingredients = (TextView) convertView.findViewById(R.id.recipe_ingridients);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.recipe_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DetailRecipeAdapter.ViewHolder) convertView.getTag();
        }

        if (mRecipeItem.size() > 0) {
            RecipeDetailItem recipeItem = mRecipeItem.get(position);
            viewHolder.name.setText(recipeItem .getTitle());

            ArrayList<RecipeIngredient> ingredients = recipeItem .getRecipeIngredients();
            StringBuilder ingre_sb = new StringBuilder();
            for(int i=1;i<=ingredients.size();i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(i).append(". ").append(ingredients.get(i).getIngreName()).append(" ")
                        .append(ingredients.get(i).getCount()).append(" ")
                        .append(ingredients.get(i).getUnit());
                ingre_sb.append(sb);
            }

            viewHolder.ingredients.setText(ingre_sb);
            Picasso.with(mContext).load(recipeItem .getImage())
                    .error(R.drawable.ic_filter)
                    .placeholder(R.drawable.ic_filter)
                    .into(viewHolder.image);
        }
        final int pos = position;
        return convertView;
    }


    @Nullable
    @Override
    public RecipeDetailItem getItem(int position) {
        /*if (mIngredientList != null && mIngredientList.size() > position) {
            return mIngredientList.get(position);
        }*/
        return mRecipeItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void loadNewData(List<RecipeDetailItem> newRecipe) {
        mRecipeItem = newRecipe;
        notifyDataSetChanged();
    }


    private static class ViewHolder {
        TextView name;
        TextView ingredients;
        ImageView image;
    }

}
