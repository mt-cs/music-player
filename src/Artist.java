import java.util.ArrayList;

/**
 * This class represents an Artist that extends entity
 * it has two data members songs and albums
 */
public class Artist extends Entity implements Comparable<Artist> {
    ArrayList<Song> songs;
    ArrayList<Album> albums;

    /**
     * empty constructor for artist
     */
    public Artist() {
        this("");
//        super();
//        songs = new ArrayList<Song>();
//        albums = new ArrayList<Album>();
    }

    /**
     * constructor with parameter
     * @param name artist name
     */
    public Artist(String name) {
        super(name);
        songs = new ArrayList<>(10);
        albums = new ArrayList<>(10);
    }

    /**
     * compare to method to implement comparable
     * @param a Artist input a to be compared to
     * @return  0 if this.name is the same with a.name
     * < 0 if the string is lexicographically less than the other string
     * > 0 if the string is lexicographically greater than the other string (more characters)
     */
    public int compareTo(Artist a){
        return this.name.compareTo(a.name);
    }

    /**
     * getter for song
     * @return songs
     */
    public ArrayList<Song> getSongs() { return songs; }

    /** setter for songs
     * @param inputSongs = song array list input
     */
    public void setSongs(ArrayList<Song> inputSongs) {
        songs = inputSongs;
    }

    /**
     * getter for Album
     * @return albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * setter for album
     * @param inputAlbums album array list input
     */
    public void setAlbums(ArrayList<Album> inputAlbums){
        this.albums = inputAlbums;
    }

    /**
     * toString method
     * Artist: The Beatles Songs: Help!, Yesterday, Helter Skelter Albums: The White Album, Abbey Road
     * @return Artist: The Beatles Songs: Help!, Yesterday, Helter Skelter Albums: The White Album, Abbey Road
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Artist: ");
        sb.append(this.getName());
        sb.append(", ");
        sb.append("Songs: ");
        for (Song s : songs){
            sb.append(s.name);
            sb.append(", ");
        }
        sb.append("Albums: ");
        for (Album a: albums){
            sb.append(a.name);
            sb.append(", ");
        }
        return sb.toString();
    }

    /**
     *  Equal method: Assume that two artists are equal if they have the same name and the same albums.
     *  @param otherArtist Artist to compare to
     */
    public boolean equals(Artist otherArtist) {
        return (this.name.equals(otherArtist.getName()) && (this.albums.containsAll(otherArtist.getAlbums())&&
                otherArtist.getAlbums().containsAll(this.albums)));
    }
}
