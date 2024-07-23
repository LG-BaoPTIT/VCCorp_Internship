package org.example.exception;

public class UncheckedExceptionExample {
    public static void main(String[] args) {
       System.out.println(check());
    }
    public static int check(){
        try {
            int[] numbers = {1, 2, 3};
            System.out.println(numbers[5]);
        } catch (ArrayIndexOutOfBoundsException e) {

            System.out.println("Array index out of bounds: " + e.getMessage());
            return 1;
        }finally {
            return 2;
        }
    }
}
