import java.util.*;

public class Simulator {

    private ArrayList<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();
    private int nextTile;
    private int balanceOneTwo;
    private int score;
    private Deque<ArrayList<ArrayList<Integer>>> undoStack = new ArrayDeque<ArrayList<ArrayList<Integer>>>();

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
        undoStack.push(grid);
    }

    //CHANGE THESE
    public void moveLeft()
    {
        boolean[] changed = {false,false,false,false};
        //shifts everything left
        for(int i=0; i<4; ++i)
        {
            for(int j=0;j<3;++j) {
                Integer currentSquare = grid.get(i).get(j);
                Integer nextSquare = grid.get(i).get(j + 1);
                Integer additionSquare = nextSquare + currentSquare;
                if (!changed[i] && ((additionSquare%3 == 0 && isPowerOfTwo((additionSquare)/3)) || (currentSquare == 0 && nextSquare!= 0)) ) {
                    if (currentSquare != 0 && nextSquare != 0)
                        score += Math.pow(3, Math.log((additionSquare) / 3) / Math.log(2) + 1);
                    grid.get(i).set(j, additionSquare);
                    changed[i] = true;
                } else if (changed[i]) {
                    grid.get(i).set(j, nextSquare);
                }
            }
            if(changed[i])
            {
                grid.get(i).set(3, 0);
            }
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
        flipGrid();
        moveLeft();
        flipGrid();
    }

    public void moveUp()
    {
        transpose();
        moveLeft();
        transpose();
    }

    public void moveDown()
    {
        transpose();
        flipGrid();
        moveLeft();
        flipGrid();
        transpose();
    }

    public void save()
    {
        undoStack.push(grid);
    }

    public void undo()
    {
        grid = undoStack.pop();
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

    public ArrayList<ArrayList<Integer>> getThreesGrid() { return grid; }

    public int getNextTile() { return nextTile; }

    public int getScore() { return score; }

    //checks if grid is alive
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

    private boolean isPowerOfTwo(int x)
    {
        return (x & (x-1)) == 0;
    }
}