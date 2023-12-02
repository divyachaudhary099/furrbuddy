package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class AboutFragment extends Fragment {

    com.example.petcare.databinding.FragmentAboutBinding binding;
    ListAdapter listAdapter;
    ArrayList<ListData> dataArrayList = new ArrayList<>();
    ListData listData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = com.example.petcare.databinding.FragmentAboutBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        int[] imageList = {R.drawable.dogs, R.drawable.cats, R.drawable.rabbits, R.drawable.ferrets, R.drawable.rodents, R.drawable.horses, R.drawable.fish};
        int[] petsList = {R.string.dogs, R.string.cats, R.string.rabbits, R.string.ferrets, R.string.rodents, R.string.horses, R.string.fish};
        int[] descList = {R.string.dogsDesc, R.string.catsDesc, R.string.rabbitsDesc, R.string.ferretsDesc, R.string.rodentsDesc, R.string.horsesDesc, R.string.fishDesc};
        String[] nameList = {"Dogs", "Cats", "Rabbits", "Ferrets", "Rodents", "Horses", "Fish"};
        for (int i = 0; i < imageList.length; i++) {
            listData = new ListData(nameList[i], petsList[i], descList[i], imageList[i]);
            dataArrayList.add(listData);
        }
        listAdapter = new ListAdapter(requireContext(), dataArrayList);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((AdapterView.OnItemClickListener) (adapterView, view, i, l) -> {
            Intent intent = new Intent(getActivity(), DetailedActivity.class);
            intent.putExtra("name", nameList[i]);
            intent.putExtra("ingredients", petsList[i]);
            intent.putExtra("desc", descList[i]);
            intent.putExtra("image", imageList[i]);
            startActivity(intent);
        });
        return rootView;
    }
}