DROP DATABASE  IF EXISTS `nic-newspaper-bd`;

CREATE DATABASE  IF NOT EXISTS `nic-newspaper-bd`;
USE `nic-newspaper-bd`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  PRIMARY KEY (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user` (first_name,last_name,email,password) /* default password : fun123 */
VALUES 
('John','Doe','john@luv2code.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K'),
('Mary','Public','mary@luv2code.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K'),
('Susan','Adams','susan@luv2code.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K');


DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `role` (name)
VALUES 
('ROLE_USER'),
('ROLE_ADMIN');


DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_01` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(3, 1),
(3, 2);


DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(1000) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `comment` (text, date)
VALUES 
('Interesting article;)', current_time()),
('God bless America', current_time()),
('I always was crazy about space and such kind of thigs', current_time()),
('Wanna rewatch Interstellar...', current_time()),
('Me first!!!...', current_time()),
('I love all kind of pizza, especially a big one!', current_time()),
('I don\'t recommend anyone even try that... think about it, you\'ll be fat and unhealthy((', current_time()),
('What am i do here?', current_time()),
('the value of the web is embodied in the data available on it', current_time()),
('In this new world of you better looking after your own data', current_time()),
('Just install Kaspersky and relax', current_time()),
('Let\'s go CS GO!', current_time()),
('Very helpful article, thx!', current_time()),
('Sleep is for wimpsXD', current_time()),
('Poor sleeping can literally ruin your life', current_time()),
('Poor sleeping can transform you to Tyler Durdan', current_time()),
('I never was abroad((', current_time()),
('Travelling is the best way to reset your brains after difficult year in the office', current_time()),
('So what should i choose first', current_time()),
('Niceeee', current_time());


DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `header` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `date` datetime NOT NULL,
  `imageURL` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `article` (header,content,date,imageURL)
VALUES 
('SpaceX wins Sentinel 6B radar satellite launch contract', 
'SpaceX has won a contract to launch the joint US-European Sentinel 6B radar satellite as early as November 2025.
Five years ago, NASA also chose SpaceX to launch Sentinel 6A, the first of two identical satellites designed to use radar altimeters to determine global sea levels more accurately than ever before. In October 2017, just half a year after SpaceX’s first Falcon 9 rocket booster reuse and well before the cost savings that followed were fully factored in, NASA awarded SpaceX $94 million to launch the 1.1-ton (~2500 lb) to a relatively low 1300-kilometer (~810 mi) orbit.
Five years and two months later, NASA has awarded SpaceX $97 million to launch a virtually identical satellite to the same orbit, from the same launch pad, with the same rocket. SpaceX, however, is far from the same company it was in 2017, and has effectively mastered Falcon booster and payload fairing reuse in the half-decade since.
    — NASA\'s Launch Services Program (@NASA_LSP) December 20, 2022',
current_time(), 
'https://www.teslarati.com/wp-content/uploads/2020/11/Sentinel-6A-Falcon-9-B1063-SLC-4E-112120-SpaceX-launch-landing-2-c-2048x1127.jpg'),

('The World\s Largest Pizza Ever Weighed 26,883 lbs',
'According the keepers of human history over at the Guinness World Records, the largest circular pizza ever baked weighed was made in Norwood, South Africa by Norwood Hypermarket on December 8, 1990. It weighed 26,883 pounds.
The data is a bit sketchy, but here are relevant numbers: The pizza measured 122 feet, 8 inches in diameter, weighed 26,883 pounds, and contained 9,920 pounds of flour, 3,960 pounds of cheese, 1 763 pounds of mushrooms, 1,984 pounds of tomato puree, and 1,984 pounds of chopped tomatoes.',
current_time(),
'https://cdn.vox-cdn.com/thumbor/xVxOlMgoL3o0JxYbfevLfvN-zeM=/41x0:688x485/920x613/filters:focal(41x0:688x485):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/39116344/worlds-largest-pizza.0.jpg'),

('Inventor of the world wide web wants us to reclaim our data from tech giants',
'London CNN  — 
The internet has come a long way since Tim Berners-Lee invented the world wide web in 1989. Now, in an era of growing concern over privacy, he believes it’s time for us to reclaim our personal data.
Through their startup Inrupt, Berners-Lee and CEO John Bruce have created the “Solid Pod” — or Personal Online Data Store. It allows people to keep their data in one central place and control which people and applications can access it, rather than having it stored by apps or sites all over the web.
Users can get a Pod from a handful of providers, hosted by web services such as Amazon (AMZN), or run their own server, if they have they the technical know-how. The main attraction to self-hosting is control and privacy, says Berners-Lee.
Not only is user data safe from corporations, and governments, it’s also less likely to be stolen by hackers, Bruce says.
“I think we’ve all come to realize that the value of the web is embodied in the data available on it,” he adds. “In this new world of you looking after your own data, it doesn’t live in big silos that are lucrative targets for attackers.”',
current_time(),
'https://cacm.acm.org/system/assets/0004/4507/122122_PATRICIA_DE_MELO_MOREIRA-AFP-AFP_via_Getty_Images_Berners-Lee.large.jpg?1671644525&1671644525'),

('Poor sleep can make you prickly. Here’s what to do',
'CNN  — Holiday stress. Work problems. Money concerns. Family issues — the worries that can keep you up at night are infinite. Develop a sleep debt from those worries, and you’ll pay a price in your ability to think, plan and manage your emotions.
STOCK person sleep loss
Sleep deprivation affects nearly half of American adults, study finds
“Sleep debt, also called a sleep deficit, is the difference between the amount of sleep someone needs and the amount they actually get,” said sleep specialist Dr. Raj Dasgupta, an associate professor of clinical medicine at the University of Southern California’s Keck School of Medicine. “Sleep affects our ability to think, react, remember and solve problems.”
Symptoms of daytime fatigue include a lack of motivation to accomplish everyday tasks, a lack of productivity at work, memory problems and a low interest in being social, Dasgupta said. There’s another side effect as well: You may find yourself going ballistic over the slightest slight.
“Sleep loss is strongly associated with reduced empathy and emotional regulation,” Dasgupta said, “often resulting in miscommunication and retaliation during conflict.”
Mood regulation occurs in the frontal lobe of the brain, where thinking, problem-solving and memory consolidation also occur.
“The frontal lobe is the highest energy user in the brain and the first to go offline or malfunction when deprived of energy by a lack of sleep,” said stress management expert Dr. Cynthia Ackrill, an editor for Contentment magazine, produced by the American Institute of Stress.
Beautiful woman sitting at the table working with laptop at home around christmas tree rubbing eyes for fatigue and headache, sleepy and tired expression. Vision problem
Don\'t ruin your slumber during the holidays. Try these sleep expert tips
Without enough sleep, your brain functions less efficiently, affecting your coping skills, according to Ackrill.
“We don’t have the bandwidth to recognize our choices, get creative or just see that we can choose not to be irritated or irritating,” she said. “Irritability is one of the key signs of stress and poor sleep.”
Unfortunately, it doesn’t take long for sleep to affect our emotional stability, Dasgupta said: “Just one night of sleep loss impairs the ability to regulate emotions and the expression of them.”',
current_time(),
'https://i.headtopics.com/images/2022/12/1/cnni/poor-sleep-can-make-you-prickly-here-s-what-to-do--poor-sleep-can-make-you-prickly-here-s-what-to-do--1598391550417010688.webp'),

('New Zealand, Japan and Samoa set to reopen to visitors',
'(CNN) — This week in travel news: the world\'s best hotels, greatest sandwiches and most spectacular railway journeys. Also, pilot emergencies in Florida and London and the latest countries to relax their entry restrictions.
News from the air
A passenger with no flying experience landed a plane at a Florida airport on May 10 after the pilot became incapacitated. An air traffic controller took the call and guided him step-by-step.
That news came after a Virgin Atlantic flight heading to New York from London had to turn back after it emerged that the first officer, though fully qualified, hadn\'t completed Virgin\'s final flying test.
Earlier this month, a United Airlines passenger was taken into custody after opening an emergency exit and walking on the wing, and in Bangkok, a man was arrested after allegedly going on an armed rampage at the country\'s biggest airport.
And finally, a new survey reveals that customer satisfaction among US air passengers is declining across the board, from ticket cost to flight crew performance.
The world\'s most loved hotel for 2022 is a Costa Rican resort with more than 50 bungalows and villas and a private beach, according to Tripadvisor\'s annual Travelers\' Choice Awards. Spots in Brazil, Greece, Turkey and Switzerland also made the top 10.
For something even more exclusive, though, a very lucky few will be able to book a night this June at Paris\' famous Moulin Rouge, in a secret room inside the windmill itself. It will be available through Airbnb with a token one euro price tag, and would-be guests will be able to put in their booking requests from May 17.
If your accommodation preferences lean more simple and back-to-nature, however, you can take inspiration instead from these travelers who converted their own cozy campervans.',
current_time(),
'https://eturbonews.com/wp-content/uploads/2022/05/0-17-e1651679199420.jpg');


DROP TABLE IF EXISTS `articles_comments_users`;

CREATE TABLE `articles_comments_users` (
  `article_id` int(11) NOT NULL,
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  
  PRIMARY KEY (`article_id`, `comment_id`, `user_id`),
  
  KEY `FK_ARTICLE_idx` (`article_id`),
  KEY `FK_COMMENT_idx` (`comment_id`),
  
  CONSTRAINT `FK_ARTICLE` FOREIGN KEY (`article_id`) 
  REFERENCES `article` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_COMMENT` FOREIGN KEY (`comment_id`) 
  REFERENCES `comment` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_USER_02` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `articles_comments_users` (article_id, comment_id, user_id)
VALUES 
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 1),
(2, 5, 3),
(2, 6, 2),
(2, 7, 1),
(2, 8, 2),
(3, 9, 2),
(3, 10, 1),
(3, 11, 2),
(3, 12, 3),
(4, 13, 1),
(4, 14, 2),
(4, 15, 1),
(4, 16, 3),
(5, 17, 2),
(5, 18, 3),
(5, 19, 2),
(5, 20, 1);
