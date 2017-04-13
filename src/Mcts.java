import javax.swing.text.html.CSS;

/**
 * Created by zjhnd on 2017/4/5.
 */
public class Mcts {
    private static final long Budget = 3000;
    public Mcts() {
    }
    State UctSearch(State s) {
        Node root = new Node(s);
        long start = System.currentTimeMillis();
        int cnt = 0;
        while(System.currentTimeMillis() - start <= Budget) {
            Node v = TreePolicy(root);
            int reward = DefaultPolicy(v);
            Backup(v, reward);
            cnt++;
            //if(v.isTerminal()) {
                //break;
            //}
        }
        System.out.println("Simulation times: " + cnt);
        Node node = BestChild(root, 1.4);
        double num1 = (double)node.getQ() / (double)node.getN();
        double num2 = Math.sqrt(2.0) * Math.sqrt(2.0 * Math.log((double)root.getN()) / (double)node.getN());
        System.out.println(node.getQ() + " " + node.getN() + " " + num1 + " " + num2);
        //(double)tmp.Q / (double)tmp.N + c * Math.sqrt(2.0 * Math.log((double)N) / (double)tmp.N);
        State res = node.cloneState();
        res.genActionSet();
        return res;
    }
    public Node TreePolicy(Node node) {
        Node v = node;
        while(!v.isTerminal()) {
            Node c = v.Expand();
            if(c != null) {
                return c;
            }
            else {
                v = BestChild(v, 1.4);
            }
        }
        return v;
    }
    public int DefaultPolicy(Node node) {
        State s = node.cloneState();
        s.genActionSet();
        //Node p = node;
        //Node child;
        while(!s.isTerminal()) {
            int action = s.getRandomAction();
            if(action != -1) {
                s.changeState(action);
            }
            s.changePlayer();
            //s.Judge();
            //child = new Node(s);
            //child.setParent(p);
            //p = child;
        }
        int winner = s.getWinner();
        if(winner == 0) {
            return 0;
        }
        if(node.isMachine()) {
            return node.getChess() == winner ? Constants.machineProfit : Constants.playerProfit;
        }
        return node.getChess() == winner ? Constants.playerProfit : Constants.machineProfit;
    }
    public void Backup(Node node, int reward) {
        Node p = node;
        while(p != null) {
            p.increN();
            p.increQ(reward);
            p = p.getParent();
        }
    }
    public Node BestChild(Node v, double c) {
        return v.getBestChild(c);
    }
}
