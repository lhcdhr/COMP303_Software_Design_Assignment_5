import comp303.assignment5.*;

import java.util.*;
import java.io.File;

/**
 * Haochen Liu
 *
 *
 * Demonstration of my understanding for  this assignment.
 * Please run Driver.java and read the output.
 */
public class Driver {
    public static void main(String[] args){
        File f1 = new File("C:\\Users\\1.mp4");
        File f2 = new File("C:\\Users\\2.mp4");
        File f3 = new File("C:\\Users\\3.mp4");
        File f4 = new File("C:\\Users\\4.mp4");
        File f5 = new File("C:\\Users\\5.mp4");
        File f6 = new File("C:\\Users\\6.mp4");
        File f7 = new File("C:\\Users\\7.mp4");
        File f8 = new File("C:\\Users\\8.mp4");
        File f9 = new File("C:\\Users\\9.mp4");

        WatchList w1 = new WatchList("WatchList1");
        WatchList w2 = new WatchList("WatchList2");
        WatchList w3 = new WatchList("WatchList3");

        Movie m1 = new Movie(f1,"Movie1",Language.ENGLISH,"Studio1");
        Movie m2 = new Movie(f2,"Movie2",Language.FRENCH,"Studio1");
        Movie m3 = new Movie(f3,"Movie3",Language.FRENCH,"Studio2");
        HashMap<String,String> cast1 = new HashMap<>();
        HashMap<String,String> cast2 = new HashMap<>();
        TVShow show1 = new TVShow("show1",Language.KLINGON,"Klingon Corps",cast1);
        show1.createAndAddEpisode(f4,"Ep1 show1");
        show1.createAndAddEpisode(f5,"Ep2 show1");
        show1.createAndAddEpisode(f6,"Ep3 show1");
        TVShow.Episode e1s1 = show1.getEpisode(1);
        TVShow show2 = new TVShow("show2",Language.ENGLISH,"Bother Brothers",cast2);
        show2.createAndAddEpisode(f7,"Ep1 Show2");
        show2.createAndAddEpisode(f8,"Ep2 Show2");
        show2.createAndAddEpisode(f9,"Ep3 Show2");
        TVShow.Episode e1s2 = show2.getEpisode(1);
        TVShow.Episode e2s2 = show2.getEpisode(2);
        TVShow.Episode e3s2 = show2.getEpisode(3);
        System.out.println("=================================================");
        System.out.println("Start testing lastWatched");
        System.out.println("-------------------------------------------------");
        System.out.println("watchlist1 is created, and it contains:");
        System.out.println("3 Movies(m1, m2, m3), 1 TVShow(show1), and 1 Episode(e1s2).");
        w1.addWatchable(m1);
        w1.addWatchable(m2);
        w1.addWatchable(m3);
        w1.addWatchable(show1);
        w1.addWatchable(e1s2);
        System.out.println("-------------------------------------------------");
        System.out.println("watchlist2 is created, and it contains:");
        System.out.println("2 Movies(m1, m2), 1 TVShow(show2), and 1 Episode(e1s1).");
        w2.addWatchable(m1);
        w2.addWatchable(m2);
        w2.addWatchable(show2);
        w2.addWatchable(e1s1);

        System.out.println("-------------------------------------------------");
        System.out.println("Now, we shall watch Movie m3.");
        m3.watch();
        System.out.println("We know watchlist1 contains m3. Let's check its lastWatched() method."+"\n");
        System.out.println(w1.lastWatched().getTitle()+"\n");
        System.out.println("No Watchable objects of w2 has been watched yet. Calling w2.lastWatched()"+"\n"
                            +"will throw NoSuchElementException.");
        System.out.println("-------------------------------------------------");
        System.out.println("Now, we shall watch show1.");
        System.out.println("Note that now watchlist1 contains show1, and watchlist2 contains e1s1,"+"\n"
                            +"the first episode of show1.");
        show1.watch();
        System.out.println("Check the lastWatched() of watchlist1."+"\n");
        System.out.println(w1.lastWatched().getTitle()+"\n");
        System.out.println("Check the lastWatched() of watchlist2."+"\n");
        System.out.println(w2.lastWatched().getTitle());
        System.out.println("-------------------------------------------------");

        System.out.println("Now, we shall watch e1s2, the 1st episode of show2.");
        System.out.println("Note that now watchlist1 contains e1s2, and watchlist2 contains show2,");
        e1s2.watch();
        System.out.println("Check the lastWatched() of watchlist1. It should be e1s2"+"\n");
        System.out.println(w1.lastWatched().getTitle()+"\n");
        System.out.println("Check the lastWatched() of watchlist2."+"\n"
                            +"Since watchlist2 contains show2 but not each episode of show2 directly,"+"\n"
                            +"it should not be affected(e1s1 should still be the last watched).");
        System.out.println(w2.lastWatched().getTitle());
        System.out.println("-------------------------------------------------");
        System.out.println("=================================================");
        System.out.println("Start testing redo and undo");
        System.out.println("-------------------------------------------------");
        w3.addWatchable(m1);
        w3.addWatchable(e1s1);
        w3.addWatchable(show1);
        w3.addWatchable(m2);
        w3.addWatchable(e1s2);
        System.out.println("The Watchables in watchlist3 is added in such an order:");
        System.out.println("-------------------------------------------------");
        System.out.println("m1,e1s1,show1,m2,e1s2");
        System.out.println("-------------------------------------------------");
        System.out.println("Since I haven't called undo() on watchlist3," +"\n"
                            +"calling redo twice will add e1s2 to watchlist3 twice.");
        System.out.println("-------------------------------------------------");
        w3.redo();
        w3.redo();
        Iterator<AbstractWatchable> i1 = w3.iterator();
        while(i1.hasNext()){
            System.out.println(i1.next().getTitle());
        }
        System.out.println("\n");
        System.out.println("Note that calling redo() right after an action other than undo() " +"\n"+
                            "causes the last action to be repeated.");
        System.out.println("-------------------------------------------------");
        System.out.println("Now, undo twice.");
        System.out.println("-------------------------------------------------");
        w3.undo();
        w3.undo();
        Iterator<AbstractWatchable> i2 = w3.iterator();
        while(i2.hasNext()){
            System.out.println(i2.next().getTitle());
        }

        System.out.println("-------------------------------------------------");
        System.out.println("Now, redo three times.");
        System.out.println("-------------------------------------------------");
        w3.redo();
        w3.redo();
        w3.redo();
        Iterator<AbstractWatchable> i3 = w3.iterator();
        while(i3.hasNext()){
            System.out.println(i3.next().getTitle());
        }
        System.out.println("\n");
        System.out.println("Note that the last undone action is redone on the second redo here.");
        System.out.println("The third redo did nothing.");
        System.out.println("-------------------------------------------------");
        System.out.println("Another thing to specify is that"+"\n"
                            +"the \"repeat\" feature of redo() only works when the state of the"+"\n"
                            +"watchlist is changed.");
        System.out.println("For example, I will use setName method to rename watchlist3 as \"watchlist3-changed\"," +"\n"
                            +" then redo twice, then undo once.");
        System.out.println("The name of watchlist3 is now: "+w3.getName());
        System.out.println("-------------------------------------------------");
        w3.setName("watchlist3-changed");
        System.out.println("The name of watchlist3 is now: "+w3.getName());
        System.out.println("-------------------------------------------------");
        System.out.println("Now, redo twice.");
        w3.redo();
        w3.redo();
        System.out.println("The name of watchlist3 is now: "+w3.getName());
        System.out.println("-------------------------------------------------");
        System.out.println("Now, undo once.");
        w3.undo();
        System.out.println("The name of watchlist3 is now: "+w3.getName());
        System.out.println("So you can see that since the two redo() didn't change the state of watchlist3,"+"\n"
                            +"undo once brought w3 back to its previous state(watchlist3).");
        System.out.println("-------------------------------------------------");
        System.out.println("Similar for reset().");
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("I will call next() twice on watchlist3");
        w3.next();
        w3.next();
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("Now I will call reset() once.");
        w3.reset();
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("Now I will call redo() twice.");
        w3.redo();
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        w3.redo();
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("Now I will call undo() once.");
        w3.undo();
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("So you see that reset() works similarly to setName().");
        System.out.println("-------------------------------------------------");
        System.out.println("Some regular operations of undo() and redo():");
        System.out.println("-------------------------------------------------");
        System.out.println("Reset watchlist3.");
        w3.reset();
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("-------------------------------------------------");
        System.out.println("1. Add m3 to watchlist3.");
        w3.addWatchable(m3);
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("-------------------------------------------------");
        System.out.println("2. Remove the Watchable at index 0, i.e. m1.");
        w3.removeWatchable(0);
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        Iterator<AbstractWatchable> i4 = w3.iterator();
        while(i4.hasNext()){
            System.out.println(i4.next().getTitle());
        }
        System.out.println("-------------------------------------------------");
        System.out.println("3. Set a new name for watchlist3.");
        w3.setName("Weird name");
        System.out.println(w3.getName());
        System.out.println("-------------------------------------------------");
        System.out.println("4. next.");
        w3.next();
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("-------------------------------------------------");
        System.out.println("5. next.");
        w3.next();
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("-------------------------------------------------");
        System.out.println("Now I will call undo 4 times, and redo twice.");
        System.out.println("Then watchlist 3 should be at state 3, with name \"Weird name\"," +"\n"
                            +"Movie1 just removed, and 7 as remaining count.");
        w3.undo();
        w3.undo();
        w3.undo();
        w3.undo();
        w3.redo();
        w3.redo();
        System.out.println("-------------------------------------------------");
        System.out.println("The remaining count of watchlist3: " + w3.getRemainingCount());
        System.out.println("The name of watchlist3 is now: "+w3.getName());
        Iterator<AbstractWatchable> i5 = w3.iterator();
        while(i5.hasNext()){
            System.out.println(i5.next().getTitle());
        }
        System.out.println("=================================================");
        System.out.println("End of Driver.java");
        System.out.println("=================================================");
    }
}
