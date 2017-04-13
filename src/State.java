
import org.jetbrains.annotations.Contract;

import java.util.*;

/**
 * Created by zjhnd on 2017/4/5.
 */
public class State {
    private HashMap<Integer, Integer> actionSet = null;
    private HashMap<Integer, Integer> dirSet = null;
    private boolean terminal = false;
    private boolean isMachine = false;
    private int Chess;
    private int leadAction = -1;
    /*
    private int[][] state = {
            { 1,  1,  1,  1,  1,  1,  1,  1},
            { 1,  1,  1,  1,  1,  1,  1,  0},
            { 1,  1,  1,  1,  1,  1,  1,  0},
            { 1,  1,  1, -1,  1,  1,  1,  0},
            { 1,  1,  1,  1,  1,  1,  1,  0},
            { 1,  1,  1,  1,  1, -1,  1,  0},
            { 1,  1,  1,  1,  1,  1,  0,  0},
            { 1,  1,  1,  1,  0,  0,  0,  0},
    };
    */
    /*
    private int[][] state = {
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1,  0, -1, -1, -1, -1, -1},
            {-1, -1, -1,  1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1,  0, -1, -1},
            {-1, -1, -1, -1, -1, -1,  0, -1},
            {-1, -1, -1, -1, -1, -1, -1,  0},
    };
    */
    /*
    private int[][] state = {
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1,  1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1,  0, -1,  1},
            {-1, -1, -1, -1, -1, -1,  0,  1},
            {-1, -1, -1, -1, -1, -1, -1,  0},
    };
    */

    private int[][] state = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, Constants.WHITE, Constants.BLACK, 0, 0, 0},
            {0, 0, 0, Constants.BLACK, Constants.WHITE, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
    };

    public State(boolean isMachine, int Chess) {
        this.isMachine = isMachine;
        this.Chess = Chess;
        actionSet = new HashMap<Integer, Integer>();
        dirSet = new HashMap<Integer, Integer>();
        genActionSet();
    }
    public State(int[][] s, boolean isMachine, int Chess) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                state[i][j] = s[i][j];
            }
        }
        this.isMachine = isMachine;
        this.Chess = Chess;
        actionSet = new HashMap<Integer, Integer>();
        dirSet = new HashMap<Integer, Integer>();
        genActionSet();
    }
    public void changePlayer() {
        Chess = -Chess;
        isMachine = !isMachine;
        genActionSet();
        //Judge();
    }
    public void genActionSet() {
        actionSet.clear();
        dirSet.clear();
        /*
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(state[i][j] == Chess) {
                    int m = i;
                    int n = j;
                    while(++m < 8 && ++n < 8 && state[m][n] + Chess == 0);
                    if(m < 8 && n < 8 && state[m][n] == 0 && (Math.abs(m - i) > 1 || Math.abs(n - j) > 1)) {
                        int a = 8 * m + n;
                        if(!actionSet.contains(a)) {
                            actionSet.add(a);
                        }
                    }
                    m = i;
                    n = j;
                    while(++m < 8 && --n > -1 && state[m][n] + Chess == 0);
                    if(m < 8 && n > -1 && state[m][n] == 0 && (Math.abs(m - i) > 1 || Math.abs(n - j) > 1)) {
                        int a = 8 * m + n;
                        if(!actionSet.contains(a)) {
                            actionSet.add(a);
                        }
                    }
                    m = i;
                    n = j;
                    while(--m > -1 && ++n < 8 && state[m][n] + Chess == 0);
                    if(m > -1 && n < 8 && state[m][n] == 0 && (Math.abs(m - i) > 1 || Math.abs(n - j) > 1)) {
                        int a = 8 * m + n;
                        if(!actionSet.contains(a)) {
                            actionSet.add(a);
                        }
                    }
                    m = i;
                    n = j;
                    while(--m > -1 && --n > -1 && state[m][n] + Chess == 0);
                    if(m > -1 && n > -1 && state[m][n] == 0 && (Math.abs(m - i) > 1 || Math.abs(n - j) > 1)) {
                        int a = 8 * m + n;
                        if(!actionSet.contains(a)) {
                            actionSet.add(a);
                        }
                    }
                    m = i;
                    n = j;
                    while(++n < 8 && state[m][n] + Chess == 0);
                    if(n < 8 && state[m][n] == 0 && (Math.abs(m - i) > 1 || Math.abs(n - j) > 1)) {
                        int a = 8 * m + n;
                        if(!actionSet.contains(a)) {
                            actionSet.add(a);
                        }
                    }
                    m = i;
                    n = j;
                    while(--n > -1 && state[m][n] + Chess == 0);
                    if(n > -1 && state[m][n] == 0 && (Math.abs(m - i) > 1 || Math.abs(n - j) > 1)) {
                        int a = 8 * m + n;
                        if(!actionSet.contains(a)) {
                            actionSet.add(a);
                        }
                    }
                    m = i;
                    n = j;
                    while(++m < 8 && state[m][n] + Chess == 0);
                    if(m < 8 && state[m][n] == 0 && (Math.abs(m - i) > 1 || Math.abs(n - j) > 1)) {
                        int a = 8 * m + n;
                        if(!actionSet.contains(a)) {
                            actionSet.add(a);
                        }
                    }
                    m = i;
                    n = j;
                    while(--m > -1 && state[m][n] + Chess == 0);
                    if(m > -1 && state[m][n] == 0 && (Math.abs(m - i) > 1 || Math.abs(n - j) > 1)) {
                        int a = 8 * m + n;
                        if(!actionSet.contains(a)) {
                            actionSet.add(a);
                        }
                    }
                }
            }
        }
        */
        Judge();
    }
    public void changeState(int action) {
        int dir = dirSet.get(action);
        int i = action / 8;
        int j = action % 8;
        int m = i;
        int n = j;
        leadAction = action;
        state[i][j] = Chess;
        if((dir & Constants.NORTH) > 0) {
            --m;
            while(state[m][n] + Chess == 0) {
                state[m--][n] = Chess;
            }
            state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.NORTHEAST) > 0) {
            --m;
            ++n;
            while(state[m][n] + Chess == 0) {
                state[m--][n++] = Chess;
            }
            state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.EAST) > 0) {
            ++n;
            while(state[m][n] + Chess == 0) {
                state[m][n++] = Chess;
            }
            state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.SOUTHEAST) > 0) {
            ++m;
            ++n;
            while(state[m][n] + Chess == 0) {
                state[m++][n++] = Chess;
            }
            state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.SOUTH) > 0) {
            ++m;
            while(state[m][n] + Chess == 0) {
                state[m++][n] = Chess;
            }
            state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.SOUTHWEST) > 0) {
            ++m;
            --n;
            while(state[m][n] + Chess == 0) {
                state[m++][n--] = Chess;
            }
            state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.WEST) > 0) {
            --n;
            while(state[m][n] + Chess == 0) {
                state[m][n--] = Chess;
            }
            state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.NORTHWEST) > 0) {
            --m;
            --n;
            while(state[m][n] + Chess == 0) {
                state[m--][n--] = Chess;
            }
            state[m][n] = Chess;
        }


    }
    public HashMap<Integer, Integer> getActionSet() {
        return actionSet;
    }
    public int getRandomAction() {


        int size = actionSet.size();
        if(size == 0) {
            return -1;
        }
        if(actionSet.containsValue(0)) {
            return 0;
        }
        if(actionSet.containsValue(7)) {
            return 7;
        }
        if(actionSet.containsValue(56)) {
            return 56;
        }
        if(actionSet.containsValue(63)) {
            return 63;
        }

        int i = 0;
        while (i < size) {
            Integer a = actionSet.get(i);
            ++i;
            int row = a / 8;
            int col = a % 8;
            if((state[0][0] != Chess && row < 2 && col < 2) || (state[7][0] != Chess && row > 5 && col < 2) || (state[7][7] != Chess && row > 5 && col > 5) || (state[0][7] != Chess && row < 2 && col > 5)) {
                continue;
            }
            if(col == 0 || col == 7 || row == 0 || row ==7) {
                return a;
            }
        }

        int cnt = 3;
        int sel = -1;
        while(cnt > 0) {
            cnt--;
            sel = Constants.random.nextInt(128) % size;
            int row = actionSet.get(sel) / 8;
            int col = actionSet.get(sel) % 8;
            if((state[0][0] != Chess && row < 2 && col < 2) || (state[7][0] != Chess && row > 5 && col < 2) || (state[7][7] != Chess && row > 5 && col > 5) || (state[0][7] != Chess && row < 2 && col > 5)) {
                continue;
            }
            break;
        }

        //if(sel == 9 || sel == 14 || sel == 49 || sel == 54) {
            //sel = r.nextInt(256) % size;
        //}
        //actionSet.remove(sel);
        return actionSet.get(sel);
    }
    private int findActionByDirection(int dir, int m, int n) {
        int first = -1;
        boolean flag = false;
        while(true) {
            if(dir == Constants.NORTH) {
                m--;
            }
            else if(dir == Constants.NORTHEAST) {
                m--;
                n++;
            }
            else if(dir == Constants.EAST) {
                n++;
            }
            else if(dir == Constants.SOUTHEAST) {
                m++;
                n++;
            }
            else if(dir == Constants.SOUTH) {
                m++;
            }
            else if(dir == Constants.SOUTHWEST) {
                m++;
                n--;
            }
            else if(dir == Constants.WEST) {
                n--;
            }
            else if(dir == Constants.NORTHWEST) {
                m--;
                n--;
            }
            if(m < 0 || m > 7 || n < 0 || n > 7) {
                break;
            }
            if(!flag) {
                if(state[m][n] == 0) {
                    return 0;
                }
                first = state[m][n];
                flag = true;
            }
            else {
                if(state[m][n] + first == 0 && state[m][n] == Chess) {
                    return 2;
                }
                else if(state[m][n] + first == 0 && state[m][n] == -Chess) {
                    return 1;
                }
                else if(state[m][n] == 0) {
                    return 0;
                }
            }
        }
        return 0;
    }
    public void Judge() {
        terminal = true;
        int no = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(state[i][j] == 0) {
                    int action = i * 8 + j;
                    boolean flag = false;
                    for(int dir = Constants.NORTH; dir <= Constants.NORTHWEST; dir <<= 1) {
                        int res = findActionByDirection(dir, i, j);
                        if(res == 0) {
                            continue;
                        }
                        if(res == 2) {
                            action |= dir;
                            flag = true;
                        }
                        terminal = false;
                    }
                    if(flag) {
                        actionSet.put(no, action & Constants.Position);
                        dirSet.put(action & Constants.Position, action & Constants.Direction);
                        no++;
                    }
                }
            }
        }
    }
    public boolean isTerminal() {
        return terminal;
    }
    public int getWinner() {
        int cnt1 = 0, cnt2 = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(state[i][j] == Constants.BLACK) {
                    cnt1++;
                }
                else if(state[i][j] == Constants.WHITE) {
                    cnt2++;
                }
            }
        }
        return cnt1 > cnt2 ? Constants.BLACK : (cnt1 == cnt2 ? 0 : Constants.WHITE);
    }
    public int[][] getState() {
        return state.clone();
    }
    public boolean getIsMatchine() {
        return isMachine;
    }
    public int getChess() {
        return Chess;
    }
    public State clone() {
        State s = new State(this.state, this.isMachine, this.Chess);
        s.terminal = this.terminal;
        if(this.actionSet != null) {
            s.actionSet = (HashMap<Integer, Integer>) this.actionSet.clone();
        }
        if(this.dirSet != null) {
            s.dirSet = (HashMap<Integer, Integer>) this.dirSet.clone();
        }
        s.leadAction = this.leadAction;
        return s;
    }
    public void Display() {
        System.out.printf("%4d", 0);
        for(int i = 1; i < 8; i++) {
            System.out.printf("%3d", i);
        }
        System.out.println();
        for(int i = 0; i < 8; i++) {
            System.out.print(i);
            for(int j = 0; j < 8; j++) {
                System.out.printf("%3d", getState()[i][j]);
            }
            System.out.println();
        }
    }
    public void setLeadAction(int a) {
        leadAction = a;
    }
    public int getLeadAction() {
        return leadAction;
    }
    public HashMap<Integer, Integer> getDirSet() {
        return dirSet;
    }
}
