use shop;
delimiter $$
drop function if exists  addInvoice $$
create procedure addInvoice(IN cl INT, OUT fv INT)
begin
    declare date DATETIME;
    declare result int;
    SET @date=now();
    insert into invoices (dateIssued,CLIENT,confirmed)
    VALUES (@date,cl,false);
    SET @result=(select id from invoices where client=cl and dateIssued=@date);
    SELECT @result INTO fv;
end $$
delimiter ;

delimiter $$
drop procedure if exists addLine $$
create procedure addLine(in inv int,in prod int,in units int)
begin
    DECLARE t DECIMAL(5,2);
    DECLARE c DECIMAL(5,2);
    SET @t=(SELECT pricePerUnit FROM offer WHERE product=prod)*units;
    INSERT INTO invoiceline (totalPrice,units,product,invoice)
    VALUES (@t,units,prod,inv); 

end $$
delimiter ;