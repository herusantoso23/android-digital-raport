-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 28, 2018 at 12:02 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_digital_raport`
--

-- --------------------------------------------------------

--
-- Table structure for table `jadwal`
--

CREATE TABLE `jadwal` (
  `idKelas` int(11) NOT NULL,
  `idMapel` int(11) NOT NULL,
  `waktu` varchar(45) DEFAULT NULL,
  `idLecturer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jadwal`
--

INSERT INTO `jadwal` (`idKelas`, `idMapel`, `waktu`, `idLecturer`) VALUES
(1, 1, 'SENIN, 10.00-13.00', 2),
(1, 2, 'SENIN, 07.00-10.00', 1),
(1, 3, 'SELASA, 07.00-10.00', 2),
(2, 1, 'SENIN, 07.00-10.00', 2),
(2, 2, 'SENIN, 10.00-13.00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `kelas`
--

CREATE TABLE `kelas` (
  `id` int(11) NOT NULL,
  `jurusan` varchar(45) DEFAULT NULL,
  `namaKelas` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kelas`
--

INSERT INTO `kelas` (`id`, `jurusan`, `namaKelas`) VALUES
(1, 'TSM', '12 TSM 1'),
(2, 'TKJ', '12 TKJ 1');

-- --------------------------------------------------------

--
-- Table structure for table `lecturer`
--

CREATE TABLE `lecturer` (
  `id` int(11) NOT NULL,
  `nip` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `mapel` varchar(100) NOT NULL,
  `noHp` varchar(15) NOT NULL,
  `alamat` varchar(256) NOT NULL,
  `photo` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lecturer`
--

INSERT INTO `lecturer` (`id`, `nip`, `nama`, `mapel`, `noHp`, `alamat`, `photo`) VALUES
(1, '311410119', 'Heru Santoso', 'Bahasa Inggris', '087760586526', 'Perum Mega', 'digital_raport/uploads/311410120.jpg'),
(2, '1212331313', 'Guru A', 'Matematika', '1231231', 'sadasdasd', 'digital_raport/uploads/tut-wuri-handayani.png');

-- --------------------------------------------------------

--
-- Table structure for table `mapel`
--

CREATE TABLE `mapel` (
  `id` int(11) NOT NULL,
  `kodeMapel` varchar(45) DEFAULT NULL,
  `namaMapel` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mapel`
--

INSERT INTO `mapel` (`id`, `kodeMapel`, `namaMapel`) VALUES
(1, 'M001', 'Bahasa Inggris'),
(2, 'M002', 'Matematika'),
(3, 'B002', 'Bahasa Indonesia'),
(4, 'A001', 'Pendidikan Agama Islam');

-- --------------------------------------------------------

--
-- Table structure for table `penilaian`
--

CREATE TABLE `penilaian` (
  `id_raport` int(11) NOT NULL,
  `id_mapel` int(11) NOT NULL,
  `uts` double DEFAULT NULL,
  `uas` double DEFAULT NULL,
  `tugas` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penilaian`
--

INSERT INTO `penilaian` (`id_raport`, `id_mapel`, `uts`, `uas`, `tugas`) VALUES
(1, 1, 90, 80, 80),
(1, 2, 80, 90, 70),
(1, 3, 80, 90, 70),
(1, 4, 90, 80, 90);

-- --------------------------------------------------------

--
-- Table structure for table `profile_sekolah`
--

CREATE TABLE `profile_sekolah` (
  `id` int(11) NOT NULL,
  `nama` varchar(256) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `telp` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `fax` varchar(20) NOT NULL,
  `gambar` varchar(256) NOT NULL,
  `jumlah_siswa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `profile_sekolah`
--

INSERT INTO `profile_sekolah` (`id`, `nama`, `alamat`, `telp`, `email`, `fax`, `gambar`, `jumlah_siswa`) VALUES
(1, 'SMKN 1 CIKARANG', 'Jalan Raya Cibarusah', '087760586526', 'admin@smkncikarang.com', '', 'digital_raport/uploads/profilesekolah.jpg', 378);

-- --------------------------------------------------------

--
-- Table structure for table `raport`
--

CREATE TABLE `raport` (
  `id` int(11) NOT NULL,
  `id_siswa` int(11) NOT NULL,
  `id_semester` int(11) NOT NULL,
  `tahun_ajaran` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `raport`
--

INSERT INTO `raport` (`id`, `id_siswa`, `id_semester`, `tahun_ajaran`) VALUES
(1, 1, 1, '2017/2018');

-- --------------------------------------------------------

--
-- Table structure for table `semester`
--

CREATE TABLE `semester` (
  `id` int(11) NOT NULL,
  `semester` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `semester`
--

INSERT INTO `semester` (`id`, `semester`) VALUES
(1, '1'),
(2, '2'),
(3, '3'),
(4, '4'),
(5, '5'),
(6, '6'),
(7, '7'),
(8, '8');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nis` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kelas` varchar(100) NOT NULL,
  `noHp` varchar(15) NOT NULL,
  `alamat` varchar(256) NOT NULL,
  `password` varchar(100) NOT NULL,
  `photo` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nis`, `nama`, `kelas`, `noHp`, `alamat`, `password`, `photo`) VALUES
(1, '311410119', 'Heru Santoso', 'TI 14 C 1', '087760586526', 'Perum Mega Regency', 'e10adc3949ba59abbe56e057f20f883e', 'digital_raport/uploads/311410119.jpg'),
(2, '311410120', 'Heru Santoso', 'TI 14 B 8', '087760586526', 'Perum Mega Regency', 'e10adc3949ba59abbe56e057f20f883e', 'digital_raport/uploads/311410120.jpg'),
(3, '311410121', 'Heru Santoso', 'TI 14 C 1', '087760586526', 'Perum Mega Regency', 'e10adc3949ba59abbe56e057f20f883e', 'digital_raport/uploads/311410121.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jadwal`
--
ALTER TABLE `jadwal`
  ADD PRIMARY KEY (`idKelas`,`idMapel`,`idLecturer`),
  ADD KEY `fk_kelas_has_mapel_mapel1_idx` (`idMapel`),
  ADD KEY `fk_kelas_has_mapel_kelas_idx` (`idKelas`),
  ADD KEY `fk_jadwal_lecturer1_idx` (`idLecturer`);

--
-- Indexes for table `kelas`
--
ALTER TABLE `kelas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lecturer`
--
ALTER TABLE `lecturer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mapel`
--
ALTER TABLE `mapel`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `penilaian`
--
ALTER TABLE `penilaian`
  ADD PRIMARY KEY (`id_raport`,`id_mapel`),
  ADD KEY `fk_raport_has_mapel_mapel1_idx` (`id_mapel`),
  ADD KEY `fk_raport_has_mapel_raport1_idx` (`id_raport`);

--
-- Indexes for table `profile_sekolah`
--
ALTER TABLE `profile_sekolah`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `raport`
--
ALTER TABLE `raport`
  ADD PRIMARY KEY (`id`,`id_semester`),
  ADD KEY `fk_raport_user1_idx` (`id_siswa`),
  ADD KEY `fk_raport_semester1_idx` (`id_semester`);

--
-- Indexes for table `semester`
--
ALTER TABLE `semester`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kelas`
--
ALTER TABLE `kelas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `lecturer`
--
ALTER TABLE `lecturer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `mapel`
--
ALTER TABLE `mapel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `profile_sekolah`
--
ALTER TABLE `profile_sekolah`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `raport`
--
ALTER TABLE `raport`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `semester`
--
ALTER TABLE `semester`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `jadwal`
--
ALTER TABLE `jadwal`
  ADD CONSTRAINT `fk_jadwal_lecturer1` FOREIGN KEY (`idLecturer`) REFERENCES `lecturer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_kelas_has_mapel_kelas` FOREIGN KEY (`idKelas`) REFERENCES `kelas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_kelas_has_mapel_mapel1` FOREIGN KEY (`idMapel`) REFERENCES `mapel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `penilaian`
--
ALTER TABLE `penilaian`
  ADD CONSTRAINT `fk_raport_has_mapel_mapel1` FOREIGN KEY (`id_mapel`) REFERENCES `mapel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_raport_has_mapel_raport1` FOREIGN KEY (`id_raport`) REFERENCES `raport` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `raport`
--
ALTER TABLE `raport`
  ADD CONSTRAINT `fk_raport_semester1` FOREIGN KEY (`id_semester`) REFERENCES `semester` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_raport_user1` FOREIGN KEY (`id_siswa`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
