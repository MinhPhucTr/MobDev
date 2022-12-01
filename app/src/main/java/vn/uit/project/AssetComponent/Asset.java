package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

public class Asset {
    @SerializedName("name")
    private String name;
    @SerializedName("attributes")
    private Attributes attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}
