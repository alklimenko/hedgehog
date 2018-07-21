INSERT INTO table_b (name, a_id)
VALUES {% for value in values %}
('{{ value.name }}', {{ value.a_id }}){% if loop.index < values.size - 1 %},{% else %};{% endif %}
{% endfor %}