package comp303.assignment5;

/**
 * Haochen Liu
 *
 *
 * Observer Design pattern.
 *
 */
public interface Observer{

    /**
     * Update the state of this observer.
     *
     * @param toUpdate the most recently watched AbstractWatchable object
     */
    void update(AbstractWatchable toUpdate);
}
