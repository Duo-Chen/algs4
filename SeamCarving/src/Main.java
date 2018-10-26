public class Main {

    public static void main(String[] args) {
	// write your code here
        String[] files = {
                "3x4.png", "3x7.png", "4x6.png", "5x6.png",
                "6x5.png", "7x3.png", "7x10.png", "10x10.png",
                "10x12.png", "12x10.png", "diagonals.png", "stripes.png"
        };

        for (String file : files) {
            String[] arguments = new String[1];
            arguments[0] = "D:\\algs4\\SeamCarving\\src\\" + file;
            //PrintEnergy.main(arguments);
            PrintSeams.main(arguments);
        }
    }
}
