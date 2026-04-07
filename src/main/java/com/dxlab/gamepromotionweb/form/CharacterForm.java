package com.dxlab.gamepromotionweb.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CharacterForm {
    @NotNull(message = "ID không được để trống")
    @Min(value = 1, message = "ID phải lớn hơn 0")
    private Integer id;

    @NotBlank(message = "Tên nhân vật không được để trống")
    @Size(max = 45, message = "Tên nhân vật tối đa 45 ký tự")
    private String name;

    @NotNull(message = "Media không được để trống")
    @Min(value = 1, message = "Media ID phải lớn hơn 0")
    private Integer mediaId;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
