package comp303.assignment5;

import java.util.*;

/**
 * Haochen liu
 *
 *
 * Command Design Pattern.
 * Observer Design Pattern.
 * Contains undo and redo commands for state-modifying methods.
 * Represents a sequence of watchables to watch in FIFO order.
 */
public class WatchList implements Bingeable<AbstractWatchable>, Observer{

    private final LinkedList<AbstractWatchable> aList = new LinkedList<>();
    private String aName;
    private int aNext;
    private Optional<AbstractWatchable> lWatched = Optional.empty();
    // list of commands(actually work as stacks here)for redo
    private LinkedList<Command> listExecute = new LinkedList<Command>();
    // list of commands(actually work as stacks here)for undo
    private LinkedList<Command> listUndo = new LinkedList<Command>();
    // sign for whether redo will repeat
    private boolean undoFlag;

    /**
     * Creates a new empty watchlist.
     *
     * @param pName
     *            the name of the list
     * @pre pName!=null;
     */
    public WatchList(String pName) {
        assert pName != null;
        aName = pName;
        aNext = 0;
    }

    /**
     * Command Design Pattern.
     * Get the name of this WatchList
     *
     * @return  the name of this WatchList
     */
    public String getName() {
        return aName;
    }

    /**
     * Command Design Pattern.
     * Undo and remove the command at the top of
     * the undo stack(End of listUndo),
     * and add it to the top of redo stack(End of listExecute)
     *
     */
    public void undo(){
        if(!listUndo.isEmpty()) {
            // set it to be true when undo
            undoFlag=true;
            Command cmd = listUndo.removeLast();
            cmd.undo();
            listExecute.add(cmd);
        }
        //Do nothing then undo stack is empty.
    }

    /**
     * Command Design Pattern.
     * Redo has two features.
     * When undoFlag is false, it will repeat the previously executed command,
     * and store the command to the end of undo stack(listUndo).
     * When undoFlag is true, it will remove the command at the top of
     * redo stack(listExecute), redo it, and add it to the top of undo stack(listUndo).
     *
     * Meanwhile, in order to support the repeat feature, some of the redo() method
     * of Command has different variants.
     *
     * @return void or an AbstractWatchable object(for this design)
     */
    public Optional<AbstractWatchable> redo(){
        //redo without undo being called before(the repeat feature)
        if(!undoFlag && !listUndo.isEmpty() && listExecute.isEmpty()){
            Command cmd = listUndo.getLast();
            return cmd.redo();
        }
        //regular redo
        if(undoFlag && !listExecute.isEmpty()){
            Command cmd = listExecute.removeLast();
            return cmd.redo();
        }
        // Do nothing when redo stack is empty and no need to repeat.
        return Optional.empty();
    }
    /**
     * Command Design Pattern.
     * Changes the name of this watchlist.
     *
     * @param pName
     *            the new name
     * @pre pName!=null;
     */
    public void setName(String pName) {
        assert pName != null;
        //create the set name command
        Command commandSetName = new Command(){
            String oldName = aName;
            String newName = pName;
            @Override
            public Optional<AbstractWatchable> execute(){
                //regular execute
                aName = newName;
                undoFlag = false;
                listUndo.add(this);
                listExecute.clear();
                return Optional.empty();
            }
            @Override
            public void undo(){
                // undo to retrieve the old name
                aName = oldName;
            }
            @Override
            public Optional<AbstractWatchable> redo(){
                // Redo to retrieve the new name
                // redo here will not change the state of WatchList.
                // This will not invoke the repeat feature.
                // Driver.java has a demo of this.
                aName = newName;
                return Optional.empty();
            }
        };
        //execute this command
        commandSetName.execute();
    }

    /**
     * Command Design Pattern.
     * Add a AbstractWatchable object to the end of this watchlist.
     *
     * @param pWatchable
     *            the watchable to add
     * @pre pWatchable!=null;
     */
    public void addWatchable(AbstractWatchable pWatchable) {
        assert pWatchable != null;
        WatchList currentWatchList = this;
        // create the add watchable command
        Command commandAdd = new Command(){
            AbstractWatchable watchableToAdd = pWatchable;
            Observer watchListToAdd = currentWatchList;
            //record the added index for retrieving
            LinkedList<Integer> addedIndex = new LinkedList<Integer>();
            @Override
            public Optional<AbstractWatchable> execute(){
                //regular execute
                undoFlag = false;
                Integer index = aList.size();
                addedIndex.add(index);
                // add observer
                watchableToAdd.addObserver(watchListToAdd);
                aList.add(watchableToAdd);
                listUndo.add(this);
                listExecute.clear();
                return Optional.empty();
            }
            @Override
            public void undo() {
                // retrieve the index of the lastly-added object
                int index = addedIndex.removeLast();
                AbstractWatchable toRemove = aList.remove(index);
                // remove the observer
                toRemove.removeObserver(watchListToAdd);
                if(aNext>index){
                    aNext--;
                }
            }
            @Override
            public Optional<AbstractWatchable> redo(){
                // retrieve the index removed by undo
                Integer index = aList.size();
                addedIndex.add(index);
                // add the observer back
                watchableToAdd.addObserver(watchListToAdd);
                // retrieve the AbstractWatchable object
                aList.add(watchableToAdd);
                // push it to undo stack(listUndo)
                listUndo.add(this);
                return Optional.empty();
            }
        };
        //execute this command
        commandAdd.execute();
    }

    /**
     * Command design pattern.
     * Retrieves and removes the Watchable at the specified index.
     *
     * @param pIndex
     *            the position of the Watchable to remove
     * @pre pIndex < getTotalCount() && pIndex >= 0
     */
    public AbstractWatchable removeWatchable(int pIndex) {
        assert pIndex < aList.size() && pIndex >= 0;
        Observer rObserver = this;
        // create the remove command
        Command commandRemove = new Command(){
            Integer indexToRemove = pIndex;
            Observer observerToRemove = rObserver;
            // record the removed watchables, for retrieving
            LinkedList<AbstractWatchable> removedWatchables = new LinkedList<AbstractWatchable>();
            // record the value of aNext before the removal
            LinkedList<Integer> aNextReset = new LinkedList<Integer>();
            @Override
            public Optional<AbstractWatchable> execute() {
                //regular execute
                undoFlag=false;
                AbstractWatchable rWatchable= aList.get(indexToRemove);
                removedWatchables.add(rWatchable);
                aNextReset.add(aNext);
                listExecute.clear();
                listUndo.add(this);
                if (aNext > indexToRemove) {
                    aNext--;
                }
                aList.remove((int)indexToRemove);
                //remove the observer
                rWatchable.removeObserver(observerToRemove);
                return Optional.of(rWatchable);
            }
            @Override
            public void undo() {
                // retrieving the value of aNext and removed watchable
                aNext = aNextReset.removeLast();
                AbstractWatchable toRecover = removedWatchables.removeLast();
                aList.add(indexToRemove,toRecover);
                // recover the observer
                toRecover.addObserver(observerToRemove);
            }
            @Override
            public Optional<AbstractWatchable> redo(){
                //make sure index is in bound
                // Repeat feature for this redo will keep remove the object
                // at the same index.
                if(indexToRemove<(aList.size())) {
                    //removing
                    AbstractWatchable toRedo = aList.get(indexToRemove);
                    aList.remove((int) indexToRemove);
                    removedWatchables.add(toRedo);
                    aNextReset.add(aNext);
                    if (aNext > indexToRemove) {
                        aNext--;
                    }
                    toRedo.removeObserver(observerToRemove);
                    // add to the undo stack(listUndo)
                    listUndo.add(this);
                    return Optional.of(toRedo);
                }
                listUndo.add(this);
                return Optional.empty();
            }
        };
        // execute the command and return the watchable object
        return commandRemove.execute().get();
    }

    /**
     * Command Design Pattern.
     * Returns an element in the watchlist and mark that element as accessed.
     *
     * @return the next element to access
     * @pre getRemainingCount() > 0
     */
    @Override
    public AbstractWatchable next() {
        assert getRemainingCount() > 0;
        // create the next command
        Command commandNext = new Command(){
            // record the previous aNext values, for retrieving
            LinkedList<Integer> previousNexts = new LinkedList<Integer>();
            @Override
            public Optional<AbstractWatchable> execute(){
                //regular execute
                assert getRemainingCount() > 0;
                undoFlag = false;
                AbstractWatchable nextWatchable = aList.get(aNext);
                previousNexts.add(aNext);
                if((aNext+1) >= aList.size()){
                    aNext = 0;
                }
                else{
                    aNext++;
                }
                listUndo.add(this);
                listExecute.clear();
                return Optional.of(nextWatchable);
            }
            @Override
            public void undo(){
                // recover the aNext
                aNext = previousNexts.removeLast();
            }
            @Override
            public Optional<AbstractWatchable> redo(){
                assert getRemainingCount() > 0;
                // recover aNext from undo
                previousNexts.add(aNext);
                AbstractWatchable nextWatchable = aList.get(aNext);
                if((aNext+1) >= aList.size()){
                    aNext = 0;
                }
                else{
                    aNext++;
                }
                listUndo.add(this);
                return Optional.of(nextWatchable);
            }
        };
        return commandNext.execute().get();
    }


    /**
     * Command Design Pattern.
     * Reset the pointer index aNext to 0.
     * Detailed demo in Driver.java
     *
     */
    @Override
    public void reset() {
        //create the reset command
        Command commandReset = new Command(){
            //record the value before reset
            int beforeReset = aNext;
            @Override
            public Optional<AbstractWatchable> execute(){
                //regular execute
                undoFlag = false;
                aNext =0;
                listUndo.add(this);
                listExecute.clear();
                return Optional.empty();
            }
            @Override
            public void undo() {
                aNext = beforeReset;
            }
            @Override
            public Optional<AbstractWatchable> redo() {
                //repeating redo will not change the state of the object.
                aNext = 0;
                return Optional.empty();
            }
        };
        commandReset.execute();
    }


    @Override
    public void update(AbstractWatchable toUpdate){
        if(aList.contains(toUpdate)) {
            this.lWatched = Optional.of(toUpdate);
        }
    }

    /**
     * Observer design pattern.
     * Get the most recently watched object.
     *
     * @return the most recently watched object
     * @throws NoSuchElementException
     */
    public AbstractWatchable lastWatched() throws NoSuchElementException{
        if(lWatched.isPresent()){
            return lWatched.get();
        }
        throw new NoSuchElementException("No recent watch record found.");
    }



    /**
     * Get the total number of valid watchable elements.
     *
     * @return the total number of valid watchable elements
     */
    public int getValidCount() {
        int count = 0;
        for (AbstractWatchable item : aList) {
            if (item.isValid()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getTotalCount() {
        return aList.size();
    }

    @Override
    public int getRemainingCount() {
        return aList.size() - aNext;
    }

    @Override
    public Iterator<AbstractWatchable> iterator() {
        return Collections.unmodifiableList(aList).iterator();
    }
}
