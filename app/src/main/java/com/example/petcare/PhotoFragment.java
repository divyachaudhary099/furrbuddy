package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PhotoFragment extends Fragment {

    private RecyclerView gridView;
    private FloatingActionButton fab;

    private MyImagesViewModel myImagesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        gridView = view.findViewById(R.id.gridView);
        fab = view.findViewById(R.id.fabShare);

        gridView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        final MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);

        myImagesViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(MyImagesViewModel.class);
        myImagesViewModel.getAllImages().observe(getViewLifecycleOwner(), myImages -> adapter.setImagesList(myImages));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                myImagesViewModel.delete(adapter.getPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(requireContext(), "Item was deleted!", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(gridView);

        adapter.setListener(myImages -> {
            Intent intent = new Intent(getActivity(), UpdateImageActivity.class);
            intent.putExtra("id", myImages.getImage_id());
            intent.putExtra("title", myImages.getImage_title());
            intent.putExtra("image", myImages.getImage());
            startActivityForResult(intent, 4);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == getActivity().RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            byte[] image = data.getByteArrayExtra("image");

            MyImages myImages = new MyImages(title,image);
            myImagesViewModel.insert(myImages);
            Toast.makeText(requireContext(), "New item was added!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 4 && resultCode == getActivity().RESULT_OK && data != null) {
            String title = data.getStringExtra("updateTitle");
            byte[] image = data.getByteArrayExtra("image");
            int id = data.getIntExtra("id", -1);

            MyImages myImages = new MyImages(title, image);
            myImages.setImage_id(id);
            myImagesViewModel.update(myImages);
            Toast.makeText(requireContext(), "Item was updated!", Toast.LENGTH_SHORT).show();
        }
    }
}
