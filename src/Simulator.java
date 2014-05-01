import java.util.*;

public class Simulator {

    private ArrayList<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();
    private int nextTile;
    private int balanceOneTwo;
    private int score;

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
                if(!changed[i] && ((current.get(j).equals(current.get(j+1)) && current.get(j)>2) || (current.get(j)+current.get(j+1)==3) || current.get(j)==0)) {
                    if ((current.get(j).equals(current.get(j+1)) && current.get(j)>2) || (current.get(j)+current.get(j+1)==3))
                        score+=Math.pow(3,Math.log((current.get(j)+current.get(j+1))/3)/Math.log(2)+1);
                    current.set(j, current.get(j)+current.get(j+1));
                    changed[i] = true;
                } else if(changed[i]){
                    current.set(j, current.get(j+1));
                }
            if(changed[i]) {current.set(3,0);}
            grid.set(i,current);
        }
        //add new tile if the grid was changed
        if(changed[0] || changed[1] || changed[2] || changed[3]) {
            int newTileLocation;
            do{
                newTileLocation = (int)(Math.random()*4);
            } while (!changed[newTileLocation]);
            grid.get(newTileLocation).set(3,nextTile);
            nextTile = randomTile();
        }
    }

    public void moveRight()
    {
        boolean[] changed = {false,false,false,false};
        //shifts everything right
        for(int i=0; i<4; ++i)
        {
            ArrayList<Integer> current = grid.get(i);
            for(int j=3;j>0;--j)
                if(!changed[i] && ((current.get(j).equals(current.get(j-1)) && current.get(j)>2) || (current.get(j)+current.get(j-1)==3) || current.get(j)==0)) {
                    if ((current.get(j).equals(current.get(j-1)) && current.get(j)>2) || (current.get(j)+current.get(j-1)==3))
                        score+=Math.pow(3,Math.log((current.get(j)+current.get(j-1))/3)/Math.log(2)+1);
                    current.set(j, current.get(j)+current.get(j-1));
                    changed[i] = true;
                } else if(changed[i]){
                    current.set(j, current.get(j-1));
                }
            if(changed[i]) {current.set(0,0);}
            grid.set(i,current);
        }
        //add new tile
        if(changed[0] || changed[1] || changed[2] || changed[3]) {
            int newTileLocation;
            do{
                newTileLocation = (int)(Math.random()*4);
            } while (!changed[newTileLocation]);
            grid.get(newTileLocation).set(0,nextTile);
            nextTile = randomTile();
        }
    }

    public void moveUp()
    {
        boolean[] changed = {false,false,false,false};
        //shifts everything up
        for(int j=0; j<4; ++j)
        {
            for(int i=0;i<3;++i)
                if(!changed[j] && ((grid.get(i).get(j).equals(grid.get(i+1).get(j)) && grid.get(i).get(j)>2) || (grid.get(i).get(j)+grid.get(i+1).get(j)==3) || grid.get(i).get(j)==0)) {
                    if ((grid.get(i).get(j).equals(grid.get(i+1).get(j)) && grid.get(i).get(j)>2) || (grid.get(i).get(j)+grid.get(i+1).get(j)==3))
                        score+=Math.pow(3,Math.log((grid.get(i).get(j)+grid.get(i+1).get(j))/3)/Math.log(2)+1);
                    grid.get(i).set(j, grid.get(i).get(j) + grid.get(i + 1).get(j));
                    changed[j] = true;
                } else if(changed[j]){
                    grid.get(i).set(j, grid.get(i + 1).get(j));
                }
            if(changed[j]) {grid.get(3).set(j, 0);}
        }
        //add new tile
        if(changed[0] || changed[1] || changed[2] || changed[3]) {
            int newTileLocation;
            do{
                newTileLocation = (int)(Math.random()*4);
            } while (!changed[newTileLocation]);
            grid.get(3).set(newTileLocation,nextTile);
            nextTile = randomTile();
        }
    }

    public void moveDown()
    {
        boolean[] changed = {false,false,false,false};
        //shifts everything down
        for(int j=0; j<4; ++j)
        {
            for(int i=3;i>0;--i)
                if(!changed[j] && ((grid.get(i).get(j).equals(grid.get(i-1).get(j)) && grid.get(i).get(j)>2) || (grid.get(i).get(j)+grid.get(i-1).get(j)==3) || grid.get(i).get(j)==0)) {
                    if ((grid.get(i).get(j).equals(grid.get(i-1).get(j)) && grid.get(i).get(j)>2) || (grid.get(i).get(j)+grid.get(i-1).get(j)==3))
                        score+=Math.pow(3,Math.log((grid.get(i).get(j)+grid.get(i-1).get(j))/3)/Math.log(2)+1);
                    grid.get(i).set(j, grid.get(i).get(j)+grid.get(i-1).get(j));
                    changed[j] = true;
                } else if(changed[j]){
                    grid.get(i).set(j, grid.get(i-1).get(j));
                }
            if(changed[j]) {grid.get(0).set(j,0);}
        }
        //add new tile
        if(changed[0] || changed[1] || changed[2] || changed[3]) {
            int newTileLocation;
            do{
                newTileLocation = (int)(Math.random()*4);
            } while (!changed[newTileLocation]);
            grid.get(0).set(newTileLocation,nextTile);
            nextTile = randomTile();
        }
    }

    public ArrayList<ArrayList<Integer>> getThreesGrid()
    {
        return grid;
    }

    public int getNextTile() { return nextTile; }

    public int getScore() { return score; }

    //checks if grid is alive
    public boolean isAlive()
    {
        boolean live = false;
        for(int i=0; i<4; ++i)
        {
            ArrayList<Integer> current = grid.get(i);
            for(int j = 0; j < 3; ++j)
                if((current.get(j).equals(current.get(j+1)) && current.get(j)>2) || (current.get(j)+current.get(j+1)==3) || current.get(j)==0) {
                    live = true;
                    break;
                }
        }
        if(!live) {
            for(int j=0; j<4; ++j) {
                for (int i = 0; i < 3; ++i)
                    if (((grid.get(i).get(j).equals(grid.get(i + 1).get(j)) && grid.get(i).get(j) > 2) || (grid.get(i).get(j) + grid.get(i + 1).get(j) == 3) || grid.get(i).get(j) == 0)) {
                        live = true;
                        break;
                    }
            }
        }
        return live;
    }

    private int randomTile()
    {
        int value;
        if(balanceOneTwo<-1)
        {
            value = (int)(Math.random()*2)+2;
        } else if(balanceOneTwo>1) {
            value = (int)(Math.random()*2)*2+1;
        } else {
            value = (int)(Math.random()*3) + 1;
        }
        if(value == 1) {
            --balanceOneTwo;
        } else if(value == 2) {
            ++balanceOneTwo;
        }
        return value;
    }
}