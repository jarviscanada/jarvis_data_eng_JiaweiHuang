\c exercises;

-- Q1
insert into cd.facilities
(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values (9, 'Spa', 20, 30, 100000, 800);         

-- Q2
insert into cd.facilities
(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values (
  (select max(facid) from cd.facilities)+1 ,
  'Spa', 20, 30, 100000, 800);

-- Q3
update cd.facilities
set initialoutlay = 10000
where name = 'Tennis Court 2';

-- Q4
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

-- Q5
delete from cd.bookings;

-- Q6
delete from cd.members
where memid = 37;

-- Q7
select facid, name, membercost, monthlymaintenance
from cd.facilities
where membercost > 0
and membercost < monthlymaintenance/50;

-- Q8
select *
from cd.facilities
where name like '%Tennis%';

-- Q9
select * from cd.facilities
where facid in (1,5);

-- Q10
select memid, surname, firstname, joindate
from cd.members
where joindate >= '2012-09-01' ;

-- Q11
select surname from cd.members
union
select name from cd.facilities;

-- Q12
select b.starttime from cd.bookings b
join cd.members m
on b.memid = m.memid
where m.surname = 'Farrell'
and m.firstname = 'David';

-- Q13
select b.starttime from cd.bookings b
join cd.members m
on b.memid = m.memid
where m.surname = 'Farrell'
and m.firstname = 'David';

-- Q14
select b.starttime as start, f.name
from cd.bookings b
join cd.facilities f
on b.facid = f.facid
where f.name like 'Tennis%'
and cast(b.starttime as date) = '2012-09-21'
order by start;

-- Q15
select
m1.firstname as memfname,
m1.surname as memsname,
m2.firstname as recfname,
m2.surname as recsname
from cd.members m1
left join cd.members m2
on m1.recommendedby = m2.memid
order by memsname, memfname;

-- Q16
select distinct concat(firstname, ' ', surname) as member, (
  select concat(firstname, ' ', surname)
  from cd.members m1
  where m2.recommendedby = m1.memid
  ) as recommender
from cd.members m2
order by member;

-- Q17
select recommendedby, count(*) as count
from cd.members
where recommendedby is not null
group by recommendedby
order by recommendedby;

-- Q18
select facid, sum(slots) as total_slots
from cd.bookings
group by facid
order by facid;

-- Q19
select facid, sum(slots) as total_slots
from cd.bookings
where cast(starttime as date) between '09-01-2012' and '09-30-2012'
group by facid
order by total_slots;

-- Q20
select facid, extract(month from starttime) as month, sum(slots) as total_slots
from cd.bookings
where extract(year from starttime) = 2012
group by facid, extract(month from starttime);

-- Q21
select count(distinct memid) from cd.bookings;

-- Q22
select m.surname, m.firstname, m.memid, min(b.starttime) as starttime
from cd.members m
join cd.bookings b
on m.memid = b.memid
where b.starttime >= '09-01-2012'
group by m.surname, m.firstname, m.memid
order by m.memid;

-- Q23
select count(*) over(), firstname, surname
from cd.members
order by joindate;

-- Q24
select row_number() over(order by joindate),
firstname, surname
from cd.members
order by joindate;

-- Q25
select facid, total
from (
  select
  rank() over (order by sum(slots) desc) as rank,
  facid, sum(slots) as total
  from cd.bookings
  group by facid
  )
where rank = 1;

-- Q26
select concat(surname, ', ', firstname) as name
from cd.members;

-- Q27
select memid, telephone
from cd.members
where telephone like '(%'
order by memid;

-- Q28
select letter, count(*) as count
from (
  select substr(surname,1,1) as letter from cd.members
  )
group by letter
having count(*) > 0
order by letter;
