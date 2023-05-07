package ps1;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

/**
 * ShiftRegisterTest
 * @author dcsslg
 * Description: set of tests for a shift register implementation
 */
public class ShiftRegisterTest {
    /**
     * Returns a shift register to test.
     * @param size
     * @param tap
     * @return a new shift register
     */
    ILFShiftRegister getRegister(int size, int tap) {
        return new ShiftRegister(size, tap);
    }

    /**
     * Tests shift with simple example.
     */
    @Test
    public void testShift1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 };
        for (int i = 0; i < 10; i++) {

            assertEquals(expected[i], r.shift());
        }
    }

    /**
     * Tests generate with simple example.
     */
    @Test
    public void testGenerate1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 6, 1, 7, 2, 2, 1, 6, 6, 2, 3 };
        for (int i = 0; i < 10; i++) {
            assertEquals("GenerateTest", expected[i], r.generate(3));
        }
    }

    /**
     * Tests register of length 1.
     */
    @Test
    public void testOneLength() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = { 1 };
        r.setSeed(seed);
        int[] expected = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.generate(3));
        }
    }

    @Test
    public void testShift2() {
        ILFShiftRegister q = getRegister(5,1);
        int[] seed = {1,0,0,1,1};
        q.setSeed(seed);
        int[] expected = {1,0,1,0,0};
        for(int i = 0; i <=4; i++){
            assertEquals(expected[i],q.shift());
        }


        }

   @Test
   public void testGenerate2() {
       ILFShiftRegister q = getRegister(5,1);
       int[] seed = {1,0,0,1,1};
       q.setSeed(seed);
       int[] expected = {20};
       for(int i = 0; i <1; i++){
           assertEquals(expected[i],q.generate(5));
       }


    }

    @Test //Test when tap bit is also the most significant bit
    public void testCorner() {
        ILFShiftRegister q = getRegister(5,4);
        int[] seed = {1,0,1,0,1};
        q.setSeed(seed);
        int[] expected = {0,0,0,0,0};
        for(int i=0;i<5;i++) {
            assertEquals(expected[i],q.shift());
        }
    }







    @Test
   public void testInvalidValues() {
        ILFShiftRegister q = getRegister(5,2);
        int[] seed = {1,2,3,4,5};
        q.setSeed(seed);
        q.shift();
   }
//Test case passed. Printed Invalid Seed.
    @Test
    public void testInvalidTap() {
        ILFShiftRegister q = getRegister(5,10);
        int[] seed = {1,2,3,4,5};
        q.setSeed(seed);
        q.shift();
    }
//Test case pass. I made my code print check input for the user.








    /**
     * Tests with erroneous seed.
     */
    @Test
    public void testError() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = { 1, 0, 0, 0, 1, 1, 0 };
        r.setSeed(seed);
        r.shift();
        r.generate(4);
    }


}
//Results in index out of bounds error because the length of array shift_register built is too short
//It should print out a message that says that the size entered is not equal to the seed inputted.
//Before we run the program we should test whether size = seed.length