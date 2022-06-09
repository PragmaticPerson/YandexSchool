CREATE TABLE shopUnit (
    id CHARACTER VARYING(36) PRIMARY KEY,
    name CHARACTER VARYING(20) NOT NULL,
    date TIMESTAMP NOT NULL,
    parent_id CHARACTER VARYING(36) DEFAULT NULL REFERENCES shopUnit,
    type CHARACTER VARYING(8) NOT NULL,
    price INTEGER DEFAULT NULL CHECK (price >= 0 OR price = NULL)
);

CREATE TABLE childrenShopUnits (
    parent_id CHARACTER VARYING(36) REFERENCES shopUnit (id),
    children_id CHARACTER VARYING(36) REFERENCES shopUnit (id),
    PRIMARY KEY (parent_id, children_id),
    CHECK (parent_id != children_id)
);