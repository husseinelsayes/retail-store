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
        {"productName" : "cucumber","category": "GROCERY","price": 100.00},
        {"productName" : "playstation","category": "ELECTRONIC_DEVICE","price": 300.00},
        {"productName" : "iphone","category": "ELECTRONIC_DEVICE","price": 600.00}
    ]
});

db.bills.insertOne({
    _id: 2,
    issuedFor: {
        _id: 2,
        email: "afiiliate@email.com",
        userType: "AFFILIATE",
        createdAt: "2020-07-25T14:10:26.113Z"
    },
    issuedAt: "2020-07-25T14:10:26.113Z",
    items: [
        {"productName" : "carrot","category": "GROCERY","price": 30.00},
        {"productName" : "playstation","category": "ELECTRONIC_DEVICE","price": 500.00},
        {"productName" : "iphone","category": "ELECTRONIC_DEVICE","price": 499.00},
        {"productName" : "smart TV","category": "ELECTRONIC_DEVICE","price": 1600.00}
    ]
});

db.bills.insertOne({
    _id: 3,
    issuedFor: {
        _id: 3,
        email: "customer@email.com",
        userType: "CUSTOMER",
        createdAt: "2020-07-25T14:10:26.113Z"
    },
    issuedAt: "2020-07-25T14:10:26.113Z",
    items: [
        {"productName" : "tomato","category": "GROCERY","price": 18.00},
        {"productName" : "nintendo","category": "ELECTRONIC_DEVICE","price": 799.00},
        {"productName" : "VR","category": "ELECTRONIC_DEVICE","price": 800.00}
    ]
});
