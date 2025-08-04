public class WiseSaying {
    private int num;
    private String content;
    private String author;
    private boolean isDeleted = false;

    public WiseSaying(int num, String content, String author) {
        this.num = num;
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return num + "번 / " + content + " / " + author ;
    }

    public int getNum() {
        return num;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isDeleted(){
        return isDeleted;
    }
}
