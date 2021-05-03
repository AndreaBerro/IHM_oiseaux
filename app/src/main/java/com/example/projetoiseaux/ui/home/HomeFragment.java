package com.example.projetoiseaux.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.SearchResult.SearchResult;
import com.example.projetoiseaux.ui.searchTool.AutoCompleteBirdAdapter;
import com.example.projetoiseaux.ui.searchTool.SearchActivity;

import java.util.Objects;

import static com.example.projetoiseaux.ui.Bird.FULL_LIST;

public class HomeFragment extends Fragment implements IBridInfo{

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        root.findViewById(R.id.filter).setOnClickListener(click -> {
            Intent filter = new Intent(getContext(), SearchActivity.class);
            startActivity(filter);
        });

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        AutoCompleteTextView editText = root.findViewById(R.id.search_editor);
        AutoCompleteBirdAdapter textAdapter = new AutoCompleteBirdAdapter(getContext(), FULL_LIST);
        editText.setAdapter(textAdapter);

        // Make a search button in the Virtual KeyBoard
        initView(root.findViewById(R.id.search_editor));

        return root;
    }

    private void initView(AutoCompleteTextView editText){
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                // go to search List
                Intent intent = new Intent(getContext(), SearchResult.class);
                intent.putExtra("name", editText.getText().toString());
                intent.putExtra("color", "Any");
                intent.putExtra("size", 0);
                startActivity(intent);

                Log.d("mylog", "is ok ?");
                return true;
            }
            return false;
        });
    }

}