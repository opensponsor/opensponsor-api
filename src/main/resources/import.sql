-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database

-- initialize country codes
INSERT INTO public.country_codes (when_created, when_deleted, when_modified, country_code, currency_alphabetic_code, dial, id, languages, cldr_display_name, official_name_cn, official_name_en) VALUES ('2024-04-27 09:37:55.346630 +00:00', null, '2024-04-27 09:37:55.346672 +00:00', 'CN', 'CNY', '+86', '0466037a-b655-4629-a748-35ca3789d68f', 'zh-CN', 'China', '中华人民共和国', 'China');

INSERT INTO public."user" (sex, when_created, when_deleted, when_modified, country_code_id, id, email, legal_name, phone_number, username, avatar, password, role) VALUES (null, '2024-05-04 10:38:01.699416 +00:00', null, '2024-05-04 10:38:01.699467 +00:00', '0466037a-b655-4629-a748-35ca3789d68f', '36e149e3-ed38-42ef-83ca-fad1f9f10303', null, 'opensponsor', null, 'opensponsor', null, '$2a$10$q7ItLTa4RRHMjlYeUw98Bejt3CzQNW17lGmpx/.k3FoFXWZKpcKpG', null);

INSERT INTO public.organization (type, when_created, when_deleted, when_modified, id, user_id, legal_name, name, slug, website, introduce) VALUES (1, '2024-05-04 10:39:16.136055 +00:00', null, '2024-05-04 10:39:16.136071 +00:00', '790f4325-8a70-4428-a433-4361359872c1', '36e149e3-ed38-42ef-83ca-fad1f9f10303', 'opensponsor', 'opensponsor', 'opensponsor', 'opensponsor.com', 'opensponsor');

INSERT INTO public.tier (amount_type, currency, interval, type, use_standalone_page, goal, when_created, when_deleted, when_modified, amount, max_quantity, minimum_amount, button, id, organization_id, name, slug, description, long_description, video_url) VALUES (0, 33, 1, 2, false, null, '2024-05-24 13:28:27.825842 +00:00', null, '2024-05-24 13:28:27.825877 +00:00', E'\\xACED0005737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000064', null, null, '贡献', 'e76290e4-4fce-4c19-9cee-01e5fdbf3678', '790f4325-8a70-4428-a433-4361359872c1', '捐赠', 'tier-1', '', null, null);

-- example data
INSERT INTO public.example (age, when_created, when_deleted, when_modified, id, name) VALUES (10, null, null, null, '45b31a02-9c2e-48ac-b722-6ed3abb3a4be', 'name1');
INSERT INTO public.example (age, when_created, when_deleted, when_modified, id, name) VALUES (20, null, null, null, '45b31a02-9c2e-48ac-b722-6ed3abb3a4ce', 'name2');
-- example end
