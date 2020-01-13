package com.acme.users;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Entity
@Table(name = "favourites")
@NamedQueries({
        @NamedQuery(
                name = "Favourite.findFavourites",
                query = "SELECT f " +
                        "FROM Favourite f"
        ),
        @NamedQuery(
                name = "Favourite.findByUser",
                query = "SELECT f " +
                        "FROM Favourite f " +
                        "WHERE f.userId = " + ":userId"
        )
})
@JsonTypeName("favourite")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class Favourite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favouriteId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "bookId")
    private int bookId;

    public int getFavouriteId() { return favouriteId; }

    public void setFavouriteId(int favouriteId) { this.favouriteId = favouriteId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }

    public void setBookId(int bookId) { this.bookId = bookId; }
}
