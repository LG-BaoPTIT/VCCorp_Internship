# Báo cáo tuần 3

## 1.Exception
### Checked Exception

- `Checked Exceptions` là những ngoại lệ được kiểm tra tại thời điểm biên dịch. Các ngoại lệ này phải được xử lý bằng cách sử dụng khối `try-catch` hoặc khai báo trong phương thức với từ khóa `throws`.
- IOException
- FileNotFoundException
- SQLException
- ClassNotFoundException

```java
public class CheckedExceptionExample {
    public static void main(String[] args) {
        try {
            File file = new File("non_existent_file.txt");
            Scanner scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}

```

### Unchecked Exception
- `Unchecked Exceptions` là những ngoại lệ không được kiểm tra tại thời điểm biên dịch. Chúng bao gồm các ngoại lệ con của `RuntimeException`. Không bắt buộc phải xử lý chúng, nhưng nếu không xử lý, chúng có thể dẫn đến việc kết thúc đột ngột của chương trình.
- NullPointerException
- ArrayIndexOutOfBoundsException
- ArithmeticException
- IllegalArgumentException
``` java
    public class UncheckedExceptionExample {
    public static void main(String[] args) {
        try {
            int[] numbers = {1, 2, 3};
            System.out.println(numbers[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index out of bounds: " + e.getMessage());
        }
    }
}
```
## 2.Concurrency
- Luồng (Thread) trong Java là một đơn vị nhỏ nhất của một quá trình xử lý (process) được lập lịch trình và thực thi bởi Hệ điều hành. Mỗi chương trình Java chạy trong một luồng chính (main thread) khi bắt đầu, và có thể tạo ra các luồng con để thực hiện các tác vụ đồng thời (concurrent tasks).
####Các Thành Phần Chính của Luồng trong Java
- Thread Class: Đây là lớp chính để làm việc với luồng. Có thể tạo luồng bằng cách kế thừa lớp Thread hoặc triển khai giao diện Runnable.
- Runnable Interface: Đây là giao diện chứa phương thức run(). Khi triển khai giao diện này, cần định nghĩa logic của luồng trong phương thức run().

### Example using runable
``` java
public class Account {
    private double balance;

    public Account() {
    }

    public Account(double balance) {
        this.balance = balance;
    }

    public synchronized void withdraw(double amount){
        if (amount <= balance) {
            System.out.println(Thread.currentThread().getName() + " is going to withdraw " + amount);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " completed withdrawal. Remaining balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw " + amount + " but insufficient balance. Current balance: " + balance);
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
```
``` java
    public class WithdrawTask implements Runnable{
    private Account account;
    private double amount;

    public WithdrawTask(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}   
```
``` java
    public class Main {
    public static void main(String[] args) {
        Account account = new Account(1000);
        Thread thread1 = new Thread(new WithdrawTask(account,300),"Thread 1");
        Thread thread2 = new Thread(new WithdrawTask(account,800),"Thread 2");

        thread1.start();
        thread2.start();
    }
}
```
### Thread pool

- `Thread pool` là một kỹ thuật quản lý và tái sử dụng các luồng (threads) trong lập trình đa luồng. Thay vì tạo ra một luồng mới cho mỗi tác vụ, `thread pool` duy trì một tập hợp các luồng có sẵn để xử lý các tác vụ đến, giúp giảm thiểu chi phí tạo và hủy luồng, và tối ưu hóa hiệu suất hệ thống.

#### Tại Sao Sử Dụng Thread Pool?

- Hiệu Quả Tài Nguyên: Tạo và hủy luồng có chi phí đáng kể về mặt tài nguyên. Bằng cách tái sử dụng các luồng, thread pool giúp giảm thiểu chi phí này.
- Quản Lý Tốt Hơn: Thread pool giúp quản lý số lượng luồng hoạt động cùng một lúc, tránh việc tạo ra quá nhiều luồng gây ra tình trạng thiếu tài nguyên và giảm hiệu suất.
- Kiểm Soát Song Song: Giới hạn số lượng luồng đang chạy đồng thời giúp kiểm soát độ song song, tránh tình trạng quá tải hệ thống.

#### Cấu Trúc Cơ Bản của Thread Pool

Một thread pool thường bao gồm các thành phần sau:

- `Core Pool Size`: Số lượng luồng cơ bản sẽ luôn duy trì trong pool.
- `Maximum Pool Size`: Số lượng luồng tối đa có thể tạo ra.
- `Keep Alive Time`: Thời gian tối đa một luồng nhàn rỗi sẽ tồn tại trước khi bị hủy nếu số lượng luồng vượt quá core pool size.
- `Work Queue`: Hàng đợi chứa các tác vụ chờ được xử lý bởi các luồng trong pool.
- `Rejection Handler`: Chính sách xử lý khi hàng đợi đã đầy và không thể thêm tác vụ mới.
    
#### Chính Sách Từ Chối (Rejection Policy)

Khi một tác vụ mới được gửi đến nhưng không thể xử lý vì pool đã đầy và hàng đợi cũng đã đầy, ThreadPoolExecutor sẽ sử dụng một trong các chính sách từ chối:

- `AbortPolicy`: Ném RejectedExecutionException.
- `CallerRunsPolicy`: Thực thi tác vụ bởi chính luồng gửi.
- `DiscardPolicy`: Bỏ qua tác vụ mới.
- `DiscardOldestPolicy`: Bỏ tác vụ cũ nhất trong hàng đợi và cố gắng gửi lại tác vụ mới.


```java

public class example_thread_pool {
    public static void main(String[] args) {
        // Cấu hình thread pool
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                handler
        );

        // Gửi tác vụ tới thread pool
        for (int i = 0; i < 3; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " is executing a task.");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Đóng thread pool
        threadPoolExecutor.shutdown();
        try {
            if (!threadPoolExecutor.awaitTermination(60,TimeUnit.SECONDS)) {
                System.out.println("shutdown now!");
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
        }
    }


}
```
### Tìm hiểu lock, atomic integer, concurrent hashmap, race condition, virtual thread(java 21)

#### 1.Lock:
- Trong Java, locks được sử dụng để đồng bộ hóa các luồng và đảm bảo rằng chỉ có một luồng có thể truy cập vào một phần của mã (critical section) vào một thời điểm nhất định.
- Java cung cấp synchronized blocks/methods và các lớp như ReentrantLock để quản lý locks.

``` java
public class LockExample {
    private int count = 0;
    private final Lock lock = new ReentrantLock();
    public void increment() {
        lock.lock(); // Bắt đầu khóa
        try {
            count++;
        } finally {
            lock.unlock(); // Giải phóng khóa
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        LockExample example = new LockExample();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final Count: " + example.getCount());
    }
}

```
#### 2.Atomic Integer:
- `AtomicInteger` là một lớp trong Java cung cấp các phép toán nguyên nguyên tử (atomic operations) cho các biến kiểu nguyên.
- Các phép toán này đảm bảo rằng các thao tác đọc và ghi trên biến nguyên được thực hiện mà không bị gián đoạn bởi các luồng khác.

#### 3. Concurent HashMap:
- `ConcurrentHashMap` là một lớp trong Java cung cấp một bản đồ (map) được thiết kế để sử dụng trong môi trường đa luồng mà không cần phải sử dụng `synchronized`.
- Nó cung cấp các phép toán an toàn cho luồng và có hiệu suất cao khi nhiều luồng cùng truy cập đến các phần tử khác nhau của bản đồ.

#### 4. Race condition

- `Race condition` xảy ra khi nhiều luồng cố gắng truy cập và thay đổi dữ liệu chia sẻ mà không có cơ chế đồng bộ hóa.
- Điều này có thể dẫn đến kết quả không xác định hoặc không chính xác, do các luồng có thể đọc/ghi vào dữ liệu vào cùng một thời điểm.

#### 5. Vitual thread

- `Virtual threads` là một tính năng mới trong Java 21, giúp cải thiện hiệu suất và khả năng mở rộng của ứng dụng Java trong việc xử lý các tác vụ đa luồng.
- Chúng được triển khai trên nền tảng Project Loom, cho phép triển khai hàng triệu luồng mà không làm quá tải hệ thống.

## 3.Json
 

