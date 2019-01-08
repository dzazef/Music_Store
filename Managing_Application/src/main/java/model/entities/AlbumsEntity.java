package model.entities;

import javax.persistence.*;

@Entity
@Table(name = "albums", schema = "music_store", catalog = "")
public class AlbumsEntity {
    private int albumId;
    private int artistId;
    private Integer duration;
    private Integer releaseYear;
    private String title;
    private Integer songsCount;
    private String imageLink;
    private String tracklist;

    @Id
    @Column(name = "album_id")
    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    @Basic
    @Column(name = "artist_id")
    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Basic
    @Column(name = "duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "release_year")
    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "songs_count")
    public Integer getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(Integer songsCount) {
        this.songsCount = songsCount;
    }

    @Basic
    @Column(name = "image_link")
    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Basic
    @Column(name = "tracklist")
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

        AlbumsEntity that = (AlbumsEntity) o;

        if (albumId != that.albumId) return false;
        if (artistId != that.artistId) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (releaseYear != null ? !releaseYear.equals(that.releaseYear) : that.releaseYear != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (songsCount != null ? !songsCount.equals(that.songsCount) : that.songsCount != null) return false;
        if (imageLink != null ? !imageLink.equals(that.imageLink) : that.imageLink != null) return false;
        if (tracklist != null ? !tracklist.equals(that.tracklist) : that.tracklist != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = albumId;
        result = 31 * result + artistId;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (releaseYear != null ? releaseYear.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (songsCount != null ? songsCount.hashCode() : 0);
        result = 31 * result + (imageLink != null ? imageLink.hashCode() : 0);
        result = 31 * result + (tracklist != null ? tracklist.hashCode() : 0);
        return result;
    }
}
