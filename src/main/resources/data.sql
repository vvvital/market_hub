INSERT INTO categories(id,name,photo_preview) VALUES (100,'Комп’ютерна техніка', 'localhost:8080/markethub/categories/100/100.png');
INSERT INTO categories(id,name,photo_preview) VALUES (175, 'Мобільні телефони', 'localhost:8080/markethub/categories/175/175.png');
INSERT INTO categories(id,name,photo_preview) VALUES (250, 'Побутова техніка', 'localhost:8080/markethub/categories/250/250.png');
INSERT INTO categories(id,name,photo_preview) VALUES (325, 'Ігрові приставки', 'localhost:8080/markethub/categories/325/325.png');
INSERT INTO categories(id,name,photo_preview) VALUES (400, 'Аудіотехніка', 'localhost:8080/markethub/categories/400/400.png');

INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (100, 'Ноутбуки', 'localhost:8080/markethub/categories/100/110.png', 100);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (160, 'Настільні комп’ютери', 'localhost:8080/markethub/categories/100/160.png', 100);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (220, 'Планшети', 'localhost:8080/markethub/categories/100/220.png', 100);

INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (340, 'Смартфони', 'localhost:8080/markethub/categories/175/340.jpg', 175);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (400, 'Розумні годинники', 'localhost:8080/markethub/categories/175/400.jpg', 175);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (460, 'Аксесуари', 'localhost:8080/markethub/categories/175/460.jpg', 175);

INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (520, 'Дрібна', 'localhost:8080/markethub/categories/250/520.jpg', 250);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (580, 'Велика', 'localhost:8080/markethub/categories/250/580.jpg', 250);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (640, 'Кліматична', 'localhost:8080/markethub/categories/250/640.jpg', 250);

INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (700, 'Приставки', 'localhost:8080/markethub/categories/325/700.jpg', 325);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (760, 'Джойстики', 'localhost:8080/markethub/categories/325/760.jpg', 325);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (820, 'Комплектуючі', 'localhost:8080/markethub/categories/325/820.png', 325);

INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (880, 'Навушники', 'localhost:8080/markethub/categories/400/880.jpg', 400);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (940, 'Акустичні системи', 'localhost:8080/markethub/categories/400/940.jpg', 400);
INSERT INTO sub_categories(id,name,photo_preview,category_id) VALUES (1000, 'Медіаплеєри', 'localhost:8080/markethub/categories/400/1000.jpg', 400);

INSERT INTO goods(id, name, price, create_at, article, available, stock_quantity, category_id, sub_category_id, photo_preview, sold, description, brand, seller_id)
VALUES  (100, 'Ноутбук LENOVO IdeaPad 3', 29999, '2024-01-26 12:12:0', '2134321', true, 25, 100, 100, 'localhost:8080/markethub/categories/100/1000.png', 18, 'none', 'Lenovo', 1),
        (128, 'Ноутбук Asus Vivobook 15', 17499, '2024-01-26 13:12:0', '6586223', false, 0, 100, 100, 'localhost:8080/markethub/categories/100/1132.png', 25, 'none', 'Asus', 1),
        (153, 'Ігрова приставка Play Station 5', 23499, '2024-01-26 13:49:0', '4524662', true, 5, 325, 700, 'localhost:8080/markethub/categories/325/1264.png', 67, 'none', 'Sony', 2),
        (178, 'Холодильник Samsung RB34C675DSA', 24799, '2024-01-26 12:47:0', '4781662', true, 9, 250, 580, 'localhost:8080/markethub/categories/250/1395.png', 12, 'none', 'Samsung', 3),
        (203, 'Смартфон APPLE iPhone 13 128GB', 29999, '2024-01-26 12:47:0', '4781652', true, 12, 175, 340, 'localhost:8080/markethub/categories/175/1923.png', 8, 'none', 'Apple', 1),
        (228, 'Навушники PANASONIC RB-HX220BEE-K', 17499, '2024-01-26 12:47:0', '35636', true, 2, 400, 880, 'localhost:8080/markethub/categories/400/1659.png', 23, 'none', 'Panasonic', 2),
        (253, 'Міксер TEFAL Prep’Mix HT450B38', 23499, '2024-01-26 12:47:0', '086403', true, 27, 250, 520, 'localhost:8080/markethub/categories/250/1527.png', 9, 'none', 'Tefal', 3),
        (278, 'Обігрівач WetAit WQH-2020', 17499, '2024-01-26 12:47:0', '224426', true, 11, 250, 640, 'localhost:8080/markethub/categories/250/1791.png', 3, 'none', 'WetAit', 3),
        (303, 'Ноутбук Acer Aspire 3 A317-55P-P6CH', 17768, '2024-01-27 13:24:23','735463', true, 15, 100, 100, 'localhost:8080/markethub/categories/100/1923.png', 1, 'none', 'Acer', 1),
        (328, 'Ноутбук ASUS X712EA-BX868', 18439, '2024-01-27 17:21:53', '721985', true, 8, 100, 100, 'localhost:8080/markethub/categories/100/2055.png', 4, 'none', 'Asus', 1),
        (353, 'Ноутбук HP 15s-eq2345nw', 25305, '2024-02-01 12:11:43', '554168', true, 5, 100, 100, 'localhost:8080/markethub/categories/100/2187.png', 5, 'none', 'Hp', 1),
        (378, 'Ноутбук Lenovo V15 G3 IAP', 26374, '2024-02-01 14:10:23', '562797', true, 12, 100, 100, 'localhost:8080/markethub/categories/100/2319.png', 9, 'none', 'Lenovo', 1),
        (403, 'Ноутбук Apple MacBook Air M2 A2681', 55199, '2024-02-02 18:14:23', '212280', true, 4, 100, 100, 'localhost:8080/markethub/categories/100/2451.png', 1, 'none', 'Apple', 1),
        (428, 'Ігровий комп ютер Ryzen 5', 22099, '2024-02-02 14:12:18', '753921', true, 4, 100, 160, 'localhost:8080/markethub/categories/100/2583.png', 1, 'none', 'Razen', 2),
        (453, 'Кишеньковий ПК Intel N95', 6998, '2024-02-03 11:09:34', '684194', true, 5, 100, 160, 'localhost:8080/markethub/categories/100/2715.png', 2, 'none', 'Intel', 2),
        (478, 'Комп''ютер Cobra Advanced I14F.16.H1S4.166S.6368', 22599, '2024-02-03 12:31:12', '337237', true, 8, 100, 160, 'localhost:8080/markethub/categories/100/2847.png', 4, 'none', 'Cobra', 2),
        (503, 'Комп''ютер ARTLINE Gaming X39 v67', 30599, '2024-02-03 14:27:12', '371268', false, 0, 100, 160, 'localhost:8080/markethub/categories/100/2979.png', 12, 'none', 'Artline', 2),
        (528, 'Планшет Lenovo Tab M10 Plus', 6999, '2024-02-03 17:12:34', '392003', true, 12, 100, 220, 'localhost:8080/markethub/categories/100/3111.png', 14, 'none', 'Lenovo', 3),
        (553, 'Планшет Apple iPad 10.9" 2022', 22499, '2024-02-03 14:11:12', '357715', true, 9, 100, 220, 'localhost:8080/markethub/categories/100/3243.png', 10, 'none', 'Apple', 3),
        (575, 'Планшет Samsung Galaxy Tab A9', 5849, '2024-02-04 11:12:05', '412056', true, 21, 100, 220, 'localhost:8080/markethub/categories/100/3375.png', 11, 'none', 'Samsung', 3),
        (600, 'Планшет Apple iPad Air 10.9', 29499, '2024-02-03 19:12:01', '337933', true, 7, 100, 220, 'localhost:8080/markethub/categories/100/3507.png', 3, 'none', 'Apple', 3),
        (625, 'Планшет Lenovo Tab M10 Plus', 9499, '2024-02-03 20:14:21', '350837', true, 12,100,220,'localhost:8080/markethub/categories/100/3639.png', 12, 'none','Lenovo',3),
        (650, 'Планшет Lenovo Yoga Tab 11',15999,'2024-02-03 20:12:11', '350838', true, 23,100,220,'localhost:8080/markethub/categories/100/3771.png', 12, 'none','Lenovo',3),
        (675, 'Планшет Samsung Galaxy Tab A7 Lite',4999,'2024-02-01 11:12:28', '350839', true, 24,100,220,'localhost:8080/markethub/categories/100/3903.png', 9, 'none','Samsung',3),
        (700, 'Планшет Pixus Titan',6999,'2024-02-01 10:24:21', '350840', true, 11,100,220,'localhost:8080/markethub/categories/100/4035.png', 4, 'none','Pixus',3),
        (725, 'Планшет Oscal Spider',9850,'2024-02-02 09:41:53', '350841', false, 0,100,220,'localhost:8080/markethub/categories/100/4167.png', 17, 'none','Oscal',1);