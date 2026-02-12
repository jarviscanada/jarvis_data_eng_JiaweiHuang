
-- inspect table schema
\d+ retail;

-- show first ten rows
select * from retail limit 10;

-- Check # of records
select count(*) from retail;

-- number of clients
select count(distinct customer_id) from retail;

-- invoice date range
select min(invoice_date), max(invoice_date) from retail;

-- number of SKU/merchants
select count(distinct stock_code) from retail;

-- Calculate average invoice amount excluding invoices with a negative amount
select avg(amt) from (
    select invoice_no, sum(quantity * unit_price) as amt
    from retail
    group by invoice_no
    having sum(retail.quantity * retail.unit_price) > 0
    ) as invoice_amt;

-- Calculate total revenue
select sum(unit_price * quantity) from retail;

-- Calculate total revenue by YYYYMM
select to_char(invoice_date, 'yyyy-mm') as month,
       sum(unit_price * quantity) as revenue
from retail
group by to_char(invoice_date, 'yyyy-mm')