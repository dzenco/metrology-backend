-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : mar. 13 août 2024 à 12:56
-- Version du serveur : 8.0.39-0ubuntu0.22.04.1
-- Version de PHP : 8.1.2-1ubuntu2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `metrology_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `parm_mesure`
--

CREATE TABLE `parm_mesure` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `date_mesure` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `debit` float NOT NULL,
  `pression` float NOT NULL,
  `temperature` float NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `site_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `parm_mesure`
--

INSERT INTO `parm_mesure` (`id`, `created_at`, `date_mesure`, `debit`, `pression`, `temperature`, `updated_at`, `site_id`) VALUES
(1, '2024-07-05 15:55:29.919202', '09-08-2024', 25, 0.8, 28, '2024-08-08 12:09:29.919404', 1),
(2, '2024-08-08 12:23:50.935729', '11-08-2024', 24.7, 1.1, 27.8, '2024-08-08 12:23:50.935851', 3),
(3, '2024-08-08 12:24:11.129570', '12-07-2024', 22, 0.95, 30.1, '2024-08-08 12:24:11.129660', 2),
(5, '2024-08-11 10:39:01.999393', '14-03-2024', 26.3, 0.87, 29.2, '2024-08-11 10:39:01.999556', 5),
(6, '2024-08-11 10:39:50.259212', '14-01-2024', 30.3, 2.87, 36.2, '2024-08-11 10:39:50.259281', 6),
(9, '2024-08-13 09:38:47.206523', '13-08-2024', 30.3, 2.87, 36.2, '2024-08-13 09:38:47.206605', 1);

-- --------------------------------------------------------

--
-- Structure de la table `sites`
--

CREATE TABLE `sites` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name_site` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `num_site` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `has_reported` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `sites`
--

INSERT INTO `sites` (`id`, `created_at`, `latitude`, `longitude`, `name_site`, `num_site`, `updated_at`, `has_reported`) VALUES
(1, '2024-08-08 09:42:27.741134', 29.625485, 78.821091, 'site1', '0001', '2024-08-13 09:47:00.182118', b'1'),
(2, '2024-08-08 09:15:52.539418', 28.625293, 82.817926, 'site2', '0002', '2024-08-13 09:47:00.139960', b'1'),
(3, '2024-08-08 09:16:16.150035', 30.625182, 79.81464, 'site3', '0003', '2024-08-13 09:47:00.060745', b'1'),
(5, '2024-08-08 09:16:59.292275', 28.625043, 81.810135, 'site4', '0004', '2024-08-13 09:47:00.153915', b'0'),
(6, '2024-08-08 09:17:11.801756', 31.625043, 80.810135, 'site5', '0005', '2024-08-13 09:47:00.168437', b'0');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `telephone`, `email`, `password`, `role`, `status`, `created_at`, `updated_at`) VALUES
(1, 'Admin', 'Admin', '74732249', 'admin@gmail.com', '$2a$10$Qk68wkTe7KjWAjbT4KVi6OOIs0TleI4iwVBlIVgmn5KPlL8qNgVMO', 'admin', 'true', '2024-08-07 21:14:09.253552', '2024-08-07 21:14:09.253625'),
(2, 'User1', 'User1', '73509052', 'user1@gmail.com', '$2a$10$GQImjMGW6IuVM3DqMf.nXeNLpAzFTriUmtdRozM25EXSmgj4j5P7S', 'user', 'false', '2024-08-07 21:43:31.923250', '2024-08-07 21:56:46.620817');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `parm_mesure`
--
ALTER TABLE `parm_mesure`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjnfkfr0hv8h74dfmal6kln2ry` (`site_id`);

--
-- Index pour la table `sites`
--
ALTER TABLE `sites`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKtdpoqrhmtfn39jgj4o6ry8a7g` (`num_site`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `parm_mesure`
--
ALTER TABLE `parm_mesure`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `sites`
--
ALTER TABLE `sites`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `parm_mesure`
--
ALTER TABLE `parm_mesure`
  ADD CONSTRAINT `FKjnfkfr0hv8h74dfmal6kln2ry` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
