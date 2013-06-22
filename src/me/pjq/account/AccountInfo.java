
package me.pjq.account;

import me.pjq.database.SqliteManager;

/**
 * The user account info.It will used to store the current user info.
 * 
 * @see SqliteManager#queryAccountInfo(String)
 * @author pjq0274
 */
public class AccountInfo {
    private String mUserName;
    private String mPassword;
    private String mEmail;
    private String mTwitterConsumerKey;
    private String mTwitterConsumerSecret;
    private String mTwitterAccessToken;
    private String mTwitterAccessTokenSecret;
    private String mFacebookAccessToken;
    private String mFacebookAccessTokenSecret;
    private String mDisplayName;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getTwitterAccessToken() {
        return mTwitterAccessToken;
    }

    public void setTwitterAccessToken(String mTwitterAccessToken) {
        this.mTwitterAccessToken = mTwitterAccessToken;
    }

    public String getTwitterAccessTokenSecret() {
        return mTwitterAccessTokenSecret;
    }

    public void setTwitterAccessTokenSecret(String mTwitterAccessTokenSecret) {
        this.mTwitterAccessTokenSecret = mTwitterAccessTokenSecret;
    }

    public String getFacebookAccessToken() {
        return mFacebookAccessToken;
    }

    public void setFacebookAccessToken(String mFacebookAccessToken) {
        this.mFacebookAccessToken = mFacebookAccessToken;
    }

    public String getFacebookAccessTokenSecret() {
        return mFacebookAccessTokenSecret;
    }

    public void setFacebookAccessTokenSecret(String mFacebookAccessTokenSecret) {
        this.mFacebookAccessTokenSecret = mFacebookAccessTokenSecret;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }

    public String getTwitterConsumerKey() {
        return mTwitterConsumerKey;
    }

    public void setTwitterConsumerKey(String mTwitterConsumerKey) {
        this.mTwitterConsumerKey = mTwitterConsumerKey;
    }

    public String getTwitterConsumerSecret() {
        return mTwitterConsumerSecret;
    }

    public void setTwitterConsumerSecret(String mTwitterConsumerSecret) {
        this.mTwitterConsumerSecret = mTwitterConsumerSecret;
    }

    /**
     * Check whether the password is right.
     * 
     * @param username
     * @param password
     * @return true if the username and password is right.
     */
    public boolean isPasswordMatched(String password) {
        boolean matched = false;

        if (getPassword().equals(password) || getPassword().contains(password)) {
            matched = true;
        } else {
            matched = false;
        }

        return matched;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String string = "userName=" + mUserName + ",\nuserEmail=" + mEmail + ",\npassword="
                + "*******"
                + ",\ndisplayName=" + mDisplayName
                + ",\ntwitterConsumerKey=" + mTwitterConsumerKey + ",\ntwitterConsumerSecret="
                + mTwitterConsumerSecret
                + ",\ntwitterAccessToken=" + mTwitterAccessToken + ",\ntwitterAccessTokenSecret="
                + mTwitterAccessTokenSecret + ",\nfacebookAccessToken=" + mFacebookAccessToken
                + ",\nfacebookAccessTokenSecret=" + mFacebookAccessTokenSecret;
        return string;
    }
}
