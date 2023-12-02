package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    com.example.petcare.databinding.FragmentHomeBinding binding;
    ListAdapter2 listAdapter;
    ArrayList<ListData2> dataArrayList = new ArrayList<>();
    ListData2 listData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = com.example.petcare.databinding.FragmentHomeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        int[] imageList = {R.drawable.come, R.drawable.walking, R.drawable.sit, R.drawable.liedown, R.drawable.stay, R.drawable.givefive, R.drawable.tips};
        int[] commandsList = {R.string.command1, R.string.command2, R.string.command3, R.string.command4, R.string.command5, R.string.command6, R.string.command7};
        String[] nameList = {"Come when called", " Loose-leash walking", "Sit", "Lie down", "Stay", "Give a high five", "Training tips"};
        String[] timeList = {"All time!", "30 mins", "5 mins", "5 mins", "10 mins", "5 mins", "Use all time!"};
        for (int i = 0; i < imageList.length; i++) {
            listData = new ListData2(nameList[i], timeList[i], commandsList[i], imageList[i]);
            dataArrayList.add(listData);
        }
        listAdapter = new ListAdapter2(requireContext(), dataArrayList);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((AdapterView.OnItemClickListener) (adapterView, view, i, l) -> {
            Intent intent = new Intent(getActivity(), DetailedActivity2.class);
            intent.putExtra("name", nameList[i]);
            intent.putExtra("time", timeList[i]);
            intent.putExtra("ingredients", commandsList[i]);
            intent.putExtra("image", imageList[i]);
            startActivity(intent);
        });
        return rootView;
    }
}