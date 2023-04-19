delimiter $$
DROP PROCEDURE if EXISTS confirm $$
CREATE PROCEDURE confirm(IN inv INT)
BEGIN
DECLARE pom INT;
SELECT id FROM invoices WHERE id=inv INTO pom;
 IF EXISTS (SELECT *  FROM invoices WHERE id=pom AND confirmed=TRUE) THEN
 signal sqlstate '45000' set message_text="Cannot confirm invoice";
 ELSE
 	START TRANSACTION;
 	UPDATE offer,invoiceline
 	SET offer.unitInStock=offer.unitInStock-invoiceline.units
 	WHERE invoiceline.invoice=pom AND offer.prodcut=invoiceline.product;
 	COMMIT;
 	UPDATE invoices
 	SET confirmed=TRUE
 	WHERE id=pom;
END IF;
END $$
delimiter ;