package com.em_and_ei.company.rickandmorty.interfaces;

import com.em_and_ei.company.rickandmorty.models.objects.Character;

import java.util.List;

public interface ViewInterface {

    void getAllCharacteres(List<Character> characteres);

    void getCharacter(Character character);

}
