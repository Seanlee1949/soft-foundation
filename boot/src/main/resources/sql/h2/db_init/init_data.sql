-- 内置账户
INSERT INTO SF_USER (ID, USER_NAME, PASSWORD, NAME, CREATE_TIME)
VALUES ('10001', 'admin', 'admin', '管理员', '2022.11.23 13:36');
INSERT INTO SF_USER (ID, USER_NAME, PASSWORD, NAME, CREATE_TIME)
VALUES ('10002', '庆平路西段', '88888888', '管理员', '2022.11.23 13:36');

INSERT INTO SF_USER (ID, USER_NAME, PASSWORD, NAME, CREATE_TIME)
VALUES ('10003', '污水泵站', '88888888', '管理员', '2023.03.14 20:39');

INSERT INTO SF_USER (ID, USER_NAME, PASSWORD, NAME, CREATE_TIME)
VALUES ('10004', '沙龙涌北侧地块周边市政道路配套工程', '88888888', '管理员', '2023.04.08 12:39');

INSERT INTO SF_USER (ID, USER_NAME, PASSWORD, NAME, CREATE_TIME)
VALUES ('10005', '横琴新区子期中学软基处理工程', '666666', '管理员', '2023.04.08 12:39');

-- 测试数据
INSERT INTO SF_DEVICE_GROUP (ID, LABEL, NAME)
VALUES ('10001', '搅拌桩', 'JBZ');


-- INSERT INTO "PUBLIC"."SF_RECORD" VALUES
-- (1105, 1668566155, '', NULL, 90, U&'\6c99\9f99\6d8c\5317\4fa7\5730\5757\5468\8fb9\5e02\653f\9053\8def\914d\5957\5de5\7a0b', NULL, U&'{"projectName":"\5bcc\5c71\5927\9053\4e8c\671f\4e1c\6bb5","worker":""}', 1668156002, 0, NULL, '', 0, 'MX01808022000111', 1679096370, 0, '', U&'\5bcc\5c71\5927\9053\4e1c\6bb56\53f7\673a', '', 0, 5183508, '113.155670137015,22.166330133333332', 44, 1, '', '', 'JBZ', U&'\6405\62cc\6869', 1679440342, 0, '', '', '', '', '', '{"nozzleStatus":"","nozzleStatusClass":"","pipeStatus":"","pipeStatusClass":"","recordStatus":"","recordStatusClass":"","startTime":"","totalAsh":"","totalSlurry":""}'),
-- (1106, 1669618409, '', NULL, 90, U&'\6c99\9f99\6d8c\5317\4fa7\5730\5757\5468\8fb9\5e02\653f\9053\8def\914d\5957\5de5\7a0b', NULL, U&'{"projectName":"\5bcc\5c71\5927\9053\4e8c\671f\4e1c\6bb5","worker":""}', 1668156022, 0, NULL, '', 0, 'MX01808022000112', 1678784462, 0, '', U&'\5bcc\5c71\5927\9053\4e1c\6bb59\53f7\673a', '', 0, 4373388, '113.15315100368167,22.165934549999996', 44, 1, '', '', 'JBZ', U&'\6405\62cc\6869', 1678865533, 0, '', '', '', '', '', '{"nozzleStatus":"","nozzleStatusClass":"","pipeStatus":"","pipeStatusClass":"","recordStatus":"","recordStatusClass":"","startTime":"","totalAsh":"","totalSlurry":""}'),
-- (1107, 1668305969, '', NULL, 90, U&'\6c99\9f99\6d8c\5317\4fa7\5730\5757\5468\8fb9\5e02\653f\9053\8def\914d\5957\5de5\7a0b', NULL, U&'{"projectName":"\5bcc\5c71\5927\9053\4e8c\671f\4e1c\6bb5","worker":""}', 1668156051, 0, NULL, '', 0, 'MX01808022000113', 1682202513, 0, '', U&'\5bcc\5c71\5927\9053\4e1c\6bb52\53f7\673a', '', 0, 6672754, '113.154704637015,22.16645026666667', 44, 1, '', '', 'JBZ', U&'\6405\62cc\6869', 1682297580, 0, '', '', '', '', '', '{"nozzleStatus":"","nozzleStatusClass":"","pipeStatus":"","pipeStatusClass":"","recordStatus":"","recordStatusClass":"","startTime":"","totalAsh":"","totalSlurry":""}'),
-- (1108, 1668679084, '', NULL, 90, U&'\6c99\9f99\6d8c\5317\4fa7\5730\5757\5468\8fb9\5e02\653f\9053\8def\914d\5957\5de5\7a0b', NULL, U&'{"projectName":"\5bcc\5c71\5927\9053\4e8c\671f\4e1c\6bb5","worker":""}', 1668156079, 0, NULL, '', 0, 'MX01808022000114', 1679256000, 0, '', U&'\5bcc\5c71\5927\9053\4e1c\6bb57\53f7\673a', '', 0, 4673169, '113.15400362034835,22.166027900000003', 44, 1, '', '', 'JBZ', U&'\6405\62cc\6869', 1679273865, 0, '', '', '', '', '', '{"nozzleStatus":"","nozzleStatusClass":"","pipeStatus":"","pipeStatusClass":"","recordStatus":"","recordStatusClass":"","startTime":"","totalAsh":"","totalSlurry":""}');

INSERT INTO "PUBLIC"."SF_RECORD"
VALUES (1105, 1668566155, '', NULL, 90, '横琴新区子期中学软基处理工程', NULL, U&'{"projectName":"横琴新区子期中学","worker":""}', 1668156002,
        0, NULL, '', 0, 'MX01808022000111', 1679096370, 0, '', U&'1号机', '', 0, 5183508,
        '113.155670137015,22.166330133333332', 44, 1, '', '', 'JBZ', U&'\6405\62cc\6869', 1679440342, 0, '', '', '', '',
        '',
        '{"nozzleStatus":"","nozzleStatusClass":"","pipeStatus":"","pipeStatusClass":"","recordStatus":"","recordStatusClass":"","startTime":"","totalAsh":"","totalSlurry":""}'),
       (1106, 1669618409, '', NULL, 90, '横琴新区子期中学软基处理工程', NULL, U&'{"projectName":"横琴新区子期中学","worker":""}', 1668156022,
        0, NULL, '', 0, 'MX01808022000112', 1678784462, 0, '', U&'2号机', '', 0, 4373388,
        '113.15315100368167,22.165934549999996', 44, 1, '', '', 'JBZ', U&'\6405\62cc\6869', 1678865533, 0, '', '', '',
        '', '',
        '{"nozzleStatus":"","nozzleStatusClass":"","pipeStatus":"","pipeStatusClass":"","recordStatus":"","recordStatusClass":"","startTime":"","totalAsh":"","totalSlurry":""}'),
       (1107, 1668305969, '', NULL, 90, '横琴新区子期中学软基处理工程', NULL, U&'{"projectName":"横琴新区子期中学","worker":""}', 1668156051,
        0, NULL, '', 0, 'MX01808022000113', 1682202513, 0, '', U&'3号机', '', 0, 6672754,
        '113.154704637015,22.16645026666667', 44, 1, '', '', 'JBZ', U&'\6405\62cc\6869', 1682297580, 0, '', '', '', '',
        '',
        '{"nozzleStatus":"","nozzleStatusClass":"","pipeStatus":"","pipeStatusClass":"","recordStatus":"","recordStatusClass":"","startTime":"","totalAsh":"","totalSlurry":""}'),
       (1108, 1668679084, '', NULL, 90, '横琴新区子期中学软基处理工程', NULL, U&'{"projectName":"横琴新区子期中学","worker":""}', 1668156079,
        0, NULL, '', 0, 'MX01808022000114', 1679256000, 0, '', U&'4号机', '', 0, 4673169,
        '113.15400362034835,22.166027900000003', 44, 1, '', '', 'JBZ', U&'\6405\62cc\6869', 1679273865, 0, '', '', '',
        '', '',
        '{"nozzleStatus":"","nozzleStatusClass":"","pipeStatus":"","pipeStatusClass":"","recordStatus":"","recordStatusClass":"","startTime":"","totalAsh":"","totalSlurry":""}');
