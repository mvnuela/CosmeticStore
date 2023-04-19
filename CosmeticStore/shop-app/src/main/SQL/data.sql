INSERT INTO brands(NAME)
VALUES("Huda Beauty"),
      ("Zoeva"),
      ("Glam Shop"),
      ("Mac"),
      ("NYX"),
      ("Fenty Beauty"),
      ("YSL");

INSERT INTO colors(NAME)
VALUES("Ivory"),
      ("Porcelain"),
      ("True Beige"),
      ("Natural Beige");

INSERT INTO coveragelevels(numericValue,NAME)
VALUES(1,"Soft Natural"),
      (1,"Glow Natural"),
      (2,"Matte Natural"),
      (3,"Medium Satin"),
      (3,"Medium Matt"),
      (4,"Full Matte"),
      (4,"Full Satin"),
      (5,"Full Coverage");

INSERT INTO types(NAME)
VALUES("concealer"),
      ("foundation");

INSERT INTO products(color,brand,coverageLevel,TYPE,NAME)
VALUES(1,1,1,1,"Magic Concealer"),
      (2,2,2,1,"Perfect Skin"),
      (3,3,3,1,"Wow Effect"),
      (4,4,4,2,"True Match"),
      (1,5,5,1,"Your C"),
      (2,6,6,2,"Perfect Foundation"),
      (3,7,7,2,"Mineral Skin"),
      (4,5,8,2,"Dolls Effect"),
      (4,6,4,1,"Goodbye Redness");

INSERT INTO offer(pricePerUnit,unitsInStock,product)
VALUES(200,50,1),
      (250,100,2),
      (400,20,3),
      (300,190,4),
      (150,400,5),
      (99,170,6),
      (190,140,7),
      (315,122,8);