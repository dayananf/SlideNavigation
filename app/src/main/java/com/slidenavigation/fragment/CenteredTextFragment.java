package com.slidenavigation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slidenavigation.R;
import com.slidenavigation.StateAdapter;

import java.util.ArrayList;

public class CenteredTextFragment extends Fragment {

    private static final String EXTRA_TEXT = "text";

    public static CenteredTextFragment createFor(String text) {
        CenteredTextFragment fragment = null;
        try {
            fragment = new CenteredTextFragment();
            Bundle args = new Bundle();
            args.putString(EXTRA_TEXT, text);
            fragment.setArguments(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        final String text = getArguments().getString(EXTRA_TEXT);

        RecyclerView recycle_view = view.findViewById(R.id.recycle_view);
        recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("Andaman and Nicobar Islands");
        nameList.add("Andhra Pradesh");
        nameList.add("Arunachal Pradesh");
        nameList.add("Assam");
        nameList.add("Bihar");
        nameList.add("Chandigarh");
        nameList.add("Chattisgarh");
        nameList.add("Dadra and Nagar Haveli");
        nameList.add("Daman and Diu");
        nameList.add("Delhi");
        nameList.add("Goa");
        nameList.add("Gujarat");
        nameList.add("Haryana");
        nameList.add("Himachal Pradesh");
        nameList.add("Jammu and Kashmir");
        nameList.add("Jharkhand");
        nameList.add("Karnataka");
        nameList.add("Kerala");
        nameList.add("Lakshadweep Islands");
        nameList.add("Madhya Pradesh");
        nameList.add("Maharashtra");
        nameList.add("Manipur");

        StateAdapter adapter = new StateAdapter(getActivity(), nameList);
        recycle_view.setAdapter(adapter);

    }
}
