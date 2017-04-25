import java.util.Random;

/**
 * Created by zjhnd on 2017/4/11.
 */
public class Constants {
    public static final int NORTH = 0x00000100;
    public static final int NORTHEAST = 0x00000200;
    public static final int EAST = 0x00000400;
    public static final int SOUTHEAST = 0x00000800;
    public static final int SOUTH = 0x00001000;
    public static final int SOUTHWEST = 0x00002000;
    public static final int WEST = 0x00004000;
    public static final int NORTHWEST = 0x00008000;
    public static final int Direction = 0x0000FF00;
    public static final int Position = 0x000000FF;
    public static final int BLACK = 1;
    public static final int WHITE = -1;
    public static final int machineProfit = 1;
    public static final int playerProfit = -1;
    public static final Random random = new Random();
    public static final int[][] weightMatrix = {
            {99, -8, 8, 6, 6, 8, -8, 99},
            {-8, -24, -4, -3, -3, -4, -24, -8},
            {8, -4, 7, 4, 4, 7, -4, 8},
            {6, -3, 4, 0, 0, 4, -3, 6},
            {6, -3, 4, 0, 0, 4, -3, 6},
            {8, -4, 7, 4, 4, 7, -4, 8},
            {-8, -24, -4, -3, -3, -4, -24, -8},
            {99, -8, 8, 6, 6, 8, -8, 99},
    };
}
