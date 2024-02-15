package project2;
import java.util.*;

public class Testing {
    public void initializeAndRun(int testCases){
        Collection<Integer> hashSet = new HashSet<>();
        Collection<Integer> treeSet = new TreeSet<>();
        Collection<Integer> linkedHashSet = new LinkedHashSet<>();
        Collection<Integer> linkedList = new LinkedList<>();
        Collection<Integer> ArrayList = new ArrayList<>();
        Collection<Integer> PriorityQueue = new PriorityQueue<>();

        loadCollection(hashSet);
        loadCollection(treeSet);
        loadCollection(linkedHashSet);
        loadCollection(linkedList);
        loadCollection(ArrayList);
        loadCollection(PriorityQueue);

        testAddSet(hashSet, "Hash Set", testCases);
        testAddSet(treeSet, "Tree Set", testCases);
        testAddSet(linkedHashSet, "Linked Hash Set", testCases);
        testAddSet(linkedList, "Linked List", testCases);
        testAddSet(linkedList, "Array List", testCases);
        testAddSet(linkedList, "Priority Queue", testCases);


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
        Random rand = new Random();
        for(int i = 0; i < tests; i++){
            Integer x = Integer.valueOf(rand.nextInt(10000));
            long start = System.nanoTime();
            set.add(x);
            long end = System.nanoTime();

            totalTime += end - start;

            set.remove(i);
        }

        long averageTime = totalTime/tests;

        System.out.printf("%s: %d ns%n", name, averageTime);
    }

    private void printCollection(List<Integer> arr){
        int x = arr.size();
        for(int i = 0; i < x; i++){
            System.out.println(arr.g);
        }
    }
    public static void main(String[] args){
        Testing test = new Testing();
        int testCases = 100;
        test.initializeAndRun(testCases);
    }
}
