-- 176. 第二高的薪水

-- 思路1： 找到最大的假设为A，然后以此作为查询条件，找到最大的比A小的，在整张表里就是第2大的
select  max(salary) as SecondHighestSalary
from Employee where salary <
(select max(salary)
from Employee)

-- 思路2：使用limit(startIndex,offset)即从startIndex下标开始，长度为offset的子集。
-- 使用limit可能会出现数组越界，所以要加上判空条件
-- mysql : ifNull(xx,default_val)
-- sql-server : isNull(xx,default)
-- oracle : nvl(xx,default)
select ifnull((select distinct salary from employee order by salary desc limit 1,1),null) as SecondHighestSalary


