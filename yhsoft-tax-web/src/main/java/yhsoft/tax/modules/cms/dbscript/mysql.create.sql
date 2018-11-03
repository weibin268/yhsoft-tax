DROP TABLE IF EXISTS cms_product;
CREATE TABLE cms_product
(
  Id VARCHAR(50) PRIMARY KEY,
  CategoryId VARCHAR(50),
  Name NVARCHAR(200) NOT NULL,
  UnitPrice DECIMAL(12,2),
  Specification VARCHAR(100),
  ImageUrl VARCHAR(100),
  Description NVARCHAR(500),
  Status       INT,
  CreatedTime  DATETIME,
  ModifiedTime DATETIME,
  CreatedBy    VARCHAR(50),
  ModifiedBy   VARCHAR(50)
);
ALTER TABLE cms_product COMMENT = '商品表';


DROP TABLE IF EXISTS cms_productcategory;
CREATE TABLE cms_productcategory
(
  Id VARCHAR(50) PRIMARY KEY,
  Name NVARCHAR(200) NOT NULL,
  ImageUrl VARCHAR(100),
  Description NVARCHAR(500),
  ParentId     VARCHAR(50),
  FullPath     VARCHAR(500),
  Seq          INT,
  Level        INT,
  Status       INT,
  CreatedTime  DATETIME,
  ModifiedTime DATETIME,
  CreatedBy    VARCHAR(50),
  ModifiedBy   VARCHAR(50)
);
ALTER TABLE cms_productcategory COMMENT = '商品类别';
