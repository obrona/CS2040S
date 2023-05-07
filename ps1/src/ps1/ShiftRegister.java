package ps1;///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

import java.util.Arrays;

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    int[] shift_register ;
    int taps;
    int sizes;
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:


    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
     ShiftRegister(int size, int tap) {
        if (size < 0 || tap < 0 || tap > size - 1) {
            System.out.println("Invalid input/s");
            return;
        } else {
            shift_register = new int[size];
            taps = tap;
            sizes = size;

        }
        // TODO:
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////

    /**
     * setSeed
     *
     * @param seed Description:
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        int len = seed.length;
        if (len != sizes) {
            System.out.println("Invalid Seed");
        } else {
            for (int i = 0; i < len; i++) {
                if (seed[i] < 0 || seed[i] > 1) {
                    System.out.println("Invalid seed");
                    shift_register = null;
                    break;
                } else {

                    shift_register[i] = seed[i];

                }
            }
        }
    }
    /**
     * shift
     *
     * @return Description:
     */
    @Override
    public int shift() {

        // TODO:
        if (shift_register == null) {
            System.out.println("Check input");
            return 1;

        } else {


            int tap_bit = shift_register[taps];
            int feedback_bit = tap_bit ^ shift_register[sizes - 1];
            int temp = shift_register[0];
            for (int i = 0; i < sizes - 1; i++) {
                int temp_2 = shift_register[i + 1];
                shift_register[i + 1] = temp;
                temp = temp_2;


            }
            shift_register[0] = feedback_bit;


            return feedback_bit;


        }
    }
    /**
     * generate
     *
     * @param k
     * @return Description:
     */
    @Override
    public int generate(int k) {
        // TODO:
        int[] output = new int[k];
        for (int i = 0; i < k; i++) {

            output[i] = shift();
        }
        int sum = 0;
        int power = 0;
        for (int m = k - 1; m >= 0; m = m - 1) {
            sum = (int) (sum + output[m] * Math.pow(2, power));
            power = power + 1;
        }
        return sum;
      //int ans = Integer.parseInt(output,2);
      //return sum;
    }

    /**
     * Returns the integer representation for a binary int array.
     *
     * @param array
     * @return
     */
    private int toDecimal(int[] array) {
        // TODO:
        return 0;
    }



}

