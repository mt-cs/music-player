import java.time.LocalDate;

/**
 * This method is the parent class that contains name and date created
 */
public class Entity {
    String name;
    LocalDate dateCreated;

    /**
     * empty constructor
     */
    public Entity() {
        this("");
    }

    /**
     * constructor with parameter name;
     * @param n = object name
     */
    public Entity(String n) {
        name = n;
        dateCreated = LocalDate.now();
    }

    /**
     * accessor for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * accessor for date
     * @return dateCreated
     */
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    /**
     * mutator for name
     * @param inputName = string input for name
     */
    public void setName(String inputName) {
        name = inputName;
    }

    /**
     * mutator for date
     * @param inputDateCreated = input date
     */
    public void setDateCreated(LocalDate inputDateCreated) {
        dateCreated = inputDateCreated;
    }

    /**
     * toString method that simply return name
     * @return name
     */
    public String toString() {
        return name;
    }

    /**
     * Two Entities are equal if they have the same name and dateCreated.
     * @param otherAlbum
     * @return true if this's and other's name and date created are the equal
     */
    public boolean equals(Artist otherAlbum) {
        return (this.name.equalsIgnoreCase(otherAlbum.getName()) && (this.dateCreated.equals(otherAlbum.dateCreated)));
    }
}
