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

###### Question 1: Show all members 
```sql
SELECT *
FROM cd.members
```

###### Question 2: Lorem ipsum...

```sql
SELECT blah blah 
```



