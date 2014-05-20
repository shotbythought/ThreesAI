/**
 * Created by Jolee on 5/19/2014.
 */

import java.util.*;

public class ThreesGrid {

    int score = 0;
    int nextTile;
    ArrayList<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();

    public ThreesGrid()
    {
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

    public ThreesGrid(int score, int nextTile, ArrayList<ArrayList<Integer>> grid)
    {
        this.score = score;
        this.nextTile = nextTile;
        this.grid = cloneGrid(grid);
    }

    public boolean move(int direction)
    {
        grid = cloneGrid(grid);
        switch(direction){
            case 'l':
                return moveLeft();
            case 'r':
                return moveRight();
            case 'd':
                return moveDown();
            case 'u':
                return moveUp();
            default:
                return false;
        }
    }

    public boolean moveLeft()
    {
        boolean[] changed = {false,false,false,false};
        //shifts everything left
        shiftLeft(changed);
        //add new tile if the grid was changed
        return insertNewTile(changed);
    }

    public boolean insertNewTile(boolean[] changed){
        if(changed[0] || changed[1] || changed[2] || changed[3]) {
            int newTileLocation;
            do{
                newTileLocation = (int)(Math.random()*4);
            } while (!changed[newTileLocation]);
            grid.get(newTileLocation).set(3,nextTile);
            nextTile = randomTile();
            return true;
        }
        else return false;
    }

    public void shiftLeft(boolean[] changed)
    {
        for(int i=0; i<4; ++i)
        {
            for(int j=0;j<3;++j) {
                Integer currentSquare = grid.get(i).get(j);
                Integer nextSquare = grid.get(i).get(j + 1);
                Integer additionSquare = nextSquare + currentSquare;
                if (!changed[i] && (((additionSquare%3 == 0 && isPowerOfTwo((additionSquare)/3)) || currentSquare == 0) && nextSquare!= 0) ) {
                    if (currentSquare != 0)
                        score += Math.pow(3, Math.log((additionSquare) / 3) / Math.log(2) + 1);
                    changed[i] = true;
                    grid.get(i).set(j, additionSquare);
                } else if (changed[i]) {
                    grid.get(i).set(j, nextSquare);
                }
            }
            if(changed[i])
            {
                grid.get(i).set(3, 0);
            }
        }
    }


    public boolean moveRight()
    {
        flipGrid();
        boolean returnBool = moveLeft();
        flipGrid();
        return returnBool;
    }

    public boolean moveUp()
    {
        transpose();
        boolean returnBool = moveLeft();
        transpose();
        return returnBool;
    }

    public boolean moveDown()
    {
        transpose();
        flipGrid();
        boolean returnBool = moveLeft();
        flipGrid();
        transpose();
        return returnBool;
    }

    private void flipGrid()
    {
        for (ArrayList<Integer> list : grid)
            Collections.reverse(list);
    }

    private void transpose()
    {
        ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i<4; ++i)
        {
            ArrayList<Integer> col = new ArrayList<Integer>();
            for(int j = 0; j<4; ++j)
            {
                col.add(grid.get(j).get(i));
            }
            temp.add(col);
        }
        grid = temp;
    }

    private int randomTile()
    {
        return (int)(Math.random()*3)+1;
    }

    private boolean isPowerOfTwo(int x)
    {
        return (x & (x-1)) == 0;
    }

    public boolean isAlive()
    {
        boolean live = false;
        for(int i=0; i<4; ++i)
        {
            for(int j = 0; j < 3; ++j) {
                Integer currentSquare = grid.get(i).get(j);
                Integer nextSquare = grid.get(i).get(j + 1);
                Integer additionSquare = currentSquare + nextSquare;
                if ((additionSquare % 3 == 0 && isPowerOfTwo((additionSquare) / 3)) || (currentSquare == 0 && nextSquare != 0)) {
                    live = true;
                    break;
                }
            }
        }
        if(!live) {
            for(int j=0; j<4; ++j) {
                for (int i = 0; i < 3; ++i) {
                    Integer currentSquare = grid.get(i).get(j);
                    Integer nextSquare = grid.get(i + 1).get(j);
                    Integer additionSquare = currentSquare + nextSquare;
                    if ((additionSquare % 3 == 0 && isPowerOfTwo((additionSquare) / 3)) || (currentSquare == 0 && nextSquare != 0)) {
                        live = true;
                        break;
                    }
                }
            }
        }
        return live;
    }

    public ArrayList<ArrayList<Integer>> getThreesGrid() { return grid; }

    public int getNextTile() { return nextTile; }

    public int getScore() { return score; }

    private ArrayList<ArrayList<Integer>> cloneGrid(ArrayList<ArrayList<Integer>> grid)
    {
        ArrayList<ArrayList<Integer>> tempGrid = new ArrayList<ArrayList<Integer>>();
        for(ArrayList<Integer> list : grid)
        {
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            for(Integer tempI : list)
            {
                tempList.add(tempI);
            }
            tempGrid.add(tempList);
        }
        return tempGrid;
    }

    public double getFavScore()
    {
        return 2;
    }
}
