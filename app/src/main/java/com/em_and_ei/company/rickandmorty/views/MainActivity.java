package com.em_and_ei.company.rickandmorty.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.em_and_ei.company.rickandmorty.databinding.ActivityMainBinding;
import com.em_and_ei.company.rickandmorty.interfaces.PresenterInterface;
import com.em_and_ei.company.rickandmorty.interfaces.ViewInterface;
import com.em_and_ei.company.rickandmorty.models.objects.Character;
import com.em_and_ei.company.rickandmorty.more.CharactersRecyclerAdapter;
import com.em_and_ei.company.rickandmorty.presenter.Presenter;
import com.em_and_ei.company.rickandmorty.utils.HelperSharedPreferences;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements ViewInterface {

    private final String KEY_PAGE = "page";
    PresenterInterface presenter = new Presenter(this);
    private ActivityMainBinding binding;
    CharactersRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        HelperSharedPreferences.getInstance(getApplicationContext());
        initView();
    }

    @Override
    public void getAllCharacteres(List<Character> characteres) {
        adapter.setCharacteres(characteres);
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter = new Presenter(this);
    }

    private void initView(){
        adapter = new CharactersRecyclerAdapter();
        if(getIntent() != null){
            if(getIntent().getSerializableExtra("Characteres") != null && HelperSharedPreferences.getIntValue(KEY_PAGE) == 1){
                adapter.setCharacteres((List<Character>) getIntent().getSerializableExtra("Characteres"));
            }else{
                presenter.getCharacteres(getApplicationContext(), HelperSharedPreferences.getIntValue(KEY_PAGE));
            }
        }
        binding.characterRecycler.setAdapter(adapter);
        binding.characterRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.mainActPrev.setEnabled(HelperSharedPreferences.getIntValue(KEY_PAGE) != 1);
        ObservablePagination paginationObserbable = new ObservablePagination(HelperSharedPreferences.getIntValue(KEY_PAGE));
        ObserverPagination paginationObserver = new ObserverPagination(paginationObserbable,
                binding.mainActPrev, binding.paginationIndexNumber);
        paginationObserbable.addObserver(paginationObserver);
        binding.mainActPrev.setOnClickListener(view -> {
            HelperSharedPreferences.saveInt(KEY_PAGE,HelperSharedPreferences.getIntValue(KEY_PAGE) - 1);
            paginationObserbable.setPagination(HelperSharedPreferences.getIntValue(KEY_PAGE));
            presenter.getCharacteres(getApplicationContext(), HelperSharedPreferences.getIntValue(KEY_PAGE));
            binding.characterRecycler.scrollToPosition(0);
        });
        binding.mainActNext.setOnClickListener(view -> {
            HelperSharedPreferences.saveInt(KEY_PAGE, HelperSharedPreferences.getIntValue(KEY_PAGE) + 1);
            paginationObserbable.setPagination(HelperSharedPreferences.getIntValue(KEY_PAGE));
            presenter.getCharacteres(getApplicationContext(), HelperSharedPreferences.getIntValue(KEY_PAGE));
            binding.characterRecycler.scrollToPosition(0);
        });
        adapter.setOnClickItem((position, idCharacter) -> {
            Intent i = new Intent(getApplicationContext(), DetailsCharacter.class);
            i.putExtra("id", idCharacter);
           startActivity(i);
           finish();
        });
    }

    //______ no important much

    @Override
    public void getCharacter(Character character) {

    }

    private static class ObservablePagination extends Observable {

        private int pagination;

        public ObservablePagination(int pagination){
            this.pagination = pagination;
        }

        public void setPagination(int value){
            this.pagination = value;
            setChanged();
            notifyObservers();
        }


    }

    private static class ObserverPagination implements Observer{
        ObservablePagination pagination;
        Button prevPagination;
        TextView pageView;

        public ObserverPagination(ObservablePagination pagination, Button prevPagination, TextView pageView){
            this.pagination = pagination;
            this.prevPagination = prevPagination;
            this.pageView = pageView;
        }

        @Override
        public void update(Observable observable, Object o) {
            if(observable == pagination){
                prevPagination.setEnabled(pagination.pagination != 1);
                pageView.setText(String.valueOf(pagination.pagination));
            }
        }
    }

}