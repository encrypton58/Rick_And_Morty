package com.em_and_ei.company.rickandmorty.interfaces;

import android.content.Context;

import com.em_and_ei.company.rickandmorty.models.objects.Character;

import java.util.List;

public interface PresenterInterface {

    void getCharacteres(Context context, int page);

    void getOneCharacter(Context context, int id);

    void getAllCharacteres(List<Character> characters);

    void getCharacter(Character character);

}
