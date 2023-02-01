package com.nhaccuaquang.musique.entity.enums;

import java.util.Objects;

public enum AlbumType {
    SINGLE(0L, "single"),
    ALBUM(1L, "album"),
    COMPILATION(2L, "compilation"),
    UNDEFINED(-1L, "undefined");
    private Long value;
    private String name;
    AlbumType(Long value, String name) {
        this.value = value;
        this.name = name;
    }
    public Long getValue() {
        return value;
    }
    public String getName() {
        return name;
    }

//    public static AlbumType of(Long value) {
//        for (AlbumType status :
//                AlbumType.values()) {
//            if(Objects.equals(status.getValue(), value)){
//                return status;
//            }
//        }
//        return UNDEFINED;
//    }

    public static String nameOf(Long value){
        for (AlbumType status :
                AlbumType.values()) {
            if(Objects.equals(status.getValue(), value)){
                return status.name;
            }
        }
        return UNDEFINED.name;
    }
}
