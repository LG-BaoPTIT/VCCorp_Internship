# Báo cáo công việc và kết quả

## Các công việc đã làm

### 1. Xây dựng chương trình quản lý tài khoản ngân hàng

- **Thiết kế lớp Account và các lớp con**
  - `Account`: Là lớp trừu tượng đại diện cho một tài khoản ngân hàng cơ bản, bao gồm các phương thức và thuộc tính chung.
  - `SavingsAccount`: Là lớp con của `Account` cho phép rút tiền từ tài khoản tiết kiệm.
  - `CheckingAccount`: Là lớp con của `Account` cho phép rút tiền từ tài khoản thanh toán với giới hạn vượt quá số dư.

- **Thiết kế lớp Customer**
  - `Customer`: Là lớp đại diện cho khách hàng của ngân hàng, có thể có nhiều tài khoản, quản lý thông tin cá nhân và danh sách các tài khoản.

- **Xây dựng các giao dịch (Transaction)**
  - `Transaction`: Interface định nghĩa phương thức execute để thực hiện các giao dịch trên tài khoản.
  - `WithdrawTransaction`: Lớp cụ thể implement `Transaction` để thực hiện giao dịch rút tiền từ tài khoản.
  - `DepositTransaction`: Lớp cụ thể implement `Transaction` để thực hiện giao dịch gửi tiền vào tài khoản.

- **Hiển thị thông tin (DisplayStrategy)**
  - `DisplayStrategy`: Interface định nghĩa các phương thức display để hiển thị thông tin khách hàng và các loại tài khoản.
  - `SimpleDisplayStrategy`: Lớp implement `DisplayStrategy` để hiển thị thông tin khách hàng và các tài khoản, bao gồm thông tin tài khoản tiết kiệm và thanh toán.

### 2. Áp dụng các tính chất của lập trình hướng đối tượng (OOP)

- **Đa hình (Polymorphism)**
  - Được áp dụng trong việc sử dụng phương thức `withdraw` của lớp `Account`, mà các lớp con `SavingsAccount` và `CheckingAccount` thực hiện theo cách khác nhau để phù hợp với loại tài khoản của từng lớp.
  - Cho phép xử lý các loại tài khoản khác nhau một cách thống nhất và linh hoạt.

- **Đóng gói (Encapsulation)**
  - Các thuộc tính của lớp `Account` được bảo vệ bằng các từ khóa `private` và truy cập thông qua các phương thức `getter` và `setter`.
  - Đảm bảo tính bao đóng và bảo mật dữ liệu trong lớp.

- **Kế thừa (Inheritance)**
  - Lớp `SavingsAccount` và `CheckingAccount` kế thừa từ lớp `Account`, thừa hưởng các phương thức và thuộc tính chung.
  - Tái sử dụng mã nguồn và giảm thiểu sự lặp lại trong việc triển khai các tính năng của từng loại tài khoản.

- **Trừu tượng hóa (Abstraction)**
  - Lớp `Account` được thiết kế là lớp trừu tượng, định nghĩa các phương thức trừu tượng như `withdraw` mà các lớp con cụ thể phải triển khai.
  - Tách biệt khái niệm logic và thực thi, giúp dễ dàng mở rộng và bảo trì mã nguồn.

## Kết quả công việc
