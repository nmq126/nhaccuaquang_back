package com.nhaccuaquang.musique.specification;

import com.nhaccuaquang.musique.entity.Playlist;
import com.nhaccuaquang.musique.entity.PlaylistDetail;
import com.nhaccuaquang.musique.entity.Song;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class PlaylistSpecification implements Specification<Playlist> {

    private SearchCriteria criteria;

    public PlaylistSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Playlist> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if (root.get(criteria.getKey()).getJavaType() == LocalDate.class) {
                return builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), (LocalDate) criteria.getValue());
            } else {
                return builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if (root.get(criteria.getKey()).getJavaType() == LocalDate.class) {
                return builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), (LocalDate) criteria.getValue());
            } else {
                return builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            }
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("join")) {
            query.distinct(true);
            Join<Playlist, PlaylistDetail> playlistPlaylistDetailJoin = root.join("playlistDetails");
            Join<PlaylistDetail, Song> playlistDetailSongJoin = playlistPlaylistDetailJoin.join("song");
            return builder.like(playlistDetailSongJoin.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        }
        return null;
    }
}
