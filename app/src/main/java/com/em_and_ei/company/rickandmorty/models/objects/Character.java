package com.em_and_ei.company.rickandmorty.models.objects;

import com.em_and_ei.company.rickandmorty.views.DetailsCharacter;

import java.io.Serializable;

public class Character implements Serializable {

    int id;
    String name;
    String status;
    String species;
    String type;
    String gender;
    String image;
    String url;
    String created;
    Origin origin;
    Location location;
    String page;
    int favoriteImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPage(String page){
        this.page = page;
    }

    public String getPage(){
        return this.page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setFavoriteImage(int idImage){ favoriteImage = idImage; }

    public int getFavoriteImage(){return favoriteImage;}

    public static class Origin implements Serializable{
        String name;
        String url;
        public Origin(String name, String url){
            this.name = name;
            this.url = url;
        }

        public String getName(){
            return name;
        }
        public String getUrl(){
            return url;
        }
    }

    public static class Location implements Serializable{
        String name;
        String url;
        public  Location(String name, String url){
            this.name = name;
            this.url = url;
        }
        public String getName(){
            return name;
        }
        public String getUrl(){
            return url;
        }
    }



}