package com.em_and_ei.company.rickandmorty.interfaces;

import android.content.Context;

public interface ModelInterface {

    void getCharacteres(Context context, int page);

    void getCharacter(Context context, int id);

}
