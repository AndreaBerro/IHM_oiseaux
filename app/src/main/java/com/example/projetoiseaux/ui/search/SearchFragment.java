package com.example.projetoiseaux.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
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

import static com.example.projetoiseaux.ui.Bird.FULL_LIST;

public class SearchFragment extends Fragment implements IBridInfo{

    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        root.findViewById(R.id.filter).setOnClickListener(click -> {
            Intent filter = new Intent(getContext(), SearchActivity.class);
            startActivity(filter);
        });

        final TextView textView = root.findViewById(R.id.text_home);
        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        AutoCompleteTextView editText = root.findViewById(R.id.search_editor);
        AutoCompleteBirdAdapter textAdapter = new AutoCompleteBirdAdapter(requireContext(), FULL_LIST);
        editText.setAdapter(textAdapter);

        // Make a search button in the Virtual KeyBoard
        initView(root.findViewById(R.id.search_editor));

        return root;
    }

    private void initView(EditText editText){
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
                startActivity(intent);

                return true;
            }
            return false;
        });
    }

}