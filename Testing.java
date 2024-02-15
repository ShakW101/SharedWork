import java.util.*;

public class Testing {
    public void initializeAndRun(int testCases){
        Collection<Integer> hashSet = new HashSet<>();
        Collection<Integer> treeSet = new TreeSet<>();
        Collection<Integer> linkedHashSet = new LinkedHashSet<>();

        loadCollection(hashSet);
        loadCollection(treeSet);
        loadCollection(linkedHashSet);

        testAddSet(hashSet, "Hash Set", testCases);
        testAddSet(treeSet, "Tree Set", testCases);
        testAddSet(linkedHashSet, "Linked Hash Set", testCases);

    }

    private void loadCollection(Collection<Integer> input){
        Random randInt = new Random();
        for(int i = 0; i < 100000; i++){
            Integer x = Integer.valueOf(randInt.nextInt(100000));
            input.add(x);
        }
    }

    private void testAddSet(Collection<Integer> set, String name, int tests){
        long totalTime = 0;
        for(int i = 0; i < tests; i++){
            long start = System.nanoTime();
            set.add(i);
            long end = System.nanoTime();

            totalTime += end - start;

            set.remove(i);
        }

        long averageTime = totalTime/tests;

        System.out.printf("%s: %d ns%n", name, averageTime);
    }
    public static void main(String[] args){
        Testing test = new Testing();
        int testCases = 100;
        test.initializeAndRun(testCases);
    }
}
