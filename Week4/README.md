# Báo cáo tuần 4

Trong MySQL, các thành phần quan trọng như vật lí, parser, optimizer và các loại storage engine đóng vai trò quan trọng trong quản lý cơ sở dữ liệu. Dưới đây là mô tả chi tiết về mỗi thành phần này:

### 1. Vật lí (Physical Layer)

Vật lí trong MySQL bao gồm các thành phần quản lý cách dữ liệu được lưu trữ và truy cập trong hệ thống quản lý cơ sở dữ liệu. Các thành phần chính của vật lí bao gồm:

- `Storage Management`: Quản lý cách dữ liệu được tổ chức và lưu trữ trên đĩa hoặc trong bộ nhớ. MySQL sử dụng các cơ chế như bảng, chỉ mục, view để quản lý không gian lưu trữ.

- `Buffer Management`: Quản lý bộ đệm để tối ưu hóa hiệu suất truy cập dữ liệu bằng cách giữ các dữ liệu phổ biến trong bộ nhớ để giảm số lần truy cập đĩa.

- `File System Interface`: Tương tác với hệ điều hành để đọc và ghi dữ liệu từ/đến đĩa. MySQL sử dụng giao diện hệ thống tệp để quản lý các tệp dữ liệu và chỉ mục.

### 2. Parser

Parser trong MySQL có nhiệm vụ phân tích và chuyển đổi các câu lệnh SQL từ dạng văn bản thành cấu trúc cây (parse tree) để có thể xử lý bởi các phần mềm quản lý cơ sở dữ liệu. Các thành phần chính của parser bao gồm:

- `Lexical Analysis`: Phân tích cú pháp (lexical analysis) để chuyển đổi dòng văn bản SQL thành các đơn vị nhỏ hơn như từ vựng (tokens).

- `Syntax Analysis`: Phân tích cú pháp (syntax analysis) để kiểm tra tính hợp lệ của cú pháp SQL dựa trên các quy tắc ngữ pháp của MySQL.

### 3. Optimizer (Trình tối ưu hóa)

Optimizer là thành phần quan trọng trong MySQL, có nhiệm vụ tối ưu hóa câu lệnh SQL để tăng hiệu suất thực thi. Các bước tối ưu hóa bao gồm:

- `Query Parsing`: Parser chuyển đổi câu lệnh SQL thành cấu trúc cây để tối ưu hóa.

- `Query Optimization`: Thực hiện các chiến lược tối ưu hóa để chọn kế hoạch thực thi câu lệnh tốt nhất, bao gồm lựa chọn phương pháp tham gia bảng (join methods), sử dụng chỉ mục, v.v.

- `Cost Estimation`: Ước tính chi phí của các kế hoạch thực thi và chọn kế hoạch có chi phí thấp nhất để thực hiện câu lệnh SQL.

### 4. Storage Engines (Bộ lưu trữ)

Storage Engine là thành phần quản lý lưu trữ thực tế của dữ liệu trong MySQL. MySQL hỗ trợ nhiều loại Storage Engines để đáp ứng các yêu cầu lưu trữ và tính năng khác nhau. Các loại phổ biến bao gồm:

- `InnoDB`: Mặc định trong MySQL từ phiên bản 5.5 trở đi, hỗ trợ giao dịch ACID (Atomicity, Consistency, Isolation, Durability), khóa ngoài (foreign key), và tính năng phục hồi.

- `MyISAM`: Sử dụng trước đây, hỗ trợ chỉ đọc và ghi, không hỗ trợ giao dịch, nhưng có hiệu suất cao cho các hoạt động đọc.

- `Memory`: Lưu trữ dữ liệu trong bộ nhớ RAM, thích hợp cho các bảng tạm thời hoặc cache.

- `Archive`: Nén dữ liệu và lưu trữ nén, thích hợp cho việc lưu trữ lịch sử hoặc dữ liệu nhật ký.

- `CSV`: Lưu trữ dữ liệu dưới dạng file CSV, cho phép dễ dàng nhập và xuất dữ liệu.

Mỗi Storage Engine có các đặc điểm và tính năng riêng biệt, ảnh hưởng đến hiệu suất và tính năng của cơ sở dữ liệu MySQL. Người quản trị cơ sở dữ liệu thường lựa chọn Storage Engine phù hợp dựa trên yêu cầu cụ thể của ứng dụng và mục đích sử dụng.

# Tối ưu hóa câu lệnh;

  ![table employees](table.png)
  ![table](table2.png)

## `SELECT * FROM employees WHERE salary > 500000;`
    
### So sánh EXPLAIN trước và sau khi tạo chỉ mục

Dưới đây là kết quả của lệnh `EXPLAIN` trước và sau khi tạo chỉ mục `idx_salary` trên bảng `employees`.

#### Trước khi tạo chỉ mục:

```sql
EXPLAIN SELECT * FROM employees WHERE salary > 500000;
```

```
+----+-------------+-----------+------------+------+---------------+------+---------+------+-------+----------+-------------+
| id | select_type | table     | partitions | type | possible_keys | key  | key_len | ref  | rows  | filtered | Extra       |
+----+-------------+-----------+------------+------+---------------+------+---------+------+-------+----------+-------------+
|  1 | SIMPLE      | employees | NULL       | ALL  | NULL          | NULL | NULL    | NULL | 99869 |    33.33 | Using where |
+----+-------------+-----------+------------+------+---------------+------+---------+------+-------+----------+-------------+
```

#### Sau khi tạo chỉ mục:

```sql
CREATE INDEX idx_salary ON employees(salary);
EXPLAIN SELECT * FROM employees WHERE salary > 500000;
```

```
+----+-------------+-----------+------------+-------+---------------+------------+---------+------+------+----------+-----------------------+
| id | select_type | table     | partitions | type  | possible_keys | key        | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-----------+------------+-------+---------------+------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | employees | NULL       | range | idx_salary    | idx_salary | 6       | NULL |    1 |   100.00 | Using index condition |
+----+-------------+-----------+------------+-------+---------------+------------+---------+------+------+----------+-----------------------+
```

### Phân tích chi tiết:

#### Trước khi tạo chỉ mục:

1. **type: ALL**:
   - MySQL thực hiện quét toàn bộ bảng, nghĩa là nó phải đọc toàn bộ 99,869 hàng trong bảng `employees`.
   - Điều này rất không hiệu quả, đặc biệt là khi bảng có nhiều hàng.

2. **key**: `NULL`:
   - Không có chỉ mục nào được sử dụng cho truy vấn này.

3. **rows**: `99869`:
   - MySQL ước tính sẽ phải đọc 99,869 hàng để tìm kiếm kết quả.
   - Điều này cho thấy một lượng công việc lớn mà MySQL phải thực hiện.

4. **filtered**: `33.33`:
   - Chỉ 33.33% của các hàng sẽ được giữ lại sau khi áp dụng điều kiện WHERE.

5. **Extra: Using where**:
   - Điều kiện WHERE được áp dụng sau khi quét toàn bộ bảng.

#### Sau khi tạo chỉ mục:

1. **type: range**:
   - MySQL sử dụng truy vấn phạm vi (`range`) trên chỉ mục `idx_salary`.
   - Điều này rất hiệu quả, vì MySQL chỉ cần quét một phạm vi nhỏ trong chỉ mục thay vì toàn bộ bảng.

2. **key**: `idx_salary`:
   - MySQL sử dụng chỉ mục `idx_salary` cho truy vấn này.

3. **rows**: `1`:
   - MySQL ước tính chỉ cần đọc 1 hàng để tìm kiếm kết quả.
   - Điều này cho thấy một lượng công việc rất nhỏ mà MySQL phải thực hiện, cải thiện đáng kể hiệu suất truy vấn.

4. **filtered**: `100.00`:
   - 100% các hàng trong phạm vi chỉ mục sẽ được giữ lại sau khi áp dụng điều kiện WHERE.

5. **Extra: Using index condition**:
   - MySQL đang sử dụng điều kiện chỉ mục để tối ưu hóa truy vấn.
   - Điều này có nghĩa là điều kiện WHERE được áp dụng trực tiếp trong quá trình quét chỉ mục, giúp tăng hiệu quả.

### Kết luận:

- **Trước khi tạo chỉ mục**, MySQL phải quét toàn bộ bảng `employees`, dẫn đến hiệu suất kém vì phải đọc rất nhiều hàng.
- **Sau khi tạo chỉ mục**, MySQL chỉ cần quét một phạm vi nhỏ trong chỉ mục `idx_salary`, dẫn đến hiệu suất cải thiện đáng kể.
- Việc sử dụng chỉ mục giúp MySQL tìm kiếm các hàng phù hợp nhanh hơn và giảm số lượng hàng cần phải đọc, do đó cải thiện hiệu suất truy vấn.

## Sử dụng JOIN mà không có chỉ mục

```sql
SELECT orders.order_id, customers.first_name 
FROM orders 
JOIN customers ON orders.customer_id = customers.customer_id;

```
```
+----+-------------+-----------+------------+------+---------------+-------------+---------+----------------------------+-------+----------+-------------+
| id | select_type | table     | partitions | type | possible_keys | key         | key_len | ref                        | rows  | filtered | Extra       |
+----+-------------+-----------+------------+------+---------------+-------------+---------+----------------------------+-------+----------+-------------+
|  1 | SIMPLE      | customers | NULL       | ALL  | PRIMARY       | NULL        | NULL    | NULL                       | 99338 |   100.00 | NULL        |
|  1 | SIMPLE      | orders    | NULL       | ref  | customer_id   | customer_id | 4       | test.customers.customer_id |     1 |   100.00 | Using index |
+----+-------------+-----------+------------+------+---------------+-------------+---------+----------------------------+-------+----------+-------------+

```
- tối ưu:

```sql
ALTER TABLE orders ADD INDEX (customer_id);
ALTER TABLE customers ADD INDEX (customer_id);
SELECT orders.order_id, customers.first_name 
FROM orders 
JOIN customers ON orders.customer_id = customers.customer_id;

```

```
+----+-------------+-----------+------------+--------+---------------------+-------------+---------+-------------------------+--------+----------+-------------+
| id | select_type | table     | partitions | type   | possible_keys       | key         | key_len | ref                     | rows   | filtered | Extra       |
+----+-------------+-----------+------------+--------+---------------------+-------------+---------+-------------------------+--------+----------+-------------+
|  1 | SIMPLE      | orders    | NULL       | index  | customer_id         | customer_id | 4       | NULL                    | 100167 |   100.00 | Using index |
|  1 | SIMPLE      | customers | NULL       | eq_ref | PRIMARY,customer_id | PRIMARY     | 4       | test.orders.customer_id |      1 |   100.00 | NULL        |
+----+-------------+-----------+------------+--------+---------------------+-------------+---------+-------------------------+--------+----------+-------------+

```
