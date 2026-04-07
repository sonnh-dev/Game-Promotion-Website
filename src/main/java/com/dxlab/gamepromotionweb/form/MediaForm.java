package com.dxlab.gamepromotionweb.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MediaForm {
    @NotBlank(message = "URL không được để trống")
    @Size(max = 255, message = "URL tối đa 255 ký tự")
    private String url;

    @NotBlank(message = "Loại media không được để trống")
    @Size(max = 50, message = "Loại media tối đa 50 ký tự")
    private String type;

    @NotBlank(message = "Entity type không được để trống")
    @Size(max = 50, message = "Entity type tối đa 50 ký tự")
    private String entityType;

    @NotNull(message = "Entity ID không được để trống")
    @Min(value = 1, message = "Entity ID phải lớn hơn 0")
    private Integer entityId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
