package com.aurigo.masterworks.testframework.utilities.models.environment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Build {

    //This tells Gson that the JSON field "Id" should be mapped to the Java field id.
    @SerializedName("Id")
    @Expose  // This tells Gson whether the field should be included or ignored during serialization or deserialization
    private Integer id;
    @SerializedName("Environment")
    @Expose
    private String environment;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("DbServer")
    @Expose
    private String dbServer;
    @SerializedName("DbName")
    @Expose
    private String dbName;
    @SerializedName("DbUsername")
    @Expose
    private String dbUsername;
    @SerializedName("DbPassword")
    @Expose
    private String dbPassword;
    @SerializedName("SystemAdminUsername")
    @Expose
    private String systemAdminUsername;
    @SerializedName("SystemAdminPassword")
    @Expose
    private String systemAdminPassword;
    @SerializedName("AdminUsername")
    @Expose
    private String adminUsername;
    @SerializedName("AdminPassword")
    @Expose
    private String adminPassword;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("ModifiedBy")
    @Expose
    private String modifiedBy;
    @SerializedName("ModifiedOn")
    @Expose
    private String modifiedOn;
    public String buildVersion;
    public int maxAllocatableThreadCount;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbServer() {
        return dbServer;
    }

    public void setDbServer(String dbServer) {
        this.dbServer = dbServer;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getSysAdminUsername() {
        return systemAdminUsername;
    }

    public void setSysAdminUsername(String systemAdminUsername) {
        this.systemAdminUsername = systemAdminUsername;
    }

    public String getSysAdminPassword() {
        return systemAdminPassword;
    }

    public void setSysAdminPassword(String systemAdminPassword) {
        this.systemAdminPassword = systemAdminPassword;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

}
