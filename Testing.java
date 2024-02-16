
import java.util.*;

public class Testing {
    public void initializeAndRun(int testCases){
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> treeSet = new TreeSet<>();
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        List<Integer> linkedList = new LinkedList<>();
        List<Integer> ArrayList = new ArrayList<>();
        Queue<Integer> PriorityQueue = new PriorityQueue<>();
        Queue<Integer> ArrayDeque = new ArrayDeque<>();

        loadSet(hashSet);
        loadSet(treeSet);
        loadSet(linkedHashSet);
        loadList(linkedList);
        loadList(ArrayList);
        loadQueue(PriorityQueue);
        loadQueue(ArrayDeque);

        testSet(hashSet, "Hash Set","add", testCases);
        testSet(treeSet, "Tree Set","add", testCases);
        testSet(linkedHashSet, "Linked Hash Set", "add", testCases);
        System.out.println();
        testSet(hashSet, "Hash Set","contains", testCases);
        testSet(treeSet, "Tree Set","contains", testCases);
        testSet(linkedHashSet, "Linked Hash Set", "contains", testCases);
        System.out.println();
        testSet(hashSet, "Hash Set","remove", testCases);
        testSet(treeSet, "Tree Set","remove", testCases);
        testSet(linkedHashSet, "Linked Hash Set", "remove", testCases);
        System.out.println();
        testSet(hashSet, "Hash Set","clear", testCases);
        testSet(treeSet, "Tree Set","clear", testCases);
        testSet(linkedHashSet, "Linked Hash Set", "clear", testCases);
//        testAddSet(linkedList, "Linked List", testCases);
//        testAddSet(ArrayList, "Array List", testCases);
//        testAddSet(PriorityQueue, "Priority Queue", testCases);
//        testAddSet(ArrayDeque, "Array Deque", testCases);



    }

    private void loadSet(Set<Integer> input){
        Random randInt = new Random();
        for(int i = 0; i < 100000; i++){
            Integer x = randInt.nextInt(100000);
            input.add(x);
        }
    }

    private void loadList(List<Integer> input){
        Random randInt = new Random();
        for(int i = 0; i < 100000; i++){
            Integer x = Integer.valueOf(randInt.nextInt(100000));
            input.add(x);
        }
    }

    private void loadQueue(Queue<Integer> input){
        Random randInt = new Random();
        for(int i = 0; i < 100000; i++){
            Integer x = Integer.valueOf(randInt.nextInt(100000));
            input.add(x);
        }
    }

    private void testSet(Set<Integer> input, String name, String test, int testNo){
        long totalTime = 0;
        long start = 0;
        long end = 0;
        Random rand = new Random();
        for(int i = 0; i < testNo; i++){
            Integer x = Integer.valueOf(rand.nextInt(10000));
            if(test.equals("add")) {
                start = System.nanoTime();
                input.add(x);
                end = System.nanoTime();
                input.remove(i);
            }else if(test.equals("contains")){
                start = System.nanoTime();
                input.contains(x);
                end = System.nanoTime();
            }else if(test.equals("remove")){
                Object[] array =  input.toArray();
                int index = rand.nextInt(array.length);
                int element = (int) array[index];
                start = System.nanoTime();
                input.remove(element);
                end = System.nanoTime();
                input.add(element);
            }else if(test.equals("clear")){
                Set<Integer> temp = null;

                if(input instanceof LinkedHashSet<Integer>){
                    temp = new LinkedHashSet<Integer>(input);
                } else if(input instanceof HashSet<Integer>){
                    temp = new HashSet<Integer>(input);
                } else if(input instanceof TreeSet<Integer>){
                    temp = new TreeSet<Integer>(input);
                }
                    //System.out.println("mmm");
               // System.out.println(temp);
                System.out.println(temp.size());
                start = System.nanoTime();

                temp.clear();
//                if(input.isEmpty())
//                    System.out.println("hmm");
                end = System.nanoTime();
                //System.out.println(input);

                //System.out.println(end - start);

            }

            totalTime += end - start;
        }

        long averageTime = totalTime/testNo;

        System.out.printf("%s: %s: %d ns%n", name,test, averageTime);
    }
    public static void main(String[] args){
        Testing test = new Testing();
        int testCases = 1;
        test.initializeAndRun(testCases);
    }
}
