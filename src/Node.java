import java.util.*;
/**
 * Created by zjhnd on 2017/4/5.
 */
public class Node {
    private boolean isMachine;
    private int Chess;
    private Node Parent = null;
    private State state = null;
    private int N = 0;
    private int Q = 0;
    private LinkedList<Node> Children = new LinkedList<Node>();


    public Node(int Chess, boolean isMachine) {
        state = new State(isMachine, Chess);
        state.Judge();
        this.Chess = Chess;
        this.isMachine = isMachine;
    }
    public Node(int[][] s, int Chess, boolean isMachine) {
        state = new State(s, isMachine, Chess);
        state.Judge();
        this.Chess = Chess;
        this.isMachine = isMachine;
    }
    public Node(State s) {
        state = s;
        this.Chess = s.getChess();
        this.isMachine = s.getIsMatchine();
    }
    public void increN() {
        N++;
    }
    public void increQ(int reward) {
        Q += reward;
    }
    public void changeState(int action) {
        state.changeState(action);
    }
    State cloneState() {
        return state.clone();
    }
    boolean isTerminal() {
        return state.isTerminal();
    }
    void setParent(Node p) {
        Parent = p;
    }
    Node getParent() {
        return Parent;
    }
    public void addChild(Node child) {
        Children.add(child);
    }
    public LinkedList<Node> getChildren() {
        return Children;
    }
    public Node getBestChild(double c) {
        double MAX = -Double.MAX_VALUE;
        Node maxNode = null;

        for (Node tmp: Children) {
            double m = isMachine ? 1.0 * (double)tmp.Q / (double)tmp.N : 1.0 - 1.0 * (double)tmp.Q / (double)tmp.N;
            double a = m + c * Math.sqrt(2.0 * Math.log((double)N) / (double)tmp.N);
            if(MAX < a) {
                maxNode = tmp;
                MAX = a;
            }
        }
        return maxNode;
    }
    public Node Expand() {
        HashMap<Integer, Integer> aset = state.getActionSet();
        HashMap<Integer, Integer> dset = state.getDirSet();
        Node child = null;

        int size = aset.size();
        if(size > 0) {
            State s = state.clone();
            Integer key = aset.get(size - 1);
            s.changeState(key);
            s.setLeadAction(key);
            //dset.remove(aset.get(size - 1));
            aset.remove(size - 1);
            s.changePlayer();
            //s.genActionSet(s.getChess());
            //s.Judge();
            child = new Node(s);
            child.setParent(this);
            addChild(child);
        }
        else {
            if(Children.size() == 0) {
                State s = state.clone();
                s.changePlayer();
                //s.genActionSet(s.getChess());
                //s.Judge();
                child = new Node(s);
                child.setParent(this);
                addChild(child);
            }
        }
        return child;
    }
    public int getN() {
        return N;
    }
    public int getQ() {
        return Q;
    }
    public static void main(String[] args) {
        Node node = new Node(Constants.BLACK, true);
        node.changeState(34);
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                System.out.print(node.cloneState().getState()[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(node.isTerminal());
        /*
        node.genActionSet();
        HashMap<Integer, Boolean> m = node.getActionSet();
        Iterator iter = m.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Integer key = (Integer) entry.getKey();
            System.out.println(key / 8 + " " + key % 8);
        }
        */
    }
    boolean isMachine() {
        return state.getIsMatchine();
    }
    int getChess() {
        return Chess;
    }
}
