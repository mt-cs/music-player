import java.util.ArrayList;

/**
 * Album Tester
 * This class contains a main method that test out all methods in Album.java
 */
public class AlbumTest {
    public static void main(String[] args) {
        Artist artist1 = new Artist("Angelina Jordan");
        Song song1 = new Song("Fly Me to The Moon");
        Song song2 = new Song("Back to Black");
        Song song3 = new Song("Fly Me to The Moon");
        Song song4 = new Song("I Put A Spell on You");

        Album a1 = new Album("It's Magic");
        ArrayList<Song> songArrayList = new ArrayList<>();
        songArrayList.add(song1);
        songArrayList.add(song2);
        songArrayList.add(song3);
        songArrayList.add(song3);
        a1.setSongs(songArrayList);
        System.out.println(a1.getSongs());

        a1.setArtist(artist1);
        System.out.println(a1.getArtist());

        a1.setTotalLength("3");
        System.out.println(a1.getTotalLength());

        a1.getSongs().add(song4);
        a1.addSong(song1);
        System.out.println(a1.toString());

        Album a2 = new Album("it's magic");
        a2.getSongs().add(song1);
        a2.getSongs().add(song2);
        a2.getSongs().add(song3);
        a2.setArtist(artist1);

        Album a3 = new Album();

        if (a1.equals(a2)){
            System.out.println("Albums are equal");
        } else {
            System.out.println("Albums are not equal");
        }

        if (a1.equals(a3)){
            System.out.println("Albums are equal");
        } else {
            System.out.println("Albums are not equal");
        }
    }
}
