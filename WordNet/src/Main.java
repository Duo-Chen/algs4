public class Main {
    private static void test_sap() {
        String[] args = {};
        SAP.main(args);
    }

    private static void test_wordnet() {
        String[] args = {};
        WordNet.main(args);
    }

    private static void test_outcast() {
        String[] args = {"synsets.txt", "hypernyms.txt", "outcast5.txt", "outcast8.txt", "outcast11.txt"};
        Outcast.main(args);
    }

    public static void main(String[] args) {
	// write your code here
        test_sap();
        test_wordnet();
        test_outcast();
    }
}
