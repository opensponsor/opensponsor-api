-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database

-- initialize country codes
INSERT INTO public.country_codes (when_created, when_deleted, when_modified, country_code, currency_alphabetic_code, dial, id, languages, cldr_display_name, official_name_cn, official_name_en) VALUES ('2024-04-27 09:37:55.346630 +00:00', null, '2024-04-27 09:37:55.346672 +00:00', 'CN', 'CNY', '+86', '0466037a-b655-4629-a748-35ca3789d68f', 'zh-CN', 'China', '中华人民共和国', 'China');

-- INSERT INTO public."user" (sex, when_created, when_deleted, when_modified, country_code_id, id, email, legal_name, phone_number, username, avatar, password, role) VALUES (null, '2024-05-04 10:38:01.699416 +00:00', null, '2024-05-04 10:38:01.699467 +00:00', '0466037a-b655-4629-a748-35ca3789d68f', '36e149e3-ed38-42ef-83ca-fad1f9f10303', null, 'opensponsor', null, 'opensponsor', null, '$2a$10$q7ItLTa4RRHMjlYeUw98Bejt3CzQNW17lGmpx/.k3FoFXWZKpcKpG', null);

-- INSERT INTO public.organization (type, when_created, when_deleted, when_modified, id, user_id, legal_name, name, slug, website, introduce, amount_of_members) VALUES (1, '2024-05-04 10:39:16.136055 +00:00', null, '2024-05-04 10:39:16.136071 +00:00', '790f4325-8a70-4428-a433-4361359872c1', '36e149e3-ed38-42ef-83ca-fad1f9f10303', 'opensponsor', 'opensponsor', 'opensponsor', 'opensponsor.com', 'opensponsor', 1);

INSERT INTO public.tier (amount_type, currency, interval, type, use_standalone_page, goal, when_created, when_deleted, when_modified, amount, max_quantity, minimum_amount, button, id, organization_id, name, slug, description, long_description, video_url) VALUES (0, 33, 1, 2, false, null, '2024-05-24 13:28:27.825842 +00:00', null, '2024-05-24 13:28:27.825877 +00:00', E'\\xACED0005737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000064', null, null, '贡献', 'e76290e4-4fce-4c19-9cee-01e5fdbf3678', '790f4325-8a70-4428-a433-4361359872c1', '捐赠', 'tier-1', '', null, null);

-- example data
INSERT INTO public.example (age, when_created, when_deleted, when_modified, id, name) VALUES (10, null, null, null, '45b31a02-9c2e-48ac-b722-6ed3abb3a4be', 'name1');
INSERT INTO public.example (age, when_created, when_deleted, when_modified, id, name) VALUES (20, null, null, null, '45b31a02-9c2e-48ac-b722-6ed3abb3a4ce', 'name2');
-- example end

-- licenses
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('fdd6a7f4-7961-4110-a255-3a0211d2e7b4', 'GNU Affero General Public License v3.0', 'agpl-3.0', 'AGPL-3.0', 'https://api.github.com/licenses/agpl-3.0', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('676d02a6-ed1b-4b4a-8172-3026e9530cb3', 'Apache License 2.0', 'apache-2.0', 'Apache-2.0', 'https://api.github.com/licenses/apache-2.0', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('f0871ab4-533e-4610-b946-4f1eb3236428', 'BSD 2-Clause "Simplified" License', 'bsd-2-clause', 'BSD-2-Clause', 'https://api.github.com/licenses/bsd-2-clause', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('7ac72f67-9e71-4632-92ef-ed624e9b9a3c', 'BSD 3-Clause "New" or "Revised" License', 'bsd-3-clause', 'BSD-3-Clause', 'https://api.github.com/licenses/bsd-3-clause', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('23d18ba7-f104-45d5-a5ec-a6954256b3c9', 'Boost Software License 1.0', 'bsl-1.0', 'BSL-1.0', 'https://api.github.com/licenses/bsl-1.0', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('51883a2d-5770-4c94-8480-bfcb098a5ec2', 'Creative Commons Zero v1.0 Universal', 'cc0-1.0', 'CC0-1.0', 'https://api.github.com/licenses/cc0-1.0', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('881645c3-3842-46e6-b52f-30dea7cb0560', 'Eclipse Public License 2.0', 'epl-2.0', 'EPL-2.0', 'https://api.github.com/licenses/epl-2.0', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('67767f98-b63a-4825-99ed-7284e1f21d26', 'GNU General Public License v2.0', 'gpl-2.0', 'GPL-2.0', 'https://api.github.com/licenses/gpl-2.0', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('e59ede8c-b2d7-41b9-9f07-92c1fc69ca8a', 'GNU General Public License v3.0', 'gpl-3.0', 'GPL-3.0', 'https://api.github.com/licenses/gpl-3.0', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('e668b0f2-92f9-4370-b226-b71ca78a7b45', 'GNU Lesser General Public License v2.1', 'lgpl-2.1', 'LGPL-2.1', 'https://api.github.com/licenses/lgpl-2.1', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('0e669cd2-ca23-4002-9547-57ee455adf7a', 'MIT License', 'mit', 'MIT', 'https://api.github.com/licenses/mit', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('f9db7611-8a74-4f0e-8831-aea1947da03a', 'Mozilla Public License 2.0', 'mpl-2.0', 'MPL-2.0', 'https://api.github.com/licenses/mpl-2.0', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
insert into licenses(id, name, key, spdx_id, url, when_created, when_modified) values('b74d18d0-58cf-4e06-b97c-cb8c8f4208ea', 'The Unlicense', 'unlicense', 'Unlicense', 'https://api.github.com/licenses/unlicense', 'Tue, 07 Jan 2025 11:42:46 GMT', 'Tue, 07 Jan 2025 11:42:46 GMT');
-- end licenses
