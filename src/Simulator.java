import java.util.*;

/**
 * Created by Student on 4/16/2014.
 */
public class Simulator {

    private ArrayList<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();
    private int nextTile;

    @SuppressWarnings("unchecked")
    public Simulator()
    {
        //initialize a 4x4 array of 0s
        for(int i=0; i<4; ++i)
        {
            ArrayList<Integer> in = new ArrayList<Integer>();
            for (int j=0; j<4; ++j)
                in.add(0);
            grid.add(in);
        }
        //create 9 tiles
        HashSet randomList = new HashSet<Integer>();
        while (randomList.size()!=9){
            randomList.add((int)(Math.random()*16));
        }
        Iterator<Integer> it = randomList.iterator();
        while(it.hasNext())
        {
            int current = it.next();
            grid.get(current/4).set(current%4,randomTile());
        }
        nextTile = randomTile();
    }

    //CHANGE THESE
    public void moveLeft()
    {
        boolean[] changed = {false,false,false,false};
        //shifts everything left
        for(int i=0; i<4; ++i)
        {
            ArrayList<Integer> current = grid.get(i);
            for(int j=0;j<3;++j)
                if(!changed[i] && ((current.get(j) == current.get(j+1) && current.get(j)>2) || (current.get(j)+current.get(j+1)==3) || current.get(j)==0)) {
                    current.set(j, current.get(j)+current.get(j+1));
                    changed[i] = true;
                } else if(changed[i]){
                    current.set(j, current.get(j+1));
                }
            if(changed[i]) {current.set(3,0);}
            grid.set(i,current);
        }
        //add new tile
        int newTileLocation;
        do{
            newTileLocation = (int)(Math.random()*4);
        } while (!changed[newTileLocation]);
        grid.get(newTileLocation).set(3,nextTile);
        nextTile = randomTile();
    }

    public void moveRight()
    {
        boolean[] changed = {false,false,false,false};
        //shifts everything right
        for(int i=0; i<4; ++i)
        {
            ArrayList<Integer> current = grid.get(i);
            for(int j=3;j>0;--j)
                if(!changed[i] && ((current.get(j) == current.get(j-1) && current.get(j)>2) || (current.get(j)+current.get(j-1)==3) || current.get(j)==0)) {
                    current.set(j, current.get(j)+current.get(j-1));
                    changed[i] = true;
                } else if(changed[i]){
                    current.set(j, current.get(j-1));
                }
            if(changed[i]) {current.set(0,0);}
            grid.set(i,current);
        }
        //add new tile
        int newTileLocation;
        do{
            newTileLocation = (int)(Math.random()*4);
        } while (!changed[newTileLocation]);
        grid.get(newTileLocation).set(0,nextTile);
        nextTile = randomTile();
    }

    public void moveUp()
    {
        boolean[] changed = {false,false,false,false};
        //shifts everything up
        for(int j=0; j<4; ++j)
        {
            for(int i=0;i<3;++i)
                if(!changed[j] && ((grid.get(i).get(j) == grid.get(i+1).get(j) && grid.get(i).get(j)>2) || (grid.get(i).get(j)+grid.get(i+1).get(j)==3) || grid.get(i).get(j)==0)) {
                    grid.get(i).set(j, grid.get(i).get(j)+grid.get(i+1).get(j));
                    changed[j] = true;
                } else if(changed[j]){
                    grid.get(i).set(j, grid.get(i+1).get(j));
                }
            if(changed[j]) {grid.get(3).set(j,0);}
        }
        //add new tile
        int newTileLocation;
        do{
            newTileLocation = (int)(Math.random()*4);
        } while (!changed[newTileLocation]);
        grid.get(3).set(newTileLocation,nextTile);
        nextTile = randomTile();
    }

    public void moveDown()
    {
        boolean[] changed = {false,false,false,false};
        //shifts everything down
        for(int j=0; j<4; ++j)
        {
            for(int i=3;i>0;--i)
                if(!changed[j] && ((grid.get(i).get(j) == grid.get(i-1).get(j) && grid.get(i).get(j)>2) || (grid.get(i).get(j)+grid.get(i-1).get(j)==3) || grid.get(i).get(j)==0)) {
                    grid.get(i).set(j, grid.get(i).get(j)+grid.get(i-1).get(j));
                    changed[j] = true;
                } else if(changed[j]){
                    grid.get(i).set(j, grid.get(i-1).get(j));
                }
            if(changed[j]) {grid.get(0).set(j,0);}
        }
        //add new tile
        int newTileLocation;
        do{
            newTileLocation = (int)(Math.random()*4);
        } while (!changed[newTileLocation]);
        grid.get(0).set(newTileLocation,nextTile);
        nextTile = randomTile();
    }

    public ArrayList<ArrayList<Integer>> getThreesGrid()
    {
        return grid;
    }

    public int getNextTile() { return nextTile; }

    public boolean ifLive()
    {
        //checks if grid is alive
        return true;
    }

    private int randomTile()
    {
        return (int)(Math.random()*3) + 1;
    }
}
