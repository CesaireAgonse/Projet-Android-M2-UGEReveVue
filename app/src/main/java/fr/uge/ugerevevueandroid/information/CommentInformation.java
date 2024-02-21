package fr.uge.ugerevevueandroid.information;

import java.util.Date;
import java.util.Objects;

public class CommentInformation {
    private long id;
    private SimpleUserInformation userInformation;
    private String content;
    private String codeSelection;
    private Date date;

    public CommentInformation(long id, SimpleUserInformation userInformation, String content, String codeSelection, Date date) {
        this.id = id;
        this.userInformation = userInformation;
        this.content = content;
        this.codeSelection = codeSelection;
        this.date = date;
    }

    /*
    public static CommentInformation from(Comment comment){
        Objects.requireNonNull(comment, "[CommentInformation] comment is null");
        return new CommentInformation(
                comment.getId(),
                SimpleUserInformation.from(comment.getUser()),
                comment.getContent(),
                comment.getCodeSelection(),
                comment.getDate()
        );
    }
    */

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public SimpleUserInformation getUserInformation() {return userInformation;}

    public void setUserInformation(SimpleUserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public String getCodeSelection() {return codeSelection;}

    public void setCodeSelection(String codeSelection) {this.codeSelection = codeSelection;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}