package model.entities;


import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Immutable
@Table(name = "album_view", schema = "music_store", catalog = "")
public class AlbumViewEntity {
    private int productId;
    private String category;
    private double price;
    private int artistId;
    private String name;
    private String genre;
    private String title;
    private Integer duration;
    private Integer releaseYear;
    private Integer songsCount;
    private String imageLink;
    private String tracklist;

    @Id
    @Column(name = "product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "category", nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "artist_id", nullable = false)
    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "genre", nullable = false, length = 50)
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 80)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "duration", nullable = true)
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "release_year", nullable = true)
    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Basic
    @Column(name = "songs_count", nullable = true)
    public Integer getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(Integer songsCount) {
        this.songsCount = songsCount;
    }

    @Basic
    @Column(name = "image_link", nullable = true, length = 120)
    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Basic
    @Column(name = "tracklist", nullable = true, length = -1)
    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumViewEntity that = (AlbumViewEntity) o;

        if (productId != that.productId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (artistId != that.artistId) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (releaseYear != null ? !releaseYear.equals(that.releaseYear) : that.releaseYear != null) return false;
        if (songsCount != null ? !songsCount.equals(that.songsCount) : that.songsCount != null) return false;
        if (imageLink != null ? !imageLink.equals(that.imageLink) : that.imageLink != null) return false;
        if (tracklist != null ? !tracklist.equals(that.tracklist) : that.tracklist != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = productId;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + artistId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (releaseYear != null ? releaseYear.hashCode() : 0);
        result = 31 * result + (songsCount != null ? songsCount.hashCode() : 0);
        result = 31 * result + (imageLink != null ? imageLink.hashCode() : 0);
        result = 31 * result + (tracklist != null ? tracklist.hashCode() : 0);
        return result;
    }
}
