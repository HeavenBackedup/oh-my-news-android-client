package com.example.wangyan.oh_my_news_android_client.util;

import com.example.wangyan.oh_my_news_android_client.entity.UserInformation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by fanfan on 2017/5/8.
 */

public class ChangeWithJson {
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private String line;

    public ChangeWithJson(String line) {
        this.line = line;
    }

    public UserInformation changForHomepage() throws Exception{
        jsonObject=new JSONObject(line);
        UserInformation userInformation=new UserInformation();
        userInformation.setSignature((String) jsonObject.get("signature"));
        userInformation.setUsersId((Integer) jsonObject.get("usersId"));
        userInformation.setAnnouncement((String) jsonObject.get("announcement"));
        userInformation.setAvatarPath((String) jsonObject.get("avatarPath"));
        userInformation.setFans((Integer) jsonObject.get("fans"));
        userInformation.setFollowers((Integer) jsonObject.get("followers"));
        userInformation.setNickName((String) jsonObject.get("nickName"));
        userInformation.setDate((Date) jsonObject.get("date"));
        return userInformation;

    }

}
