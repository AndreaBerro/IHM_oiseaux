package com.example.projetoiseaux.ui.searchTool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.Bird;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteBirdAdapter extends ArrayAdapter<Bird> {

    private List<Bird> birdListFull;

    public AutoCompleteBirdAdapter(@NonNull Context context, @NonNull List<Bird> birdList) {
        super(context, 0, birdList);
        birdListFull = new ArrayList<>(birdList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return birdFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.bird_autocomplete_row, parent, false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.text_view_name);
        ImageView imageViewPicture = convertView.findViewById(R.id.image_view_picture);

        Bird birdItem = getItem(position);

        if (birdItem != null) {
            textViewName.setText(birdItem.getName());
            imageViewPicture.setImageResource(birdItem.getPicture());
        }

        return convertView;
    }

    private final Filter birdFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<Bird> suggestions = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(birdListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                System.out.println(birdListFull);
                for (Bird bird : birdListFull) {
                    if (bird.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(bird);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Bird) resultValue).getName();
        }
    };
}
