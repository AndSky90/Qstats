package com.i550.qstats.Model;

import com.google.gson.annotations.SerializedName;

public class NameSearchEntity {

    @SerializedName("entityName")
    private String entityName;
    /*
    @SerializedName("entityId")
    private String entityId;


    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }


    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    */

    public String getEntityName() {
        return entityName;
    }

}
