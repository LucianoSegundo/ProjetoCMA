
INSERT INTO papel (autoridade)
SELECT 'aluno' WHERE NOT EXISTS (SELECT 1 FROM papel WHERE autoridade = 'aluno');

INSERT INTO papel (autoridade)
SELECT 'professor' WHERE NOT EXISTS (SELECT 1 FROM papel WHERE autoridade = 'professor');

INSERT INTO papel (autoridade)
SELECT 'administrador' WHERE NOT EXISTS (SELECT 1 FROM papel WHERE autoridade = 'administrador');

