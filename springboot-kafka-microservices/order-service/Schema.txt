-- SCHEMA: order_service
	-- =====================================================
	-- Xóa nếu tồn tại (tùy chọn)
	-- DROP SCHEMA IF EXISTS order_service CASCADE;

	CREATE SCHEMA IF NOT EXISTS order_service
	    AUTHORIZATION postgres;

	-- =====================================================
	-- ENUM: order_status
	-- =====================================================
	CREATE TYPE order_status AS ENUM(
	 'PENDING',
	 'PAID',
	 'APPROVED',
	 'CANCELLING',
	 'CANCELLED'
	 );

	 CREATE CAST (varchar AS order_status) WITH INOUT AS IMPLICIT;

	-- =====================================================
	-- TABLE: orders (aggregate root)
	-- =====================================================
	CREATE TABLE IF NOT EXISTS order_service.orders (
	    id UUID NOT NULL,
	    customer_id UUID NOT NULL,
	    restaurant_id UUID NOT NULL,
	    tracking_id UUID NOT NULL UNIQUE,
	    price NUMERIC(10,2) NOT NULL,
	    order_status order_status NOT NULL,
	    failure_messages VARCHAR,
	    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
	    CONSTRAINT orders_pkey PRIMARY KEY (id)
	);

	-- =====================================================
	-- TABLE: order_items (aggregate members)
	-- =====================================================
	CREATE TABLE IF NOT EXISTS order_service.order_items (
	    id BIGINT NOT NULL,
	    order_id UUID NOT NULL,
	    product_id UUID NOT NULL,
	    price NUMERIC(10,2) NOT NULL,
	    quantity INTEGER NOT NULL,
	    sub_total NUMERIC(10,2) NOT NULL,
	    CONSTRAINT order_items_pkey PRIMARY KEY (id, order_id),
	    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id)
	        REFERENCES order_service.orders (id)
	        ON DELETE CASCADE
	);

	-- =====================================================
	-- TABLE: order_address (owned entity / value object)
	-- =====================================================
	CREATE TABLE IF NOT EXISTS order_service.order_address (
	    id UUID NOT NULL,
	    street VARCHAR(255) NOT NULL,
	    postal_code VARCHAR(50) NOT NULL,
	    city VARCHAR(100) NOT NULL,
	    CONSTRAINT order_address_pkey PRIMARY KEY (id),
	    CONSTRAINT fk_order_address_order FOREIGN KEY (id)
	        REFERENCES order_service.orders (id)
	        ON DELETE CASCADE
	);

    -- =====================================================
	-- TABLE: order_rating (owned entity / value object)
	-- =====================================================
	CREATE TABLE IF NOT EXISTS order_service.order_rating (
	    id UUID NOT NULL,
	    star INT NOT NULL,
	    comment text,
	    CONSTRAINT order_rating_pkey PRIMARY KEY (id),
	    CONSTRAINT fk_order_rating_order FOREIGN KEY (id)
	        REFERENCES order_service.orders (id)
	        ON DELETE CASCADE
	);
	-- =====================================================
	-- INDEXES for performance
	-- =====================================================
	CREATE INDEX IF NOT EXISTS idx_orders_tracking_id
	    ON order_service.orders (tracking_id);

	CREATE INDEX IF NOT EXISTS idx_orders_customer_id
	    ON order_service.orders (customer_id);