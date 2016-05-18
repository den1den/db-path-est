-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 18, 2016 at 01:30 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dbt`
--

-- --------------------------------------------------------

--
-- Table structure for table `e`
--

CREATE TABLE IF NOT EXISTS `e` (
  `id` int(11) NOT NULL,
  `source` int(11) NOT NULL,
  `label` int(11) NOT NULL,
  `target` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `e`
--

INSERT INTO `e` (`id`, `source`, `label`, `target`) VALUES
(0, 0, 5, 450),
(1, 1, 5, 82),
(2, 3, 0, 626),
(3, 3, 0, 502),
(4, 3, 0, 626),
(5, 3, 0, 544),
(6, 3, 0, 726),
(7, 3, 0, 583),
(8, 3, 0, 622),
(9, 3, 0, 649),
(10, 3, 0, 508),
(11, 3, 5, 14),
(12, 4, 0, 571),
(13, 4, 5, 178),
(14, 6, 0, 620),
(15, 7, 5, 49),
(16, 7, 5, 465),
(17, 9, 5, 113),
(18, 9, 5, 167),
(19, 9, 5, 387),
(20, 10, 0, 579),
(21, 10, 0, 635),
(22, 10, 5, 221),
(23, 12, 5, 113),
(24, 12, 5, 302),
(25, 14, 5, 370),
(26, 15, 5, 387),
(27, 20, 5, 459),
(28, 22, 5, 419),
(29, 24, 0, 577),
(30, 24, 5, 438),
(31, 25, 5, 434),
(32, 27, 0, 749),
(33, 27, 0, 516),
(34, 27, 0, 751),
(35, 27, 0, 753),
(36, 27, 0, 700),
(37, 27, 0, 671),
(38, 27, 0, 530),
(39, 27, 0, 665),
(40, 27, 0, 581),
(41, 27, 0, 511),
(42, 27, 0, 661),
(43, 27, 0, 582),
(44, 27, 0, 672),
(45, 27, 0, 588),
(46, 27, 0, 778),
(47, 27, 0, 695),
(48, 27, 0, 546),
(49, 27, 0, 769),
(50, 27, 0, 530),
(51, 27, 0, 760),
(52, 27, 0, 751),
(53, 27, 0, 696),
(54, 27, 0, 723),
(55, 27, 0, 614),
(56, 27, 0, 667),
(57, 27, 0, 539),
(58, 27, 0, 748),
(59, 27, 0, 678),
(60, 27, 0, 526),
(61, 27, 0, 698),
(62, 27, 0, 662),
(63, 27, 0, 779),
(64, 27, 0, 591),
(65, 27, 0, 667),
(66, 27, 0, 601),
(67, 27, 0, 712),
(68, 27, 0, 707),
(69, 27, 0, 584),
(70, 27, 0, 718),
(71, 27, 0, 513),
(72, 27, 0, 778),
(73, 27, 0, 577),
(74, 27, 0, 761),
(75, 27, 5, 86),
(76, 28, 5, 465),
(77, 29, 5, 363),
(78, 30, 5, 95),
(79, 31, 5, 353),
(80, 32, 5, 179),
(81, 32, 5, 195),
(82, 36, 0, 665),
(83, 36, 0, 647),
(84, 36, 0, 622),
(85, 36, 0, 595),
(86, 36, 0, 586),
(87, 36, 5, 291),
(88, 37, 5, 63),
(89, 39, 5, 94),
(90, 39, 5, 282),
(91, 40, 5, 224),
(92, 41, 5, 489),
(93, 44, 5, 39),
(94, 44, 5, 360),
(95, 45, 0, 637),
(96, 45, 0, 521),
(97, 45, 5, 240),
(98, 45, 5, 253),
(99, 46, 5, 282),
(100, 48, 5, 179),
(101, 49, 0, 740),
(102, 49, 0, 656),
(103, 49, 0, 635),
(104, 49, 0, 693),
(105, 49, 0, 675),
(106, 49, 0, 782),
(107, 49, 0, 565),
(108, 49, 0, 681),
(109, 49, 0, 603),
(110, 51, 5, 5),
(111, 52, 0, 576),
(112, 52, 0, 548),
(113, 52, 0, 539),
(114, 53, 5, 241),
(115, 53, 5, 282),
(116, 56, 5, 94),
(117, 56, 5, 224),
(118, 56, 5, 400),
(119, 56, 5, 465),
(120, 58, 5, 217),
(121, 59, 5, 291),
(122, 60, 5, 122),
(123, 61, 0, 715),
(124, 63, 0, 661),
(125, 63, 0, 670),
(126, 63, 0, 739),
(127, 63, 0, 623),
(128, 63, 0, 590),
(129, 63, 0, 672),
(130, 64, 0, 526),
(131, 64, 0, 715),
(132, 64, 0, 680),
(133, 64, 5, 254),
(134, 66, 5, 111),
(135, 66, 5, 251),
(136, 67, 5, 337),
(137, 67, 5, 363),
(138, 68, 0, 721),
(139, 68, 5, 43),
(140, 69, 5, 335),
(141, 70, 5, 112),
(142, 72, 5, 174),
(143, 74, 5, 434),
(144, 76, 5, 114),
(145, 77, 0, 647),
(146, 77, 5, 224),
(147, 77, 5, 254),
(148, 78, 0, 661),
(149, 78, 0, 574),
(150, 80, 5, 179),
(151, 80, 5, 384),
(152, 82, 5, 353),
(153, 82, 5, 400),
(154, 83, 5, 79),
(155, 84, 5, 353),
(156, 86, 0, 595),
(157, 89, 5, 465),
(158, 90, 5, 347),
(159, 90, 5, 400),
(160, 90, 5, 418),
(161, 91, 0, 507),
(162, 91, 0, 772),
(163, 91, 5, 63),
(164, 91, 5, 434),
(165, 92, 0, 558),
(166, 92, 0, 659),
(167, 92, 0, 501),
(168, 92, 0, 749),
(169, 92, 0, 583),
(170, 92, 0, 607),
(171, 92, 0, 587),
(172, 96, 0, 675),
(173, 96, 5, 467),
(174, 98, 0, 633),
(175, 98, 5, 63),
(176, 99, 5, 282),
(177, 100, 0, 602),
(178, 100, 0, 769),
(179, 100, 5, 40),
(180, 102, 0, 671),
(181, 102, 5, 229),
(182, 103, 0, 739),
(183, 103, 5, 434),
(184, 104, 5, 282),
(185, 104, 5, 465),
(186, 105, 0, 639),
(187, 105, 0, 549),
(188, 106, 0, 557),
(189, 106, 5, 302),
(190, 107, 5, 409),
(191, 108, 0, 516),
(192, 108, 5, 95),
(193, 109, 5, 438),
(194, 111, 5, 221),
(195, 113, 5, 434),
(196, 115, 0, 784),
(197, 115, 0, 758),
(198, 115, 0, 765),
(199, 115, 0, 503),
(200, 115, 0, 576),
(201, 115, 0, 677),
(202, 115, 0, 772),
(203, 115, 0, 788),
(204, 115, 5, 174),
(205, 115, 5, 192),
(206, 116, 5, 434),
(207, 117, 0, 642),
(208, 117, 5, 434),
(209, 119, 5, 95),
(210, 121, 0, 522),
(211, 121, 0, 637),
(212, 121, 0, 543),
(213, 121, 0, 784),
(214, 121, 0, 652),
(215, 123, 0, 682),
(216, 124, 5, 174),
(217, 125, 5, 488),
(218, 126, 0, 608),
(219, 126, 5, 114),
(220, 130, 5, 219),
(221, 132, 0, 511),
(222, 132, 5, 181),
(223, 132, 5, 259),
(224, 133, 0, 589),
(225, 134, 0, 566),
(226, 134, 5, 174),
(227, 134, 5, 282),
(228, 136, 5, 1),
(229, 137, 5, 114),
(230, 138, 5, 465),
(231, 139, 5, 19),
(232, 139, 5, 73),
(233, 142, 0, 594),
(234, 142, 5, 192),
(235, 142, 5, 373),
(236, 143, 5, 282),
(237, 146, 5, 434),
(238, 148, 5, 181),
(239, 154, 0, 755),
(240, 154, 5, 95),
(241, 155, 5, 402),
(242, 156, 0, 789),
(243, 156, 0, 756),
(244, 156, 5, 282),
(245, 157, 0, 534),
(246, 158, 5, 282),
(247, 159, 5, 181),
(248, 159, 5, 465),
(249, 159, 5, 489),
(250, 162, 5, 455),
(251, 165, 5, 114),
(252, 165, 5, 489),
(253, 166, 5, 63),
(254, 166, 5, 323),
(255, 167, 0, 649),
(256, 167, 0, 532),
(257, 167, 0, 682),
(258, 167, 0, 768),
(259, 167, 0, 791),
(260, 167, 0, 711),
(261, 167, 0, 611),
(262, 167, 0, 509),
(263, 169, 5, 141),
(264, 169, 5, 402),
(265, 170, 5, 167),
(266, 170, 5, 434),
(267, 170, 5, 465),
(268, 171, 5, 282),
(269, 171, 5, 457),
(270, 172, 5, 305),
(271, 173, 5, 52),
(272, 173, 5, 254),
(273, 175, 5, 434),
(274, 176, 0, 648),
(275, 176, 5, 39),
(276, 177, 0, 740),
(277, 177, 0, 700),
(278, 177, 0, 739),
(279, 177, 0, 566),
(280, 177, 0, 729),
(281, 177, 0, 787),
(282, 177, 0, 746),
(283, 177, 0, 735),
(284, 177, 0, 582),
(285, 177, 0, 645),
(286, 177, 0, 666),
(287, 177, 0, 784),
(288, 177, 0, 629),
(289, 177, 0, 710),
(290, 178, 0, 668),
(291, 179, 5, 179),
(292, 182, 0, 591),
(293, 182, 0, 789),
(294, 182, 0, 644),
(295, 182, 0, 745),
(296, 182, 0, 640),
(297, 182, 0, 612),
(298, 182, 0, 779),
(299, 183, 5, 400),
(300, 185, 5, 282),
(301, 186, 5, 453),
(302, 188, 0, 772),
(303, 188, 0, 747),
(304, 188, 5, 122),
(305, 189, 0, 740),
(306, 190, 5, 94),
(307, 190, 5, 362),
(308, 191, 5, 179),
(309, 192, 5, 73),
(310, 193, 5, 418),
(311, 195, 0, 747),
(312, 195, 0, 712),
(313, 195, 5, 489),
(314, 197, 5, 28),
(315, 201, 0, 685),
(316, 201, 0, 705),
(317, 207, 0, 776),
(318, 207, 5, 358),
(319, 208, 5, 221),
(320, 209, 0, 706),
(321, 209, 5, 122),
(322, 209, 5, 347),
(323, 210, 5, 167),
(324, 210, 5, 384),
(325, 211, 0, 519),
(326, 211, 0, 546),
(327, 211, 0, 785),
(328, 211, 0, 595),
(329, 211, 0, 738),
(330, 212, 0, 694),
(331, 213, 0, 551),
(332, 213, 5, 353),
(333, 214, 5, 302),
(334, 215, 5, 61),
(335, 219, 5, 47),
(336, 219, 5, 282),
(337, 220, 5, 174),
(338, 221, 5, 434),
(339, 225, 5, 282),
(340, 226, 5, 400),
(341, 228, 0, 618),
(342, 228, 0, 530),
(343, 228, 0, 654),
(344, 228, 0, 578),
(345, 228, 0, 551),
(346, 228, 0, 669),
(347, 230, 5, 387),
(348, 233, 5, 205),
(349, 235, 5, 11),
(350, 235, 5, 192),
(351, 236, 5, 86),
(352, 237, 5, 419),
(353, 238, 5, 376),
(354, 240, 5, 387),
(355, 240, 5, 465),
(356, 241, 0, 719),
(357, 241, 0, 779),
(358, 241, 0, 577),
(359, 241, 5, 418),
(360, 242, 0, 503),
(361, 242, 0, 701),
(362, 242, 0, 690),
(363, 242, 0, 568),
(364, 242, 0, 685),
(365, 242, 0, 782),
(366, 242, 0, 648),
(367, 242, 0, 563),
(368, 243, 5, 95),
(369, 244, 0, 725),
(370, 244, 0, 533),
(371, 245, 5, 360),
(372, 247, 5, 254),
(373, 248, 5, 282),
(374, 249, 0, 507),
(375, 249, 0, 571),
(376, 249, 0, 751),
(377, 249, 0, 653),
(378, 250, 0, 636),
(379, 250, 5, 67),
(380, 252, 5, 318),
(381, 253, 5, 438),
(382, 254, 5, 98),
(383, 255, 5, 489),
(384, 256, 0, 715),
(385, 257, 0, 767),
(386, 257, 0, 600),
(387, 257, 0, 773),
(388, 259, 5, 113),
(389, 261, 5, 479),
(390, 262, 5, 370),
(391, 264, 5, 375),
(392, 268, 5, 109),
(393, 268, 5, 221),
(394, 272, 5, 444),
(395, 273, 5, 165),
(396, 274, 0, 631),
(397, 274, 5, 95),
(398, 276, 5, 402),
(399, 277, 5, 73),
(400, 278, 0, 601),
(401, 278, 0, 798),
(402, 278, 0, 559),
(403, 278, 0, 785),
(404, 278, 0, 508),
(405, 278, 0, 505),
(406, 278, 0, 553),
(407, 278, 0, 560),
(408, 278, 0, 708),
(409, 279, 0, 784),
(410, 279, 0, 669),
(411, 279, 0, 762),
(412, 279, 5, 113),
(413, 280, 0, 505),
(414, 280, 0, 567),
(415, 280, 0, 620),
(416, 281, 5, 179),
(417, 281, 5, 192),
(418, 282, 5, 434),
(419, 283, 5, 335),
(420, 284, 5, 196),
(421, 284, 5, 402),
(422, 285, 5, 114),
(423, 285, 5, 489),
(424, 286, 0, 729),
(425, 286, 0, 582),
(426, 286, 0, 537),
(427, 286, 5, 419),
(428, 287, 5, 224),
(429, 287, 5, 434),
(430, 288, 5, 403),
(431, 289, 5, 112),
(432, 290, 5, 94),
(433, 290, 5, 192),
(434, 290, 5, 434),
(435, 290, 5, 465),
(436, 291, 0, 753),
(437, 291, 0, 593),
(438, 291, 0, 627),
(439, 291, 0, 747),
(440, 291, 0, 767),
(441, 291, 5, 282),
(442, 291, 5, 391),
(443, 292, 5, 343),
(444, 294, 0, 692),
(445, 296, 5, 254),
(446, 299, 0, 542),
(447, 299, 0, 635),
(448, 299, 0, 733),
(449, 299, 5, 321),
(450, 301, 5, 39),
(451, 301, 5, 130),
(452, 301, 5, 335),
(453, 303, 5, 2),
(454, 304, 0, 543),
(455, 304, 5, 165),
(456, 305, 0, 585),
(457, 305, 0, 798),
(458, 305, 0, 681),
(459, 306, 0, 738),
(460, 306, 5, 468),
(461, 308, 5, 240),
(462, 308, 5, 254),
(463, 310, 0, 673),
(464, 310, 0, 616),
(465, 310, 0, 586),
(466, 310, 0, 745),
(467, 310, 0, 527),
(468, 310, 0, 644),
(469, 310, 0, 756),
(470, 310, 0, 505),
(471, 310, 0, 676),
(472, 310, 0, 588),
(473, 310, 0, 723),
(474, 310, 0, 642),
(475, 310, 0, 581),
(476, 310, 0, 558),
(477, 310, 0, 612),
(478, 310, 0, 787),
(479, 310, 0, 752),
(480, 310, 0, 792),
(481, 310, 0, 549),
(482, 310, 0, 590),
(483, 310, 0, 703),
(484, 310, 0, 512),
(485, 310, 0, 748),
(486, 310, 0, 696),
(487, 310, 0, 793),
(488, 310, 0, 674),
(489, 310, 0, 786),
(490, 310, 0, 722),
(491, 310, 0, 573),
(492, 310, 0, 797),
(493, 310, 0, 599),
(494, 310, 0, 754),
(495, 310, 0, 673),
(496, 310, 0, 743),
(497, 310, 0, 796),
(498, 310, 0, 673),
(499, 310, 0, 724),
(500, 310, 0, 622),
(501, 310, 0, 532),
(502, 310, 0, 556),
(503, 310, 0, 591),
(504, 310, 0, 795),
(505, 310, 0, 572),
(506, 310, 0, 647),
(507, 310, 0, 755),
(508, 310, 0, 716),
(509, 310, 0, 771),
(510, 310, 0, 776),
(511, 310, 0, 642),
(512, 310, 0, 677),
(513, 310, 0, 658),
(514, 310, 0, 750),
(515, 310, 0, 515),
(516, 310, 0, 518),
(517, 310, 0, 684),
(518, 310, 0, 560),
(519, 310, 0, 538),
(520, 310, 0, 761),
(521, 310, 0, 741),
(522, 310, 0, 744),
(523, 310, 0, 691),
(524, 310, 0, 580),
(525, 310, 0, 615),
(526, 310, 0, 597),
(527, 310, 0, 793),
(528, 310, 0, 512),
(529, 310, 0, 592),
(530, 310, 0, 589),
(531, 310, 0, 730),
(532, 310, 0, 619),
(533, 310, 0, 789),
(534, 310, 0, 762),
(535, 310, 0, 703),
(536, 310, 0, 647),
(537, 310, 0, 796),
(538, 310, 0, 723),
(539, 310, 0, 689),
(540, 310, 0, 761),
(541, 310, 0, 561),
(542, 311, 0, 544),
(543, 311, 5, 112),
(544, 311, 5, 489),
(545, 312, 0, 612),
(546, 312, 0, 732),
(547, 312, 0, 519),
(548, 313, 5, 31),
(549, 314, 5, 221),
(550, 315, 0, 576),
(551, 315, 0, 674),
(552, 315, 0, 709),
(553, 315, 0, 782),
(554, 315, 5, 63),
(555, 315, 5, 174),
(556, 316, 5, 465),
(557, 317, 0, 545),
(558, 317, 0, 710),
(559, 317, 0, 720),
(560, 317, 0, 507),
(561, 317, 0, 624),
(562, 317, 0, 684),
(563, 317, 0, 557),
(564, 317, 0, 639),
(565, 317, 0, 569),
(566, 317, 0, 558),
(567, 317, 0, 624),
(568, 317, 0, 661),
(569, 317, 0, 504),
(570, 317, 0, 797),
(571, 317, 0, 736),
(572, 317, 0, 556),
(573, 317, 0, 708),
(574, 317, 0, 656),
(575, 317, 5, 125),
(576, 318, 5, 46),
(577, 319, 5, 465),
(578, 320, 5, 192),
(579, 321, 5, 321),
(580, 322, 0, 683),
(581, 323, 5, 95),
(582, 324, 5, 294),
(583, 325, 5, 174),
(584, 326, 5, 179),
(585, 328, 0, 786),
(586, 328, 0, 746),
(587, 331, 5, 63),
(588, 331, 5, 383),
(589, 332, 0, 506),
(590, 332, 0, 663),
(591, 332, 5, 6),
(592, 333, 5, 391),
(593, 333, 5, 465),
(594, 336, 5, 398),
(595, 337, 0, 562),
(596, 337, 5, 402),
(597, 337, 5, 434),
(598, 338, 0, 654),
(599, 339, 0, 641),
(600, 339, 0, 535),
(601, 339, 0, 773),
(602, 339, 0, 540),
(603, 339, 0, 587),
(604, 340, 5, 465),
(605, 341, 0, 670),
(606, 341, 0, 500),
(607, 341, 0, 716),
(608, 341, 0, 769),
(609, 341, 0, 627),
(610, 341, 0, 660),
(611, 341, 0, 534),
(612, 341, 0, 633),
(613, 341, 0, 580),
(614, 341, 0, 689),
(615, 341, 0, 650),
(616, 341, 0, 604),
(617, 344, 5, 254),
(618, 345, 5, 13),
(619, 345, 5, 434),
(620, 347, 0, 629),
(621, 347, 0, 517),
(622, 347, 5, 259),
(623, 348, 5, 302),
(624, 349, 5, 221),
(625, 351, 0, 773),
(626, 351, 5, 282),
(627, 352, 5, 224),
(628, 353, 5, 302),
(629, 354, 5, 254),
(630, 354, 5, 434),
(631, 356, 5, 489),
(632, 357, 0, 594),
(633, 357, 0, 578),
(634, 357, 0, 783),
(635, 357, 0, 679),
(636, 357, 0, 521),
(637, 357, 0, 652),
(638, 357, 0, 566),
(639, 357, 0, 660),
(640, 357, 0, 732),
(641, 357, 0, 518),
(642, 357, 0, 691),
(643, 357, 0, 799),
(644, 357, 0, 695),
(645, 357, 0, 699),
(646, 357, 0, 682),
(647, 357, 0, 560),
(648, 357, 0, 584),
(649, 357, 5, 174),
(650, 357, 5, 181),
(651, 358, 5, 491),
(652, 359, 5, 202),
(653, 360, 5, 398),
(654, 364, 5, 465),
(655, 365, 5, 179),
(656, 366, 5, 179),
(657, 366, 5, 434),
(658, 366, 5, 447),
(659, 367, 0, 530),
(660, 367, 5, 52),
(661, 368, 5, 202),
(662, 368, 5, 489),
(663, 371, 5, 243),
(664, 371, 5, 400),
(665, 372, 5, 253),
(666, 374, 0, 639),
(667, 374, 0, 522),
(668, 374, 5, 221),
(669, 374, 5, 282),
(670, 377, 5, 497),
(671, 378, 5, 282),
(672, 379, 5, 465),
(673, 382, 5, 402),
(674, 383, 5, 465),
(675, 385, 0, 670),
(676, 385, 5, 40),
(677, 386, 0, 514),
(678, 386, 5, 419),
(679, 387, 5, 160),
(680, 387, 5, 282),
(681, 388, 0, 607),
(682, 388, 0, 751),
(683, 388, 0, 662),
(684, 388, 0, 673),
(685, 388, 0, 607),
(686, 388, 5, 390),
(687, 388, 5, 465),
(688, 389, 0, 560),
(689, 389, 0, 552),
(690, 389, 0, 739),
(691, 389, 0, 548),
(692, 389, 0, 774),
(693, 389, 0, 665),
(694, 390, 5, 59),
(695, 390, 5, 465),
(696, 392, 5, 282),
(697, 393, 0, 647),
(698, 393, 5, 434),
(699, 394, 5, 179),
(700, 396, 0, 580),
(701, 396, 0, 611),
(702, 396, 0, 777),
(703, 396, 0, 578),
(704, 396, 0, 600),
(705, 396, 0, 725),
(706, 396, 5, 60),
(707, 398, 0, 637),
(708, 400, 5, 95),
(709, 400, 5, 489),
(710, 401, 5, 63),
(711, 403, 5, 438),
(712, 404, 0, 780),
(713, 405, 0, 731),
(714, 405, 0, 590),
(715, 406, 0, 675),
(716, 406, 5, 479),
(717, 408, 5, 113),
(718, 411, 5, 438),
(719, 412, 5, 93),
(720, 413, 5, 434),
(721, 414, 0, 689),
(722, 417, 5, 94),
(723, 420, 5, 6),
(724, 420, 5, 398),
(725, 422, 5, 63),
(726, 423, 5, 5),
(727, 423, 5, 174),
(728, 424, 5, 221),
(729, 425, 0, 780),
(730, 425, 5, 114),
(731, 425, 5, 302),
(732, 426, 0, 537),
(733, 426, 0, 525),
(734, 426, 0, 778),
(735, 426, 0, 563),
(736, 426, 5, 224),
(737, 428, 0, 526),
(738, 428, 0, 605),
(739, 428, 5, 122),
(740, 428, 5, 353),
(741, 430, 5, 224),
(742, 430, 5, 282),
(743, 431, 0, 621),
(744, 431, 5, 353),
(745, 436, 5, 6),
(746, 437, 5, 94),
(747, 437, 5, 282),
(748, 439, 0, 743),
(749, 439, 0, 524),
(750, 439, 0, 502),
(751, 439, 0, 626),
(752, 439, 5, 460),
(753, 440, 0, 654),
(754, 441, 0, 777),
(755, 441, 0, 757),
(756, 441, 0, 693),
(757, 441, 0, 521),
(758, 441, 5, 94),
(759, 442, 5, 114),
(760, 443, 0, 538),
(761, 444, 0, 550),
(762, 444, 0, 686),
(763, 444, 0, 748),
(764, 444, 0, 598),
(765, 444, 0, 709),
(766, 444, 0, 541),
(767, 444, 0, 524),
(768, 444, 0, 664),
(769, 444, 0, 708),
(770, 444, 0, 548),
(771, 444, 5, 17),
(772, 444, 5, 40),
(773, 445, 0, 528),
(774, 445, 5, 444),
(775, 445, 5, 444),
(776, 447, 0, 552),
(777, 447, 0, 746),
(778, 447, 0, 623),
(779, 447, 0, 616),
(780, 447, 0, 632),
(781, 447, 0, 521),
(782, 447, 5, 489),
(783, 448, 5, 94),
(784, 449, 0, 795),
(785, 449, 0, 655),
(786, 450, 5, 49),
(787, 450, 5, 186),
(788, 451, 5, 282),
(789, 452, 0, 677),
(790, 452, 0, 555),
(791, 452, 0, 643),
(792, 452, 0, 730),
(793, 452, 0, 777),
(794, 452, 0, 549),
(795, 452, 0, 559),
(796, 452, 0, 563),
(797, 452, 0, 792),
(798, 452, 0, 586),
(799, 452, 0, 679),
(800, 452, 0, 509),
(801, 454, 5, 335),
(802, 454, 5, 387),
(803, 456, 0, 798),
(804, 458, 5, 167),
(805, 459, 5, 465),
(806, 463, 5, 113),
(807, 464, 5, 96),
(808, 465, 0, 660),
(809, 465, 0, 545),
(810, 468, 5, 36),
(811, 468, 5, 73),
(812, 469, 0, 767),
(813, 469, 0, 595),
(814, 469, 5, 95),
(815, 470, 5, 19),
(816, 471, 5, 40),
(817, 472, 5, 122),
(818, 475, 5, 114),
(819, 475, 5, 200),
(820, 478, 5, 465),
(821, 479, 5, 282),
(822, 480, 5, 1),
(823, 481, 5, 460),
(824, 482, 5, 486),
(825, 483, 5, 358),
(826, 484, 5, 418),
(827, 485, 0, 609),
(828, 485, 0, 627),
(829, 485, 5, 302),
(830, 486, 5, 174),
(831, 487, 0, 777),
(832, 487, 5, 150),
(833, 488, 5, 95),
(834, 489, 5, 380),
(835, 491, 5, 418),
(836, 491, 5, 434),
(837, 492, 5, 174),
(838, 493, 0, 541),
(839, 493, 0, 601),
(840, 493, 0, 551),
(841, 493, 5, 6),
(842, 494, 5, 78),
(843, 494, 5, 391),
(844, 496, 0, 771),
(845, 496, 0, 661),
(846, 497, 5, 410),
(847, 498, 5, 162),
(848, 499, 5, 147),
(849, 500, 1, 990),
(850, 501, 1, 902),
(851, 501, 4, 730),
(852, 502, 1, 919),
(853, 502, 4, 555),
(854, 502, 4, 618),
(855, 502, 4, 618),
(856, 502, 4, 624),
(857, 503, 1, 913),
(858, 503, 3, 831),
(859, 503, 4, 503),
(860, 504, 1, 934),
(861, 505, 1, 972),
(862, 506, 1, 933),
(863, 506, 4, 599),
(864, 507, 1, 978),
(865, 508, 1, 966),
(866, 508, 3, 840),
(867, 509, 1, 984),
(868, 510, 1, 912),
(869, 510, 3, 802),
(870, 511, 1, 937),
(871, 511, 3, 839),
(872, 511, 4, 611),
(873, 511, 4, 770),
(874, 512, 1, 950),
(875, 512, 3, 852),
(876, 512, 4, 682),
(877, 513, 1, 905),
(878, 514, 1, 904),
(879, 514, 4, 757),
(880, 515, 1, 974),
(881, 515, 3, 846),
(882, 515, 4, 599),
(883, 516, 1, 908),
(884, 516, 4, 503),
(885, 517, 1, 900),
(886, 517, 3, 839),
(887, 517, 4, 594),
(888, 518, 1, 998),
(889, 518, 3, 831),
(890, 519, 1, 958),
(891, 519, 4, 506),
(892, 519, 4, 564),
(893, 520, 1, 997),
(894, 520, 3, 870),
(895, 521, 1, 995),
(896, 522, 1, 918),
(897, 523, 1, 963),
(898, 524, 1, 916),
(899, 524, 4, 548),
(900, 525, 1, 921),
(901, 525, 3, 839),
(902, 525, 4, 599),
(903, 526, 1, 933),
(904, 526, 4, 717),
(905, 527, 1, 906),
(906, 527, 4, 562),
(907, 527, 4, 668),
(908, 528, 1, 975),
(909, 529, 1, 902),
(910, 529, 3, 839),
(911, 530, 1, 963),
(912, 530, 4, 733),
(913, 531, 1, 931),
(914, 531, 3, 885),
(915, 531, 4, 538),
(916, 532, 1, 950),
(917, 532, 4, 555),
(918, 533, 1, 917),
(919, 534, 1, 916),
(920, 535, 1, 951),
(921, 535, 3, 807),
(922, 536, 1, 927),
(923, 537, 1, 975),
(924, 538, 1, 946),
(925, 538, 3, 839),
(926, 538, 4, 678),
(927, 538, 4, 717),
(928, 539, 1, 927),
(929, 540, 1, 928),
(930, 540, 3, 839),
(931, 540, 4, 604),
(932, 541, 1, 998),
(933, 542, 1, 919),
(934, 543, 1, 955),
(935, 543, 3, 839),
(936, 544, 1, 930),
(937, 545, 1, 996),
(938, 546, 1, 978),
(939, 546, 3, 874),
(940, 546, 4, 538),
(941, 547, 1, 931),
(942, 548, 1, 900),
(943, 549, 1, 967),
(944, 549, 4, 564),
(945, 550, 1, 942),
(946, 550, 3, 861),
(947, 550, 4, 538),
(948, 551, 1, 970),
(949, 552, 1, 916),
(950, 552, 4, 761),
(951, 553, 1, 951),
(952, 553, 3, 842),
(953, 554, 1, 917),
(954, 554, 4, 642),
(955, 555, 1, 935),
(956, 556, 1, 976),
(957, 556, 4, 602),
(958, 557, 1, 901),
(959, 557, 4, 573),
(960, 558, 1, 942),
(961, 558, 3, 824),
(962, 559, 1, 963),
(963, 560, 1, 991),
(964, 560, 4, 515),
(965, 561, 1, 907),
(966, 561, 3, 839),
(967, 561, 4, 604),
(968, 562, 1, 949),
(969, 563, 1, 956),
(970, 563, 4, 538),
(971, 564, 1, 939),
(972, 565, 1, 965),
(973, 565, 4, 503),
(974, 565, 4, 515),
(975, 565, 4, 594),
(976, 566, 1, 921),
(977, 566, 4, 584),
(978, 567, 1, 985),
(979, 567, 4, 538),
(980, 567, 4, 702),
(981, 568, 1, 998),
(982, 568, 3, 892),
(983, 569, 1, 903),
(984, 569, 3, 839),
(985, 569, 4, 770),
(986, 570, 1, 952),
(987, 570, 4, 661),
(988, 571, 1, 928),
(989, 571, 3, 839),
(990, 571, 4, 515),
(991, 572, 1, 917),
(992, 572, 3, 839),
(993, 573, 1, 977),
(994, 573, 3, 890),
(995, 574, 1, 955),
(996, 574, 4, 757),
(997, 575, 1, 930),
(998, 575, 4, 710),
(999, 575, 4, 769),
(1000, 576, 1, 918),
(1001, 577, 1, 954),
(1002, 577, 3, 871),
(1003, 577, 4, 604),
(1004, 578, 1, 968),
(1005, 579, 1, 930),
(1006, 579, 3, 864),
(1007, 579, 4, 515),
(1008, 580, 1, 951),
(1009, 581, 1, 948),
(1010, 582, 1, 955),
(1011, 582, 4, 598),
(1012, 583, 1, 915),
(1013, 583, 4, 639),
(1014, 584, 1, 924),
(1015, 585, 1, 912),
(1016, 586, 1, 953),
(1017, 586, 3, 810),
(1018, 586, 4, 506),
(1019, 586, 4, 604),
(1020, 587, 1, 900),
(1021, 587, 4, 538),
(1022, 587, 4, 576),
(1023, 587, 4, 599),
(1024, 588, 1, 997),
(1025, 589, 1, 980),
(1026, 589, 4, 564),
(1027, 590, 1, 994),
(1028, 590, 3, 843),
(1029, 590, 4, 506),
(1030, 590, 4, 761),
(1031, 591, 1, 977),
(1032, 591, 4, 604),
(1033, 592, 1, 916),
(1034, 592, 4, 543),
(1035, 592, 4, 604),
(1036, 592, 4, 710),
(1037, 593, 1, 943),
(1038, 593, 3, 840),
(1039, 594, 1, 913),
(1040, 594, 3, 864),
(1041, 594, 4, 569),
(1042, 594, 4, 796),
(1043, 595, 1, 940),
(1044, 595, 4, 604),
(1045, 596, 1, 960),
(1046, 596, 4, 716),
(1047, 597, 1, 909),
(1048, 597, 4, 503),
(1049, 598, 1, 925),
(1050, 598, 4, 507),
(1051, 599, 1, 937),
(1052, 600, 1, 919),
(1053, 600, 3, 839),
(1054, 601, 1, 914),
(1055, 601, 3, 824),
(1056, 602, 1, 983),
(1057, 603, 1, 954),
(1058, 604, 1, 901),
(1059, 605, 1, 929),
(1060, 605, 4, 506),
(1061, 605, 4, 757),
(1062, 606, 1, 949),
(1063, 606, 4, 747),
(1064, 607, 1, 955),
(1065, 607, 3, 804),
(1066, 607, 4, 599),
(1067, 608, 1, 920),
(1068, 608, 3, 850),
(1069, 608, 4, 773),
(1070, 609, 1, 928),
(1071, 609, 3, 839),
(1072, 609, 4, 604),
(1073, 610, 1, 961),
(1074, 610, 3, 890),
(1075, 611, 1, 928),
(1076, 612, 1, 911),
(1077, 613, 1, 994),
(1078, 613, 3, 839),
(1079, 613, 4, 685),
(1080, 614, 1, 973),
(1081, 614, 4, 543),
(1082, 615, 1, 912),
(1083, 615, 4, 716),
(1084, 616, 1, 998),
(1085, 616, 3, 890),
(1086, 617, 1, 970),
(1087, 618, 1, 938),
(1088, 618, 4, 521),
(1089, 619, 1, 997),
(1090, 620, 1, 993),
(1091, 620, 3, 839),
(1092, 621, 1, 985),
(1093, 621, 4, 515),
(1094, 622, 1, 966),
(1095, 622, 3, 846),
(1096, 623, 1, 994),
(1097, 623, 4, 694),
(1098, 624, 1, 962),
(1099, 625, 1, 929),
(1100, 625, 3, 824),
(1101, 625, 4, 599),
(1102, 626, 1, 921),
(1103, 626, 3, 839),
(1104, 626, 4, 761),
(1105, 627, 1, 954),
(1106, 627, 3, 804),
(1107, 627, 4, 538),
(1108, 628, 1, 983),
(1109, 628, 3, 864),
(1110, 629, 1, 947),
(1111, 630, 1, 964),
(1112, 630, 3, 824),
(1113, 630, 4, 521),
(1114, 631, 1, 920),
(1115, 631, 4, 661),
(1116, 632, 1, 953),
(1117, 632, 4, 543),
(1118, 633, 1, 915),
(1119, 633, 4, 564),
(1120, 634, 1, 947),
(1121, 634, 4, 717),
(1122, 635, 1, 904),
(1123, 636, 1, 962),
(1124, 637, 1, 901),
(1125, 637, 4, 717),
(1126, 638, 1, 925),
(1127, 638, 4, 564),
(1128, 639, 1, 938),
(1129, 639, 3, 824),
(1130, 640, 1, 975),
(1131, 640, 4, 532),
(1132, 640, 4, 611),
(1133, 641, 1, 941),
(1134, 642, 1, 944),
(1135, 642, 4, 761),
(1136, 643, 1, 957),
(1137, 643, 3, 839),
(1138, 644, 1, 943),
(1139, 645, 1, 952),
(1140, 645, 4, 602),
(1141, 646, 1, 931),
(1142, 647, 1, 957),
(1143, 647, 4, 562),
(1144, 647, 4, 584),
(1145, 648, 1, 974),
(1146, 649, 1, 939),
(1147, 649, 4, 717),
(1148, 650, 1, 941),
(1149, 650, 3, 890),
(1150, 650, 4, 796),
(1151, 651, 1, 959),
(1152, 651, 4, 636),
(1153, 652, 1, 916),
(1154, 652, 4, 555),
(1155, 653, 1, 912),
(1156, 653, 3, 839),
(1157, 654, 1, 917),
(1158, 654, 4, 506),
(1159, 654, 4, 661),
(1160, 655, 1, 942),
(1161, 656, 1, 969),
(1162, 656, 4, 584),
(1163, 657, 1, 907),
(1164, 657, 3, 839),
(1165, 657, 4, 606),
(1166, 658, 1, 917),
(1167, 658, 3, 800),
(1168, 658, 4, 604),
(1169, 659, 1, 938),
(1170, 659, 3, 839),
(1171, 660, 1, 917),
(1172, 661, 1, 914),
(1173, 662, 1, 947),
(1174, 662, 4, 521),
(1175, 663, 1, 908),
(1176, 664, 1, 912),
(1177, 665, 1, 945),
(1178, 665, 4, 559),
(1179, 665, 4, 571),
(1180, 666, 1, 985),
(1181, 666, 4, 730),
(1182, 667, 1, 932),
(1183, 668, 1, 983),
(1184, 668, 4, 684),
(1185, 669, 1, 928),
(1186, 670, 1, 981),
(1187, 670, 4, 604),
(1188, 670, 4, 773),
(1189, 671, 1, 943),
(1190, 672, 1, 961),
(1191, 672, 4, 570),
(1192, 673, 1, 959),
(1193, 673, 3, 839),
(1194, 673, 4, 757),
(1195, 674, 1, 949),
(1196, 674, 4, 786),
(1197, 675, 1, 902),
(1198, 675, 4, 553),
(1199, 676, 1, 938),
(1200, 677, 1, 997),
(1201, 678, 1, 934),
(1202, 678, 4, 564),
(1203, 678, 4, 684),
(1204, 678, 4, 751),
(1205, 679, 1, 920),
(1206, 679, 3, 839),
(1207, 680, 1, 974),
(1208, 681, 1, 922),
(1209, 681, 4, 757),
(1210, 682, 1, 935),
(1211, 682, 3, 839),
(1212, 683, 1, 971),
(1213, 683, 3, 891),
(1214, 683, 4, 575),
(1215, 684, 1, 965),
(1216, 684, 3, 839),
(1217, 684, 4, 564),
(1218, 685, 1, 961),
(1219, 685, 3, 839),
(1220, 686, 1, 998),
(1221, 687, 1, 979),
(1222, 688, 1, 927),
(1223, 688, 4, 506),
(1224, 689, 1, 985),
(1225, 689, 4, 759),
(1226, 689, 4, 770),
(1227, 690, 1, 956),
(1228, 690, 4, 773),
(1229, 691, 1, 983),
(1230, 692, 1, 952),
(1231, 693, 1, 901),
(1232, 693, 3, 847),
(1233, 693, 4, 503),
(1234, 694, 1, 940),
(1235, 694, 4, 786),
(1236, 695, 1, 942),
(1237, 695, 3, 800),
(1238, 695, 4, 794),
(1239, 696, 1, 945),
(1240, 696, 3, 853),
(1241, 697, 1, 909),
(1242, 698, 1, 910),
(1243, 699, 1, 970),
(1244, 699, 4, 641),
(1245, 700, 1, 905),
(1246, 700, 4, 773),
(1247, 701, 1, 912),
(1248, 701, 4, 503),
(1249, 701, 4, 506),
(1250, 702, 1, 912),
(1251, 703, 1, 977),
(1252, 703, 4, 514),
(1253, 704, 1, 902),
(1254, 705, 1, 911),
(1255, 705, 3, 826),
(1256, 705, 4, 606),
(1257, 706, 1, 980),
(1258, 706, 4, 717),
(1259, 707, 1, 980),
(1260, 708, 1, 960),
(1261, 708, 4, 529),
(1262, 709, 1, 970),
(1263, 710, 1, 929),
(1264, 710, 3, 839),
(1265, 710, 4, 532),
(1266, 711, 1, 908),
(1267, 711, 3, 833),
(1268, 711, 4, 538),
(1269, 711, 4, 690),
(1270, 711, 4, 730),
(1271, 712, 1, 920),
(1272, 712, 4, 503),
(1273, 713, 1, 939),
(1274, 714, 1, 904),
(1275, 714, 3, 839),
(1276, 715, 1, 923),
(1277, 716, 1, 982),
(1278, 716, 3, 839),
(1279, 717, 1, 914),
(1280, 718, 1, 931),
(1281, 718, 4, 747),
(1282, 719, 1, 916),
(1283, 719, 4, 564),
(1284, 720, 1, 931),
(1285, 720, 4, 515),
(1286, 721, 1, 935),
(1287, 722, 1, 929),
(1288, 722, 3, 845),
(1289, 722, 4, 622),
(1290, 723, 1, 981),
(1291, 723, 3, 820),
(1292, 724, 1, 921),
(1293, 724, 3, 839),
(1294, 724, 4, 604),
(1295, 725, 1, 958),
(1296, 725, 4, 503),
(1297, 725, 4, 564),
(1298, 726, 1, 930),
(1299, 726, 3, 807),
(1300, 727, 1, 970),
(1301, 728, 1, 960),
(1302, 729, 1, 971),
(1303, 729, 3, 839),
(1304, 730, 1, 905),
(1305, 730, 3, 839),
(1306, 731, 1, 921),
(1307, 731, 3, 839),
(1308, 731, 4, 604),
(1309, 732, 1, 924),
(1310, 732, 3, 839),
(1311, 733, 1, 998),
(1312, 733, 3, 839),
(1313, 733, 4, 555),
(1314, 734, 1, 907),
(1315, 734, 3, 829),
(1316, 735, 1, 901),
(1317, 736, 1, 965),
(1318, 737, 1, 946),
(1319, 738, 1, 996),
(1320, 738, 3, 800),
(1321, 738, 4, 506),
(1322, 738, 4, 553),
(1323, 739, 1, 974),
(1324, 739, 3, 839),
(1325, 739, 4, 564),
(1326, 740, 1, 966),
(1327, 740, 3, 892),
(1328, 740, 4, 776),
(1329, 741, 1, 985),
(1330, 742, 1, 976),
(1331, 742, 3, 801),
(1332, 742, 4, 564),
(1333, 743, 1, 943),
(1334, 743, 4, 690),
(1335, 744, 1, 908),
(1336, 744, 3, 839),
(1337, 744, 4, 611),
(1338, 745, 1, 940),
(1339, 746, 1, 995),
(1340, 746, 4, 719),
(1341, 747, 1, 971),
(1342, 748, 1, 966),
(1343, 748, 3, 890),
(1344, 748, 4, 564),
(1345, 749, 1, 996),
(1346, 749, 4, 704),
(1347, 750, 1, 934),
(1348, 750, 4, 586),
(1349, 750, 4, 606),
(1350, 751, 1, 919),
(1351, 752, 1, 928),
(1352, 752, 3, 829),
(1353, 752, 4, 779),
(1354, 753, 1, 953),
(1355, 753, 4, 506),
(1356, 754, 1, 959),
(1357, 754, 4, 794),
(1358, 755, 1, 998),
(1359, 755, 4, 757),
(1360, 756, 1, 985),
(1361, 757, 1, 963),
(1362, 757, 3, 809),
(1363, 758, 1, 953),
(1364, 758, 3, 839),
(1365, 759, 1, 980),
(1366, 760, 1, 980),
(1367, 760, 3, 839),
(1368, 761, 1, 947),
(1369, 761, 3, 839),
(1370, 762, 1, 923),
(1371, 762, 3, 839),
(1372, 763, 1, 910),
(1373, 763, 4, 559),
(1374, 764, 1, 997),
(1375, 765, 1, 994),
(1376, 765, 4, 604),
(1377, 766, 1, 971),
(1378, 766, 3, 810),
(1379, 766, 4, 579),
(1380, 767, 1, 953),
(1381, 767, 4, 584),
(1382, 767, 4, 717),
(1383, 768, 1, 917),
(1384, 769, 1, 931),
(1385, 770, 1, 972),
(1386, 770, 4, 506),
(1387, 770, 4, 661),
(1388, 771, 1, 976),
(1389, 772, 1, 997),
(1390, 772, 3, 801),
(1391, 773, 1, 971),
(1392, 773, 4, 613),
(1393, 774, 1, 943),
(1394, 774, 3, 839),
(1395, 774, 4, 671),
(1396, 775, 1, 959),
(1397, 776, 1, 937),
(1398, 776, 4, 532),
(1399, 777, 1, 917),
(1400, 777, 4, 578),
(1401, 777, 4, 604),
(1402, 778, 1, 936),
(1403, 778, 4, 627),
(1404, 779, 1, 955),
(1405, 780, 1, 937),
(1406, 780, 4, 624),
(1407, 781, 1, 902),
(1408, 781, 3, 839),
(1409, 781, 4, 713),
(1410, 782, 1, 992),
(1411, 782, 4, 538),
(1412, 783, 1, 917),
(1413, 783, 3, 824),
(1414, 784, 1, 942),
(1415, 784, 3, 839),
(1416, 784, 4, 546),
(1417, 785, 1, 975),
(1418, 786, 1, 909),
(1419, 786, 3, 807),
(1420, 786, 4, 519),
(1421, 787, 1, 975),
(1422, 787, 3, 837),
(1423, 788, 1, 915),
(1424, 789, 1, 911),
(1425, 789, 4, 538),
(1426, 789, 4, 576),
(1427, 790, 1, 927),
(1428, 790, 4, 796),
(1429, 791, 1, 966),
(1430, 791, 4, 599),
(1431, 792, 1, 919),
(1432, 793, 1, 931),
(1433, 794, 1, 912),
(1434, 795, 1, 981),
(1435, 796, 1, 939),
(1436, 796, 4, 761),
(1437, 797, 1, 918),
(1438, 798, 1, 988),
(1439, 798, 3, 826),
(1440, 799, 1, 955),
(1441, 799, 3, 824),
(1442, 900, 2, 1000),
(1443, 904, 2, 1035),
(1444, 906, 2, 1025),
(1445, 908, 2, 1082),
(1446, 910, 2, 1024),
(1447, 919, 2, 1043),
(1448, 920, 2, 1000),
(1449, 921, 2, 1000),
(1450, 924, 2, 1081),
(1451, 927, 2, 1000),
(1452, 929, 2, 1082),
(1453, 931, 2, 1043),
(1454, 932, 2, 1082),
(1455, 933, 2, 1026),
(1456, 936, 2, 1000),
(1457, 937, 2, 1050),
(1458, 939, 2, 1000),
(1459, 940, 2, 1082),
(1460, 942, 2, 1000),
(1461, 944, 2, 1034),
(1462, 945, 2, 1000),
(1463, 946, 2, 1000),
(1464, 948, 2, 1043),
(1465, 950, 2, 1093),
(1466, 951, 2, 1031),
(1467, 957, 2, 1071),
(1468, 958, 2, 1091),
(1469, 959, 2, 1000),
(1470, 960, 2, 1054),
(1471, 962, 2, 1005),
(1472, 963, 2, 1000),
(1473, 964, 2, 1000),
(1474, 966, 2, 1000),
(1475, 967, 2, 1043),
(1476, 968, 2, 1000),
(1477, 969, 2, 1000),
(1478, 973, 2, 1000),
(1479, 974, 2, 1082),
(1480, 975, 2, 1000),
(1481, 976, 2, 1000),
(1482, 977, 2, 1000),
(1483, 978, 2, 1046),
(1484, 979, 2, 1043),
(1485, 980, 2, 1082),
(1486, 983, 2, 1000),
(1487, 984, 2, 1037),
(1488, 985, 2, 1000),
(1489, 987, 2, 1093),
(1490, 989, 2, 1043),
(1491, 990, 2, 1043),
(1492, 991, 2, 1000),
(1493, 992, 2, 1000),
(1494, 993, 2, 1093),
(1495, 995, 2, 1000),
(1496, 997, 2, 1082),
(1497, 998, 2, 1092),
(1498, 999, 2, 1000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `e`
--
ALTER TABLE `e`
 ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
