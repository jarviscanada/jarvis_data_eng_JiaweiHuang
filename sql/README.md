# Introduction

# SQL Queries

###### Table Setup (DDL)

``` sql
create table if not exists cd.members (
  memid integer not null,
  surname varchar(200) not null,
  firstname varchar(200) not null,
  address varchar(300) not null,
  zipcode integer not null,
  telephone varchar(20) not null,
  recommendedby integer,
  joindate timestamp not null,
  constraint members_pk primary key (memid),
  constraint fk_members_recommended_by foreign key (recommendedby)
);

create table if not exists cd.bookings (
  bookid integer not null,
  facid integer not null,
  memid integer not null,
  starttime timestamp not null,
  slots integer not null,
  constraint bookings_pk primary key (bookid),
  constraint fk_bookings_facid foreign key (facid),
  constarint fk_bookings_memid foreign key (memid) references cd.members (memid)
);

create table if not exists cd.facilities (
  facid integer not null,
  name varchar(100) not null,
  membercost numeric not null,
  guestcost numeric not null, 
  initialoutlay numeric not null,
  monthlymaintenance numeric not null,
  constraint facilities_pk primary key (facid)
);
```

###### Question 1: Insert some data into a table

```sql
insert into cd.facilities
(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values (9, 'Spa', 20, 30, 100000, 800);         
```

###### Question 2: Insert calculated data into a table
```sql
insert into cd.facilities
(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values (
  (select max(facid) from cd.facilities)+1 ,
  'Spa', 20, 30, 100000, 800)
```

###### Question 3: Update some existing data
```sql
update cd.facilities
set initialoutlay = 10000
where name = 'Tennis Court 2';
```

###### Question 4: Update a row based on the contents of another row
```sql
update cd.facilities
set 
  membercost = (
  	select membercost
  	from cd.facilities
  	where name = 'Tennis Court 1')*1.1,
  guestcost = (
  	select guestcost
  	from cd.facilities
  	where name = 'Tennis Court 1')*1.1
where name = 'Tennis Court 2';
```
###### Question 5: Delete all bookings
```sql
delete from cd.bookings;
```
###### Question 5:  Delete a member from the cd.members table
```sql
delete from cd.members
where memid = 37;
```
###### Question 6:  Control which rows are retrieved - part 2
```sql
select facid, name, membercost, monthlymaintenance
from cd.facilities
where membercost > 0
and membercost < monthlymaintenance/50;
```
###### Question 7:  Basic string searches
```sql
select * 
from cd.facilities
where name like '%Tennis%';
```

###### Question 8:  Matching against multiple possible values
```sql
select * from cd.facilities
where facid in (1,5);
```

###### Question 9:  Working with dates
```sql
select memid, surname, firstname, joindate
from cd.members
where joindate >= '2012-09-01' ;
```

###### Question 10:  Combining results from multiple queries
```sql
select surname from cd.members
union
select name from cd.facilities;
```

###### Question 11: Retrieve the start times of members' bookings
```sql
select b.starttime from cd.bookings b
join cd.members m
on b.memid = m.memid
where m.surname = 'Farrell'
and m.firstname = 'David’;
```

###### Question 12: Retrieve the start times of members' bookings
```sql
select b.starttime from cd.bookings b
join cd.members m
on b.memid = m.memid
where m.surname = 'Farrell'
and m.firstname = 'David’;
```

###### Question 13: Work out the start times of bookings for tennis courts
```sql
select b.starttime as start, f.name
from cd.bookings b
join cd.facilities f
on b.facid = f.facid
where f.name like 'Tennis%'
and cast(b.starttime as date) = '2012-09-21'
order by start;
```

###### Question 14: Produce a list of all members, along with their recommender
```sql
select 
m1.firstname as memfname,
m1.surname as memsname,
m2.firstname as recfname,
m2.surname as recsname
from cd.members m1
left join cd.members m2
on m1.recommendedby = m2.memid
order by memsname, memfname;
```

###### Question 15: Produce a list of all members, along with their recommenders, using no joins
```sql
select distinct concat(firstname, ' ', surname) as member, (
  select concat(firstname, ' ', surname)
  from cd.members m1
  where m2.recommendedby = m1.memid
  ) as recommender
from cd.members m2
order by member;
```

###### Question 16: Count the number of recommendations each member makes.
```sql
select recommendedby, count(*) as count
from cd.members
where recommendedby is not null
group by recommendedby
order by recommendedby;
```
###### Question 17: List the total slots booked per facility
```sql
select facid, sum(slots) as total_slots
from cd.bookings
group by facid
order by facid;
```
###### Question 18: List the total slots booked per facility in a given month
```sql
select facid, sum(slots) as total_slots
from cd.bookings
where cast(starttime as date) between '09-01-2012' and '09-30-2012'
group by facid
order by total_slots;
```

###### Question 19: List the total slots booked per facility per month
```sql
select facid, extract(month from starttime) as month, sum(slots) as total_slots
from cd.bookings
where extract(year from starttime) = 2012
group by facid, extract(month from starttime);
```

###### Question 20: Find the count of members who have made at least one booking
```sql
select count(distinct memid) from cd.bookings;
```

###### Question 21: List each member's first booking after September 1st 2012
```sql
select m.surname, m.firstname, m.memid, min(b.starttime) as starttime
from cd.members m
join cd.bookings b
on m.memid = b.memid
where b.starttime >= '09-01-2012'
group by m.surname, m.firstname, m.memid
order by m.memid;
```

###### Question 22: Produce a list of member names, with each row containing the total member count
```sql
select count(*) over(), firstname, surname
from cd.members
order by joindate;
```

###### Question 23: Produce a numbered list of members
```sql
select row_number() over(order by joindate), 
firstname, surname
from cd.members
order by joindate;
```

###### Question 24: Output the facility id that has the highest number of slots booked, again
```sql
select facid, total
from (
  select 
  rank() over (order by sum(slots) desc) as rank, 
  facid, sum(slots) as total
  from cd.bookings
  group by facid
  )
where rank = 1
```
###### Question 25: Format the names of members
```sql
select concat(surname, ', ', firstname) as name
from cd.members
```

###### Question 26: Find telephone numbers with parentheses
```sql
select memid, telephone
from cd.members
where telephone like '(%'
order by memid
```

###### Question 27: Count the number of members whose surname starts with each letter of the alphabet
```sql
select letter, count(*) as count
from (
  select substr(surname,1,1) as letter from cd.members
  )
group by letter
having count(*) > 0
order by letter
```


