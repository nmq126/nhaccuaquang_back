package com.nhaccuaquang.musique.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBody {
    private int page;
    private int limit;
    private String name;
    private String from;
    private String to;
    private boolean status;
    private Long genre_id;
    private Long id;


    public static final class SearchBodyBuilder {
        private int page;
        private int limit;
        private String name;
        private String from;
        private String to;
        private boolean status;
        private Long genre_id;
        private Long id;

        private SearchBodyBuilder() {
        }

        public static SearchBodyBuilder aSearchBody() {
            return new SearchBodyBuilder();
        }

        public SearchBodyBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public SearchBodyBuilder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public SearchBodyBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SearchBodyBuilder withFrom(String from) {
            this.from = from;
            return this;
        }

        public SearchBodyBuilder withTo(String to) {
            this.to = to;
            return this;
        }

        public SearchBodyBuilder withStatus(boolean status) {
            this.status = status;
            return this;
        }

        public SearchBodyBuilder withGenre_id(Long genre_id) {
            this.genre_id = genre_id;
            return this;
        }

        public SearchBodyBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SearchBody build() {
            SearchBody searchBody = new SearchBody();
            searchBody.setPage(page);
            searchBody.setLimit(limit);
            searchBody.setName(name);
            searchBody.setFrom(from);
            searchBody.setTo(to);
            searchBody.setStatus(status);
            searchBody.setGenre_id(genre_id);
            searchBody.setId(id);
            return searchBody;
        }
    }
}
