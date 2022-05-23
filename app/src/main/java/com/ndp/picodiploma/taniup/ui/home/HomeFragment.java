package com.ndp.picodiploma.taniup.ui.home;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.ndp.picodiploma.taniup.Article;
import com.ndp.picodiploma.taniup.ListArticleAdapter;
import com.ndp.picodiploma.taniup.Login.Login;
import com.ndp.picodiploma.taniup.R;
import com.ndp.picodiploma.taniup.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<Article> list = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvArtikel.setHasFixedSize(true);
        list.addAll(getListArticle());
        showRecyclerList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<Article> getListArticle() {

        String [] dataTitle = getResources().getStringArray(R.array.data_title);
        String [] dataDesc = getResources().getStringArray(R.array.data_description);
        String [] dataLink = getResources().getStringArray(R.array.data_link);
        TypedArray dataPhoto = getResources().obtainTypedArray(R.array.data_photo);

        ArrayList<Article> listArticle = new ArrayList<>();
        for (int i = 0; i <  dataTitle.length; i++) {
            Article art = new Article();
            art.setTitle(dataTitle[i]);
            art.setDescription(dataDesc[i]);
            art.setLink(dataLink[i]);
            art.setPhoto(dataPhoto.getResourceId(i, -1));

            listArticle.add(art);
        }
        return listArticle;
    }
    private void showRecyclerList(){
        binding.rvArtikel.setLayoutManager(new LinearLayoutManager(getContext()));
        ListArticleAdapter listArticleAdapter = new ListArticleAdapter(list);
        binding.rvArtikel.setAdapter(listArticleAdapter);

        listArticleAdapter.setOnItemClickCallBack(datas -> selectedArticle(datas));
    }

    private void selectedArticle(Article art) {
        String url = art.getLink();
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browser);

    }

}