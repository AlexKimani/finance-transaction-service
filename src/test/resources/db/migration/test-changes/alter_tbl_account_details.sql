ALTER TABLE account_details ADD COLUMN active ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE';