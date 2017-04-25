
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import layout.TableLayout;

public class WBGOGame extends JFrame{

	static int playerturn; // 1表示黑方执棋，-1表示白方执棋,这里假设电脑是白方，人是黑方
	static boolean isMachine;
	static int blockwidth;
	static int blockheight;
	static int blackplayer = 1;
	static int whiteplayer = -1;
	static int totalStep;
	//private ComputerPlayer computerPlayer;
	private static board gameboard = new board();
	private State state;
	private Mcts mcts = new Mcts();
	private Node root;
	public WBGOGame() {
		// TODO Auto-generated constructor stub

		gameboard.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(isMachine) {
					return ;
				}
				int xmouse = e.getX();
				int ymouse = e.getY();
				blockwidth = gameboard.getEachWidth();
				blockheight = gameboard.getEachHeight();
				int x = ymouse / blockwidth; //这里的x是二维数组中的行，换言之，即是鼠标的纵坐标相对开头的偏移单位数
				int y = xmouse / blockheight;
                int action = 8 * (x - 1) + y - 1;
				if(x < 1 || x > 8 || y < 1 || y > 8) {
					return;
				}
                if(!state.getActionSet().containsValue(action)) {
                    return;
                }


				if(gameboard.playable(x - 1, y - 1, playerturn)) {
					gameboard.setValueByindex(x - 1, y - 1, playerturn);
					gameboard.setNewChessIndex(x - 1, y - 1);
                    gameboard.repaint();

					state.changeState(action);
					state.changePlayer();
					playerturn = -playerturn;
					if(state.isTerminal()) {
						System.out.println("Winner: " + state.getWinner());
						return;
					}
					boolean flag = false;
					for (Node c: root.getChildren()) {
						if(c.getLeadAction() == action) {
							root = c;
							state.genActionSet();
							flag = true;
							break;
						}
					}
					if(!flag) {
						root = new Node(state);
					}

					Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                if(state.getActionSet().size() == 0) {
									boolean flag = false;
									for (Node c: root.getChildren()) {
										if(c.getLeadAction() == -1) {
											root = c;
											//state = root.getState();
											flag = true;
											break;
										}
									}
									if(!flag) {
										root = new Node(state);
									}
                                    state.changePlayer();
                                    playerturn = -playerturn;
                                    if(state.getActionSet().size() == 0) {
                                       System.out.println("Winner: " + state.getWinner());
                                    }
                                    break;
                                }
                                else {
                                    root = mcts.UctSearch(root);
                                    state.changeState(root.getLeadAction());
                                    state.changePlayer();
									playerturn = -playerturn;
                                    setBoard(state);
									//gameboard.setValueByindex(state.getLeadAction() / 8, state.getLeadAction() % 8, playerturn);
                                    gameboard.setNewChessIndex(state.getLeadAction() / 8, state.getLeadAction() % 8);
                                    gameboard.repaint();

                                    if(state.getActionSet().size() == 0) {
										boolean flag = false;
										for (Node c: root.getChildren()) {
											if(c.getLeadAction() == -1) {
												root = c;
												//state = root.getState();
												flag = true;
												break;
											}
										}
										if(!flag) {
											root = new Node(state);
										}
										//state.changeState(action);
										state.changePlayer();
										playerturn = -playerturn;
										if(state.isTerminal()) {
											System.out.println("Winner: " + state.getWinner());
											return;
										}

                                        //playerturn = -playerturn;
                                        //state.changePlayer();
                                        continue;
                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                        }
                    });
					thread.start();
					isMachine = false;
				}
				else {

                }
			}
		});
	}
	public void initializeGame() {
		//中间四个子分别为
		totalStep = 4;
		gameboard.setValueByindex(3, 3, whiteplayer);
		gameboard.setValueByindex(3, 4, blackplayer);
		gameboard.setValueByindex(4, 3, blackplayer);
		gameboard.setValueByindex(4, 4, whiteplayer);
		gameboard.setWBnums(2, 2);  //初始的时候黑方和白方均为2个
	}
	private void machineFirst(boolean flag) {
		if(flag) {
			state = new State(true, Constants.BLACK);
			root = new Node(state);
			root = mcts.UctSearch(root);
			state = root.getState();
			setBoard(state);
			isMachine = false;
			playerturn = whiteplayer;
		}
		else {
			state = new State(false, Constants.BLACK);
			root = new Node(state);
			setBoard(state);
			isMachine = false;
			playerturn = blackplayer;
		}
	}
	public void setBoard(State state) {
		int[][] s = state.getState();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(s[i][j] == Constants.BLACK) {
					gameboard.setValueByindex(i, j, blackplayer);
				}
				else if(s[i][j] == Constants.WHITE) {
					gameboard.setValueByindex(i, j, whiteplayer);
				}
			}
		}
        gameboard.repaint();
	}
	public void beginGame() {

		initializeGame();
		repaint();
	}
	public static void main(String[] args) {

		double size[][] ={
				{0.25,TableLayout.FILL},
				{TableLayout.FILL}};
		Random r1 = new Random();
		int index = r1.nextInt(10);
		for(int i= 0; i < 10 ; i++)
		{
			index = r1.nextInt(10);
			//System.out.print(index + " ");
		}
		TableLayout mtTableLayout = new TableLayout(size);
		WBGOGame newWbgoGame = new WBGOGame();
		newWbgoGame.setBounds(200, 200, 500, 410);
		newWbgoGame.setResizable(true);
		newWbgoGame.setLayout(mtTableLayout);
		newWbgoGame.add(gameboard,"1,0");
		newWbgoGame.setResizable(false);
		newWbgoGame.setTitle("Reversi");
		newWbgoGame.setVisible(true);
		newWbgoGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newWbgoGame.initializeGame();
        newWbgoGame.machineFirst(false);
		//newWbgoGame.beginGame();

	}
}
