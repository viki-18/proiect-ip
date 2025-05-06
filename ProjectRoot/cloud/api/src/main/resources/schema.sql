-- Drop tables if they exist (in reverse order to avoid foreign key constraint violations)
DROP TABLE IF EXISTS Istoric_Pacienti;
DROP TABLE IF EXISTS Alarme;
DROP TABLE IF EXISTS Semne_Vitale;
DROP TABLE IF EXISTS Pacienti;
DROP TABLE IF EXISTS Supraveghetori;
DROP TABLE IF EXISTS Ingrijitori;
DROP TABLE IF EXISTS Medici;
DROP TABLE IF EXISTS Administratori;
DROP TABLE IF EXISTS Utilizatori;

-- Create Utilizatori table
CREATE TABLE Utilizatori (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(30) UNIQUE NOT NULL,
    nr_telefon CHAR(12) UNIQUE NOT NULL,
    Tip_utilizator CHAR(1) NOT NULL,
    parola VARCHAR(30) NOT NULL
);

-- Create Administratori table
CREATE TABLE Administratori (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Utilizatori(id)
);

-- Create Medici table
CREATE TABLE Medici (
    id_medic INT AUTO_INCREMENT PRIMARY KEY,
    id INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Utilizatori(id)
);

-- Create Ingrijitori table
CREATE TABLE Ingrijitori (
    id_ingrijitor INT AUTO_INCREMENT PRIMARY KEY,
    id INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Utilizatori(id)
);

-- Create Supraveghetori table
CREATE TABLE Supraveghetori (
    id_supraveghetor INT AUTO_INCREMENT PRIMARY KEY,
    id INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Utilizatori(id)
);

-- Create Pacienti table
CREATE TABLE Pacienti (
    id_pacient INT AUTO_INCREMENT PRIMARY KEY,
    id INT NOT NULL,
    localitate VARCHAR(20),
    strada VARCHAR(30),
    nr VARCHAR(3),
    CNP CHAR(13) NOT NULL,
    profesie VARCHAR(20),
    id_medic INT,
    recomandari_de_la_medic VARCHAR(300),
    id_supraveghetor INT,
    id_ingrijitor INT,
    id_smartphone INT UNIQUE NOT NULL,
    path_rapoarte VARCHAR(50),
    FOREIGN KEY (id) REFERENCES Utilizatori(id),
    FOREIGN KEY (id_medic) REFERENCES Medici(id_medic),
    FOREIGN KEY (id_supraveghetor) REFERENCES Supraveghetori(id_supraveghetor),
    FOREIGN KEY (id_ingrijitor) REFERENCES Ingrijitori(id_ingrijitor)
);

-- Create Semne_Vitale table
CREATE TABLE Semne_Vitale (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pacient INT NOT NULL,
    puls INT,
    val_alarma_puls INT,
    tensiune_arteriala INT,
    val_alarma_tensiune INT,
    temperatura_corporala FLOAT,
    val_alarma_temperatura FLOAT,
    greutate FLOAT,
    val_alarma_greutate FLOAT,
    glicemie FLOAT,
    val_alarma_glicemie FLOAT,
    lumina BOOLEAN,
    val_alarma_lumina BOOLEAN,
    temperatura_ambientala FLOAT,
    val_alarma_temperatura_amb FLOAT,
    gaz BOOLEAN,
    val_alarma_gaz BOOLEAN,
    umiditate BOOLEAN,
    val_alarma_umiditate BOOLEAN,
    proximitate BOOLEAN,
    val_alarma_proximitate BOOLEAN,
    data_inregistrare TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pacient) REFERENCES Pacienti(id_pacient)
);

-- Create Alarme table
CREATE TABLE Alarme (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pacient INT NOT NULL,
    durata_alarmei INT NOT NULL,
    perioada_alarmei INT,
    alarma VARCHAR(50) NOT NULL,
    detalii_alarma VARCHAR(300),
    avertizari VARCHAR(200),
    data_creare TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pacient) REFERENCES Pacienti(id_pacient)
);

-- Create Istoric_Pacienti table
CREATE TABLE Istoric_Pacienti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pacient INT NOT NULL,
    diagnostice_tratamente VARCHAR(300),
    lista_alergii VARCHAR(200),
    recomandari_medicale VARCHAR(300),
    scheme_medicatie VARCHAR(300),
    data_adaugare TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pacient) REFERENCES Pacienti(id_pacient)
); 