import java.util.Arrays;
import java.util.Random;

public class SortingTester {

    public static boolean checkSort(ISort sorter, int size) {
        // TODO: implement this
        Random gen = new Random();

            KeyValuePair[] testArray = new KeyValuePair[size];
            for (int x = 1; x <= 5; x++) {
                for (int i = 0; i < size; i += 1) {

                    testArray[i] = new KeyValuePair(gen.nextInt(300), i);
                }

                long cost = sorter.sort(testArray);

                System.out.println(cost);
                //System.out.println(Arrays.toString(testArray));
                for (int j = 0; j < size - 1; j++) {
                    if (testArray[j].getKey() > testArray[j + 1].getKey()) {
                        return false;
                    }

                }
            }

        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        // TODO: implement this
        Random gen = new Random();

        KeyValuePair[] testArray = new KeyValuePair[size];


        for(int i=0;i<size;i+=1) {
           /*if(i%2==0) {

               testArray[i] = new KeyValuePair(-10, i);
           } */

          // at least n/3 sets of 3 equal key values to test stability
               testArray[i] = new KeyValuePair(gen.nextInt(size/3), i);



        }

        long cost = sorter.sort(testArray);

        System.out.println(cost);
       System.out.println(Arrays.toString(testArray));
        for(int i=0;i<size-1;i+=1)
            if(testArray[i].getKey()==testArray[i+1].getKey()) {
                if(testArray[i].getValue()>testArray[i+1].getValue()) {
                    return false;
                }
            }






        return true;
    }

    public static boolean SortedIncreasing(ISort sorter, int size) {

        KeyValuePair[] testArray = new KeyValuePair[size];

        for(int i = 0;i < size; i++) {

                testArray[i] = new KeyValuePair(i, i);

        }
        long cost = sorter.sort(testArray);

        System.out.println(cost);

        for(int j=0;j<size-1;j++) {
            if(testArray[j].getKey()>testArray[j+1].getKey()) {

                return false;
            }

        }
       return true;
    }

    public static boolean SortedDecreasing(ISort sorter, int size){

        KeyValuePair[] testArray = new KeyValuePair[size];

        for(int i = 0;i < size; i++) {
            testArray[i] = new KeyValuePair(size-i,i);
        }

        long cost = sorter.sort(testArray);

        System.out.println(cost);

        for(int j=0;j<size-1;j++) {
            if(testArray[j].getKey()>testArray[j+1].getKey()) {

                return false;
            }

        }
        return true;



    }

    public static boolean specialTestCase(ISort sorter, int size) {
     //to distinguish between bubble and insert
        KeyValuePair[] test = new KeyValuePair[size];

        for(int i=0;i<size;i++) {

            if(i==size-1) {
                test[i] = new KeyValuePair(0,i);
            }
            else {
                test[i] = new KeyValuePair(i+1,i);
            }
        }


        long cost = sorter.sort(test);
        System.out.println(cost);

        for (int j = 0; j < size - 1; j++) {
            if (test[j].getKey() > test[j + 1].getKey()) {
                return false;
            }

        }

        return true;
    }

    public static boolean specialTestCase2(ISort sorter, int size) {
        //all values equal
        KeyValuePair[] test = new KeyValuePair[size];

        for(int i=0;i<size;i++) {
            if(i<size/4) {
                test[i] = new KeyValuePair(6,i);
            }
            else {
                test[i] = new KeyValuePair(6,i);
            }
        }

        long cost = sorter.sort(test);
        System.out.println(cost);
        //System.out.println(Arrays.toString(test));


        for (int j = 0; j < size - 1; j++) {
            if (test[j].getKey() > test[j + 1].getKey()) {
                return false;
            }

        }





        return true;
    }







    public static void main(String[] args) {
        // TODO: implement this
        ISort x = new SorterE();
        //System.out.println(isStable( x,1000));

        System.out.println(checkSort(x,500000));
        //System.out.println(checkSort(x,250));


        //System.out.println(specialTestCase(x,1000));
        //System.out.println(specialTestCase(x,250));

        //System.out.println(SortedDecreasing(x,1000));
        //System.out.println(SortedDecreasing(x,250));

        //System.out.println(SortedIncreasing(x,1000));
        //System.out.println(SortedIncreasing(x,250));

       // System.out.println(specialTestCase2(x,1000000));
        //System.out.println(specialTestCase2(x,250000));
    }
}
