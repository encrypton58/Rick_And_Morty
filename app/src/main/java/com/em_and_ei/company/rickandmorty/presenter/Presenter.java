package com.em_and_ei.company.rickandmorty.presenter;

import android.content.Context;
import com.em_and_ei.company.rickandmorty.interfaces.ModelInterface;
import com.em_and_ei.company.rickandmorty.interfaces.PresenterInterface;
import com.em_and_ei.company.rickandmorty.interfaces.ViewInterface;
import com.em_and_ei.company.rickandmorty.models.HttpRequest;
import com.em_and_ei.company.rickandmorty.models.objects.Character;
import com.em_and_ei.company.rickandmorty.views.View;

import java.util.List;

public class Presenter implements PresenterInterface {
    public static ViewInterface viewInterface;
    ModelInterface model = new HttpRequest();

    public Presenter(ViewInterface vi){
        viewInterface = vi;
    }

    public Presenter() {}

    @Override
    public void getAllCharacteres(List<Character> characters){
        viewInterface.getAllCharacteres(characters);
    }

    @Override
    public void getCharacter(Character character) {
        viewInterface.getCharacter(character);
    }

    @Override
    public void getCharacteres(Context context, int page) {
        model.getCharacteres(context, page);
    }

    @Override
    public void getOneCharacter(Context context, int id) {
        model.getCharacter(context, id);
    }

}
