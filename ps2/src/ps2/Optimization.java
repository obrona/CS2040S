package ps2;


/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */













    public static int searchMax(int[] dataArray) {
        // TODO: Implement this
        int begin = 0;
        int end = dataArray.length-1;
        int output = 0;

        while(begin<=end) {
            if(begin==end) {
                if (begin == 0) {
                    output = Math.max(dataArray[0],dataArray[dataArray.length-1]);
                            //(dataArray[0] > dataArray[dataArray.length - 1]) ? dataArray[0] : dataArray[dataArray.length - 1];

                    break;
                }
                else if (begin== dataArray.length-1) {
                    output = Math.max(dataArray[0],dataArray[dataArray.length-1]);
                            //(dataArray[0] > dataArray[begin]) ? dataArray[0] : dataArray[begin];
                    break;

                }



                else {
                    output = dataArray[begin];
                    break;
                }
            }

             else if(end-begin==1) {
                if(dataArray[end]>dataArray[begin]) {
                     if(end== dataArray.length-1) {
                         //int possible = dataArray[0];
                         output = Math.max(dataArray[0],dataArray[end]);

                                 //(possible>dataArray[end]) ? possible : dataArray[end];
                         break;
                     }
                   else {
                       output = dataArray[end];
                       break;
                   }


                }
                else {
                    if(begin==0) {
                        //int possible = dataArray[dataArray.length-1];
                        output = Math.max(dataArray[begin],dataArray[dataArray.length-1]);
                                //(possible>dataArray[begin]) ? possible : dataArray[begin];
                        break;

                    }
                    else {
                        output = dataArray[begin];
                        break;

                    }


                }




            }
            else {
                int mid = (begin+end)/2;
                int a = dataArray[mid-1];
                int b = dataArray[mid];
                int c = dataArray[mid+1];
                if((a>b)&&(b>c)) {
                    end = mid;

                }
                else if((a<b)&&(b<c)) {
                    begin = mid+1;


                }
                else if ((a<b)&&(b>c)) {
                    output = b;
                    break;


                }
                else if ((a>b)&&(b<c)) {
                    output = Math.max(dataArray[0],dataArray[dataArray.length-1]);

                           // (dataArray[0]>dataArray[dataArray.length-1]) ? dataArray[0] : dataArray[dataArray.length-1];
                    break;

                }


            }




        }








        return output;
    }

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
