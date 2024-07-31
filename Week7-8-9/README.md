# Bác cáo tuần 7-8-9
## 1. Tìm hiểu các pattern
### 1.1 Domain Driven Design
Domain-Driven Design (DDD) là một phương pháp thiết kế phần mềm tập trung vào việc mô hình hóa các phần cốt lõi của ứng dụng dựa trên các quy tắc và ngữ cảnh của domain (lĩnh vực, lĩnh vực chuyên môn). DDD giúp các nhà phát triển hiểu sâu hơn về vấn đề mà họ đang giải quyết và xây dựng các giải pháp phần mềm sao cho sát với yêu cầu thực tế của domain. Dưới đây là một số khái niệm và thành phần chính của DDD:

### Các Khái Niệm Chính

1. **Domain**:
    - **Khái niệm**: Domain là lĩnh vực mà ứng dụng phần mềm đang giải quyết. Nó có thể là bất kỳ lĩnh vực nào như tài chính, y tế, thương mại điện tử, v.v.
    - **Ví dụ**: Trong một hệ thống ngân hàng, domain sẽ bao gồm các khái niệm như tài khoản, giao dịch, khách hàng, lãi suất, v.v.

2. **Ubiquitous Language (Ngôn ngữ phổ biến)**:
    - **Khái niệm**: Một ngôn ngữ chung mà cả nhà phát triển và chuyên gia domain đều hiểu và sử dụng để mô tả các khía cạnh của domain.
    - **Mục đích**: Đảm bảo rằng mọi người đều hiểu và sử dụng cùng một thuật ngữ khi nói về domain.

3. **Bounded Context (Ngữ cảnh giới hạn)**:
    - **Khái niệm**: Một giới hạn rõ ràng trong đó một mô hình cụ thể được áp dụng. Mỗi bounded context có thể có mô hình và ngữ cảnh riêng của nó.
    - **Ví dụ**: Trong một hệ thống lớn, có thể có các bounded context như Quản lý Khách hàng, Quản lý Giao dịch, Quản lý Tài khoản, v.v.

4. **Entities (Thực thể)**:
    - **Khái niệm**: Các đối tượng có định danh duy nhất và trạng thái thay đổi theo thời gian.
    - **Ví dụ**: Một tài khoản ngân hàng hoặc một khách hàng trong hệ thống ngân hàng.

5. **Value Objects (Đối tượng giá trị)**:
    - **Khái niệm**: Các đối tượng không có định danh duy nhất và thường là bất biến. Chúng được xác định bởi các thuộc tính của chúng.
    - **Ví dụ**: Địa chỉ của khách hàng hoặc số tiền trong một giao dịch.

6. **Aggregates (Tập hợp)**:
    - **Khái niệm**: Một nhóm các thực thể và đối tượng giá trị liên quan mà được xử lý như một đơn vị duy nhất cho các mục đích nhất định.
    - **Root Entity (Thực thể gốc)**: Thực thể chính trong aggregate, chịu trách nhiệm duy trì tính nhất quán của tập hợp.
    - **Ví dụ**: Một tài khoản ngân hàng và các giao dịch liên quan.

7. **Repositories (Kho lưu trữ)**:
    - **Khái niệm**: Các đối tượng cung cấp các phương thức để truy xuất và lưu trữ các thực thể.
    - **Ví dụ**: Một repository để lưu trữ và truy xuất các tài khoản ngân hàng.

8. **Services (Dịch vụ)**:
    - **Khái niệm**: Các đối tượng thực hiện các thao tác không thuộc về các thực thể hoặc đối tượng giá trị.
    - **Ví dụ**: Một dịch vụ để tính lãi suất hoặc xử lý các giao dịch.

### Lợi ích của Domain-Driven Design
- **Hiểu sâu hơn về domain**: Giúp các nhà phát triển hiểu rõ hơn về các yêu cầu và quy tắc của domain.
- **Tính modular**: Giúp chia hệ thống thành các phần độc lập, dễ dàng quản lý và bảo trì.
- **Tăng cường sự giao tiếp**: Ngôn ngữ phổ biến giúp cải thiện giao tiếp giữa các nhà phát triển và chuyên gia domain.

### 1.2 CQRS & Event Sourcing

### Command Query Responsibility Segregation (CQRS) và Event Sourcing

CQRS và Event Sourcing là hai mẫu thiết kế thường được sử dụng cùng nhau để giải quyết các vấn đề liên quan đến khả năng mở rộng, tính nhất quán, và hiệu suất trong các hệ thống phần mềm phức tạp.

### 1. Command Query Responsibility Segregation (CQRS)

#### Khái niệm
CQRS là một mẫu thiết kế trong đó trách nhiệm của các lệnh (commands) và truy vấn (queries) được tách biệt. Trong mô hình CQRS:
- **Command**: Được sử dụng để thực hiện các thao tác ghi (write operations), thay đổi trạng thái của hệ thống.
- **Query**: Được sử dụng để thực hiện các thao tác đọc (read operations), truy xuất dữ liệu mà không thay đổi trạng thái của hệ thống.

#### Lợi ích
- **Tối ưu hóa hiệu suất**: Các thao tác đọc và ghi có thể được tối ưu hóa riêng biệt.
- **Tính nhất quán cuối cùng (eventual consistency)**: Giúp dễ dàng quản lý tính nhất quán trong các hệ thống phân tán.
- **Đơn giản hóa logic**: Tách biệt rõ ràng giữa các thao tác đọc và ghi, giúp đơn giản hóa logic và tăng khả năng bảo trì.

#### Ví dụ
Trong một hệ thống quản lý đơn hàng:
- **Command**: Tạo mới đơn hàng, cập nhật trạng thái đơn hàng, hủy đơn hàng.
- **Query**: Lấy danh sách các đơn hàng, xem chi tiết đơn hàng.

### 2. Event Sourcing

#### Khái niệm
Event Sourcing là một mẫu thiết kế trong đó trạng thái của hệ thống được lưu trữ dưới dạng một chuỗi các sự kiện (events). Thay vì lưu trữ trạng thái hiện tại của dữ liệu, hệ thống lưu trữ tất cả các sự kiện đã xảy ra, và trạng thái hiện tại được xây dựng lại bằng cách áp dụng các sự kiện đó.

#### Lợi ích
- **Khả năng tái tạo trạng thái**: Có thể tái tạo lại trạng thái của hệ thống tại bất kỳ thời điểm nào trong quá khứ bằng cách áp dụng các sự kiện.
- **Audit trail (dấu vết kiểm toán)**: Lưu trữ toàn bộ lịch sử các thay đổi, giúp dễ dàng kiểm tra và xác minh.
- **Tính nhất quán**: Dễ dàng đảm bảo tính nhất quán bằng cách áp dụng các sự kiện theo thứ tự.

#### Ví dụ
Trong hệ thống ngân hàng:
- **Sự kiện (Event)**: Giao dịch tiền vào tài khoản, giao dịch rút tiền, chuyển khoản.
- **Trạng thái hiện tại**: Số dư tài khoản được tính toán bằng cách áp dụng các sự kiện giao dịch.

### Kết hợp CQRS và Event Sourcing

CQRS và Event Sourcing thường được kết hợp để tận dụng lợi ích của cả hai mẫu thiết kế:
- **Tách biệt các thao tác đọc và ghi**: CQRS tách biệt rõ ràng các thao tác đọc và ghi, giúp tối ưu hóa hiệu suất và tính nhất quán.
- **Lưu trữ dưới dạng sự kiện**: Event Sourcing lưu trữ các sự kiện thay vì trạng thái hiện tại, giúp dễ dàng tái tạo lại trạng thái và duy trì dấu vết kiểm toán.

### Minh họa bằng ví dụ

Giả sử bạn đang xây dựng một hệ thống quản lý đơn hàng với các chức năng như tạo đơn hàng, cập nhật trạng thái đơn hàng, và xem chi tiết đơn hàng.

#### 1. Command
```java
public class CreateOrderCommand {
    private String orderId;
    private String customerId;
    private List<OrderItem> items;

}
```

#### 2. Event
```java
public class OrderCreatedEvent {
    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private LocalDateTime timestamp;

}
```
a
#### 3. Command Handler
```java
public class OrderCommandHandler {
    private final EventStore eventStore;

    public OrderCommandHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void handle(CreateOrderCommand command) {
        // Validate and process the command
        OrderCreatedEvent event = new OrderCreatedEvent(
            command.getOrderId(),
            command.getCustomerId(),
            command.getItems(),
            LocalDateTime.now()
        );
        eventStore.save(event);
    }
}
```

#### 4. Query
```java
public class OrderQueryService {
    private final EventStore eventStore;

    public OrderQueryService(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public Order getOrderById(String orderId) {
        // Rebuild the state of the order by applying events
        List<Event> events = eventStore.getEventsForOrder(orderId);
        Order order = new Order();
        for (Event event : events) {
            order.apply(event);
        }
        return order;
    }
}
```
- **CQRS**: Tách biệt các thao tác đọc và ghi, giúp tối ưu hóa và đơn giản hóa hệ thống.
- **Event Sourcing**: Lưu trữ các sự kiện để tái tạo lại trạng thái, giúp duy trì dấu vết kiểm toán và đảm bảo tính nhất quán.

## 2. Tìm hiểu về ORMs & Transactions trong cơ sở dữ liệu

### ORM (Object-Relational Mapping)

**ORM (Object-Relational Mapping)** là một kỹ thuật lập trình cho phép truy cập và thao tác với cơ sở dữ liệu quan hệ (SQL) sử dụng các đối tượng của ngôn ngữ lập trình hướng đối tượng (OOP). Các ORM frameworks cung cấp một cách để ánh xạ các bảng trong cơ sở dữ liệu thành các lớp trong mã nguồn, và các hàng trong bảng thành các đối tượng của các lớp đó.

#### Lợi ích của ORM:
1. **Tăng hiệu suất phát triển**: Giảm bớt lượng mã SQL phải viết, giúp các lập trình viên tập trung vào logic của ứng dụng.
2. **Bảo trì dễ dàng**: Thay đổi cấu trúc bảng trong cơ sở dữ liệu không cần phải thay đổi nhiều mã SQL trong mã nguồn.
3. **Bảo mật**: ORM giúp bảo vệ ứng dụng khỏi các lỗi bảo mật phổ biến như SQL Injection.
4. **Tính di động**: Dễ dàng chuyển đổi giữa các cơ sở dữ liệu khác nhau mà không cần thay đổi mã nguồn.

#### Một số ORM phổ biến:
- **Hibernate** (Java)
- **Entity Framework** (C#)
- **SQLAlchemy** (Python)
- **ActiveRecord** (Ruby on Rails)

### Transactions trong cơ sở dữ liệu

**Transaction** (giao dịch) là một đơn vị công việc logic trong cơ sở dữ liệu mà bao gồm một hoặc nhiều hoạt động cơ sở dữ liệu. Một transaction có các thuộc tính ACID:

1. **Atomicity**: Tất cả các hoạt động trong một transaction phải được thực hiện đầy đủ hoặc không được thực hiện gì cả. Nếu một phần của transaction thất bại, toàn bộ transaction phải bị rollback.
2. **Consistency**: Sau khi transaction kết thúc, cơ sở dữ liệu phải ở trạng thái nhất quán. Tất cả các ràng buộc toàn vẹn của cơ sở dữ liệu phải được duy trì.
3. **Isolation**: Các transaction riêng biệt phải không ảnh hưởng đến nhau. Kết quả của một transaction chưa được commit không được nhìn thấy bởi các transaction khác.
4. **Durability**: Khi một transaction được commit, những thay đổi của nó phải được lưu trữ vĩnh viễn, ngay cả khi có sự cố hệ thống.


## 3. Apache Kafka

**Apache Kafka** là một nền tảng truyền tải luồng dữ liệu phân tán có khả năng mở rộng cao và có độ tin cậy cao. Kafka thường được sử dụng cho việc xây dựng các hệ thống xử lý luồng dữ liệu thời gian thực và xử lý dữ liệu phân tán.

#### Thành phần chính của Kafka:
1. **Producer**: Ứng dụng hoặc dịch vụ gửi dữ liệu vào Kafka.
2. **Consumer**: Ứng dụng hoặc dịch vụ lấy dữ liệu từ Kafka để xử lý hoặc lưu trữ.
3. **Broker**: Máy chủ chạy Kafka, chịu trách nhiệm lưu trữ và quản lý dữ liệu. Một cụm Kafka có thể có nhiều broker để tăng khả năng mở rộng và độ tin cậy.
4. **Topic**: Một kênh mà qua đó dữ liệu được gửi và nhận. Mỗi topic có thể chia thành nhiều phân đoạn nhỏ hơn gọi là partition.
5. **Partition**: Một phần của topic, giúp tăng khả năng mở rộng và đảm bảo dữ liệu được phân phối đều trên các broker.
6. **ZooKeeper**: Một dịch vụ quản lý và điều phối các node trong cụm Kafka.

#### Các tính năng chính của Kafka:
1. **Khả năng mở rộng**: Kafka có thể mở rộng ngang bằng cách thêm nhiều broker vào cụm.
2. **Tính chịu lỗi**: Dữ liệu được sao chép giữa các broker để đảm bảo tính toàn vẹn của dữ liệu ngay cả khi một hoặc nhiều broker bị hỏng.
3. **Hiệu suất cao**: Kafka có thể xử lý hàng triệu sự kiện mỗi giây với độ trễ thấp.
4. **Đảm bảo thứ tự**: Kafka đảm bảo rằng các thông điệp trong một partition được xử lý theo thứ tự chúng được gửi đến.

#### Cách hoạt động của Kafka:
1. **Producer gửi dữ liệu**: Producer gửi các thông điệp (messages) vào các topic. Mỗi thông điệp có thể có một khóa (key) để xác định partition mà nó sẽ được gửi đến.
2. **Broker lưu trữ dữ liệu**: Các broker lưu trữ thông điệp trong các partition của các topic. Mỗi partition là một log được append-only.
3. **Consumer đọc dữ liệu**: Consumer đọc các thông điệp từ các partition theo thứ tự. Mỗi consumer nhóm (consumer group) đảm bảo rằng mỗi thông điệp chỉ được xử lý một lần bởi một consumer trong nhóm.

## 4. Tìm hiểu về authentication
**Authentication** là quá trình xác thực danh tính của người dùng hoặc dịch vụ. Trong các ứng dụng web và API, có nhiều phương pháp để thực hiện authentication. một số phương pháp phổ biến:

1. **JWT (JSON Web Token)**
2. **Basic Authentication**
3. **Token-based Authentication**

### JWT (JSON Web Token)

**JWT** là một chuẩn mở để tạo token chứa thông tin xác thực. JWT là một chuỗi được mã hóa và ký với một khóa bí mật, cho phép xác thực thông tin người dùng mà không cần lưu trạng thái trên máy chủ.

#### Cấu trúc của JWT:
- **Header**: Chứa thông tin về thuật toán ký và loại token (thường là "JWT").
- **Payload**: Chứa các tuyên bố (claims). Các claims có thể là thông tin về người dùng, thời gian hết hạn, v.v.
- **Signature**: Được tạo bằng cách ký Header và Payload với một khóa bí mật.

#### Ví dụ về JWT:
```json
{
  "header": {
    "alg": "HS256",
    "typ": "JWT"
  },
  "payload": {
    "sub": "1234567890",
    "name": "John Doe",
    "iat": 1516239022
  },
  "signature": "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```


### Basic Authentication

**Basic Authentication** là một phương thức xác thực đơn giản, trong đó tên người dùng và mật khẩu được gửi cùng với mỗi yêu cầu HTTP dưới dạng base64-encoded.

#### Cách hoạt động:
- Client gửi tên người dùng và mật khẩu được mã hóa base64 trong tiêu đề HTTP.
- Server giải mã tiêu đề và xác thực thông tin đăng nhập.


### Token-based Authentication

**Token-based Authentication** là một phương pháp xác thực trong đó người dùng nhận được một token sau khi đăng nhập thành công, và sử dụng token này cho các yêu cầu tiếp theo.

#### Cách hoạt động:
- Người dùng đăng nhập và nhận token.
- Người dùng gửi token trong tiêu đề HTTP của mỗi yêu cầu.
- Server xác thực token để cho phép hoặc từ chối yêu cầu.

## ========>
- **JWT**: An toàn và không lưu trạng thái, thường dùng cho ứng dụng web và API.
- **Basic Authentication**: Đơn giản nhưng ít an toàn, thích hợp cho các ứng dụng cơ bản.
- **Token-based Authentication**: Linh hoạt và an toàn, phổ biến trong các ứng dụng web và mobile.