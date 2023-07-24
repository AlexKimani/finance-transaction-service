INSERT INTO transaction_types (transaction_type, transaction_fee, transaction_tax, created_at) VALUES ('Deposit', 0, 0, NOW()),
                                                                                                      ('Withdraw', 23, 0.16, NOW()),
                                                                                                      ('Transfer', 27, 0.16, NOW()),
                                                                                                      ('Reverse', 0, 0, NOW()),
                                                                                                      ('Agent Pay', 22, 0.16, NOW());