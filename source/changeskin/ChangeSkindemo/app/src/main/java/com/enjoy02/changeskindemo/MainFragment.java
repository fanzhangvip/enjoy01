package com.enjoy02.changeskindemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;



public class MainFragment extends ListFragment {


    public static Fragment newIntance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    ArrayAdapter<String> arrayAdapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] array = new String[]{
                "SimpleHookViewDemo",
                "平时改变背景的办法",
                "换肤demo",
        };
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        String item = arrayAdapter.getItem(position);
        Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
        switch (position) {
            case 0:
                Intent intent0 = new Intent(getActivity(), SimpleDemoActivity.class);
                startActivity(intent0);
                break;
            case 1:
                Intent intent1 = new Intent(getActivity(), NormalChangeActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }


}
