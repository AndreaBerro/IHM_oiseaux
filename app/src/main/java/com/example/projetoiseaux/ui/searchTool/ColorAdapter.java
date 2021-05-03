package com.example.projetoiseaux.ui.searchTool;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetoiseaux.R;

import java.util.ArrayList;

public class ColorAdapter extends ArrayAdapter<String> {

    public ColorAdapter(Context context, ArrayList<String> colorList) {
        super(context, 0, colorList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.color_spinner_row, parent, false
            );
        }
        View colorView = convertView.findViewById(R.id.color_rectangle);
        TextView textViewName = convertView.findViewById(R.id.color_name);

        String currentColor = getItem(position);

        if (currentColor != null) {
            colorView.setBackgroundColor(currentColor.equals("Any") ?
                    Color.parseColor("white") : Color.parseColor(currentColor.toLowerCase()));
            textViewName.setText(currentColor);
        }
        return convertView;
    }
}
