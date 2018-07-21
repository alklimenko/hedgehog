SELECT Acc.Account_Id
     ,Acc.Open_Date
     ,Acc.Product_Cd
     ,Acc.Avail_Balance
FROM   Account Acc
WHERE  Acc.Cust_Id IN
      (SELECT Cus.Cust_Id FROM Customer Cus WHERE Cus.Cust_Type_Cd = '{{ cust_type }}');