package iter;

import org.junit.Test;

public class IterSkiplistTest {

    @Test
    public void simpleTest() {
        IterSkiplist myList= new IterSkiplist();
        System.out.println("List: ");
        myList.printg(); //should print an empty list
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("20 is in the list " + myList.isPresent(20));
        myList.insert(5); //[5]
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        myList.insert(9); //[5, 9]
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        myList.insert(2);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(7); //[2, 5, 7, 9]
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        myList.insert(1);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(3);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(4);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(6);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(8);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(10); //[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        System.out.println("4 is in the list " + myList.isPresent(4));
        System.out.println("List: ");
        myList.printg();
        myList.delete(4);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        System.out.println("4 is in the list " + myList.isPresent(4));
        System.out.println("Size is " + myList.size());
        System.out.println("Height is " + myList.height());
    }
}
