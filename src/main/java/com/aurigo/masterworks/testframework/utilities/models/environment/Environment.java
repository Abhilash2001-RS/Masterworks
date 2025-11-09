package com.aurigo.masterworks.testframework.utilities.models.environment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Environment {

    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("IsFacelift")
    @Expose
    private Boolean IsFacelift;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("Product")
    @Expose
    private String Product;
    @SerializedName("DisplayName")
    @Expose
    private String DisplayName;
    @SerializedName("ModifiedBy")
    @Expose
    private String ModifiedBy;
    @SerializedName("ModifiedOn")
    @Expose
    private String ModifiedOn;
    @SerializedName("Builds")
    @Expose
    private List<Build> Builds = null;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Boolean getIsFacelift() {
        return IsFacelift;
    }

    public void setIsFacelift(Boolean isFacelift) {
        this.IsFacelift = isFacelift;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        this.Product = product;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        this.DisplayName = displayName;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.ModifiedBy = modifiedBy;
    }

    public String getModifiedOn() {
        return ModifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.ModifiedOn = modifiedOn;
    }

    public List<Build> getBuilds() {
        return Builds;
    }

    public void setBuilds(List<Build> builds) {
        this.Builds = builds;
    }

}
