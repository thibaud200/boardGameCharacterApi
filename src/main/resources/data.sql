-- ================================
-- Données de base : jeux
-- ================================
INSERT INTO games (id, title, description, release_year)
VALUES
  (1, 'Citadels', 'Un jeu de stratégie médiéval', 2016),
  (2, 'Dark Souls Board Game', 'Jeu de plateau inspiré du jeu vidéo', 2020);

-- ================================
-- Types de personnages
-- ================================
INSERT INTO types (id, name, description)
VALUES
  (1, 'Assassin', 'Personnage rapide et furtif'),
  (2, 'Guerrier', 'Spécialiste du combat rapproché'),
  (3, 'Mage', 'Utilise la magie à distance');

-- ================================
-- Compétences
-- ================================
INSERT INTO skills (id, name, description)
VALUES
  (1, 'Stealth', 'Permet d’être invisible pendant un tour'),
  (2, 'Double Strike', 'Peut attaquer deux fois'),
  (3, 'Fireball', 'Lance une boule de feu infligeant des dégâts de zone');

-- ================================
-- Personnages
-- ================================
INSERT INTO characters (id, name, description, game_id, type_id)
VALUES
  (1, 'Shadowblade', 'Assassin maître du camouflage', 1, 1),
  (2, 'Thorgar', 'Guerrier redoutable au marteau de guerre', 1, 2),
  (3, 'Eldrin', 'Mage capable de contrôler le feu', 1, 3);

-- ================================
-- Association personnages <-> skills
-- ================================
INSERT INTO characters_skills (character_id, skill_id)
VALUES
  (1, 1),  -- Shadowblade → Stealth
  (2, 2),  -- Thorgar → Double Strike
  (3, 3);  -- Eldrin → Fireball
