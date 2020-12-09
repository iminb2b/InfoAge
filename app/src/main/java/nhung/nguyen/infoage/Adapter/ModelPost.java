package nhung.nguyen.infoage.Adapter;

public class ModelPost {
    public String title, content, author,postid;

    public ModelPost(String title, String content, String author, String postid) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.postid = postid;
    }

    public ModelPost() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostid() {
        return postid;
    }

    public void setPotid(String potid) {
        this.postid = postid;
    }
}
