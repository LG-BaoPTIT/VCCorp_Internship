# Báo cáo tuần 1
Các công việc đã làm:
- Tìm hiểu 4 tính chất của OOP
- Tìm hiểu 5 tính chất của SOLID
- Xây dựng chương trình java áp dụng tính chất OOP
- Viết trương trình java đọc ghi file theo 2dạng binary và text
- Viết trương trình java thao tác với file và thư mục: list các file, đọc nội dung file

## Tìm hiểu lý thuyết về lập trình hướng đối tượng (OOP) và nguyên lý SOLID

### Yêu cầu (1): oop

Các khái niệm cơ bản trong OOP bao gồm:
- **Đóng gói (Encapsulation)**: Bảo vệ dữ liệu của đối tượng bằng cách giới hạn truy cập đến các thuộc tính và phương thức của nó. Chỉ có thể truy cập và thay đổi dữ liệu thông qua các phương thức công khai (public methods).
- **Kế thừa (Inheritance)**: Cho phép lớp con kế thừa các thuộc tính và phương thức từ lớp cha, giúp tái sử dụng mã nguồn và giảm thiểu sự lặp lại.
- **Đa hình (Polymorphism)**: Cho phép các đối tượng của các lớp con thực hiện phương thức theo cách khác nhau, dựa trên lớp cha chung. Phương thức có thể được định nghĩa lại trong các lớp con để phù hợp với nhu cầu cụ thể.
- **Trừu tượng hóa (Abstraction)**: Tách biệt khái niệm logic và thực thi, giúp dễ dàng mở rộng và bảo trì mã nguồn. Lớp trừu tượng không thể được khởi tạo và các phương thức trừu tượng phải được triển khai trong các lớp con.

### Nguyên lý SOLID

Các nguyên lý SOLID bao gồm:
- **S (Single Responsibility Principle)**: Một lớp chỉ nên có một chức năng cụ thể,.
- **O (Open/Closed Principle)**: Mở cho mở rộng (extension), đóng cho sửa đổi (modification). Mã nguồn nên mở rộng được mà không cần sửa đổi.
- **L (Liskov Substitution Principle)**: Các đối tượng của lớp con có thể thay thế đối tượng của lớp cha mà không làm thay đổi tính đúng đắn của chương trình.
- **I (Interface Segregation Principle)**: Nên tách các interface thành các interface nhỏ, cụ thể để các lớp chỉ phụ thuộc vào những gì cần thiết.
- **D (Dependency Inversion Principle)**: Các module cấp cao không nên phụ thuộc vào các module cấp thấp. Cả hai nên phụ thuộc vào các abstraction. Các abstraction không nên phụ thuộc vào chi tiết, mà ngược lại.

### Khái niệm Interface và Static

#### Interface
- Interface là một loại lớp đặc biệt trong Java mà chỉ chứa các phương thức trừu tượng và các hằng số.
- Interface được sử dụng để định nghĩa một bộ các phương thức mà các lớp phải triển khai. Nó cung cấp một cách để đạt được tính đa hình và hỗ trợ thiết kế linh hoạt hơn.
- Interface hỗ trợ đa kế thừa.

#### Static
- Từ khóa `static` trong Java được sử dụng để định nghĩa các thành phần mà không thuộc về đối tượng cụ thể nào mà thuộc về lớp. Các thành phần static có thể là biến, phương thức, hoặc khối static.
- Thành phần static có thể được truy cập trực tiếp thông qua tên lớp mà không cần tạo đối tượng của lớp.
- Các biến static được chia sẻ giữa tất cả các đối tượng của lớp, và chỉ có một bản sao của biến static tồn tại trong bộ nhớ.


### 1.1.1. Xây dựng chương trình quản lý tài khoản ngân hàng

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

### Yêu cầu (2) Đọc ghi file <DONE>
- viết trương trình java đọc ghi file theo 2 dạng binary và text
- viết trương trình java thao tác với file và thư mục: list các file, đọc nội dung file
