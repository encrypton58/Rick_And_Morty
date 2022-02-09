package com.em_and_ei.company.rickandmorty.more;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;
import com.em_and_ei.company.rickandmorty.utils.HelperSharedPreferences;
import com.em_and_ei.company.rickandmorty.R;
import com.em_and_ei.company.rickandmorty.models.objects.Character;
import com.em_and_ei.company.rickandmorty.utils.CircleTranform;
import com.em_and_ei.company.rickandmorty.views.DetailsCharacter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CharactersRecyclerAdapter extends RecyclerView.Adapter<CharactersRecyclerAdapter.CharacterViewHolder> {

    List<Character> characteres = new ArrayList<>();
    private static OnClickItem onClickItem;

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_recycler_item, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.render(characteres.get(position));
    }

    @Override
    public int getItemCount() {
        return characteres.size();
    }

    public void setOnClickItem(OnClickItem onClickItem){
        CharactersRecyclerAdapter.onClickItem = onClickItem;
    }

    public void setCharacteres(List<Character> characteres) {
        this.characteres = new ArrayList<>(characteres);
        this.notifyDataSetChanged();
    }

    protected static class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageCharacter;
        CardView card;
        Character character;
        ImageView favorite;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void render(Character character){
            this.character = character;
            imageCharacter = itemView.findViewById(R.id.character_item_image);
            card = itemView.findViewById(R.id.character_card);
            card.setOnClickListener(this);
            favorite = itemView.findViewById(R.id.character_item_favorite);
            HelperSharedPreferences.getInstance(itemView.getContext());
            favorite.setImageResource(character.getFavoriteImage());
            Picasso.get().load(character.getImage()).transform(new CircleTranform()).into(imageCharacter, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    Bitmap map = ((BitmapDrawable) imageCharacter.getDrawable()).getBitmap();
                    Palette.from(map).generate(palette -> {
                        assert palette != null;
                        int colorDominant = palette.getDominantColor(Color.rgb(100, 100, 100));
                        card.setCardBackgroundColor(colorDominant);
                    });
                }

                @Override
                public void onError(Exception e) {
                    System.out.println(e.getMessage());
                }
            });
            TextView name = itemView.findViewById(R.id.character_item_name);
            name.setText(character.getName());
            TextView status = itemView.findViewById(R.id.character_item_status);
            status.setText(character.getStatus());
            switch (character.getStatus()) {
                case "Alive":
                    status.setTextColor(Color.rgb(139,163,126));
                    break;
                case "Dead":
                    status.setTextColor(Color.rgb(255, 0, 0));
                    break;
                case "unknown":
                    status.setTextColor(Color.rgb(0,0,0));
                    break;
            }
            TextView id = itemView.findViewById(R.id.character_item_id);
            String idSet = "#0" + character.getId();
            id.setText(idSet);
            id.setTextColor(Color.argb(100, 255, 255,255));
        }

        @Override
        public void onClick(View view) {
            onClickItem.onItemClick(getAdapterPosition(), character.getId());
        }
    }

    public interface OnClickItem{
        void onItemClick(int position, int idCharacter);
    }
}
