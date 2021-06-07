package comp303.assignment5;

import java.util.Optional;

/**
 * Haochen Liu
 *
 *
 * Command Design Pattern.
 * Represent a command that can be executed.
 *
 */
public interface Command {

    /**
     * Execute this command.
     *
     * @return void, or an AbstractWatchable object(for this project)
     */
    Optional<AbstractWatchable> execute();

    /**
     * Make the state of the object change to
     * what it was before this command is executed.
     *
     */
    void undo();

    /**
     * Make the state of the object change to
     * what it was before this command is undo.
     *
     * @return void, or an AbstractWatchable object(for this project)
     */
    Optional<AbstractWatchable> redo();
}
