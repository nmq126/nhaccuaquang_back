//package com.nhaccuaquang.musique.specification.test;
//
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//
//@Component
//public class SpecificationFactory<T>{
//    public Specification<T> isEqual(String key, Object arg) {
//        GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
//        return builder.with(key, SearchOperation.EQUALITY, Collections.singletonList(arg)).build();
//    }
//
//    public Specification<T> isLike(String key, Object arg) {
//        GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
//        return builder.with(key, SearchOperation.LIKE, Collections.singletonList(arg)).build();
//    }
//}
