-- Insert test data into Utilizatori
INSERT INTO Utilizatori (id, email, nr_telefon, Tip_utilizator, parola) VALUES 
(1, 'admin@spital.ro', '0712345678', 'A', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(2, 'medic1@spital.ro', '0723456789', 'M', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(3, 'medic2@spital.ro', '0734567890', 'M', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(4, 'ingrijitor1@spital.ro', '0745678901', 'I', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(5, 'ingrijitor2@spital.ro', '0756789012', 'I', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(6, 'supraveghetor1@spital.ro', '0767890123', 'S', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(7, 'supraveghetor2@spital.ro', '0778901234', 'S', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(8, 'pacient1@email.com', '0789012345', 'P', '$2a$10$7EqJtq98hPqEX7fNZaFWoO5rG1b6b6h1g6Z5j6YyF2F2F2F2F2F2F'),
(9, 'pacient2@email.com', '0790123456', 'P', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(10, 'pacient3@email.com', '0701234567', 'P', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(11, 'pacient4@email.com', '0712345670', 'P', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'),
(12, 'pacient5@email.com', '0723456780', 'P', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW');

-- Insert test data into Administratori
INSERT INTO Administratori (id) VALUES (1);

-- Insert test data into Medici
INSERT INTO Medici (id_medic, id) VALUES 
(1, 2),
(2, 3);

-- Insert test data into Ingrijitori
INSERT INTO Ingrijitori (id_ingrijitor, id) VALUES 
(1, 4),
(2, 5);

-- Insert test data into Supraveghetori
INSERT INTO Supraveghetori (id_supraveghetor, id) VALUES 
(1, 6),
(2, 7);

-- Insert test data into Pacienti
INSERT INTO Pacienti (id_pacient, id, localitate, strada, nr, CNP, profesie, id_medic, recomandari_de_la_medic, id_supraveghetor, id_ingrijitor, id_smartphone, path_rapoarte) VALUES 
(1, 8, 'Bucuresti', 'Strada Primariei', '10', '1900501123456', 'Profesor', 1, 'Odihna si vitamine, control saptamanal', 1, 1, 1001, '/rapoarte/pacient1/'),
(2, 9, 'Cluj', 'Bulevardul Independentei', '25', '1911215789012', 'Inginer', 1, 'Regim alimentar strict, evitarea efortului fizic', 1, 1, 1002, '/rapoarte/pacient2/'),
(3, 10, 'Timisoara', 'Strada Libertatii', '5', '2870302456789', 'Asistent', 2, 'Monitorizare continua a tensiunii, medicamente la ore fixe', 2, 2, 1003, '/rapoarte/pacient3/'),
(4, 11, 'Iasi', 'Strada Unirii', '15', '1930712345678', 'Contabil', 2, 'Oxigenoterapie, repaus la pat', 2, 2, 1004, '/rapoarte/pacient4/'),
(5, 12, 'Brasov', 'Strada Victoriei', '7', '2850825901234', 'Programator', 1, 'Hidratare abundenta, medicamente antiinflamatorii', 1, 2, 1005, '/rapoarte/pacient5/');

-- Insert test data into Semne_Vitale
INSERT INTO Semne_Vitale (id_pacient, puls, val_alarma_puls, tensiune_arteriala, val_alarma_tensiune, temperatura_corporala, val_alarma_temperatura, greutate, val_alarma_greutate, glicemie, val_alarma_glicemie, lumina, val_alarma_lumina, temperatura_ambientala, val_alarma_temperatura_amb, gaz, val_alarma_gaz, umiditate, val_alarma_umiditate, proximitate, val_alarma_proximitate) VALUES
(1, 72, 100, 120, 150, 36.8, 38.0, 75.5, 80.0, 95.0, 120.0, TRUE, FALSE, 22.5, 26.0, FALSE, TRUE, TRUE, FALSE, FALSE, TRUE),
(2, 68, 95, 135, 150, 36.5, 38.0, 82.3, 85.0, 110.0, 130.0, TRUE, FALSE, 23.0, 27.0, FALSE, TRUE, TRUE, FALSE, FALSE, TRUE),
(3, 75, 110, 145, 160, 37.1, 38.5, 65.7, 70.0, 105.0, 140.0, TRUE, FALSE, 21.5, 25.0, FALSE, TRUE, TRUE, FALSE, FALSE, TRUE),
(4, 80, 120, 130, 150, 36.9, 38.0, 90.2, 95.0, 130.0, 150.0, TRUE, FALSE, 22.0, 26.0, FALSE, TRUE, TRUE, FALSE, FALSE, TRUE),
(5, 70, 100, 125, 150, 36.7, 38.0, 71.8, 75.0, 92.0, 120.0, TRUE, FALSE, 23.5, 27.0, FALSE, TRUE, TRUE, FALSE, FALSE, TRUE);

-- Insert additional data points for patient 1 to show variation
INSERT INTO Semne_Vitale (id_pacient, puls, tensiune_arteriala, temperatura_corporala, greutate, glicemie, lumina, temperatura_ambientala, gaz, umiditate, proximitate) VALUES
(1, 75, 125, 36.9, 75.3, 97.0, TRUE, 22.8, FALSE, TRUE, FALSE),
(1, 73, 128, 37.0, 75.0, 98.5, TRUE, 22.0, FALSE, TRUE, FALSE),
(1, 71, 118, 36.7, 74.8, 94.0, TRUE, 23.0, FALSE, TRUE, FALSE);

-- Insert test data into Alarme
INSERT INTO Alarme (id_pacient, durata_alarmei, perioada_alarmei, alarma, detalii_alarma, avertizari) VALUES
(1, 30, 480, 'Medicatie', 'Administrare Paracetamol 500mg', 'A nu se administra pe stomacul gol'),
(1, 15, 720, 'Medicatie', 'Administrare Vitamina C 1000mg', 'A se administra cu apa'),
(2, 20, 360, 'Medicatie', 'Administrare Enalapril 10mg', 'A se verifica tensiunea inainte de administrare'),
(3, 45, 240, 'Oxigenoterapie', 'Oxigenoterapie 2L/min timp de 45 minute', 'A se mentine pozitia sezand'),
(4, 10, 180, 'Medicatie', 'Administrare Insulina 10UI', 'A se verifica glicemia inainte de administrare'),
(5, 25, 720, 'Medicatie', 'Administrare Omeprazol 20mg', 'A se administra dimineata, pe stomacul gol');

-- Insert test data into Istoric_Pacienti
INSERT INTO Istoric_Pacienti (id_pacient, diagnostice_tratamente, lista_alergii, recomandari_medicale, scheme_medicatie) VALUES
(1, 'Hipertensiune arteriala esentiala grad I, Diabet zaharat tip 2', 'Penicilina, Aspirina', 'Regim hiposodat, Evitarea efortului fizic intens', 'Concor 5mg 1-0-0, Metformin 850mg 1-0-1'),
(2, 'Insuficienta cardiaca clasa II NYHA, Fibrilatie atriala', 'Amoxicilina', 'Regim hiposodat, Monitorizare puls si TA, Evitarea efortului fizic', 'Digoxin 0.25mg 1-0-0, Furosemid 40mg 1-0-0, Warfarina 3mg 0-0-1'),
(3, 'BPOC, Hipertensiune arteriala secundara', 'Nu se cunosc', 'Evitarea fumatului, Evitarea expunerii la frig', 'Salbutamol 100mcg 2puf la 8 ore, Enalapril 10mg 1-0-1'),
(4, 'AVC ischemic in antecedente, HTA esentiala grad II', 'Sulfamide', 'Kinetoterapie zilnica, Evitarea stresului', 'Aspenter 75mg 0-1-0, Perindopril 10mg 1-0-0, Indapamida 2.5mg 1-0-0'),
(5, 'Diabet zaharat tip 1, Hipotiroidism', 'Iod, Polen', 'Automonitorizarea glicemiei, Regim alimentar strict', 'Insulina Lantus 12UI 0-0-1, Insulina NovoRapid dupa schema, Levotiroxina 50mcg 1-0-0'); 