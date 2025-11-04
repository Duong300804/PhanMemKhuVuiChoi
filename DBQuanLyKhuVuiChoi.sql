-- Tạo cơ sở dữ liệu
CREATE DATABASE QuanLyKhuVuiChoi;
GO

-- Sử dụng cơ sở dữ liệu vừa tạo
USE QuanLyKhuVuiChoi;
GO

--//////////////////--
-- Tạo bảng taiKhoan
CREATE TABLE taiKhoan (
    maTaiKhoan INT PRIMARY KEY IDENTITY(1,1),
    tenDangNhap NVARCHAR(255) NOT NULL,
    matKhau NVARCHAR(255) NOT NULL,
	loaiTaiKhoan NVARCHAR(50) NOT NULL,
	maNhanVien VARCHAR(10),
	FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
);

-- Thêm một dòng dữ liệu
INSERT INTO taiKhoan (tenDangNhap, matKhau, loaiTaiKhoan) VALUES 
(N'admin', N'admin',N'Admin')

INSERT INTO taiKhoan (tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien) VALUES 
(N'admin', N'admin',N'User', 'NV001')

SELECT * FROM taiKhoan


--//////////////////--
--Tạo bảng phân khu
CREATE TABLE phanKhu (
    maPhanKhu VARCHAR(100) PRIMARY KEY,     
    tenPhanKhu NVARCHAR(100) NOT NULL,     
    moTa NVARCHAR(255) NOT NULL,                     
    viTri NVARCHAR(100) NOT NULL,                    
    dienTich FLOAT  NOT NULL                        
);

--Thêm dữ liệu
INSERT INTO phanKhu (maPhanKhu, tenPhanKhu, moTa, viTri, dienTich)
VALUES 
(N'PK01', N'Khu Kinh Dị', N'Phân khu các trò chơi kinh dị và rùng rợn', N'Tầng 1, góc Đông Bắc', 180.0),
(N'PK02', N'Khu Cảm Giác Mạnh', N'Phân khu các trò chơi mạo hiểm và tốc độ cao', N'Tầng 1, phía Nam', 250.0),
(N'PK03', N'Khu Trẻ Nhỏ', N'Phân khu trò chơi nhẹ nhàng và an toàn dành cho trẻ nhỏ', N'Tầng 2, phía Tây', 120.0),
(N'PK04', N'Khu Ẩm Thực', N'Phân khu nhà hàng và quầy ăn uống', N'Tầng trệt, trung tâm', 300.0),
(N'PK05', N'Khu Thư Giãn', N'Phân khu nghỉ ngơi, và không gian yên tĩnh', N'Tầng 3, phía Bắc', 160.0);

SELECT * FROM phanKhu


--//////////////////--
--Tạo bảng trò chơi
CREATE TABLE TroChoi (
    maTroChoi VARCHAR(100) PRIMARY KEY,
    maPhanKhu VARCHAR(100),
    tenTroChoi NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(255) NOT NULL,
	FOREIGN KEY (maPhanKhu) REFERENCES phanKhu(maPhanKhu)
);

--Thêm dữ liệu
INSERT INTO TroChoi (maTroChoi, maPhanKhu, tenTroChoi, moTa)
VALUES 
('TC01', 'PK01', N'Ngôi Nhà Ma', N'Trò chơi kinh dị với hiệu ứng âm thanh và ánh sáng rùng rợn.'),
('TC02', 'PK01', N'Búp Bê Ma Ám', N'Trò chơi phiêu lưu trong không gian tối tăm đầy bí ẩn.'),
('TC03', 'PK02', N'Tàu Lượn Siêu Tốc', N'Trò chơi cảm giác mạnh với đường ray cao tốc.'),
('TC04', 'PK02', N'Đu Quay Mạo Hiểm', N'Trò chơi xoay tốc độ cao dành cho người ưa mạo hiểm.'),
('TC05', 'PK03', N'Vòng Quay Cầu Vồng', N'Trò chơi nhẹ nhàng cho trẻ nhỏ.'),
('TC06', 'PK03', N'Mê Cung Bong Bóng', N'Không gian chơi an toàn với hàng nghìn quả bóng đủ màu.'),
('TC07', 'PK04', N'Nhà Hàng Buffet', N'Khu ăn uống tự chọn với nhiều món ăn phong phú.'),
('TC08', 'PK04', N'Quầy Ăn Nhanh', N'Nơi bán đồ ăn nhanh và nước giải khát.'),
('TC09', 'PK05', N'Phòng Nghỉ Dưỡng', N'Không gian yên tĩnh với ghế massage và nhạc nhẹ.'),
('TC10', 'PK05', N'Quán Cafe Sách', N'Nơi thưởng thức cà phê và đọc sách thư giãn.');
INSERT INTO TroChoi (maTroChoi, maPhanKhu, tenTroChoi, moTa)
VALUES 
-- PK01: Khu Kinh Dị
('TC11', 'PK01', N'Mê Cung Bóng Tối', N'Khám phá mê cung tối với hiệu ứng ánh sáng và tiếng động bất ngờ.'),
-- PK02: Khu Cảm Giác Mạnh
('TC12', 'PK02', N'Rơi Tự Do', N'Trò chơi rơi từ độ cao 30m tạo cảm giác hồi hộp cực độ.'),
-- PK03: Khu Trẻ Nhỏ
('TC13', 'PK03', N'Bập Bênh Thú Nhún', N'Khu vui chơi với các loại thú nhún ngộ nghĩnh cho bé.'),
-- PK04: Khu Ẩm Thực
('TC14', 'PK04', N'Gian Hàng Kem Tươi', N'Nơi bán các loại kem và tráng miệng mát lạnh cho trẻ em.'),
-- PK05: Khu Thư Giãn
('TC15', 'PK05', N'Khu Thiền Nhẹ', N'Không gian yên tĩnh để thiền, nghe nhạc và thư giãn.');

SELECT *
FROM TroChoi tc
JOIN phanKhu pk ON pk.maPhanKhu = tc.maPhanKhu


--//////////////////--
--Tạo bảng thiết bị
CREATE TABLE thietBi (
    maThietBi VARCHAR(100) PRIMARY KEY,
    maTroChoi VARCHAR(100),
	tenThietBi NVARCHAR(100) NOT NULL,
    tinhTrang NVARCHAR(255) NOT NULL,
	FOREIGN KEY (maTroChoi) REFERENCES TroChoi(maTroChoi),
);

--Thêm dữ liệu thiết bị
INSERT INTO thietBi (maThietBi, maTroChoi, tenThietBi, tinhTrang)
VALUES
-- PK01 - Khu kinh dị
('TB01', 'TC01', N'Hệ thống âm thanh', N'Đang hoạt động'),
('TB02', 'TC01', N'Đèn chiếu ma quái', N'Đang bảo trì'),
('TB03', 'TC02', N'Mô hình búp bê chuyển động', N'Đang hoạt động'),
-- PK02 - Khu cảm giác mạnh
('TB04', 'TC03', N'Tàu lượn siêu tốc', N'Đang hoạt động'),
('TB05', 'TC03', N'Hệ thống an toàn', N'Đang bảo trì'),
('TB06', 'TC04', N'Ghế xoay tốc độ cao', N'Đang hoạt động'),
-- PK03 - Khu trẻ em
('TB07', 'TC05', N'Ghế xoay cầu vồng', N'Đang hoạt động'),
('TB08', 'TC06', N'Bể bóng', N'Đang hoạt động'),
-- PK04 - Khu ẩm thực
('TB09', 'TC07', N'Quầy buffet', N'Đang hoạt động'),
('TB10', 'TC08', N'Máy bán nước tự động', N'Đang bảo trì'),
-- PK05 - Khu nghỉ ngơi
('TB11', 'TC09', N'Ghế massage', N'Đang hoạt động'),
('TB12', 'TC10', N'Kệ sách thông minh', N'Đang bảo trì');
INSERT INTO thietBi (maThietBi, maTroChoi, tenThietBi, tinhTrang)
VALUES
-- Thiết bị bổ sung cho PK01 (Khu Kinh Dị)
('TB13', 'TC01', N'Máy tạo khói', N'Đang hoạt động'),
('TB14', 'TC02', N'Loa âm thanh đa hướng', N'Đang bảo trì'),
-- Thiết bị bổ sung cho PK02 (Khu Cảm Giác Mạnh)
('TB15', 'TC03', N'Cảm biến tốc độ', N'Đang hoạt động'),
('TB16', 'TC04', N'Dây an toàn', N'Đang hoạt động'),
-- Thiết bị bổ sung cho PK03 (Khu Trẻ Nhỏ)
('TB17', 'TC05', N'Mái che an toàn', N'Đang bảo trì'),
('TB18', 'TC06', N'Máy khử khuẩn bóng', N'Đang hoạt động'),
-- Thiết bị bổ sung cho PK04 (Khu Ẩm Thực)
('TB19', 'TC07', N'Máy giữ nhiệt', N'Đang hoạt động'),
('TB20', 'TC08', N'Tủ lạnh mini', N'Đang bảo trì'),
-- Thiết bị bổ sung cho PK05 (Khu Thư Giãn)
('TB21', 'TC09', N'Máy phát nhạc thư giãn', N'Đang hoạt động'),
('TB22', 'TC10', N'Đèn đọc sách', N'Đang hoạt động'),
-- Bổ sung cho trò chơi mới thêm
('TB23', 'TC11', N'Cửa tự động mê cung', N'Đang bảo trì'),
('TB24', 'TC12', N'Hệ thống nâng thang', N'Đang hoạt động');
INSERT INTO thietBi (maThietBi, maTroChoi, tenThietBi, tinhTrang)
VALUES
-- TC11: Mê Cung Bóng Tối
('TB25', 'TC11', N'Đèn LED cảm biến', N'Đang hoạt động'),
('TB26', 'TC11', N'Hệ thống âm thanh bí ẩn', N'Đang hoạt động'),
('TB27', 'TC11', N'Camera giám sát hành lang', N'Đang bảo trì'),
-- TC12: Rơi Tự Do
('TB28', 'TC12', N'Ghế rơi tự do', N'Đang hoạt động'),
('TB29', 'TC12', N'Dây đai an toàn', N'Đang bảo trì'),
('TB30', 'TC12', N'Hệ thống phanh khẩn cấp', N'Đang hoạt động'),
-- TC13: Bập Bênh Thú Nhún
('TB31', 'TC13', N'Thú nhún hình voi', N'Đang hoạt động'),
('TB32', 'TC13', N'Thú nhún hình ngựa', N'Đang bảo trì'),
('TB33', 'TC13', N'Nền cao su chống trượt', N'Đang hoạt động'),
-- TC14: Gian Hàng Kem Tươi
('TB34', 'TC14', N'Máy làm kem tươi', N'Đang hoạt động'),
('TB35', 'TC14', N'Tủ lạnh trữ kem', N'Đang bảo trì'),
('TB36', 'TC14', N'Máy thanh toán POS', N'Đang hoạt động'),
-- TC15: Khu Thiền Nhẹ
('TB37', 'TC15', N'Thảm yoga cao cấp', N'Đang hoạt động'),
('TB38', 'TC15', N'Loa phát nhạc thiền', N'Đang bảo trì'),
('TB39', 'TC15', N'Máy khuếch tán tinh dầu', N'Đang hoạt động');

select * from thietBi;


--//////////////////--
-- Tạo bảng suKien
CREATE TABLE suKien (
    maSuKien VARCHAR(255) PRIMARY KEY,
    tenSuKien NVARCHAR(255),
    thoiGianBatDau DATETIME,
    thoiGianKetThuc DATETIME,
    moTa NVARCHAR(255)
);

-- Thêm 5 dòng dữ liệu mẫu
INSERT INTO suKien (maSuKien, tenSuKien, thoiGianBatDau, thoiGianKetThuc, moTa)
VALUES 
    ('SK001', N'Lễ hội Mèo Kitty', '2025-06-15 10:00:00', '2025-06-15 15:00:00', N'Gặp gỡ và chụp ảnh cùng Mèo Kitty khổng lồ'),
    ('SK002', N'Cuộc đua Xe Scooter Nhí', '2025-06-20 14:00:00', '2025-06-20 16:00:00', N'Thi đua xe scooter cho trẻ em dưới 10 tuổi'),
    ('SK003', N'Ngày hội Ném bóng nước', '2025-07-01 09:00:00', '2025-07-01 12:00:00', N'Chơi ném bóng nước giải nhiệt mùa hè'),
    ('SK004', N'Biểu diễn Ảo thuật Vui nhộn', '2025-07-05 18:00:00', '2025-07-05 19:30:00', N'Ảo thuật gia biểu diễn cùng các bé'),
    ('SK005', N'Sinh nhật Khu vui chơi', '2025-07-10 16:00:00', '2025-07-10 21:00:00', N'Tiệc sinh nhật 5 năm với nhiều quà tặng');
	-- Thêm dữ liệu sự kiện từ SK006 đến SK040, dàn đều trong năm 2025
INSERT INTO suKien (maSuKien, tenSuKien, thoiGianBatDau, thoiGianKetThuc, moTa)
VALUES
-- Tháng 1
('SK006', N'Lễ Hội Khai Xuân', '2025-01-05 09:00:00', '2025-01-05 12:00:00', N'Múa lân khai xuân và lì xì cho bé'),
('SK007', N'Thử Tài Ghép Hình', '2025-01-18 14:00:00', '2025-01-18 16:00:00', N'Thi ghép hình lego theo chủ đề tết'),
-- Tháng 2
('SK008', N'Ngày Hội Tình Bạn', '2025-02-14 15:00:00', '2025-02-14 18:00:00', N'Làm thiệp và tặng quà cho bạn thân'),
('SK009', N'Cuộc Thi Đua Xe Nhí', '2025-02-26 10:00:00', '2025-02-26 12:00:00', N'Thi đua xe đồ chơi cho trẻ dưới 8 tuổi'),
-- Tháng 3
('SK010', N'Vẽ Tranh Vì Mẹ', '2025-03-08 09:00:00', '2025-03-08 11:30:00', N'Thể hiện tình cảm với mẹ qua tranh vẽ'),
('SK011', N'Hội Chơi Sáng Tạo', '2025-03-21 13:00:00', '2025-03-21 16:00:00', N'Làm đồ handmade từ giấy và nhựa tái chế'),
-- Tháng 4
('SK012', N'Ngày Trái Đất Xanh', '2025-04-22 10:00:00', '2025-04-22 12:00:00', N'Trồng cây và chơi trò bảo vệ môi trường'),
('SK013', N'Lớp Nấu Ăn Nhí', '2025-04-28 14:00:00', '2025-04-28 16:30:00', N'Hướng dẫn nấu món ăn đơn giản cho trẻ'),
-- Tháng 5
('SK014', N'Ngày Quốc Tế Thiếu Nhi Sớm', '2025-05-25 10:00:00', '2025-05-25 13:00:00', N'Sân khấu giao lưu và tặng quà cho bé'),
('SK015', N'Hội Thi Câu Đố Vui', '2025-05-30 15:00:00', '2025-05-30 17:00:00', N'Thi giải câu đố và nhận phần thưởng hấp dẫn'),
-- Tháng 6
('SK016', N'Lễ hội Bóng Nước', '2025-06-01 09:00:00', '2025-06-01 11:30:00', N'Ném bóng nước và các trò chơi dưới nước'),
('SK017', N'Trình Diễn Ảo Thuật', '2025-06-12 17:00:00', '2025-06-12 18:30:00', N'Ảo thuật gia biểu diễn tại sân khấu chính'),
('SK018', N'Đêm Nhạc Thiếu Nhi', '2025-06-20 19:00:00', '2025-06-20 21:00:00', N'Giao lưu ca hát giữa các bé'),
('SK019', N'Lớp Dạy Vẽ Miễn Phí', '2025-06-26 10:00:00', '2025-06-26 12:00:00', N'Vẽ phong cảnh bằng màu nước cho trẻ em'),
-- Tháng 7
('SK020', N'Làm Lồng Đèn Trung Thu', '2025-07-05 14:00:00', '2025-07-05 16:00:00', N'Làm lồng đèn giấy thủ công'),
('SK021', N'Ngày Hội Thả Diều', '2025-07-15 16:00:00', '2025-07-15 18:00:00', N'Thi thả diều ngoài trời với phụ huynh'),
('SK022', N'Cuộc Thi Xếp Hình Lego', '2025-07-25 10:00:00', '2025-07-25 12:00:00', N'Thi xếp hình theo chủ đề vui nhộn'),
-- Tháng 8
('SK023', N'Lễ Hội Màu Sắc', '2025-08-10 15:00:00', '2025-08-10 18:00:00', N'Tung bột màu, vẽ mặt, nhảy múa vui nhộn'),
('SK024', N'Thử Thách Nhảy Bao Bố', '2025-08-20 09:00:00', '2025-08-20 11:00:00', N'Thi nhảy bao bố trong sân chơi'),
('SK025', N'Chơi Ô Ăn Quan', '2025-08-28 13:00:00', '2025-08-28 15:00:00', N'Ôn lại trò chơi dân gian hấp dẫn'),
-- Tháng 9
('SK026', N'Tết Trung Thu Rước Đèn', '2025-09-07 17:30:00', '2025-09-07 20:00:00', N'Múa lân, phá cỗ, rước đèn quanh công viên'),
('SK027', N'Vẽ Mặt Hóa Trang', '2025-09-18 14:00:00', '2025-09-18 16:00:00', N'Trang điểm hóa trang thành nhân vật yêu thích'),
-- Tháng 10
('SK028', N'Lễ Hội Halloween Nhí', '2025-10-30 16:00:00', '2025-10-30 19:00:00', N'Hóa trang, xin kẹo và cuộc diễu hành nhỏ'),
('SK029', N'Thi Trang Trí Bí Ngô', '2025-10-25 13:30:00', '2025-10-25 15:30:00', N'Thiết kế bí ngô cho Halloween'),
-- Tháng 11
('SK030', N'Lễ Hội Ẩm Thực', '2025-11-03 10:00:00', '2025-11-03 14:00:00', N'Nếm thử món ăn truyền thống các vùng miền'),
('SK031', N'Cuộc Thi Kể Chuyện', '2025-11-12 09:00:00', '2025-11-12 11:00:00', N'Các bé kể chuyện theo tranh minh họa'),
-- Tháng 12
('SK032', N'Thi Làm Thiệp Giáng Sinh', '2025-12-01 14:00:00', '2025-12-01 16:00:00', N'Làm thiệp gửi tặng ông già Noel'),
('SK033', N'Lễ Hội Tuyết Giả', '2025-12-10 17:00:00', '2025-12-10 19:00:00', N'Tuyết nhân tạo và sân chơi mùa đông'),
('SK034', N'Thi Trang Trí Cây Thông', '2025-12-18 13:00:00', '2025-12-18 15:00:00', N'Các nhóm thi trang trí cây thông Noel'),
('SK035', N'Giáng Sinh Diệu Kỳ', '2025-12-24 18:00:00', '2025-12-24 21:00:00', N'Giao lưu cùng ông già Noel và phát quà'),
('SK036', N'Chào Năm Mới 2026', '2025-12-31 20:00:00', '2026-01-01 00:00:00', N'Đếm ngược và bắn pháo hoa mừng năm mới'),
('SK037', N'Hội Thi Vũ Điệu Nhí', '2025-12-15 14:00:00', '2025-12-15 16:00:00', N'Thi nhảy theo nhạc Giáng Sinh'),
('SK038', N'Xưởng Làm Quà Noel', '2025-12-05 09:30:00', '2025-12-05 11:30:00', N'Tự tay làm quà tặng bạn bè dịp lễ'),
('SK039', N'Ngày Hội Lắp Ráp Robot', '2025-12-20 10:00:00', '2025-12-20 13:00:00', N'Thiết kế robot mini và thi đấu mô hình'),
('SK040', N'Vòng Quay Trúng Thưởng Cuối Năm', '2025-12-29 16:00:00', '2025-12-29 18:00:00', N'Quay thưởng cuối năm và nhận quà tặng bất ngờ');

SELECT * FROM suKien;


--//////////////////--
-- Tạo bảng banDoAn 
CREATE TABLE banDoAn (
    maDoAn varchar(255) PRIMARY KEY,
    tenDoAn nvarchar(255),
    giaDoAn float,
    soLuong int
);

--Thêm dữ liệu
INSERT INTO banDoAn (maDoAn, tenDoAn, giaDoAn, soLuong)
VALUES 
    ('DA001', N'Bánh mì', 20000, 45),
    ('DA002', N'Phở bò', 35000, 25),
    ('DA003', N'Cơm gà', 25000, 60),
    ('DA004', N'Nước ép cam', 15000, 80),
    ('DA005', N'Bún chả', 30000, 15 );
	INSERT INTO banDoAn (maDoAn, tenDoAn, giaDoAn, soLuong)
VALUES 
    ('DA006', N'Hamburger bò', 40000, 35),
    ('DA007', N'Mì xào hải sản', 45000, 28),
    ('DA008', N'Cơm sườn', 30000, 40),
    ('DA009', N'Nước suối', 10000, 100),
    ('DA010', N'Sữa đậu nành', 12000, 60),
    ('DA011', N'Gà rán', 35000, 50),
    ('DA012', N'Pizza cá ngừ', 55000, 20),
    ('DA013', N'Cà phê sữa đá', 18000, 90),
    ('DA014', N'Trà đào', 22000, 75),
    ('DA015', N'Bánh bao', 15000, 45),
    ('DA016', N'Sandwich trứng', 20000, 40),
    ('DA017', N'Xôi gà', 25000, 55),
    ('DA018', N'Súp cua', 18000, 38),
    ('DA019', N'Bánh xèo', 28000, 30),
    ('DA020', N'Chè thập cẩm', 17000, 60),
    ('DA021', N'Mì Quảng', 32000, 25),
    ('DA022', N'Bánh canh giò heo', 30000, 35),
    ('DA023', N'Sữa tươi trân châu', 25000, 45),
    ('DA024', N'Trà sữa matcha', 28000, 50),
    ('DA025', N'Nước chanh', 12000, 80),
    ('DA026', N'Trà vải', 23000, 60),
    ('DA027', N'Bún bò Huế', 35000, 30),
    ('DA028', N'Bánh cuốn', 22000, 40),
    ('DA029', N'Bánh tráng trộn', 15000, 50),
    ('DA030', N'Khoai tây chiên', 18000, 65),
    ('DA031', N'Trà đào cam sả', 25000, 70),
    ('DA032', N'Cơm tấm', 28000, 35),
    ('DA033', N'Mì cay Hàn Quốc', 35000, 25),
    ('DA034', N'Bánh flan', 12000, 60),
    ('DA035', N'Trà sữa socola', 27000, 45),
    ('DA036', N'Bánh mì que pate', 18000, 55),
    ('DA037', N'Trà sữa trân châu đen', 29000, 50),
    ('DA038', N'Bún thịt nướng', 32000, 30),
    ('DA039', N'Sinh tố bơ', 25000, 40),
    ('DA040', N'Kem dừa', 22000, 50);
INSERT INTO banDoAn (maDoAn, tenDoAn, giaDoAn, soLuong)
VALUES 
    ('DA041', N'Bánh tráng nướng', 20000, 0),
    ('DA042', N'Mì ly', 12000, 0),
    ('DA043', N'Trà chanh', 10000, 0),
    ('DA044', N'Nem rán', 30000, 0),
    ('DA045', N'Sữa chua mít', 15000, 0);
INSERT INTO banDoAn (maDoAn, tenDoAn, giaDoAn, soLuong)
VALUES 
    ('DA046', N'Mì ý bò bằm', 45000, 0),
    ('DA047', N'Bánh mì chảo', 38000, 0),
    ('DA048', N'Chè khúc bạch', 25000, 0),
    ('DA049', N'Nước ép dưa hấu', 16000, 0),
    ('DA050', N'Khoai lang lắc phô mai', 20000, 0),
    ('DA051', N'Bánh plan caramel', 15000, 0),
    ('DA052', N'Sinh tố xoài', 22000, 0),
    ('DA053', N'Bánh gạo cay', 27000, 0),
    ('DA054', N'Trà đào macchiato', 30000, 0),
    ('DA055', N'Trà sữa bạc hà', 28000, 0),
    ('DA056', N'Xúc xích chiên', 15000, 0),
    ('DA057', N'Cơm chiên dương châu', 35000, 0),
    ('DA058', N'Gỏi cuốn tôm thịt', 20000, 0),
    ('DA059', N'Trà dâu tằm', 26000, 0),
    ('DA060', N'Sữa tươi đường đen', 30000, 0);

SELECT * FROM banDoAn;


--//////////////////--
-- Tạo bảng doLuuNiem 
CREATE TABLE doLuuNiem (
    maDoLuuNiem varchar(50),
    tenDoLuuNiem nvarchar(100),
    giaTien float,
    soLuong int
);

-- Thêm dữ liệu
INSERT INTO doLuuNiem (maDoLuuNiem, tenDoLuuNiem, giaTien, soLuong)
VALUES 
    ('DLN001', N'Móc khóa gỗ', 15000, 12),
    ('DLN002', N'Bút khắc tên', 25000, 30),
    ('DLN003', N'Áo in hình', 120000, 15),
    ('DLN004', N'Huy hiệu lưu niệm', 10000, 100),
    ('DLN005', N'Sổ tay vintage', 45000, 15);

	INSERT INTO doLuuNiem (maDoLuuNiem, tenDoLuuNiem, giaTien, soLuong)
VALUES
('DLN006', N'Túi vải in logo', 35000, 25),
('DLN007', N'Bình nước nhựa', 40000, 20),
('DLN008', N'Kẹp tóc handmade', 18000, 50),
('DLN009', N'Ốp lưng điện thoại', 50000, 40),
('DLN010', N'Gấu bông mini', 75000, 18),
('DLN011', N'Ví da nhỏ', 60000, 22),
('DLN012', N'Móc khóa kim loại', 20000, 45),
('DLN013', N'Bảng tên in chữ', 30000, 35),
('DLN014', N'Sổ da ghi chú', 70000, 16),
('DLN015', N'Thanh treo ảnh', 25000, 26),
('DLN016', N'Khung ảnh mini', 45000, 14),
('DLN017', N'Chén sứ vẽ tay', 40000, 10),
('DLN018', N'Hộp quà nhỏ', 22000, 32),
('DLN019', N'Gương cầm tay', 28000, 30),
('DLN020', N'Móc khóa hoạt hình', 17000, 55),
('DLN021', N'Huy hiệu kim loại', 22000, 40),
('DLN022', N'Băng đô vải nỉ', 15000, 38),
('DLN023', N'Ống đựng bút gỗ', 50000, 20),
('DLN024', N'Túi đeo chéo mini', 80000, 12),
('DLN025', N'Bút chì khắc tên', 12000, 60),
('DLN026', N'Đèn ngủ hình thú', 95000, 10),
('DLN027', N'Sổ sketch vẽ tay', 90000, 8),
('DLN028', N'Thẻ treo hành lý', 30000, 30),
('DLN029', N'Thước đo in logo', 18000, 40),
('DLN030', N'Hộp đựng trang sức', 65000, 15),
('DLN031', N'Đồng hồ treo tường mini', 120000, 9),
('DLN032', N'Gối kê cổ', 110000, 11),
('DLN033', N'Kẹp sách nghệ thuật', 16000, 33),
('DLN034', N'Túi thơm handmade', 20000, 28),
('DLN035', N'Băng dính họa tiết', 10000, 60),
('DLN036', N'Hộp bút vintage', 35000, 25),
('DLN037', N'Đèn pin mini', 30000, 18),
('DLN038', N'Bảng treo cửa gỗ', 40000, 20),
('DLN039', N'Sổ nhật ký', 55000, 14),
('DLN040', N'Áo mưa tiện lợi', 20000, 50),
('DLN041', N'Móc khóa acrylic', 25000, 45),
('DLN042', N'Quạt giấy vẽ tay', 22000, 35),
('DLN043', N'Túi dây rút in hình', 40000, 30),
('DLN044', N'Kẹp ảnh vintage', 12000, 60),
('DLN045', N'Ly sứ in logo', 60000, 22),
('DLN046', N'Sổ lò xo bìa cứng', 30000, 30),
('DLN047', N'Bút dạ quang 5 màu', 35000, 25),
('DLN048', N'Gối ôm hình thú', 130000, 7),
('DLN049', N'Ví vải canvas', 50000, 20),
('DLN050', N'Bình giữ nhiệt mini', 95000, 15),
('DLN051', N'Đồng hồ để bàn', 105000, 10),
('DLN052', N'Thẻ bookmark kim loại', 18000, 40),
('DLN053', N'Hộp nhạc quay tay', 150000, 6),
('DLN054', N'Mặt dây chuyền handmade', 50000, 18),
('DLN055', N'Túi chống sốc laptop', 120000, 8);
INSERT INTO doLuuNiem (maDoLuuNiem, tenDoLuuNiem, giaTien, soLuong)
VALUES 
('DLN056', N'Túi tote họa tiết', 45000, 0),
('DLN057', N'Sổ tay kèm bút', 30000, 0),
('DLN058', N'Bình nước thủy tinh', 70000, 0),
('DLN059', N'Bút bi in tên', 15000, 0),
('DLN060', N'Khung hình để bàn', 60000, 0),
('DLN061', N'Thẻ hành lý cao su', 25000, 0),
('DLN062', N'Ốp lưng vẽ tay', 50000, 0),
('DLN063', N'Móc khóa phát sáng', 22000, 0),
('DLN064', N'Ví đựng thẻ mini', 18000, 0),
('DLN065', N'Gương bỏ túi', 17000, 0),
('DLN066', N'Hộp quà nhạc', 140000, 0),
('DLN067', N'Sổ bìa vải', 65000, 0),
('DLN068', N'Kẹp giấy kim loại', 12000, 0),
('DLN069', N'Túi zip đựng đồ', 10000, 0),
('DLN070', N'Ly nhựa nắp bật', 28000, 0);

SELECT * FROM doLuuNiem;


--//////////////////--
-- Tạo bảng khachHang 
CREATE TABLE khachHang (
    maKhachHang VARCHAR(50) PRIMARY KEY,
    hoTen NVARCHAR(100),
    ngaySinh DATE,
    SDT VARCHAR(15),
    gioiTinh NVARCHAR(10)
);

-- Thêm dữ liệu
INSERT INTO khachHang (maKhachHang, hoTen, ngaySinh, SDT, gioiTinh) VALUES
('KH001', N'Nguyễn Văn An', '1990-03-15', '0912345671', N'Nam'),
('KH002', N'Trần Thị Bình', '1985-07-22', '0923456782', N'Nữ'),
('KH003', N'Lê Hoàng Cường', '1995-11-30', '0934567893', N'Nam'),
('KH004', N'Phạm Thị Dung', '1992-04-17', '0945678904', N'Nữ'),
('KH005', N'Hoàng Văn Em', '1988-09-05', '0956789015', N'Nam'),
('KH006', N'Ngô Thị Phương', '1997-02-28', '0967890126', N'Nữ'),
('KH007', N'Vũ Minh Giang', '1993-06-12', '0978901237', N'Nam'),
('KH008', N'Đỗ Thị Hà', '1987-12-01', '0989012348', N'Nữ'),
('KH009', N'Bùi Văn Hùng', '1991-08-19', '0990123459', N'Nam'),
('KH010', N'Lý Thị In', '1994-05-25', '0901234560', N'Nữ'),
('KH011', N'Nguyễn Quốc Khánh', '1989-10-10', '0912345681', N'Nam'),
('KH012', N'Trần Thị Lan', '1996-03-03', '0923456792', N'Nữ'),
('KH013', N'Lê Văn Minh', '1990-07-14', '0934567803', N'Nam'),
('KH014', N'Phạm Thị Ngọc', '1986-11-20', '0945678914', N'Nữ'),
('KH015', N'Hoàng Văn Phát', '1998-01-09', '0956789025', N'Nam'),
('KH016', N'Ngô Thị Quỳnh', '1992-09-27', '0967890136', N'Nữ'),
('KH017', N'Vũ Văn Rồng', '1985-04-04', '0978901247', N'Nam'),
('KH018', N'Đỗ Thị Sương', '1993-12-12', '0989012358', N'Nữ'),
('KH019', N'Bùi Văn Tài', '1997-06-18', '0990123469', N'Nam'),
('KH020', N'Lý Thị Uyên', '1988-02-23', '0901234570', N'Nữ'),
('KH021', N'Nguyễn Văn Vĩ', '1991-10-30', '0912345691', N'Nam'),
('KH022', N'Trần Thị Xuân', '1995-05-15', '0923456802', N'Nữ'),
('KH023', N'Lê Văn Ý', '1987-08-07', '0934567813', N'Nam'),
('KH024', N'Phạm Thị Ánh', '1994-03-22', '0945678924', N'Nữ'),
('KH025', N'Hoàng Văn Bảo', '1990-11-11', '0956789035', N'Nam'),
('KH026', N'Ngô Thị Cẩm', '1986-06-29', '0967890146', N'Nữ'),
('KH027', N'Vũ Văn Dũng', '1992-01-16', '0978901257', N'Nam'),
('KH028', N'Đỗ Thị Hồng', '1998-04-04', '0989012368', N'Nữ'),
('KH029', N'Bùi Văn Kiên', '1989-09-09', '0990123479', N'Nam'),
('KH030', N'Lý Thị Mai', '1993-07-07', '0901234580', N'Nữ'),
('KH031', N'Nguyễn Văn Nam', '1985-12-25', '0912345701', N'Nam'),
('KH032', N'Trần Thị Oanh', '1996-02-14', '0923456812', N'Nữ'),
('KH033', N'Lê Văn Phú', '1991-05-30', '0934567823', N'Nam'),
('KH034', N'Phạm Thị Quyên', '1987-10-18', '0945678934', N'Nữ'),
('KH035', N'Hoàng Văn Sơn', '1994-08-26', '0956789045', N'Nam');
-- Thêm thêm dữ liệu khách hàng rải rác độ tuổi 10-70
INSERT INTO khachHang (maKhachHang, hoTen, ngaySinh, SDT, gioiTinh) VALUES
-- Nhóm tuổi 10–20 (năm sinh khoảng 2005–2015)
('KH036', N'Nguyễn Tuấn Anh', '2010-06-15', '0911111111', N'Nam'),
('KH037', N'Lê Thị Bích', '2008-09-10', '0911111112', N'Nữ'),
('KH038', N'Phạm Minh Châu', '2006-01-20', '0911111113', N'Nữ'),
('KH039', N'Vũ Đức Duy', '2012-12-01', '0911111114', N'Nam'),
('KH040', N'Hoàng Ngọc Hà', '2009-03-05', '0911111115', N'Nữ'),

-- Nhóm tuổi 20–30 (năm sinh khoảng 1995–2005)
('KH041', N'Ngô Văn Hải', '1998-07-22', '0922222221', N'Nam'),
('KH042', N'Đỗ Thị Lan Hương', '2001-10-13', '0922222222', N'Nữ'),
('KH043', N'Trần Văn Hậu', '1996-04-09', '0922222223', N'Nam'),
('KH044', N'Lý Mỹ Linh', '1999-11-29', '0922222224', N'Nữ'),
('KH045', N'Bùi Tiến Lộc', '2000-02-17', '0922222225', N'Nam'),

-- Nhóm tuổi 30–40 (năm sinh khoảng 1985–1994)
('KH046', N'Nguyễn Hữu Lợi', '1990-08-02', '0933333331', N'Nam'),
('KH047', N'Trịnh Thị Mai', '1989-05-14', '0933333332', N'Nữ'),
('KH048', N'Lê Quốc Nam', '1986-12-07', '0933333333', N'Nam'),
('KH049', N'Phạm Thị Oanh', '1991-01-30', '0933333334', N'Nữ'),
('KH050', N'Hoàng Văn Phúc', '1987-09-19', '0933333335', N'Nam'),

-- Nhóm tuổi 40–50 (năm sinh khoảng 1975–1984)
('KH051', N'Trần Thanh Quang', '1980-06-11', '0944444441', N'Nam'),
('KH052', N'Nguyễn Thị Quyên', '1982-03-27', '0944444442', N'Nữ'),
('KH053', N'Bùi Đình Sang', '1984-07-08', '0944444443', N'Nam'),
('KH054', N'Lý Thị Thảo', '1979-10-22', '0944444444', N'Nữ'),
('KH055', N'Đỗ Văn Trung', '1983-02-05', '0944444445', N'Nam'),

-- Nhóm tuổi 50–60 (năm sinh khoảng 1965–1974)
('KH056', N'Phạm Văn Thành', '1970-01-12', '0955555551', N'Nam'),
('KH057', N'Trịnh Thị Uyên', '1968-08-24', '0955555552', N'Nữ'),
('KH058', N'Ngô Hữu Vinh', '1973-04-18', '0955555553', N'Nam'),
('KH059', N'Vũ Thị Xuân', '1971-06-30', '0955555554', N'Nữ'),
('KH060', N'Lê Văn Yên', '1969-11-06', '0955555555', N'Nam'),

-- Nhóm tuổi 60–70 (năm sinh khoảng 1955–1964)
('KH061', N'Nguyễn Thị Ánh', '1960-03-15', '0966666661', N'Nữ'),
('KH062', N'Hoàng Văn Bắc', '1959-09-21', '0966666662', N'Nam'),
('KH063', N'Trần Thị Chi', '1962-07-19', '0966666663', N'Nữ'),
('KH064', N'Phạm Đình Dậu', '1955-12-12', '0966666664', N'Nam'),
('KH065', N'Lý Thị Giang', '1964-05-02', '0966666665', N'Nữ');

SELECT * FROM khachHang;

--//////////////////--
-- Tạo bảng guiDo 
CREATE TABLE guiDo (
    maGuiDo INT IDENTITY(1,1) PRIMARY KEY,  -- TỰ TĂNG
    maTuDo VARCHAR(50),
    maKhachHang VARCHAR(50),
    thoiGianGui DATETIME,
    thoiGianNhan DATETIME NULL,
    FOREIGN KEY (maKhachHang) REFERENCES khachHang(maKhachHang)
);

-- Thêm dữ liệu
INSERT INTO guiDo (maTuDo, maKhachHang, thoiGianGui, thoiGianNhan) VALUES
('TD001', 'KH001', '2025-01-05 08:30:00', '2025-01-06 14:20:00'),
('TD002', 'KH002', '2025-01-10 10:15:00', '2025-01-11 09:45:00'),
('TD003', 'KH003', '2025-01-15 14:00:00', NULL),
('TD004', 'KH004', '2025-01-20 16:45:00', '2025-01-21 12:30:00'),
('TD005', 'KH005', '2025-01-25 09:00:00', '2025-01-26 15:10:00'),
('TD006', 'KH006', '2025-02-01 11:20:00', '2025-02-02 10:00:00'),
('TD007', 'KH007', '2025-02-05 13:50:00', NULL),
('TD008', 'KH008', '2025-02-10 15:30:00', '2025-02-11 11:25:00'),
('TD009', 'KH009', '2025-02-15 17:00:00', '2025-02-16 08:40:00'),
('TD010', 'KH010', '2025-02-20 08:10:00', '2025-02-21 14:50:00'),
('TD001', 'KH011', '2025-03-01 09:45:00', NULL),
('TD002', 'KH012', '2025-03-05 12:00:00', '2025-03-06 10:15:00'),
('TD003', 'KH013', '2025-03-10 14:30:00', '2025-03-11 09:00:00'),
('TD004', 'KH014', '2025-03-15 16:20:00', '2025-03-16 13:40:00'),
('TD005', 'KH015', '2025-03-20 10:50:00', NULL),
('TD006', 'KH016', '2025-03-25 11:30:00', '2025-03-26 15:00:00'),
('TD007', 'KH017', '2025-04-01 13:15:00', '2025-04-02 08:30:00'),
('TD008', 'KH018', '2025-04-05 15:45:00', '2025-04-06 12:20:00'),
('TD009', 'KH019', '2025-04-10 09:00:00', NULL),
('TD010', 'KH020', '2025-04-15 10:30:00', '2025-04-16 14:10:00'),
('TD001', 'KH021', '2025-04-20 12:45:00', '2025-04-21 09:50:00'),
('TD002', 'KH022', '2025-04-25 14:00:00', NULL),
('TD003', 'KH023', '2025-05-01 16:15:00', '2025-05-02 11:30:00'),
('TD004', 'KH024', '2025-05-05 08:20:00', '2025-05-06 13:45:00'),
('TD005', 'KH025', '2025-05-10 10:00:00', '2025-05-11 15:20:00'),
('TD006', 'KH026', '2025-05-15 11:40:00', NULL),
('TD007', 'KH027', '2025-05-20 13:50:00', '2025-05-21 09:10:00'),
('TD008', 'KH028', '2025-06-01 15:30:00', '2025-06-02 12:00:00'),
('TD009', 'KH029', '2025-06-05 09:45:00', '2025-06-06 14:30:00'),
('TD010', 'KH030', '2025-06-10 11:15:00', NULL),
('TD001', 'KH031', '2025-06-15 12:50:00', '2025-06-16 08:40:00'),
('TD002', 'KH032', '2025-06-20 14:20:00', '2025-06-21 10:50:00'),
('TD003', 'KH033', '2025-07-01 16:00:00', NULL),
('TD004', 'KH034', '2025-07-05 08:30:00', '2025-07-06 13:15:00'),
('TD005', 'KH035', '2025-07-10 10:45:00', '2025-07-11 09:20:00');

SELECT * FROM guiDo;


--//////////////////--
-- Tạo bảng nhanVien
CREATE TABLE NhanVien (
    maNhanVien VARCHAR(10) PRIMARY KEY,
    tenNhanVien NVARCHAR(100),
    ngaySinh DATE,
    SDT NVARCHAR(10),
    chucVu NVARCHAR(50),
    gioiTinh NVARCHAR(10)
);

-- Thêm dữ liệu
INSERT INTO NhanVien VALUES
-- Nhân viên bán hàng (4 người)
('NV001', N'Lê Trọng Hải Dương', '1997-06-15', '0901111001', N'Nhân viên bán hàng', N'Nam'),
('NV002', N'Vũ Đình Quân', '1996-07-20', '0901111002', N'Nhân viên bán hàng', N'Nam'),
('NV003', N'Khuất Thị Thùy Tiên', '1995-08-10', '0901111003', N'Nhân viên bán hàng', N'Nữ'),
('NV004', N'Nguyễn Thái Sơn', '1994-09-25', '0901111004', N'Nhân viên bán hàng', N'Nam'),
-- Bảo vệ (6 người)
('NV005', N'Phạm Văn Hùng', '1985-03-10', '0901111005', N'Bảo vệ', N'Nam'),
('NV006', N'Ngô Văn Duy', '1987-11-22', '0901111006', N'Bảo vệ', N'Nam'),
('NV007', N'Vũ Minh Tuấn', '1984-05-09', '0901111007', N'Bảo vệ', N'Nam'),
('NV008', N'Trịnh Hữu Phúc', '1980-02-18', '0901111008', N'Bảo vệ', N'Nam'),
('NV009', N'Tô Quang Bình', '1979-07-14', '0901111009', N'Bảo vệ', N'Nam'),
('NV010', N'Đỗ Văn Hòa', '1982-01-05', '0901111010', N'Bảo vệ', N'Nam'),
-- Nhân viên hỗ trợ (6 người)
('NV011', N'Nguyễn Thị Hồng', '1993-01-11', '0901111011', N'Nhân viên hỗ trợ', N'Nữ'),
('NV012', N'Trần Thị Mai', '1994-04-23', '0901111012', N'Nhân viên hỗ trợ', N'Nữ'),
('NV013', N'Ngô Văn Kiên', '1992-09-02', '0901111013', N'Nhân viên hỗ trợ', N'Nam'),
('NV014', N'Nguyễn Hữu Phúc', '1990-11-30', '0901111014', N'Nhân viên hỗ trợ', N'Nam'),
('NV015', N'Lê Thị Hoa', '1996-12-05', '0901111015', N'Nhân viên hỗ trợ', N'Nữ'),
('NV016', N'Phạm Thị Yến', '1995-03-19', '0901111016', N'Nhân viên hỗ trợ', N'Nữ'),
-- Nhân viên kỹ thuật (7 người)
('NV017', N'Trần Văn Khánh', '1989-06-17', '0901111017', N'Nhân viên kỹ thuật', N'Nam'),
('NV018', N'Hoàng Thế Anh', '1990-08-22', '0901111018', N'Nhân viên kỹ thuật', N'Nam'),
('NV019', N'Nguyễn Văn Tân', '1991-03-03', '0901111019', N'Nhân viên kỹ thuật', N'Nam'),
('NV020', N'Trịnh Thị Thanh', '1992-10-01', '0901111020', N'Nhân viên kỹ thuật', N'Nữ'),
('NV021', N'Lê Hữu Phước', '1988-12-09', '0901111021', N'Nhân viên kỹ thuật', N'Nam'),
('NV022', N'Phạm Quốc Thịnh', '1987-09-18', '0901111022', N'Nhân viên kỹ thuật', N'Nam'),
('NV023', N'Nguyễn Nhật Linh', '1993-07-07', '0901111023', N'Nhân viên kỹ thuật', N'Nữ'),
-- Nhân viên chăm sóc khách hàng (7 người)
('NV024', N'Lê Thị Thu Hà', '1995-06-11', '0901111024', N'Nhân viên chăm sóc khách hàng', N'Nữ'),
('NV025', N'Trần Thanh Tùng', '1994-02-15', '0901111025', N'Nhân viên chăm sóc khách hàng', N'Nam'),
('NV026', N'Hoàng Ngọc Bích', '1992-01-09', '0901111026', N'Nhân viên chăm sóc khách hàng', N'Nữ'),
('NV027', N'Nguyễn Thị Yến Nhi', '1996-05-28', '0901111027', N'Nhân viên chăm sóc khách hàng', N'Nữ'),
('NV028', N'Vũ Thị Hải Yến', '1991-08-19', '0901111028', N'Nhân viên chăm sóc khách hàng', N'Nữ'),
('NV029', N'Trịnh Hoàng Long', '1989-11-12', '0901111029', N'Nhân viên chăm sóc khách hàng', N'Nam'),
('NV030', N'Phạm Minh Đức', '1990-04-04', '0901111030', N'Nhân viên chăm sóc khách hàng', N'Nam');

SELECT * FROM NhanVien;


--//////////////////--
-- Tạo bảng ve
CREATE TABLE Ve (
    maVe VARCHAR(10) PRIMARY KEY,
    giaVe INT,
    loaiVe NVARCHAR(50),
    moTa NVARCHAR(255)
);

-- Thêm dữ liệu
INSERT INTO Ve VALUES
('VE001', 50000, N'Vé người lớn', N'Vé dành cho người lớn, không giới hạn trò chơi'),
('VE002', 30000, N'Vé trẻ em', N'Vé dành cho trẻ em cao dưới 1m3'),
('VE003', 70000, N'Vé VIP', N'Trọn gói tất cả các trò chơi và ưu tiên xếp hàng'),
('VE004', 20000, N'Vé tham quan', N'Chỉ áp dụng cho tham quan, không bao gồm trò chơi'),
('VE005', 40000, N'Vé học sinh', N'Ưu đãi dành cho học sinh cấp 2-3'),
('VE006', 60000, N'Vé gia đình', N'Áp dụng cho nhóm từ 4 người trở lên');

SELECT * FROM Ve;


--//////////////////--
-- Tạo bảng hoaDon
CREATE TABLE HoaDon (
    maHoaDon VARCHAR(10) PRIMARY KEY,
    maNhanVien VARCHAR(10),
    ngayMua DATE,
    tongTien INT	,
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
);

-- Thêm dữ liệu
INSERT INTO HoaDon (maHoaDon, maNhanVien, ngayMua, tongTien) VALUES
('HD001', 'NV001', '2025-06-15', 150000),
('HD002', 'NV002', '2025-06-16', 230000),
('HD003', 'NV003', '2025-06-17', 175000),
('HD004', 'NV004', '2025-06-18', 200000),
('HD005', 'NV001', '2025-06-19', 265000);
INSERT INTO HoaDon (maHoaDon, maNhanVien, ngayMua, tongTien) VALUES
('HD006', 'NV001', '2025-01-05', 110000),
('HD007', 'NV002', '2025-01-10', 190000),
('HD008', 'NV003', '2025-01-15', 80000),
('HD009', 'NV004', '2025-01-20', 170000),
('HD010', 'NV001', '2025-01-25', 130000),
('HD011', 'NV002', '2025-02-01', 230000),
('HD012', 'NV003', '2025-02-05', 60000),
('HD013', 'NV004', '2025-02-10', 140000),
('HD014', 'NV001', '2025-02-15', 90000),
('HD015', 'NV002', '2025-02-20', 210000),
('HD016', 'NV003', '2025-03-01', 120000),
('HD017', 'NV004', '2025-03-05', 180000),
('HD018', 'NV001', '2025-03-10', 70000),
('HD019', 'NV002', '2025-03-15', 150000),
('HD020', 'NV003', '2025-03-20', 200000),
('HD021', 'NV004', '2025-03-25', 110000),
('HD022', 'NV001', '2025-04-01', 190000),
('HD023', 'NV002', '2025-04-05', 80000),
('HD024', 'NV003', '2025-04-10', 170000),
('HD025', 'NV004', '2025-04-15', 130000),
('HD026', 'NV001', '2025-04-20', 230000),
('HD027', 'NV002', '2025-04-25', 60000),
('HD028', 'NV003', '2025-05-01', 140000),
('HD029', 'NV004', '2025-05-05', 90000),
('HD030', 'NV001', '2025-05-10', 210000),
('HD031', 'NV002', '2025-05-15', 120000),
('HD032', 'NV003', '2025-05-20', 180000),
('HD033', 'NV004', '2025-05-25', 70000),
('HD034', 'NV001', '2025-06-01', 150000),
('HD035', 'NV002', '2025-06-05', 200000),
('HD036', 'NV003', '2025-06-10', 110000),
('HD037', 'NV004', '2025-06-15', 190000),
('HD038', 'NV001', '2025-06-20', 80000),
('HD039', 'NV002', '2025-06-25', 170000),
('HD040', 'NV003', '2025-06-30', 130000),
('HD041', 'NV004', '2025-07-01', 230000),
('HD042', 'NV001', '2025-07-05', 60000),
('HD043', 'NV002', '2025-07-10', 140000),
('HD044', 'NV003', '2025-07-15', 90000),
('HD045', 'NV004', '2025-07-20', 210000),
('HD046', 'NV001', '2025-07-25', 120000),
('HD047', 'NV002', '2025-07-30', 180000),
('HD048', 'NV003', '2025-01-02', 70000),
('HD049', 'NV004', '2025-01-07', 150000),
('HD050', 'NV001', '2025-01-12', 200000),
('HD051', 'NV002', '2025-01-17', 110000),
('HD052', 'NV003', '2025-01-22', 190000),
('HD053', 'NV004', '2025-01-27', 80000),
('HD054', 'NV001', '2025-02-02', 170000),
('HD055', 'NV002', '2025-02-07', 130000);


SELECT * FROM HoaDon;

--tổng tiền
SELECT SUM(tongTien) AS tongDoanhThu
FROM HoaDon

--tổng vé bán ra
SELECT SUM(soLuong)
FROM ChiTietHoaDon cthd
JOIN HoaDon hd ON cthd.maHoaDon = hd.maHoaDon

--vé bán chạy nhất
SELECT TOP 1 v.loaiVe, SUM(soLuong) AS sl
FROM ChiTietHoaDon cthd
JOIN Ve v ON cthd.maVe = v.maVe
JOIN HoaDon hd ON cthd.maHoaDon = hd.maHoaDon
GROUP BY v.loaiVe
ORDER BY sl DESC

--tháng doanh thu cao nhất
SELECT TOP 1 MONTH(ngayMua) AS thang, SUM(tongTien) AS tong
FROM HoaDon
GROUP BY MONTH(ngayMua)
ORDER BY tong DESC

--trung bình 1 hóa đơn
SELECT AVG(tongTien * 1.0) AS doanhThuTB
FROM HoaDon

--tháng doanh thu cao nhất theo năm
SELECT TOP 1 MONTH(ngayMua) AS thang, YEAR(ngayMua) AS nam, SUM(tongTien) AS tongDoanhThu
FROM HoaDon
GROUP BY MONTH(ngayMua), YEAR(ngayMua)
ORDER BY tongDoanhThu DESC

--doanh thu từng loại vé
SELECT v.loaiVe, SUM(cthd.soLuong) AS soLuong, SUM(cthd.soLuong * v.giaVe) AS doanhThu
FROM ChiTietHoaDon cthd
JOIN Ve v ON cthd.maVe = v.maVe
JOIN HoaDon hd ON cthd.maHoaDon = hd.maHoaDon
GROUP BY v.loaiVe


--//////////////////--
-- Tạo bảng chiTietHoaDon
CREATE TABLE ChiTietHoaDon (
    maHoaDon VARCHAR(10),
    maVe VARCHAR(10),
    soLuong INT,
    PRIMARY KEY (maHoaDon, maVe),
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    FOREIGN KEY (maVe) REFERENCES Ve(maVe)
);

-- Thêm dữ liệu
INSERT INTO ChiTietHoaDon (maHoaDon, maVe, soLuong) VALUES
('HD001', 'VE001', 2),
('HD001', 'VE002', 1),
('HD002', 'VE003', 3),
('HD003', 'VE001', 1),
('HD003', 'VE003', 2),
('HD004', 'VE002', 2),
('HD004', 'VE003', 1),
('HD005', 'VE001', 1),
('HD005', 'VE002', 1),
('HD005', 'VE003', 2);
INSERT INTO ChiTietHoaDon (maHoaDon, maVe, soLuong) VALUES
('HD006', 'VE001', 1), ('HD006', 'VE002', 2),
('HD007', 'VE003', 2), ('HD007', 'VE001', 1),
('HD008', 'VE002', 1), ('HD008', 'VE004', 2),
('HD009', 'VE005', 3), ('HD009', 'VE002', 1),
('HD010', 'VE006', 1), ('HD010', 'VE001', 1),
('HD011', 'VE003', 2), ('HD011', 'VE006', 1),
('HD012', 'VE004', 3),
('HD013', 'VE001', 2), ('HD013', 'VE005', 1),
('HD014', 'VE002', 3),
('HD015', 'VE003', 3),
('HD016', 'VE006', 2),
('HD017', 'VE001', 2), ('HD017', 'VE002', 1),
('HD018', 'VE004', 1), ('HD018', 'VE005', 1),
('HD019', 'VE003', 1), ('HD019', 'VE001', 1),
('HD020', 'VE006', 2), ('HD020', 'VE002', 1),
('HD021', 'VE001', 1), ('HD021', 'VE002', 2),
('HD022', 'VE003', 2), ('HD022', 'VE005', 1),
('HD023', 'VE004', 2), ('HD023', 'VE002', 1),
('HD024', 'VE001', 1), ('HD024', 'VE003', 1),
('HD025', 'VE006', 1), ('HD025', 'VE005', 1),
('HD026', 'VE003', 2), ('HD026', 'VE004', 1),
('HD027', 'VE002', 2),
('HD028', 'VE001', 2), ('HD028', 'VE004', 1),
('HD029', 'VE005', 1), ('HD029', 'VE002', 1),
('HD030', 'VE003', 3),
('HD031', 'VE006', 2),
('HD032', 'VE001', 2), ('HD032', 'VE002', 1),
('HD033', 'VE004', 1), ('HD033', 'VE005', 1),
('HD034', 'VE003', 1), ('HD034', 'VE001', 1),
('HD035', 'VE006', 2), ('HD035', 'VE002', 1),
('HD036', 'VE001', 1), ('HD036', 'VE002', 2),
('HD037', 'VE003', 2), ('HD037', 'VE005', 1),
('HD038', 'VE004', 2), ('HD038', 'VE002', 1),
('HD039', 'VE001', 1), ('HD039', 'VE003', 1),
('HD040', 'VE006', 1), ('HD040', 'VE005', 1),
('HD041', 'VE003', 2), ('HD041', 'VE004', 1),
('HD042', 'VE002', 2),
('HD043', 'VE001', 2), ('HD043', 'VE004', 1),
('HD044', 'VE005', 1), ('HD044', 'VE002', 1),
('HD045', 'VE003', 3),
('HD046', 'VE006', 2),
('HD047', 'VE001', 2), ('HD047', 'VE002', 1),
('HD048', 'VE004', 1), ('HD048', 'VE005', 1),
('HD049', 'VE003', 1), ('HD049', 'VE001', 1),
('HD050', 'VE006', 2), ('HD050', 'VE002', 1),
('HD051', 'VE001', 1), ('HD051', 'VE002', 2),
('HD052', 'VE003', 2), ('HD052', 'VE005', 1),
('HD053', 'VE004', 2), ('HD053', 'VE002', 1),
('HD054', 'VE001', 1), ('HD054', 'VE003', 1),
('HD055', 'VE006', 1), ('HD055', 'VE005', 1);

SELECT * FROM ChiTietHoaDon;


-- Tạo bảng hoaDonDoAn
CREATE TABLE hoaDonDoAn (
    maHoaDon varchar(50) PRIMARY KEY,
     maNhanVien VARCHAR(10),
    ngayMua datetime,
    tongTien float,
    FOREIGN KEY (maNhanVien) REFERENCES nhanVien(maNhanVien)
);

-- Chèn dữ liệu vào bảng hoaDonDoAn
INSERT INTO hoaDonDoAn (maHoaDon, maNhanVien, ngayMua, tongTien)
VALUES 
    ('HD001', 'NV001', '2025-06-29 10:00:00', 90000),
    ('HD002', 'NV002', '2025-06-29 12:30:00', 135000),
    ('HD003', 'NV003', '2025-06-28 15:45:00', 75000),
    ('HD004', 'NV004', '2025-06-28 17:00:00', 60000),
    ('HD005', 'NV005', '2025-06-27 09:15:00', 110000);
INSERT INTO hoaDonDoAn (maHoaDon, maNhanVien, ngayMua, tongTien)
VALUES 
    ('HD006', 'NV001', '2025-01-05 10:00:00', 86000),
    ('HD007', 'NV002', '2025-01-15 12:15:00', 110000),
    ('HD008', 'NV003', '2025-01-25 14:30:00', 84000),
    ('HD009', 'NV004', '2025-02-05 16:45:00', 66000),
    ('HD010', 'NV001', '2025-02-15 09:00:00', 102000),
    ('HD011', 'NV002', '2025-02-25 11:15:00', 86000),
    ('HD012', 'NV003', '2025-03-05 13:30:00', 110000),
    ('HD013', 'NV004', '2025-03-15 15:45:00', 60000),
    ('HD014', 'NV001', '2025-03-25 10:00:00', 99000),
    ('HD015', 'NV002', '2025-04-05 12:15:00', 72000),
    ('HD016', 'NV003', '2025-04-15 14:30:00', 96000),
    ('HD017', 'NV004', '2025-04-25 16:45:00', 56000),
    ('HD018', 'NV001', '2025-05-05 09:00:00', 78000),
    ('HD019', 'NV002', '2025-05-15 11:15:00', 115000),
    ('HD020', 'NV003', '2025-05-25 13:30:00', 84000),
    ('HD021', 'NV004', '2025-06-05 15:45:00', 69000),
    ('HD022', 'NV001', '2025-06-15 10:00:00', 103000),
    ('HD023', 'NV002', '2025-06-25 12:15:00', 56000),
    ('HD024', 'NV003', '2025-07-05 14:30:00', 92000),
    ('HD025', 'NV004', '2025-07-15 16:45:00', 71000),
    ('HD026', 'NV001', '2025-01-10 09:00:00', 100000),
    ('HD027', 'NV002', '2025-01-20 11:15:00', 76000),
    ('HD028', 'NV003', '2025-01-30 13:30:00', 110000),
    ('HD029', 'NV004', '2025-02-10 15:45:00', 76000),
    ('HD030', 'NV001', '2025-02-20 10:00:00', 96000),
    ('HD031', 'NV002', '2025-03-02 12:15:00', 60000),
    ('HD032', 'NV003', '2025-03-12 14:30:00', 87000),
    ('HD033', 'NV004', '2025-03-22 16:45:00', 61000),
    ('HD034', 'NV001', '2025-04-01 09:00:00', 90000),
    ('HD035', 'NV002', '2025-04-11 11:15:00', 84000),
    ('HD036', 'NV003', '2025-04-21 13:30:00', 56000),
    ('HD037', 'NV004', '2025-05-01 15:45:00', 104000),
    ('HD038', 'NV001', '2025-05-11 10:00:00', 74000),
    ('HD039', 'NV002', '2025-05-21 12:15:00', 101000),
    ('HD040', 'NV003', '2025-05-31 14:30:00', 54000),
    ('HD041', 'NV004', '2025-06-10 16:45:00', 105000),
    ('HD042', 'NV001', '2025-06-20 09:00:00', 78000),
    ('HD043', 'NV002', '2025-06-30 11:15:00', 88000),
    ('HD044', 'NV003', '2025-07-10 13:30:00', 70000),
    ('HD045', 'NV004', '2025-07-20 15:45:00', 88000),
    ('HD046', 'NV001', '2025-01-15 10:00:00', 64000),
    ('HD047', 'NV002', '2025-01-25 12:15:00', 90000),
    ('HD048', 'NV003', '2025-02-04 14:30:00', 68000),
    ('HD049', 'NV004', '2025-02-14 16:45:00', 97000),
    ('HD050', 'NV001', '2025-02-24 09:00:00', 72000),
    ('HD051', 'NV002', '2025-03-06 11:15:00', 85000),
    ('HD052', 'NV003', '2025-03-16 13:30:00', 57000),
    ('HD053', 'NV004', '2025-03-26 15:45:00', 99000),
    ('HD054', 'NV001', '2025-04-05 10:00:00', 76000),
    ('HD055', 'NV002', '2025-04-15 12:15:00', 89000),
    ('HD056', 'NV003', '2025-04-25 14:30:00', 56000),
    ('HD057', 'NV004', '2025-05-05 16:45:00', 105000),
    ('HD058', 'NV001', '2025-05-15 09:00:00', 71000),
    ('HD059', 'NV002', '2025-05-25 11:15:00', 87000),
    ('HD060', 'NV003', '2025-06-04 13:30:00', 99000);

-- Tạo bảng chiTietHoaDonDoAn
CREATE TABLE chiTietHoaDonDoAn (
    maHoaDon varchar(50),
    maDoAn varchar(255),
    tenDoAn nvarchar(255),
    soLuong int,
    PRIMARY KEY (maHoaDon, maDoAn),
    FOREIGN KEY (maHoaDon) REFERENCES hoaDonDoAn(maHoaDon),
    FOREIGN KEY (maDoAn) REFERENCES banDoAn(maDoAn)
);

-- Chèn dữ liệu vào bảng chiTietHoaDonDoAn
INSERT INTO chiTietHoaDonDoAn (maHoaDon, maDoAn, tenDoAn, soLuong)
VALUES 
    ('HD001', 'DA001', N'Phở bò', 1),
    ('HD001', 'DA002', N'Bún chả', 1),
    ('HD002', 'DA003', N'Cơm tấm', 2),
    ('HD002', 'DA004', N'Mì quảng', 2),
    ('HD003', 'DA005', N'Bánh mì', 3),
    ('HD004', 'DA002', N'Bún chả', 1),
    ('HD004', 'DA005', N'Bánh mì', 2),
    ('HD005', 'DA001', N'Phở bò', 1),
    ('HD005', 'DA004', N'Mì quảng', 2);
INSERT INTO chiTietHoaDonDoAn (maHoaDon, maDoAn, tenDoAn, soLuong)
VALUES 
    ('HD006', 'DA003', N'Cơm gà', 2), ('HD006', 'DA025', N'Nước chanh', 3),
    ('HD007', 'DA006', N'Hamburger bò', 1), ('HD007', 'DA027', N'Bún bò Huế', 2),
    ('HD008', 'DA008', N'Cơm sườn', 1), ('HD008', 'DA014', N'Trà đào', 2), ('HD008', 'DA009', N'Nước suối', 1),
    ('HD009', 'DA015', N'Bánh bao', 2), ('HD009', 'DA025', N'Nước chanh', 3),
    ('HD010', 'DA027', N'Bún bò Huế', 1), ('HD010', 'DA012', N'Pizza cá ngừ', 1), ('HD010', 'DA034', N'Bánh flan', 1),
    ('HD011', 'DA003', N'Cơm gà', 2), ('HD011', 'DA025', N'Nước chanh', 3),
    ('HD012', 'DA006', N'Hamburger bò', 1), ('HD012', 'DA027', N'Bún bò Huế', 2),
    ('HD013', 'DA009', N'Nước suối', 3), ('HD013', 'DA015', N'Bánh bao', 2),
    ('HD014', 'DA012', N'Pizza cá ngừ', 1), ('HD014', 'DA014', N'Trà đào', 2),
    ('HD015', 'DA008', N'Cơm sườn', 2), ('HD015', 'DA034', N'Bánh flan', 2),
    ('HD016', 'DA002', N'Phở bò', 1), ('HD016', 'DA038', N'Bún thịt nướng', 2),
    ('HD017', 'DA001', N'Bánh mì', 2), ('HD017', 'DA013', N'Cà phê sữa đá', 2),
    ('HD018', 'DA019', N'Bánh xèo', 2), ('HD018', 'DA025', N'Nước chanh', 2),
    ('HD019', 'DA027', N'Bún bò Huế', 1), ('HD019', 'DA006', N'Hamburger bò', 2),
    ('HD020', 'DA032', N'Cơm tấm', 2), ('HD020', 'DA009', N'Nước suối', 3),
    ('HD021', 'DA015', N'Bánh bao', 2), ('HD021', 'DA034', N'Bánh flan', 3),
    ('HD022', 'DA012', N'Pizza cá ngừ', 1), ('HD022', 'DA038', N'Bún thịt nướng', 2),
    ('HD023', 'DA013', N'Cà phê sữa đá', 2), ('HD023', 'DA025', N'Nước chanh', 2),
    ('HD024', 'DA002', N'Phở bò', 2), ('HD024', 'DA014', N'Trà đào', 1),
    ('HD025', 'DA008', N'Cơm sườn', 1), ('HD025', 'DA039', N'Sinh tố bơ', 2),
    ('HD026', 'DA027', N'Bún bò Huế', 2), ('HD026', 'DA009', N'Nước suối', 3),
    ('HD027', 'DA001', N'Bánh mì', 2), ('HD027', 'DA034', N'Bánh flan', 3),
    ('HD028', 'DA006', N'Hamburger bò', 2), ('HD028', 'DA012', N'Pizza cá ngừ', 1),
    ('HD029', 'DA019', N'Bánh xèo', 2), ('HD029', 'DA025', N'Nước chanh', 2),
    ('HD030', 'DA002', N'Phở bò', 2), ('HD030', 'DA013', N'Cà phê sữa đá', 1),
    ('HD031', 'DA015', N'Bánh bao', 3), ('HD031', 'DA009', N'Nước suối', 3),
    ('HD032', 'DA038', N'Bún thịt nướng', 2), ('HD032', 'DA014', N'Trà đào', 1),
    ('HD033', 'DA003', N'Cơm gà', 1), ('HD033', 'DA034', N'Bánh flan', 3),
    ('HD034', 'DA027', N'Bún bò Huế', 1), ('HD034', 'DA012', N'Pizza cá ngừ', 1),
    ('HD035', 'DA008', N'Cơm sườn', 2), ('HD035', 'DA025', N'Nước chanh', 2),
    ('HD036', 'DA001', N'Bánh mì', 2), ('HD036', 'DA013', N'Cà phê sữa đá', 2),
    ('HD037', 'DA006', N'Hamburger bò', 1), ('HD037', 'DA038', N'Bún thịt nướng', 2),
    ('HD038', 'DA019', N'Bánh xèo', 2), ('HD038', 'DA009', N'Nước suối', 2),
    ('HD039', 'DA002', N'Phở bò', 1), ('HD039', 'DA014', N'Trà đào', 3),
    ('HD040', 'DA015', N'Bánh bao', 2), ('HD040', 'DA034', N'Bánh flan', 2),
    ('HD041', 'DA012', N'Pizza cá ngừ', 1), ('HD041', 'DA027', N'Bún bò Huế', 2),
    ('HD042', 'DA008', N'Cơm sườn', 1), ('HD042', 'DA025', N'Nước chanh', 4),
    ('HD043', 'DA002', N'Phở bò', 1), ('HD043', 'DA014', N'Trà đào', 2),
    ('HD044', 'DA003', N'Cơm gà', 2), ('HD044', 'DA009', N'Nước suối', 2),
    ('HD045', 'DA038', N'Bún thịt nướng', 2), ('HD045', 'DA014', N'Trà đào', 1),
    ('HD046', 'DA001', N'Bánh mì', 2), ('HD046', 'DA034', N'Bánh flan', 2),
    ('HD047', 'DA006', N'Hamburger bò', 1), ('HD047', 'DA027', N'Bún bò Huế', 1),
    ('HD048', 'DA019', N'Bánh xèo', 2), ('HD048', 'DA009', N'Nước suối', 1),
    ('HD049', 'DA012', N'Pizza cá ngừ', 1), ('HD049', 'DA038', N'Bún thịt nướng', 2),
    ('HD050', 'DA008', N'Cơm sườn', 2), ('HD050', 'DA009', N'Nước suối', 2),
    ('HD051', 'DA002', N'Phở bò', 1), ('HD051', 'DA014', N'Trà đào', 2),
    ('HD052', 'DA015', N'Bánh bao', 2), ('HD052', 'DA034', N'Bánh flan', 2),
    ('HD053', 'DA027', N'Bún bò Huế', 1), ('HD053', 'DA012', N'Pizza cá ngừ', 1),
    ('HD054', 'DA008', N'Cơm sườn', 1), ('HD054', 'DA025', N'Nước chanh', 3),
    ('HD055', 'DA038', N'Bún thịt nướng', 2), ('HD055', 'DA009', N'Nước suối', 1),
    ('HD056', 'DA001', N'Bánh mì', 2), ('HD056', 'DA013', N'Cà phê sữa đá', 2),
    ('HD057', 'DA006', N'Hamburger bò', 1), ('HD057', 'DA027', N'Bún bò Huế', 2),
    ('HD058', 'DA019', N'Bánh xèo', 2), ('HD058', 'DA009', N'Nước suối', 1),
    ('HD059', 'DA002', N'Phở bò', 1), ('HD059', 'DA014', N'Trà đào', 2),
    ('HD060', 'DA012', N'Pizza cá ngừ', 1), ('HD060', 'DA038', N'Bún thịt nướng', 2);