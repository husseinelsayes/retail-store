db = db.getSiblingDB('retail_store');

db.createUser({
    user: 'test',
    pwd: 'test123',
    roles: [
        { role: 'readWrite', db: 'retail_store' }
    ]
});

db.createCollection('db_sequence');

db.db_sequence.insertOne({
    id: 1,
    seq: "bill_seq"
});

db.createCollection('bills');

db.bills.insertOne({
    _id: 1,
    issuedFor: {
        _id: 1,
        email: "employee@email.com",
        userType: "EMPLOYEE",
        createdAt: "2020-07-25T14:10:26.113Z"
    },
    issuedAt: "2020-07-25T14:10:26.113Z",
    items: [
        {"productName" : "","category": "GROCERY","price": 100.00},
        {"productName" : "playstation","category": "ELECTRONIC_DEVICE","price": 300.00},
        {"productName" : "iphone","category": "ELECTRONIC_DEVICE","price": 600.00}
    ]
});
