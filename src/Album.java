import java.util.ArrayList;

/**
 * This class represents an Album that extends entity
 * it has three data members songs, artist, and albums
 */
public class Album extends Entity implements Comparable<Album> {
    ArrayList<Song> songs;
    Artist artist;
    String totalLength;

    /**
     * empty constructor
     */
    public Album() {
        this("");
    }

    /**
     * constructor with parameters:
     * @param n = super name
     * @param inSongs = input song
     * @param inArtist = input artist
     * @param inTotalLength = input total length
     */
    public Album(String n, ArrayList<Song> inSongs, Artist inArtist, String inTotalLength) {
        super(n);
        songs = inSongs;
        artist = inArtist;
        totalLength = inTotalLength;
    }

    /**
     * Constructor with parameter name
     * @param n = Album name
     */
    public Album(String n) {
        this(n, new ArrayList<>(), new Artist(), "");
    }

    /**
     * getter for songs
     * @return songs
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * mutator for songs
     * @param inputSongs = song input
     */
    public void setSongs(ArrayList<Song> inputSongs) {
        this.songs = inputSongs;
    }

    /**
     * accessor for artist
     * @return artist
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * mutator for artist
     * @param inputArtist = artist input
     */
    public void setArtist(Artist inputArtist) {
        this.artist = inputArtist;
    }

    /**
     * accessor for total length
     * @return totalLength
     */
    public String getTotalLength() {
        return totalLength;
    }

    /**
     * mutator for total length
     * @param inputTotalLength user input for total length
     */
    public void setTotalLength(String inputTotalLength) {
        this.totalLength = inputTotalLength;
    }

    /**
     * method to add song to album, check if the song is already in list ot not
     * @param songInput = new song input
     */
    public void addSong(Song songInput){
        if (this.songs.contains(songInput)){
            System.out.println("This song is already in the library");
        } else {
            this.songs.add(songInput);
        }
    }

    /**
     * toString method for album
     * Album: The White,Artist: The Beatles Songs: Back in the USSR, Revolution #9
     * @return format: "Album: Album name, Artist: Artist name, Songs: song names"
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Album: ").append(super.toString()).append(", ");
        sb.append("Artist: ").append(artist.getName()).append(", ");
        sb.append("Songs: ");
        for (Song s: songs){
            sb.append(s.getName()).append(", ");
        }
        return sb.toString();
    }

    /**
     * Equal method, assume that two albums are equal if they have the same name, artist, and song.
     * @param otherAlbum = another album to compare with
     * @return true if albums have the same name, artist, and song
     */
    public boolean equals(Album otherAlbum) {
        return (this.name.equalsIgnoreCase(otherAlbum.getName()) && (this.songs.containsAll(otherAlbum.getSongs()))
            && artist.getName().equalsIgnoreCase(otherAlbum.getArtist().getName()));
    }

    /**
     * comparable method
     * @param album input album to be compared
     * @return 0 if this.name is the same with album.name
     */
    public int compareTo(Album album) {
        return this.name.compareTo(album.name);
    }
}
