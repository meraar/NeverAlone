package com.example.neveralone.Activity.ListaChat;

public class RelacionChat {
    private String idUs1;
    private String idFriendUser;
    private String friendName;
    private String idPeticion;

    public RelacionChat(String idFriendUser, String friendName, String idPeticion) {
        this.idFriendUser = idFriendUser;
        this.friendName = friendName;
        this.idPeticion = idPeticion;
    }

    public String getIdUs1() {
        return idUs1;
    }

    public void setIdUs1(String idUs1) {
        this.idUs1 = idUs1;
    }

    public String getIdFriendUser() {
        return idFriendUser;
    }

    public void setIdFriendUser(String idFriendUser) {
        this.idFriendUser = idFriendUser;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getidPeticion() {
        return idPeticion;
    }

    public void setidPeticion(String idPeticion) {
        this.idPeticion = idPeticion;
    }
}
