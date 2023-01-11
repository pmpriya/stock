package com.example.app16.ui.main;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;

import java.io.InputStream;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.app16.R;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;

import android.view.View.OnClickListener;

import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.util.Log;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.webkit.WebView;
import android.widget.TextView;


public class findQuoteFragment extends Fragment implements OnClickListener {
    View root;
    Context myContext;
    findQuoteBean findquotebean;

    EditText findQuotedateFromField;
    EditText findQuotedateToField;
    EditText findQuoteFirstSymbolField;
    EditText findQuoteSecondSymbolField;
    String findQuotedateFrom = "";
    String findQuotedateTo = "";
    String findQuoteFirstSymbol = "";
    TextView findQuoteResult;
    Button findQuoteOkButton;
    Button findQuoteSecondSymbolButton;
    Button findQuotecancelButton;


    public findQuoteFragment() {
    }

    public static findQuoteFragment newInstance(Context c) {
        findQuoteFragment fragment = new findQuoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.myContext = c;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.findquote_layout, container, false);
        Bundle data = getArguments();
        findQuotedateFromField = (EditText) root.findViewById(R.id.findQuotedateFromField);
        findQuotedateToField = (EditText) root.findViewById(R.id.findQuotedateToField);
        findQuoteFirstSymbolField = (EditText) root.findViewById(R.id.findQuoteFirstSymbol);
        findQuoteSecondSymbolField = (EditText) root.findViewById(R.id.findQuoteSecondSymbol);
        findQuoteResult = (TextView) root.findViewById(R.id.findQuoteResult);
        findquotebean = new findQuoteBean(myContext);
        findQuoteOkButton = root.findViewById(R.id.findQuoteOK);
        findQuoteSecondSymbolButton = root.findViewById(R.id.findQuoteOKSecond);
        findQuoteOkButton.setOnClickListener(this);
        findQuoteSecondSymbolButton.setOnClickListener(this);
        findQuotecancelButton = root.findViewById(R.id.findQuoteCancel);
        findQuotecancelButton.setOnClickListener(this);
        return root;
    }


    public void onClick(View _v) {
        InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        try {
            _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0);
        } catch (Exception _e) {
        }
        if (_v.getId() == R.id.findQuoteOK) {
            findQuoteOK(findQuoteFirstSymbolField.getText()+"",1);
        } else if (_v.getId() == R.id.findQuoteOKSecond) {
            findQuoteOK(findQuoteSecondSymbolField.getText()+"",2);
        }else if (_v.getId() == R.id.findQuoteCancel) {
            findQuoteCancel(_v);
        }
    }

    public void findQuoteOK(String symbol, int id) {
        findQuotedateFrom = findQuotedateFromField.getText() + "";
        findQuotedateTo = findQuotedateToField.getText() + "";
        findquotebean.setdate(findQuotedateFrom, findQuotedateTo);
        if (findquotebean.isfindQuoteerror()) {
            Log.w(getClass().getName(), findquotebean.errors());
            Toast.makeText(myContext, "Errors: " + findquotebean.errors(), Toast.LENGTH_LONG).show();
        } else {
                findQuoteResult.setText(findquotebean.findQuote(symbol,id) + "");
        }
    }


    public void findQuoteCancel(View _v) {
        findquotebean.resetData();
        findQuotedateFromField.setText("");
        findQuotedateToField.setText("");
        findQuoteResult.setText("");
        findQuoteFirstSymbolField.setText("");
        findQuoteSecondSymbolField.setText("");
    }
}
