package com.example.linxing.create;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by linxing on 3/17/18.
 */

class IngredientListViewAdapter extends ArrayAdapter<Ingredient> {
    private static final String TAG = "IngredientListViewAdapt";
    private List<Ingredient> mIngredientList;
    private Context mContext;
    private TextView buttonDelete;

    public IngredientListViewAdapter(Context context, List<Ingredient> ingredientList) {
        super(context, R.layout.ingredient, ingredientList);
        Log.d(TAG, "IngredientListViewAdapter: inside!!!!");
        mContext = context;
        mIngredientList = ingredientList;
    }


    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        //View view = super.getView(position, convertView, parent);
        Log.d(TAG, "getView: position" + position);

        ViewHolder viewHolder;
        if (null == convertView) {
            Log.d(TAG, "getView: view is null!!!!!");
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.title);
            viewHolder.nutrition = (TextView) convertView.findViewById(R.id.info);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final int pos = position;
        if (mIngredientList.size() > 0) {
            Ingredient ingredient = mIngredientList.get(position);
            viewHolder.name.setText(ingredient.getFood_name());
            viewHolder.nutrition.setText(ingredient.getNf_calories());
        }


        buttonDelete = (TextView) convertView.findViewById(R.id.txtv_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIngredientList.remove(pos);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return ((mIngredientList != null) && (mIngredientList.size() != 0) ? mIngredientList.size() : 1);
    }

    void loadNewData(List<Ingredient> newIngredient) {
        mIngredientList = newIngredient;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView name;
        TextView nutrition;
        ImageView image;
    }
}
