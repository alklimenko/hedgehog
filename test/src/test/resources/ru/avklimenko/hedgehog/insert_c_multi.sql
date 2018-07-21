INSERT INTO table_c (name, a_id, b_id)
VALUES {% for value in values %}
('{{ value.name }}', {{ value.a_id }}, {{ value.b_id }}){% if loop.index < values.size - 1 %},{% else %};{% endif %}
{% endfor %}

