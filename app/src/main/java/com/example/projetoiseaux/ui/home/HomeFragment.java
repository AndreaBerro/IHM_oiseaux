package com.example.projetoiseaux.ui.home;

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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.SearchResult.SearchResult;

public class HomeFragment extends Fragment implements IBridInfo{

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Log.d("EditText", "111");
        initView((EditText)root.findViewById(R.id.search_editor)); // Create a search button in the virtuel KeyBoard
        Log.d("EditText", "222");

        return root;
    }

    private void initView(EditText editText){
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    // 先隐藏键盘
                    ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                    // go to search List
                    Intent intent = new Intent(getContext(), SearchResult.class);
                    intent.putExtra(BRID_INFO, editText.getText().toString());
                    startActivity(intent);

                    Log.d("EditText", "is ok ?");
                    return true;
                }
                return false;
            }
        });
    }

}