package com.example.projetoiseaux.ui.searchTool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.Bird.Bird;
import com.example.projetoiseaux.ui.SearchResult.ListBird;
import com.example.projetoiseaux.ui.SearchResult.SearchResult;

import java.util.List;

import static com.example.projetoiseaux.Bird.Bird.COLORS;
import static com.example.projetoiseaux.Bird.Bird.SIZES;

public class SearchActivity extends AppCompatActivity {

    private static final List<Bird> BIRDS = new ListBird();

    private String selectedColor = "Any";
    private int selectedSize = 2;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText editText = findViewById(R.id.actv);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                // go to search List
                Intent intent = new Intent(this, SearchResult.class);
                intent.putExtra("name", editText.getText().toString());
                intent.putExtra("color", selectedColor);
                intent.putExtra("size", selectedSize);
                startActivity(intent);
                return true;
            }
            return false;
        });

        Spinner colorSpinner = findViewById(R.id.spinner_color);
        ColorAdapter colorAdapter = new ColorAdapter(this, COLORS);

        colorSpinner.setAdapter(colorAdapter);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedColor = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(SearchActivity.this, clickedColor + " selected", Toast.LENGTH_SHORT).show();
                selectedColor = clickedColor;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        NumberPicker sizePicker = findViewById(R.id.size_picker);
        ImageView birdPreview = findViewById(R.id.birdPreview);

        sizePicker.setMaxValue(SIZES.length - 1);
        sizePicker.setMinValue(0);
        sizePicker.setValue(2);
        sizePicker.setDisplayedValues(SIZES);

        sizePicker.setOnValueChangedListener((numberPicker, oldValue, newValue) -> {
            birdPreview.getLayoutParams().height = newValue * 75;
            birdPreview.getLayoutParams().width = newValue * 75;
            birdPreview.requestLayout();
            selectedSize = newValue;
        });

        Button search = findViewById(R.id.search);
        search.setOnClickListener(click -> {
            Intent intent = new Intent(SearchActivity.this, SearchResult.class);
            intent.putExtra("name", editText.getText().toString());
            intent.putExtra("color", this.selectedColor);
            intent.putExtra("size", this.selectedSize);
            startActivity(intent);
        });
    }
}
