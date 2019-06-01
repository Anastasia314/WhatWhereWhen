package com.epam.whatwherewhen.entity;

/**
 * Date: 29.01.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class Article extends Entity {
    private long articleId;
    private long authorId;
    private String theme;
    private String header;
    private String body;
    private long date;
    private String photo;

    public Article() {
    }

    public Article(long articleId, long authorId, String theme, String header, String body, long date) {
        this.articleId = articleId;
        this.authorId = authorId;
        this.theme = theme;
        this.header = header;
        this.body = body;
        this.date = date;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;

        Article article = (Article) o;

        if (articleId != article.articleId) return false;
        if (authorId != article.authorId) return false;
        if (date != article.date) return false;
        if (theme != null ? !theme.equals(article.theme) : article.theme != null) return false;
        if (header != null ? !header.equals(article.header) : article.header != null) return false;
        if (body != null ? !body.equals(article.body) : article.body != null) return false;
        return photo != null ? photo.equals(article.photo) : article.photo == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (articleId ^ (articleId >>> 32));
        result = 31 * result + (int) (authorId ^ (authorId >>> 32));
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (header != null ? header.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", authorId=" + authorId +
                ", theme='" + theme + '\'' +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                ", photo='" + photo + '\'' +
                '}';
    }
}
