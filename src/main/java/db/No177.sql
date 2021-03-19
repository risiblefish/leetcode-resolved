CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
--由于起始下标为0，所以这里要将N处理为N-1
  SET N := N-1;
  RETURN (
      # Write your MySQL query statement below.
      select ifnull(
          (select distinct salary
           from Employee
           order by salary desc limit N,1)
      ,null)as getNthHighestSalary
  );
END