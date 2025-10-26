-- ===========================================
-- Create schema
-- ===========================================
CREATE SCHEMA IF NOT EXISTS "payment_service";

-- ===========================================
-- Create ENUM types
-- ===========================================

-- Payment status enum
CREATE TYPE payment_status AS ENUM (
    'COMPLETED',
    'CANCELLED',
    'FAILED'
);

-- Transaction type enum (for credit history)
CREATE TYPE transaction_type AS ENUM (
    'CREDIT',
    'DEBIT'
);

-- ===========================================
-- Table: credit_entry
-- ===========================================
CREATE TABLE "payment_service".credit_entry (
                                                id UUID NOT NULL,
                                                customer_id UUID NOT NULL,
                                                total_credit_amount NUMERIC(19,2) NOT NULL CHECK (total_credit_amount >= 0),
                                                CONSTRAINT credit_entry_pkey PRIMARY KEY (id)
);

-- ===========================================
-- Table: credit_history
-- ===========================================
CREATE TABLE "payment_service".credit_history (
                                                  id UUID NOT NULL,
                                                  customer_id UUID NOT NULL,
                                                  amount NUMERIC(19,2) NOT NULL CHECK (amount >= 0),
                                                  type transaction_type NOT NULL,
                                                  CONSTRAINT credit_history_pkey PRIMARY KEY (id)
);

-- ===========================================
-- Table: payments
-- ===========================================
CREATE TABLE "payment_service".payments (
                                            id UUID NOT NULL,
                                            customer_id UUID NOT NULL,
                                            order_id UUID NOT NULL,
                                            price NUMERIC(19,2) NOT NULL CHECK (price >= 0),
                                            status payment_status NOT NULL,
                                            created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                            CONSTRAINT payments_pkey PRIMARY KEY (id)
);


-- ===========================================
-- Indexes for performance
-- ===========================================
CREATE INDEX idx_credit_entry_customer_id
    ON "payment_service".credit_entry (customer_id);

CREATE INDEX idx_credit_history_customer_id
    ON "payment_service".credit_history (customer_id);

CREATE INDEX idx_payments_customer_id
    ON "payment_service".payments (customer_id);

CREATE INDEX idx_payments_order_id
    ON "payment_service".payments (order_id);

CREATE INDEX idx_payments_created_at
    ON "payment_service".payments (created_at);
