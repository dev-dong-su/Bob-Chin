package com.example.bobchin;

import android.app.Application;

import java.util.ArrayList;

// 전역변수 관리용 클래스
public class BobChin extends Application {
    private UserInfo userInfoObj = new UserInfo();
    public UserInfo getUserInfoObj() {
        return userInfoObj;
    }

    public void setUserInfoObj(UserInfo userInfoObj) { this.userInfoObj = userInfoObj; }

    public class UserInfo {
        private String userEmail, userName, userAccessToken, userAuthLevel, userPhotoURL, userId;
        private Boolean isSignedIn;

        public Boolean getSignedIn() { return this.isSignedIn; }

        public void setSignedIn(Boolean signedIn) { this.isSignedIn = signedIn; }

        public void setUserAccessToken(String userAccessToken) { this.userAccessToken = userAccessToken; }

        public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

        public void setUserName(String userName) { this.userName = userName; }

        public String getUserAccessToken() { return userAccessToken; }

        public String getUserName() { return userName; }

        public String getUserEmail() { return userEmail; }

        public String getUserAuthLevel() { return userAuthLevel; }

        public void setUserAuthLevel(String userAuthLevel) { this.userAuthLevel = userAuthLevel; }

        public String getUserPhotoURL() { return userPhotoURL; }

        public void setUserPhotoURL(String userPhotoURL) { this.userPhotoURL = userPhotoURL; }

        public String getUserId() { return userId; }

        public void setUserId(String userId) { this.userId = userId; }
    }

}