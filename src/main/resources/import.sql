-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database

-- initialize country codes
INSERT INTO public.country_codes (when_created, when_deleted, when_modified, country_codes, currency_alphabetic_code, dial, id, languages, cldr_display_name, official_name_cn, official_name_en) VALUES ('2024-04-27 09:37:55.346630 +00:00', null, '2024-04-27 09:37:55.346672 +00:00', 'CN', 'CNY', '86', '0466037a-b655-4629-a748-35ca3789d68f', 'zh-CN', 'China', '中华人民共和国', 'China');
