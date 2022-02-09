package com.em_and_ei.company.rickandmorty.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.em_and_ei.company.rickandmorty.R;
import com.em_and_ei.company.rickandmorty.databinding.ActivityDetailsCharacterBinding;
import com.em_and_ei.company.rickandmorty.interfaces.PresenterInterface;
import com.em_and_ei.company.rickandmorty.interfaces.ViewInterface;
import com.em_and_ei.company.rickandmorty.models.objects.Character;
import com.em_and_ei.company.rickandmorty.presenter.Presenter;
import com.em_and_ei.company.rickandmorty.utils.CircleTranform;
import com.em_and_ei.company.rickandmorty.utils.HelperSharedPreferences;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

public class DetailsCharacter extends AppCompatActivity implements ViewInterface {

    private ActivityDetailsCharacterBinding binding;
    ProgressDialog chargingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsCharacterBinding.inflate(getLayoutInflater());
        HelperSharedPreferences.getInstance(getApplicationContext());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        String idString = String.valueOf(id);
        binding.detailsBtnReturn.setOnClickListener(view -> {
            if(HelperSharedPreferences.getIntValue(idString) == R.drawable.icon_favorite){
                HelperSharedPreferences.saveInt(idString, R.drawable.icon_out_heart);
            }else if(HelperSharedPreferences.getIntValue(idString) == R.drawable.icon_out_heart){
                HelperSharedPreferences.saveInt(idString, R.drawable.icon_favorite);
            }
            setTextFavorite(idString);
        });
        chargingDialog = new ProgressDialog(DetailsCharacter.this);
        chargingDialog.show();
        chargingDialog.setContentView(R.layout.charging_dialog);
        chargingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initView();
        PresenterInterface presenter = new Presenter(this);
        presenter.getOneCharacter(getApplicationContext(), id);
    }

    private void initView() {
        binding.detailsActWaves.setVisibility(View.INVISIBLE);
        binding.detailsActWaves.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.waves));
    }

    @Override
    public void getAllCharacteres(List<Character> characteres) {

    }

    private void setTextFavorite(String id){
        switch (HelperSharedPreferences.getIntValue(id)){
            case R.drawable.icon_favorite:
                binding.detailsBtnReturn.setText(getText(R.string.details_btn_text_quit_favorite));
                break;
            case R.drawable.icon_out_heart:
                binding.detailsBtnReturn.setText(getText(R.string.details_btn_text_add_favorite));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void getCharacter(Character character) {
        ImageView image = findViewById(R.id.details_act_image);
        Picasso.get().load(character.getImage()).transform(new CircleTranform()).into(image, new com.squareup.picasso.Callback() {

            @Override
            public void onSuccess() {
                Bitmap map = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Palette.from(map).generate(palette -> {
                    assert palette != null;
                    int colorDominant = palette.getDominantColor(Color.rgb(100, 100, 100));
                    binding.detailsActCard.setBackgroundColor(colorDominant);
                    ColorFilter colorFilter = new LightingColorFilter(Color.TRANSPARENT, colorDominant);
                    binding.detailsActWaves.getDrawable().setColorFilter(colorFilter);
                    binding.detailsActWaves.setVisibility(View.VISIBLE);
                    chargingDialog.dismiss();
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });
        binding.detailsActName.setText(character.getName());
        binding.detailsActStatus.append(" " + character.getStatus());
        binding.detailsActSpecies.append(" " + character.getSpecies());
        binding.detailsActType.append(
                character.getType().isEmpty() ? " " + getText(R.string.details_no_contenteType) :
                        " " + character.getType());
        binding.detailsActGender.append(" " + character.getGender());
        binding.detailsActLocation.append(" " + character.getLocation().getName());
        binding.detailsActOrigin.append(" " + character.getOrigin().getName());
        binding.detailsActCreated.append(" " + character.getCreated().split("T")[0]);
        binding.detailsInfo.append(getText(R.string.details_information_text) + " " + character.getName());
        if (character.getStatus().equals("Dead")) {
            binding.detailsActStatus.setTextColor(Color.RED);
        } else if (character.getStatus().equals("Alive")) {
            binding.detailsActStatus.setTextColor(Color.rgb(139, 163, 126));
        }

    }
}