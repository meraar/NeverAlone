package com.example.neveralone.Activity.ListaChat;

public class RelacionChatTutor {
    private String idFriendUser;
    private String friendName;

    public RelacionChatTutor(String idFriendUser, String friendName) {
        this.idFriendUser = idFriendUser;
        this.friendName = friendName;
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

}