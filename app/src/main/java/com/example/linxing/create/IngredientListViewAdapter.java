package com.example.linxing.create;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        mContext = context;
        mIngredientList = ingredientList;
    }

    public IngredientListViewAdapter(Context context, int resource, List<Ingredient> ingredientList) {
        super(context, resource, ingredientList);
        mIngredientList = ingredientList;
        mContext = context;
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final int pos = position;
        if (mIngredientList.size() > 0) {
            Ingredient ingredient = mIngredientList.get(position);
            viewHolder.name.setText(ingredient.getFood_name());
            viewHolder.nutrition.setText(ingredient.getNf_calories());
            //viewHolder.image.setImageResource(ingredient.getImage());
            Picasso.with(mContext).load(ingredient.getImage())
                    .error(R.drawable.ic_filter)
                    .placeholder(R.drawable.ic_filter)
                    .into(viewHolder.image);
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

    @Nullable
    @Override
    public Ingredient getItem(int position) {
        if (mIngredientList != null) {
            return mIngredientList.get(position);
        }
        return super.getItem(position);
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
