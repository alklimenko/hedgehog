SELECT * FROM table_a a, table_b b, table_c c
WHERE 1=1
    AND a.name NOT LIKE '%14%'
    AND b.name NOT LIKE '%12%'
    AND c.name NOT LIKE '%10%'
    AND b.a_id = a.id
    AND c.a_id = a.id
    AND c.b_id = b.id