package org.example.volattile_example;

public class VolatileExample {
    private static volatile boolean flag = false;

    public static void main(String[] args){
        // thread 1
        new Thread(() -> {
           while (!flag){
               System.out.println("Thread 1: false");
           }
            System.out.println("Thread 1: Flag is set to true");
        }).start();

        // thread 2
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            flag = true;
            System.out.println("Flag has been set to true by Thread 2");
        }).start();

        // thread 3
        new Thread(() -> {
            while (!flag){
                System.out.println("Thread 3: false");
            }
            System.out.println("Thread 3: Flag is set to true ");
        }).start();


    }
}
//note
//Khi một biến được khai báo là volatile,
// nó đảm bảo rằng mọi thao tác đọc và ghi đối với biến này được thực hiện trực tiếp từ và đến bộ nhớ chính.
// Điều này có nghĩa là các thay đổi được thực hiện bởi một luồng đối với biến volatile sẽ ngay lập tức hiển thị cho các luồng khác.
//Nếu không có volatile, các luồng có thể lưu trữ biến trong bộ nhớ cục bộ của chúng,
// dẫn đến việc các luồng có thể thấy trạng thái của biến không nhất quán.