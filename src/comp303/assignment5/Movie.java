package comp303.assignment5;

import java.io.File;

/**
 * Haochen Liu
 *
 *
 * Inheritance is applied.
 * Represents a single movie, with at least a title, language, and publishing studio. Each movie is identified by its
 * path on the file system.
 */
public class Movie extends AbstractWatchable implements Sequenceable<Movie>{

    private Language aLanguage;
    private String aStudio;
    private final File aPath;
    private Movie prequel;
    private Movie sequel;
    /**
     * Creates a movie from the file path. Callers must also provide required metadata about the movie.
     *
     * @param pPath
     *            location of the movie on the file system.
     * @param pTitle
     *            official title of the movie in its original language
     * @param pLanguage
     *            language of the movie
     * @param pStudio
     *            studio which originally published the movie
     * @pre pPath!=null && pTitle!=null && pLanguage!=null && pStudio!=null
     * @throws IllegalArgumentException
     *             if the path doesn't point to a file (e.g., it denotes a directory)
     */
    public Movie(File pPath, String pTitle, Language pLanguage, String pStudio) {
        //Inherited from AbstractWatchable.
        super(pTitle);
        if (pPath==null ||(pPath.exists() && !pPath.isFile())){
            throw new IllegalArgumentException("The path should point to a file.");
        }
        aLanguage=pLanguage;
        aStudio = pStudio;
        aPath = pPath; // ok because File is immutable.

    }
    @Override
    public Language getLanguage(){
        return aLanguage;
    }

    @Override
    public String getStudio(){
        return aStudio;
    }

    @Override
    public void watch() {
        // Just a stub.
        // We don't expect you to implement a full media player!
        System.out.println("Now playing " + aTitle);
        //if wacth is called, update the observers.
        this.updateObserver();
    }

    @Override
    public boolean isValid() {
        return aPath.isFile() && aPath.canRead();
    }

    @Override
    public boolean hasPrevious() {
        return prequel != null;
    }

    @Override
    public boolean hasNext() {
        return sequel != null;
    }

    @Override
    public Movie getPrevious() {
        return prequel;
    }

    @Override
    public Movie getNext() {
        return sequel;
    }

    /**
     * Sets the previous Movie in the series, and updates the prequel and sequel information of all related movies
     * involved.
     *
     * @param pMovie
     *            the Movie object to set as previous
     * @pre pMovie != null
     */
    public void setPrevious(Movie pMovie) {
        assert pMovie != null;
        if (prequel != null) {
            prequel.sequel = null;
        }
        if (pMovie.sequel != null) {
            pMovie.sequel.prequel = null;
        }
        prequel = pMovie;
        pMovie.sequel = this;
    }
}
