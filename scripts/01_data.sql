-- 1. Insertion des Utilisateurs
-- Note : Les mots de passe sont ici en clair ou simulés. 
-- En production avec BCrypt, ils ressembleraient à : $2a$10$X.xyz...
INSERT INTO users (email, last_name, first_name, password, role, major) VALUES
('admin@eseo.fr', 'BOSS', 'Adam', 'admin123', 'ADMINISTRATOR', NULL),
('prof.durand@eseo.fr', 'DURAND', 'Marie', 'prof123', 'TEACHER', 'Informatique'),
('prof.martin@eseo.fr', 'MARTIN', 'Jean', 'prof123', 'TEACHER', 'Electronique'),
('etudiant.dupond@reseau.eseo.fr', 'DUPOND', 'Lucas', 'student123', 'STUDENT', 'Informatique'),
('etudiant.lefevre@reseau.eseo.fr', 'LEFEVRE', 'Emma', 'student123', 'STUDENT', 'Systèmes Embarqués');

-- 2. Insertion des Entreprises
INSERT INTO companies (siret, corporate_name, address, contact_email, contact_phone) VALUES
('12345678901234', 'Tech Solutions', '12 Rue de l''Innovation, Paris', 'hr@techsolutions.com', '0102030405'),
('98765432109876', 'ElectroCorp', '45 Avenue des Circuits, Angers', 'contact@electrocorp.fr', '0241000000');

-- 3. Insertion des Stages
-- Stage 1 : En cours (Informatique)
INSERT INTO internships (objective, start_date, duration_weeks, status, student_email, teacher_email, company_siret) VALUES
('Développement d''une API REST en Spring Boot', '2026-02-01', 16, 'ONGOING', 
 'etudiant.dupond@reseau.eseo.fr', 'prof.durand@eseo.fr', '12345678901234');

-- Stage 2 : Terminé (Systèmes Embarqués)
INSERT INTO internships (objective, start_date, duration_weeks, status, student_email, teacher_email, company_siret) VALUES
('Conception d''un driver pour capteur IoT', '2026-01-15', 20, 'COMPLETED', 
 'etudiant.lefevre@reseau.eseo.fr', 'prof.martin@eseo.fr', '98765432109876');

-- 4. Insertion des Rapports
-- Un rapport lié au stage 2 (Emma Lefevre)
INSERT INTO reports (file_name, storage_path, internship_id) VALUES
('rapport_lefevre_v1.pdf', '/uploads/reports/2026/rapport_lefevre_v1.pdf', 2);

-- 5. Insertion des Evaluations
-- Note pour le rapport d'Emma Lefevre par le Prof Martin
INSERT INTO evaluations (grade, comment, report_file_name, teacher_email) VALUES
(17.5, 'Excellent travail technique sur la gestion des interruptions.', 'rapport_lefevre_v1.pdf', 'prof.martin@eseo.fr');

-- 6. Insertion des Soutenances
-- Soutenance prévue pour Lucas Dupond (Note: Correction du type FK student_email dans ton script ci-dessous)
INSERT INTO defenses (`date`, room, student_email) VALUES
('2026-06-15 14:00:00', 'Amphi A', 'etudiant.dupond@reseau.eseo.fr');
