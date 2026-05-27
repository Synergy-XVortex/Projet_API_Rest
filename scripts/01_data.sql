-- ==============================================================================
-- DATABASE INITIALIZATION SCRIPT (TESTING DATA)
-- ==============================================================================
-- Passwords:
-- Admin: admin123
-- Teacher: prof1234
-- Student: student123
-- ==============================================================================

-- 1. Insert Users (Admins, Teachers, Students)
INSERT INTO users (email, last_name, first_name, password, role, major, is_active) VALUES
-- Admins (2)
('admin@eseo.fr', 'ADMIN', 'Super', '$2a$10$Tj8YNHLA1GNOSiWJvKtkjOopSgfc6jsMTwsc60DwS0wEyjibXwsTW', 'ADMINISTRATOR', NULL, true),
('admin2@eseo.fr', 'SYSTEM', 'Root', '$2a$10$Tj8YNHLA1GNOSiWJvKtkjOopSgfc6jsMTwsc60DwS0wEyjibXwsTW', 'ADMINISTRATOR', NULL, true),

-- Teachers (10)
('prof.durand@eseo.fr', 'DURAND', 'Marie', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Informatique', true),
('prof.martin@eseo.fr', 'MARTIN', 'Jean', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Electronique', true),
('prof.petit@eseo.fr', 'PETIT', 'Alain', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Data Science', true),
('prof.robert@eseo.fr', 'ROBERT', 'Sophie', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Cybersecurite', true),
('prof.richard@eseo.fr', 'RICHARD', 'Luc', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Systemes Embarques', true),
('prof.simon@eseo.fr', 'SIMON', 'Claire', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Informatique', true),
('prof.michel@eseo.fr', 'MICHEL', 'Paul', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Electronique', false), -- Inactive teacher
('prof.leroy@eseo.fr', 'LEROY', 'Julie', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Data Science', true),
('prof.roux@eseo.fr', 'ROUX', 'Antoine', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Cybersecurite', true),
('prof.moreau@eseo.fr', 'MOREAU', 'Celine', '$2a$10$XHGfJE.OlsKCFR0RUe4u3OehZu297DXc8D0LaVPU8g0rDLnNqdzae', 'TEACHER', 'Systemes Embarques', true),

-- Students (20)
('etudiant.dupond@reseau.eseo.fr', 'DUPOND', 'Lucas', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Informatique', true),
('etudiant.lefevre@reseau.eseo.fr', 'LEFEVRE', 'Emma', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Systemes Embarques', true),
('etudiant.bernard@reseau.eseo.fr', 'BERNARD', 'Hugo', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Data Science', true),
('etudiant.thomas@reseau.eseo.fr', 'THOMAS', 'Lea', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Cybersecurite', true),
('etudiant.dubois@reseau.eseo.fr', 'DUBOIS', 'Leo', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Informatique', true),
('etudiant.fontaine@reseau.eseo.fr', 'FONTAINE', 'Chloe', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Electronique', true),
('etudiant.rousseau@reseau.eseo.fr', 'ROUSSEAU', 'Gabin', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Systemes Embarques', true),
('etudiant.vincent@reseau.eseo.fr', 'VINCENT', 'Manon', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Data Science', false), -- Inactive student
('etudiant.muller@reseau.eseo.fr', 'MULLER', 'Arthur', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Cybersecurite', true),
('etudiant.garcia@reseau.eseo.fr', 'GARCIA', 'Camille', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Informatique', true),
('etudiant.mathieu@reseau.eseo.fr', 'MATHIEU', 'Louis', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Electronique', true),
('etudiant.blanc@reseau.eseo.fr', 'BLANC', 'Juliette', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Systemes Embarques', true),
('etudiant.gauthier@reseau.eseo.fr', 'GAUTHIER', 'Raphael', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Data Science', true),
('etudiant.morin@reseau.eseo.fr', 'MORIN', 'Sarah', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Cybersecurite', true),
('etudiant.perrin@reseau.eseo.fr', 'PERRIN', 'Nathan', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Informatique', true),
('etudiant.clement@reseau.eseo.fr', 'CLEMENT', 'Zoe', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Electronique', true),
('etudiant.girard@reseau.eseo.fr', 'GIRARD', 'Paul', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Systemes Embarques', true),
('etudiant.bonnet@reseau.eseo.fr', 'BONNET', 'Eva', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Data Science', true),
('etudiant.masson@reseau.eseo.fr', 'MASSON', 'Victor', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Cybersecurite', true),
('etudiant.colin@reseau.eseo.fr', 'COLIN', 'Mia', '$2a$10$QgltEc89xP/LSqrTdqjGy.wYAcAR8VmqUvFBpjxT0e1wPc/b7OBX2', 'STUDENT', 'Informatique', true);


-- 2. Insert Companies
INSERT INTO companies (siret, corporate_name, address, contact_email, contact_phone) VALUES
('12345678901234', 'Tech Solutions', '12 Rue de l''Innovation, Paris', 'hr@techsolutions.com', '0102030405'),
('98765432109876', 'ElectroCorp', '45 Avenue des Circuits, Angers', 'contact@electrocorp.fr', '0241000000'),
('11111111111111', 'Data Factory', '8 Boulevard des Algorithmes, Lyon', 'recrutement@datafactory.io', '0405060708'),
('22222222222222', 'CyberShield', '10 Rue de la Securite, Rennes', 'jobs@cybershield.net', '0299887766'),
('33333333333333', 'AutoDrive IoT', '50 Route de Toulouse, Bordeaux', 'contact@autodrive.fr', '0556677889'),
('44444444444444', 'WebNova', '5 Rue du Code, Lille', 'hello@webnova.com', '0320112233'),
('55555555555555', 'Green Energy Tech', '120 Avenue de l''Ecologie, Nantes', 'rh@greenenergy.fr', '0240123456'),
('66666666666666', 'Quantum Computing', '15 Place de la Machine, Grenoble', 'careers@quantum.com', '0555666777'),
('77777777777777', 'AeroSpace Dev', '1 Boulevard de l''Espace, Toulouse', 'stage@aerospace.fr', '0534223344'),
('88888888888888', 'HealthTech AI', '88 Rue de la Sante, Marseille', 'contact@healthtech.ai', '0491001122');


-- 3. Insert Internships (Covering all states)
-- (Explicit IDs used to safely link reports later)
INSERT INTO internships (id, objective, start_date, duration_weeks, status, student_email, teacher_email, company_siret) VALUES
-- Case 1: Standard ONGOING
(1, 'Developpement d''une API REST en Spring Boot', '2026-02-01', 16, 'ONGOING', 'etudiant.dupond@reseau.eseo.fr', 'prof.durand@eseo.fr', '12345678901234'),
-- Case 2: COMPLETED, Evaluated and Validated
(2, 'Conception d''un driver pour capteur IoT', '2026-01-15', 20, 'VALIDATED', 'etudiant.lefevre@reseau.eseo.fr', 'prof.martin@eseo.fr', '98765432109876'),
-- Case 3: ONGOING but NO Teacher assigned yet
(3, 'Analyse de donnees massives', '2026-04-01', 12, 'ONGOING', 'etudiant.bernard@reseau.eseo.fr', NULL, '11111111111111'),
-- Case 4: COMPLETED, Report uploaded, waiting for teacher evaluation
(4, 'Audit de securite et pentesting', '2025-09-01', 24, 'COMPLETED', 'etudiant.thomas@reseau.eseo.fr', 'prof.robert@eseo.fr', '22222222222222'),
-- Case 5: VALIDATED
(5, 'Creation d''un dashboard React', '2025-10-01', 16, 'VALIDATED', 'etudiant.dubois@reseau.eseo.fr', 'prof.simon@eseo.fr', '44444444444444'),
-- Case 6: REJECTED (Bad evaluation)
(6, 'Developpement firmware STM32', '2025-09-15', 12, 'REJECTED', 'etudiant.fontaine@reseau.eseo.fr', 'prof.richard@eseo.fr', '33333333333333'),
-- Case 7: ONGOING with future defense
(7, 'Optimisation d''algorithmes embarques', '2026-03-01', 20, 'ONGOING', 'etudiant.rousseau@reseau.eseo.fr', 'prof.moreau@eseo.fr', '77777777777777'),
-- Case 8: COMPLETED
(8, 'Developpement IA Sante', '2025-11-01', 24, 'COMPLETED', 'etudiant.vincent@reseau.eseo.fr', 'prof.leroy@eseo.fr', '88888888888888'),
-- Case 9: VALIDATED with excellent grade
(9, 'Mise en place infrastructure Cloud', '2025-08-01', 24, 'VALIDATED', 'etudiant.muller@reseau.eseo.fr', 'prof.roux@eseo.fr', '11111111111111'),
-- Case 10: ONGOING
(10, 'Maintenance d''un parc serveur', '2026-05-01', 12, 'ONGOING', 'etudiant.garcia@reseau.eseo.fr', 'prof.durand@eseo.fr', '66666666666666');

-- Note: etudiant.mathieu, etudiant.blanc, etudiant.gauthier, etudiant.morin, etudiant.perrin, 
-- etudiant.clement, etudiant.girard, etudiant.bonnet, etudiant.masson, etudiant.colin 
-- DO NOT have an internship assigned yet (Status: Searching).


-- 4. Insert Reports (Files uploaded by students)
INSERT INTO reports (file_name, storage_path, internship_id) VALUES
('rapport_lefevre_v1.pdf', '/uploads/reports/2026/rapport_lefevre_v1.pdf', 2),
('rapport_thomas_final.pdf', '/uploads/reports/2026/rapport_thomas_final.pdf', 4),
('rapport_dubois_react.pdf', '/uploads/reports/2026/rapport_dubois_react.pdf', 5),
('rapport_fontaine_echec.pdf', '/uploads/reports/2026/rapport_fontaine_echec.pdf', 6),
('rapport_vincent_ia.pdf', '/uploads/reports/2026/rapport_vincent_ia.pdf', 8),
('rapport_muller_cloud.pdf', '/uploads/reports/2026/rapport_muller_cloud.pdf', 9);


-- 5. Insert Evaluations (Grades given by teachers)
INSERT INTO evaluations (grade, comment, report_file_name, teacher_email) VALUES
(17.5, 'Excellent travail technique sur la gestion des interruptions.', 'rapport_lefevre_v1.pdf', 'prof.martin@eseo.fr'),
(15.0, 'Bon travail sur le frontend, code propre.', 'rapport_dubois_react.pdf', 'prof.simon@eseo.fr'),
(7.5, 'Rapport incomplet, objectifs non atteints.', 'rapport_fontaine_echec.pdf', 'prof.richard@eseo.fr'),
(19.0, 'Exceptionnel. Architecture tres robuste.', 'rapport_muller_cloud.pdf', 'prof.roux@eseo.fr');


-- 6. Insert Defenses (Past and Upcoming)
INSERT INTO defenses (`date`, room, student_email) VALUES
-- Upcoming
('2026-06-15 14:00:00', 'Amphi A', 'etudiant.dupond@reseau.eseo.fr'),
('2026-06-16 09:30:00', 'Salle B204', 'etudiant.rousseau@reseau.eseo.fr'),
('2026-06-20 10:00:00', 'Salle C101', 'etudiant.garcia@reseau.eseo.fr'),
-- Past
('2026-02-10 11:00:00', 'Amphi B', 'etudiant.lefevre@reseau.eseo.fr'),
('2026-02-12 15:00:00', 'Salle A102', 'etudiant.dubois@reseau.eseo.fr'),
('2026-02-15 16:30:00', 'Salle B205', 'etudiant.fontaine@reseau.eseo.fr');