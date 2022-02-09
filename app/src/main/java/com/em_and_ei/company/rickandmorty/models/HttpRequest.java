package com.em_and_ei.company.rickandmorty.models;

import android.app.AlertDialog;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.em_and_ei.company.rickandmorty.R;
import com.em_and_ei.company.rickandmorty.http.URLS;
import com.em_and_ei.company.rickandmorty.http.VolleySingleton;
import com.em_and_ei.company.rickandmorty.interfaces.ModelInterface;
import com.em_and_ei.company.rickandmorty.interfaces.PresenterInterface;
import com.em_and_ei.company.rickandmorty.models.objects.Character;
import com.em_and_ei.company.rickandmorty.presenter.Presenter;
import com.em_and_ei.company.rickandmorty.utils.HelperSharedPreferences;
import com.em_and_ei.company.rickandmorty.views.DetailsCharacter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpRequest implements ModelInterface {

    @Override
    public void getCharacteres(Context context, int page) {
        PresenterInterface presenter = new Presenter();
        HelperSharedPreferences.getInstance(context);
        JsonObjectRequest jsonRequest =
                new JsonObjectRequest(Request.Method.GET, URLS.GET_CHARACTERES + page, null, response -> {
            try {
                System.out.println(response.toString());
                JSONObject info = response.getJSONObject("info");
                String nextPage = info.getString("next");
                JSONArray results = response.getJSONArray("results");
                List<Character> characteres = new ArrayList<>();
                for(int i = 0; i < Objects.requireNonNull(results).length(); i++){
                    JSONObject object = results.getJSONObject(i);
                    Character character = new Character();
                    character.setId(object.getInt("id"));
                    character.setName(object.getString("name"));
                    character.setPage(nextPage);
                    character.setStatus(object.getString("status"));
                    character.setSpecies(object.getString("species"));
                    character.setType(object.getString("type"));
                    character.setGender(object.getString("gender"));
                    JSONObject origin = object.getJSONObject("origin");
                    character.setOrigin(new Character.Origin(origin.getString("name"), origin.getString("url")));
                    JSONObject location = object.getJSONObject("location");
                    character.setLocation(new Character.Location(location.getString("name"), location.getString("url")));
                    character.setImage(object.getString("image"));
                    character.setFavoriteImage(HelperSharedPreferences.getIntValue(String.valueOf(character.getId())));
                    HelperSharedPreferences.saveInt(String.valueOf(character.getId()), character.getFavoriteImage());
                    characteres.add(character);
                }
                presenter.getAllCharacteres(characteres);
            }catch(JSONException e){
                System.out.println(e.getMessage());
            }
                }, error ->{
                    AlertDialog.Builder build = new AlertDialog.Builder(context)
                            .setTitle("Message Error")
                            .setMessage(error.getMessage())
                            .setPositiveButton("Accept", (dialogInterface, i) -> dialogInterface.dismiss())
                            .setCancelable(false);
                    AlertDialog dialog = build.create();
                    dialog.show();
                }
        );
        VolleySingleton.getInstance(context).addRequestQueque(jsonRequest);
    }

    @Override
    public void getCharacter(Context context, int id) {
        PresenterInterface presenter = new Presenter();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLS.GET_CHARACTER + id,
                null, response -> {
            Character character = new Character();
            try{
                character.setId(response.getInt("id"));
                character.setName(response.getString("name"));
                character.setStatus(response.getString("status"));
                character.setSpecies(response.getString("species"));
                character.setType(response.getString("type"));
                character.setGender(response.getString("gender"));
                JSONObject origin = response.getJSONObject("origin");
                character.setOrigin(new Character.Origin(origin.getString("name"), origin.getString("url")));
                JSONObject location = response.getJSONObject("location");
                character.setLocation(new Character.Location(location.getString("name"), location.getString("url")));
                character.setImage(response.getString("image"));
                character.setUrl(response.getString("url"));
                character.setCreated(response.getString("created"));
                presenter.getCharacter(character);
            }catch (JSONException e){
                System.out.println(e.getMessage());
            }

        }, error ->
            System.out.println(error.getMessage())
        );
        VolleySingleton.getInstance(context).addRequestQueque(jsonObjectRequest);
    }
}
