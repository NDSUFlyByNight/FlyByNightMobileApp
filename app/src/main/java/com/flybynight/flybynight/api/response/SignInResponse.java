package com.flybynight.flybynight.api.response;

import com.flybynight.flybynight.api.objects.User;
import com.google.gson.annotations.SerializedName;

/**
 * Created by closestudios on 10/11/15.
 */
public class SignInResponse extends Response {

    @SerializedName("user")
    public User user;

    @SerializedName("token")
    public String token;

}
