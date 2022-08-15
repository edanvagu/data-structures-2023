import sequential.ArrayList;

/**
 * @author Andres Gutierrez
 */
public class Main {
    public static void main(String[] args) {

        ArrayList<Integer> myList = new ArrayList<>(5);
        myList.add(10);
        myList.add(5);
        myList.add(0,3);
        myList.add(7);
        myList.add(1,1);
        //[3,1,10,5,7]
        System.out.println(myList);
        myList.add(0, 9);
        myList.add(9);
        System.out.println(myList.size());
        System.out.println(myList);
        System.out.println(myList.contains(10));
        System.out.println(myList.contains(2));
        System.out.println(myList.indexOf(10));
        myList.remove(Integer.valueOf(10));
        System.out.println(myList);
        myList.remove(0);
        System.out.println(myList);
        System.out.println(myList.isEmpty());
        myList.clear();
        System.out.println(myList.isEmpty());
        System.out.println(myList);

        myList.add(2);
        myList.add(5);
        myList.add(0,1);
        myList.add(2,4);
        myList.add(2,3);
        //[1,2,3,4,5]
        for (Integer el: myList) {
            System.out.println(el);
        }
    }
}
