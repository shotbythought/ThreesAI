import java.util.*;

public class Simulator {

    private ArrayList<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();
    private Deque<ThreesGrid> history = new ArrayDeque<ThreesGrid>();

    @SuppressWarnings("unchecked")
    public Simulator()
    {
        history.push(new ThreesGrid());
    }

    public void move(int direction)
    {
        history.push(new ThreesGrid(getCurrent().getScore(),getCurrent().getNextTile(),getCurrent().getThreesGrid()));
        boolean change = getCurrent().move(direction);
        if(!change) {
            history.pop();
        }
    }

    public ThreesGrid getCurrent()
    {
        return history.getFirst();
    }

    public void undo()
    {
        if(history.size()>1)
        {
            history.pop();
        }
    }

    public void artificialMove()
    {
        ThreesGrid myThreesGrid = getCurrent();
        ArrayList<ArrayList<Integer>> myGrid = myThreesGrid.getThreesGrid();
        Integer myNextTile = myThreesGrid.getNextTile();

    }

    public void runArtificial()
    {
        while(getCurrent().isAlive())
        {
            artificialMove();
        }
    }

}