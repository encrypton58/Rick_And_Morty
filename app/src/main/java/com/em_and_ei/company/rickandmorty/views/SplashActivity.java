package com.em_and_ei.company.rickandmorty.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.em_and_ei.company.rickandmorty.interfaces.PresenterInterface;
import com.em_and_ei.company.rickandmorty.interfaces.ViewInterface;
import com.em_and_ei.company.rickandmorty.models.objects.Character;
import com.em_and_ei.company.rickandmorty.presenter.Presenter;
import com.em_and_ei.company.rickandmorty.utils.HelperSharedPreferences;
import java.io.Serializable;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements ViewInterface {

    private final String KEY_PAGE = "page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PresenterInterface presenter = new Presenter(this);
        HelperSharedPreferences.getInstance(getApplicationContext());
        int page = HelperSharedPreferences.getIntValue(KEY_PAGE);
        presenter.getCharacteres(SplashActivity.this, page);
    }

    @Override
    public void getAllCharacteres(List<Character> characteres) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("Characteres", (Serializable) characteres);
        startActivity(intent);
    }

    @Override
    public void getCharacter(Character character) {

    }
}