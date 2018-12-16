package com.i550.qstats.Model;

public class Entry {
    private String userName;
    private Integer eloRating;
    private String profileIconId;
    private String namePlateId;

    public String getUserName() {
        return userName;
    }

    public Integer getEloRating() {
        return eloRating;
    }

    public String getProfileIconId() {
        return profileIconId;
    }

    public String getNamePlateId() {
        return namePlateId;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "userName='" + userName + '\'' +
                ", eloRating=" + eloRating +
                ", profileIconId='" + profileIconId + '\'' +
                ", namePlateId='" + namePlateId + '\'' +
                '}';
    }
}
