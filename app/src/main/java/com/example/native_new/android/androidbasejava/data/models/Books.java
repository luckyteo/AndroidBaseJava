package com.example.native_new.android.androidbasejava.data.models;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Entity(tableName = "books")
public class Books {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "full_name")
    private String name;
    @ColumnInfo(name = "avatar")
    private String avatar;

    @ColumnInfo(name = "create_at")
    private String createAt;

    public Books() {
        id = "";
    }

    public @NotNull String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return id.equals(books.id) &&
                Objects.equals(name, books.name) &&
                Objects.equals(avatar, books.avatar) &&
                Objects.equals(createAt, books.createAt);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatar, createAt);
    }
}
