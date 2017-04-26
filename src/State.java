
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
    private int numBlack = 2;
    private int numWhite = 2;
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
    /*
    private int[][] state = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, -1, 0, 0},
            {0, 0, 0, 0, 1, 0, -1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, -1, 0, 0},
            {0, 0, 0, -1, -1, 0, 0, 0},
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

    public int getNumBlack() {
        return numBlack;
    }
    public int getNumWhite() {
        return numWhite;
    }
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
    public void changeState(int action) {
        int dir = dirSet.get(action);
        int i = action / 8;
        int j = action % 8;
        int m = i;
        int n = j;
        leadAction = action;
        state[i][j] = Chess;
        int cnt = 1;
        if((dir & Constants.NORTH) > 0) {
            --m;
            while(state[m][n] + Chess == 0) {
                state[m--][n] = Chess;
                cnt++;
            }
            //state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.NORTHEAST) > 0) {
            --m;
            ++n;
            while(state[m][n] + Chess == 0) {
                state[m--][n++] = Chess;
                cnt++;
            }
            //state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.EAST) > 0) {
            ++n;
            while(state[m][n] + Chess == 0) {
                state[m][n++] = Chess;
                cnt++;
            }
            //state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.SOUTHEAST) > 0) {
            ++m;
            ++n;
            while(state[m][n] + Chess == 0) {
                state[m++][n++] = Chess;
                cnt++;
            }
            //state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.SOUTH) > 0) {
            ++m;
            while(state[m][n] + Chess == 0) {
                state[m++][n] = Chess;
                cnt++;
            }
            //state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.SOUTHWEST) > 0) {
            ++m;
            --n;
            while(state[m][n] + Chess == 0) {
                state[m++][n--] = Chess;
                cnt++;
            }
            //state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.WEST) > 0) {
            --n;
            while(state[m][n] + Chess == 0) {
                state[m][n--] = Chess;
                cnt++;
            }
            //state[m][n] = Chess;
        }
        m = i;
        n = j;
        if((dir & Constants.NORTHWEST) > 0) {
            --m;
            --n;
            while(state[m][n] + Chess == 0) {
                state[m--][n--] = Chess;
                cnt++;
            }
            //state[m][n] = Chess;
        }
        if(Chess == Constants.BLACK) {
            numBlack += cnt;
            numWhite -= (cnt - 1);
        }
        else {
            numWhite += cnt;
            numBlack -= (cnt - 1);
        }
    }
    public HashMap<Integer, Integer> getActionSet() {
        return actionSet;
    }
    public int getRandomAction() {
        /*
        int size = actionSet.size();
        if(size == 0) {
            return -1;
        }
        if(numBlack + numWhite < 50) {
            int ret;
            if((ret = strategyCorner()) != -1) {
                return ret;
            }
            if((ret = strategyEdge()) != -1) {
                return ret;
            }
            return strategyWeightedMatrix();
        }
        return strategyMaxScore();
        */
        int size = actionSet.size();
        int i = 0;
        int maxIndex = -1;
        int maxReward = -100;
        if(size == 0) {
            return -1;
        }
        if(numBlack + numWhite > 50) {
            /*
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
            return actionSet.get(sel);
            */
            //return strategyMaxScore();
            return actionSet.get(Constants.random.nextInt(2048) % size);
        }
        int min = 1000;
        while (i < size) {
            Integer a = actionSet.get(i);
            int row = a / 8;
            int col = a % 8;
            if(Constants.weightMatrix[row][col] > maxReward) {
                maxIndex = i;
                maxReward = Constants.weightMatrix[row][col];
            }
            if(Constants.weightMatrix[row][col] < min) {
                min = Constants.weightMatrix[row][col];
            }
            ++i;
        }
        i = 0;
        int sum = 0;
        while(i  < size) {
            Integer a = actionSet.get(i);
            int row = a / 8;
            int col = a % 8;
            sum += Constants.weightMatrix[row][col] - min + 1;
            i++;
        }
        int r = Constants.random.nextInt(4096) % sum + 1;
        sum = 0;
        while(i < size) {
            Integer a = actionSet.get(i);
            int row = a / 8;
            int col = a % 8;
            sum += Constants.weightMatrix[row][col] - min + 1;
            if(r <= sum) {
                return a;
            }
            i++;
        }
        return actionSet.get(maxIndex);
        /*
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

        int cnt = 4;
        int sel;
        while(cnt > 0) {
            cnt--;
            sel = Constants.random.nextInt(128) % size;
            int row = actionSet.get(sel) / 8;
            int col = actionSet.get(sel) % 8;
            if((state[0][0] != Chess && row < 2 && col < 2) || (state[7][0] != Chess && row > 5 && col < 2) || (state[7][7] != Chess && row > 5 && col > 5) || (state[0][7] != Chess && row < 2 && col > 5)) {
                continue;
            }
            return actionSet.get(sel);
        }
        */
        /*
        if(numBlack + numWhite > 64) {
            int cnt = 3;
            int sel = -1;
            while(cnt > 0) {
                cnt--;
                sel = Constants.random.nextInt(1024) % size;
                int row = actionSet.get(sel) / 8;
                int col = actionSet.get(sel) % 8;
                if((state[0][0] != Chess && row < 2 && col < 2) || (state[7][0] != Chess && row > 5 && col < 2) || (state[7][7] != Chess && row > 5 && col > 5) || (state[0][7] != Chess && row < 2 && col > 5)) {
                    continue;
                }
                break;
            }
            return actionSet.get(sel);
        }
        */
        /*
        int size = actionSet.size();
        int i = 0;
        int maxIndex = -1;
        int maxReward = -10000;
        if(size == 0) {
            return -1;
        }
        State tmp = this.clone();
        int W = getWeight(this.getState(), Chess);
        int min = 10000;
        i = 0;
        while (i < size) {
            Integer a = actionSet.get(i);
            tmp.changeState(a);
            int w = getWeight(tmp.getState(), Chess);
            int d = w - W ;
            if(min > d) {
                min = d;
            }
            if(d > maxReward) {
                maxReward = d;
                maxIndex = i;
            }
            if(tmp.getActionSet().size() <= 1) {
                return a;
            }
            tmp.Reverse(this);
            i++;
        }
        return actionSet.get(maxIndex);
        */
        /*
        i = 0;
        int sum = 0;
        while(i < size) {
            list.set(i, list.get(i) - min + 1);
            sum += list.get(i);
            i++;
        }
        int r = Constants.random.nextInt(10 * sum) % sum + 1;
        i = 0;
        sum = 0;
        while(i < size) {
            sum += list.get(i);
            if(r <= sum) {
                return actionSet.get(i);
            }
            i++;
        }
        */

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

    public boolean isTerminal() {
        return terminal;
    }
    public int getWinner() {
        /*
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
        */
        return numBlack > numWhite ? Constants.BLACK : (numBlack == numWhite ? 0 : Constants.WHITE);
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
        s.numWhite = this.numWhite;
        s.numBlack = this.numBlack;
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
    private void Reverse(State last) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                state[i][j] = last.getState()[i][j];
            }
        }
        this.isMachine = last.getIsMatchine();
        this.Chess = last.getChess();
        this.numBlack = last.numBlack;
        this.numWhite = last.numWhite;
        this.leadAction = last.leadAction;
        //genActionSet();
    }
    private int getWeight(int[][] state, int chess) {
        int w = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(state[i][j] == chess) {
                    w += Constants.weightMatrix[i][j];
                }
            }
        }
        return w;
    }
    private int strategyWeightedMatrix() {
        int size = actionSet.size();
        State tmp = this.clone();
        int i = 0;
        int maxIndex = -1;
        int maxReward = -10000;
        while (i < size) {
            Integer a = actionSet.get(i);
            tmp.changeState(a);
            int w = getWeight(tmp.getState(), Chess);
            if(w > maxReward) {
                maxReward = w;
                maxIndex = i;
            }
            if(tmp.getActionSet().size() <= 1) {
                return a;
            }
            tmp.Reverse(this);
            i++;
        }
        return actionSet.get(maxIndex);
    }
    private int strategyMaxScore() {
        int size = actionSet.size();
        State tmp = this.clone();
        int orig = (Chess == 1 ? numBlack : numWhite);
        int i = 0;
        int maxIndex = -1;
        int maxScore = -10000;
        while (i < size) {
            Integer a = actionSet.get(i);
            tmp.changeState(a);
            int n = (Chess == 1 ? tmp.getNumBlack() : tmp.getNumWhite());
            if(n > maxScore) {
                maxScore = n;
                maxIndex = i;
            }
            tmp.Reverse(this);
            i++;
        }
        return actionSet.get(maxIndex);
    }
    int strategyCorner() {
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
        return -1;
    }
    int strategyEdge() {
        int i = 0;
        int size = actionSet.size();
        State tmp = this.clone();
        while (i < size) {
            Integer a = actionSet.get(i);
            ++i;
            int row = a / 8;
            int col = a % 8;
            if(col == 0 || col == 7) {
                boolean good = true;
                boolean flag = false;
                tmp.changeState(a);
                int j = row;
                while(j > 0) {
                    j--;
                    if(tmp.getState()[j][col] == 0) {
                        if(j - 1 >= 0) {
                            if(tmp.getState()[j - 1][col] == Chess) {
                                flag = true;
                            }
                        }
                        break;
                    }
                    if(tmp.getState()[j][col] == -Chess) {
                        good  = !good;
                        break;
                    }
                }
                if(j == 0 && state[j][col] == Chess) {
                    return a;
                }
                j = row;
                while(j < 7) {
                    j++;
                    if(tmp.getState()[j][col] == 0) {
                        if(j + 1 <= 7) {
                            if(tmp.getState()[j + 1][col] == Chess) {
                                flag = true;
                            }
                        }
                        break;
                    }
                    if(tmp.getState()[j][col] == -Chess) {
                        good  = !good;
                        break;
                    }
                }
                if(!flag && (good || (j == 7 && state[j][col] == Chess))) {
                    return a;
                }
                tmp.Reverse(this);
            }
            if(row == 0 || row == 7) {
                boolean good = true;
                boolean flag = false;
                tmp.changeState(a);
                int j = col;
                while(j > 0) {
                    j--;
                    if(tmp.getState()[row][j] == 0) {
                        if(j - 1 >= 0) {
                            if(tmp.getState()[row][j - 1] == Chess) {
                                flag = true;
                            }
                        }
                        break;
                    }
                    if(tmp.getState()[row][j] == -Chess) {
                        good  = !good;
                        break;
                    }
                }
                if(j == 0 && state[row][j] == Chess) {
                    return a;
                }
                j = col;
                while(j < 7) {
                    j++;
                    if(tmp.getState()[row][j] == 0) {
                        if(j + 1 <= 7) {
                            if(tmp.getState()[row][j + 1] == Chess) {
                                flag = true;
                            }
                        }
                        break;
                    }
                    if(tmp.getState()[row][j] == -Chess) {
                        good  = !good;
                        break;
                    }
                }
                if(!flag && (good || (j == 7 && state[row][j] == Chess))) {
                    return a;
                }
                tmp.Reverse(this);
            }
        }
        return -1;
    }
}
