# Movie Library Version 5
The 5th assignment of COMP303 Software Design
## Features
1. For a watchlist, the last watched object will be able to get by calling lastWatched() method, returning a reference of the last watched object.  
2. There are 5 state-modifying actions for a watchlist:  
    1) setName(), change the name of the watchlist  
    2) addWatchable(), add a watchable object to the watchlist  
    3) removeWatchable(), remove a watchable object from the watchlist  
    4) next(), return the next watchable object in the watchlist  
    5) reset(), the current movie will become the first one in the watchlist
3. There is a redo() method and an undo() method that works like redo and undo in Microsoft Word. The basic undo and redo can be done in order to restore a state. If the no action is done after calling undo(), then redo() can be called for multiple times, and will result in repeating redo the undone action, just like in Microsoft Word.
## Design
Observer - class WatchList  
Command - the 5 state-modifying actions, redo() and undo()
## Diagram
### State Diagram
![alt text](https://github.com/lhcdhr/Movie-Library-V5-Software-Design/blob/main/state%20diagram.state.png)
