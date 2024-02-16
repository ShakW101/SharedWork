import java.util.*;

public class Testing {
    public void initializeAndRun(int testCases){
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> treeSet = new TreeSet<>();
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        List<Integer> linkedList = new LinkedList<>();
        List<Integer> arrayList = new ArrayList<>();
        Queue<Integer> priorityQueue = new PriorityQueue<>();
        Queue<Integer> arrayDeque = new ArrayDeque<>();
        Map<Integer, Integer> hashMap = new HashMap<>();
        Map<Integer, Integer> treeMap = new TreeMap<>();
        Map<Integer, Integer> linkedHashMap = new LinkedHashMap<>();



        testSet(hashSet, "Hash Set","add", testCases);
        testSet(treeSet, "Tree Set","add", testCases);
        testSet(linkedHashSet, "Linked Hash Set", "add", testCases);
        testList(linkedList, "Linked List", "add", testCases);
        testList(arrayList, "Array List", "add", testCases);
        testQueue(priorityQueue, "Priority Queue", "add", testCases);
        testQueue(arrayDeque, "Array Deque", "add", testCases);
        testMap(hashMap, "Hash Map", "add", testCases);
        testMap(treeMap, "Tree Map", "add", testCases);
        testMap(linkedHashMap, "Linked Hash Map", "add", testCases);


        System.out.println();

        testSet(hashSet, "Hash Set","contains", testCases);
        testSet(treeSet, "Tree Set","contains", testCases);
        testSet(linkedHashSet, "Linked Hash Set", "contains", testCases);
        testList(linkedList, "Linked List", "contains", testCases);
        testList(arrayList, "Array List", "contains", testCases);
        testQueue(priorityQueue, "Priority Queue", "contains", testCases);
        testQueue(arrayDeque, "Array Deque", "contains", testCases);
        testMap(hashMap, "Hash Map", "contains", testCases);
        testMap(treeMap, "Tree Map", "contains", testCases);
        testMap(linkedHashMap, "Linked Hash Map", "contains", testCases);

        System.out.println();

        testSet(hashSet, "Hash Set","remove", testCases);
        testSet(treeSet, "Tree Set","remove", testCases);
        testSet(linkedHashSet, "Linked Hash Set", "remove", testCases);
        testList(linkedList, "Linked List", "remove", testCases);
        testList(arrayList, "Array List", "remove", testCases);
        testQueue(priorityQueue, "Priority Queue", "remove", testCases);
        testQueue(arrayDeque, "Array Deque", "remove", testCases);
        testMap(hashMap, "Hash Map", "remove", testCases);
        testMap(treeMap, "Tree Map", "remove", testCases);
        testMap(linkedHashMap, "Linked Hash Map", "remove", testCases);

        System.out.println();

        testSet(hashSet, "Hash Set","clear", testCases);
        testSet(treeSet, "Tree Set","clear", testCases);
        testSet(linkedHashSet, "Linked Hash Set", "clear", testCases);
        testList(linkedList, "Linked List", "clear", testCases);
        testList(arrayList, "Array List", "clear", testCases);
        testQueue(priorityQueue, "Priority Queue", "clear", testCases);
        testQueue(arrayDeque, "Array Deque", "clear", testCases);
        testMap(hashMap, "Hash Map", "clear", testCases);
        testMap(treeMap, "Tree Map", "clear", testCases);
        testMap(linkedHashMap, "Linked Hash Map", "clear", testCases);

        System.out.println();

    }

    private void loadSet(Set<Integer> input){
        input.clear();
        Random randInt = new Random();
        for(int i = 0; i < 100000; i++){
            Integer x = randInt.nextInt(100000);
            input.add(x);
        }
    }

    private void loadList(List<Integer> input){
        input.clear();
        Random randInt = new Random();
        for(int i = 0; i < 100000; i++){
            Integer x = Integer.valueOf(randInt.nextInt(100000));
            input.add(x);
        }
    }

    private void loadQueue(Queue<Integer> input){
        input.clear();
        Random randInt = new Random();
        for(int i = 0; i < 100000; i++){
            Integer x = Integer.valueOf(randInt.nextInt(100000));
            input.add(x);
        }
    }

    private void loadMap(Map<Integer, Integer> input){
        input.clear();
        Random randInt = new Random();
        for(int i = 0; i < 100000; i++){
            Integer x = Integer.valueOf(randInt.nextInt(100000));
            input.put(x, x);
        }
    }

    private void testSet(Set<Integer> input, String name, String test, int testNo){
        long totalTime = 0;
        long start = 0;
        long end = 0;
        Random rand = new Random();
        for(int i = 0; i < testNo; i++){
            loadSet(input);
            Integer x = Integer.valueOf(rand.nextInt(10000));
            if(test.equals("add")) {
                start = System.nanoTime();
                input.add(x);
                end = System.nanoTime();
                input.remove(x);
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
                start = System.nanoTime();

                input.clear();

                end = System.nanoTime();
            }

            totalTime += end - start;
        }

        long averageTime = totalTime/testNo;

        printResult(name, test, averageTime);
    }

    private void testList(List<Integer> input, String name, String test, int testNo){
        long totalTime = 0;
        long start = 0;
        long end = 0;
        Random rand = new Random();
        for(int i = 0; i < testNo; i++){
            loadList(input);
            Integer x = Integer.valueOf(rand.nextInt(10000));
            if(test.equals("add")) {
                start = System.nanoTime();
                input.add(x);
                end = System.nanoTime();
                input.remove(x);
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
                start = System.nanoTime();

                input.clear();

                end = System.nanoTime();
            }

            totalTime += end - start;
        }

        long averageTime = totalTime/testNo;

        printResult(name, test, averageTime);
    }

    private void testQueue(Queue<Integer> input, String name, String test, int testNo){
        long totalTime = 0;
        long start = 0;
        long end = 0;
        Random rand = new Random();
        for(int i = 0; i < testNo; i++){
            loadQueue(input);
            Integer x = Integer.valueOf(rand.nextInt(10000));
            if(test.equals("add")) {
                start = System.nanoTime();
                input.add(x);
                end = System.nanoTime();
                input.remove(x);
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
                start = System.nanoTime();

                input.clear();

                end = System.nanoTime();
            }

            totalTime += end - start;
        }

        long averageTime = totalTime/testNo;

        printResult(name, test, averageTime);
    }

    private void testMap(Map<Integer, Integer> input, String name, String test, int testNo){
        long totalTime = 0;
        long start = 0;
        long end = 0;
        Random rand = new Random();
        for(int i = 0; i < testNo; i++){
            loadMap(input);
            Integer x = Integer.valueOf(rand.nextInt(10000));
            if(test.equals("add")) {
                start = System.nanoTime();
                input.put(x, x);
                end = System.nanoTime();
                input.remove(x, x);
            }else if(test.equals("contains")){
                start = System.nanoTime();
                input.containsKey(x);
                end = System.nanoTime();
            }else if(test.equals("remove")){
                Set<Integer> keysSet = input.keySet();
                Integer[] array = new Integer[keysSet.size()];
                keysSet.toArray(array);
                int index = rand.nextInt(array.length);
                int element = (int) array[index];
                start = System.nanoTime();
                input.remove(element);
                end = System.nanoTime();
                input.put(element, element);
            }else if(test.equals("clear")){
                start = System.nanoTime();

                input.clear();

                end = System.nanoTime();
            }

            totalTime += end - start;
        }

        long averageTime = totalTime/testNo;

        printResult(name, test, averageTime);
    }

    private void printResult(String name, String test, long averageTime){
        System.out.printf("%s: %s: %d ns%n", name,test, averageTime);
    }

    public static void main(String[] args){
        Testing test = new Testing();
        int testCases = 100;
        test.initializeAndRun(testCases);
    }
}
