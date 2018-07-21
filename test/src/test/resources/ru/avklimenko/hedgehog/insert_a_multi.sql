INSERT INTO table_a (name)
VALUES {% for name in names %}
('{{ name }}'){% if loop.index < names.size - 1 %},{% else %};{% endif %}
{% endfor %}