package de.gultsch.ejabberd.api.requests;

import com.google.gson.annotations.SerializedName;

public enum Type {
    @SerializedName("normal") NORMAL,
    @SerializedName("chat") CHAT,
    @SerializedName("headline") HEADLINE
}
