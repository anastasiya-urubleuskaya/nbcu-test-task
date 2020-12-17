package epam.com.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Post {

    private int id;
    private String title;
    private String body;
    private int userId;

    private Post(PostBuilder builder) {
        id = builder.id;
        title = builder.title;
        body = builder.body;
        userId = builder.userId;
    }

    public static class PostBuilder{
        private int id;
        private String title;
        private String body;
        private int userId;

        public PostBuilder() {
        }

        public PostBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public PostBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public PostBuilder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
