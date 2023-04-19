drop user if exists 'admin'@'localhost';
create user 'admin'@'localhost';
set password for 'admin'@'localhost' = 'admin';
grant select, update, drop, insert on shop.* to 'admin'@'localhost'; -- nwm czy drop też, to niby admin ale no nie wiem