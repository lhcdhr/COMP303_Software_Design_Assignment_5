package comp303.assignment5;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Haochen Liu
 *
 *
 * Inheritance is applied.
 * Observer Design Pattern, work as subjects.
 * Abstract class that grouped the common properties
 * of Movie, TVShow, and Episode.
 */
public abstract class AbstractWatchable implements Watchable{

    protected String aTitle;
    protected Map<String,String> aTags = new HashMap<String,String>();
    //Observer Design Pattern.
    protected LinkedList<Observer> aObservers=new LinkedList<Observer>();

    /**
     * Constructor.
     * Initialize the 3 common fields of Watchable objects.
     *
     * @param pTitle the title of Watchable object
     */
    public AbstractWatchable(String pTitle){
        assert pTitle != null;
        aTitle=pTitle;
    }

    /**
     * Get the title.
     *
     * @return the title of Watchable object
     */
    public String getTitle(){
        return this.aTitle;
    }

    /**
     * Get the language.
     * Episode does not have a field for language.
     * Its language is determined by the TVShow that it belongs to.
     * Otherwise, this will lead to confusion - TVShow and its Episode
     * has different language and studio, and this will be a problem
     * for filtering.
     *
     * @return the title of Watchable object
     */
    public abstract Language getLanguage();

    /**
     * Get the publishing studio.
     * Episode does not have a field for studio.
     * Its language is determined by the TVShow that it belongs to.
     * Otherwise, this will lead to confusion - TVShow and its Episode
     * has different language and studio, and this will be a problem
     * for filtering.
     *
     * @return the publishing studio of Watchable object
     */
    public abstract String getStudio();

    /**
     * Using a key-value pair of Strings to represent
     * the tags, stored in a hashmap. If value is null,
     * then the existing pair will be removed. If not, it will
     * be added or overridden.
     *
     * @param pKey key to store
     * @param pValue value to store
     * @return value that the specified key associates to
     */
    public String setInfo(String pKey, String pValue) {
        assert pKey != null && !pKey.isBlank();
        if (pValue == null) {
            return aTags.remove(pKey);
        }
        else {
            return aTags.put(pKey, pValue);
        }
    }

    /**
     * Check whether the tag with key be pKey
     * is already stored.
     *
     * @param pKey key to check
     * @return true if contains, false otherwise.
     */
    public boolean hasInfo(String pKey) {
        assert pKey != null && !pKey.isBlank();
        return aTags.containsKey(pKey);
    }

    /**
     * Get the value of associating stored key.
     *
     * @param pKey key stored in aTag
     * @return associated value
     */
    public String getInfo(String pKey) {
        assert hasInfo(pKey);
        return aTags.get(pKey);
    }

    /**
     * Observer Design Pattern.
     * For encapsulation purposes, this method is not in
     * interface Watchable.
     * Add an observer of this object.
     *
     * @param pObserver the observer to add
     */
    void addObserver(Observer pObserver){
        this.aObservers.add(pObserver);
    }

    /**
     * Observer Design Pattern.
     * For encapsulation purposes, this method is not in
     * interface Watchable.
     * Update every observer about the state of this subject.
     * Only call this method when it is watched.
     *
     */
    void updateObserver(){
        for(Observer o: aObservers){
            o.update(this);
        }
    }

    /**
     * Observer Design Pattern.
     * For encapsulation purposes, this method is not in
     * interface Watchable.
     * Remove an observer from the list.
     *
     * @param rObserver
     */
    Observer removeObserver(Observer rObserver){
        this.aObservers.remove(rObserver);
        return rObserver;
    }

    /**
     * Plays the video to the user
     * @pre isValid()
     */
    @Override
    public abstract void watch();

    /**
     * Indicates whether the Watchable element is ready to be played.
     *
     * @return true if the file path points to an existing location that can be read and is not a directory
     */
    @Override
    public abstract boolean isValid();

}
