import java.util.ArrayList;
/**
 * Artist Tester
 * main method to test Artist.java
 */

public class ArtistTest {
    public static void main(String[] args) {
        Artist a1 = new Artist("Lady Gaga");
        Artist a2 = new Artist();

        Song s1 = new Song("Million Reasons");
        Song s2 = new Song ("Applause");
        a1.getSongs().add(s1);
        a1.getSongs().add(s2);

        ArrayList<Song> songArrayList = new ArrayList<>();
        Song s3 = new Song("California");
        songArrayList.add(s3);
        a2.setSongs(songArrayList);
        System.out.println(a2.getSongs());

        Album album1 = new Album("Joanne");
        album1.addSong(s1);
        a1.getAlbums().add(album1);

        ArrayList<Album> albumArrayList = new ArrayList<>();
        albumArrayList.add(album1);

        a1.setAlbums(albumArrayList);
        System.out.println(a1);
        System.out.println(a2);
    }
}
