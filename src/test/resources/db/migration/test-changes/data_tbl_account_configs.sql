INSERT INTO account_configs(account_type, account_minimum_limit, account_maximum_limit, created_at) VALUES
                                                                                                        ('Default Customer Account', 1, 150000, NOW()),
                                                                                                        ('Agent Account', 1, 500000, NOW()),
                                                                                                        ('VIP Account', 1000, 1000000, NOW());