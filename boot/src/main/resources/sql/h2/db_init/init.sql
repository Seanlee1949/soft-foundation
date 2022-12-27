CREATE TABLE IF NOT EXISTS SF_USER
(
    ID          VARCHAR(32)  NOT NULL primary key,
    USER_NAME   VARCHAR(64)  NOT NULL,
    PASSWORD    VARCHAR(255) NOT NULL,
    NAME        VARCHAR(255) NOT NULL,
    CREATE_TIME VARCHAR(64)  NOT NULL
);

CREATE TABLE IF NOT EXISTS SF_DEVICE_GROUP
(
    ID    VARCHAR(32)  NOT NULL primary key,
    LABEL VARCHAR(64)  NOT NULL,
    NAME  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS SF_HISTORY_DETAIL_DATA
(
    ID              VARCHAR(32) NOT NULL primary key,
    PART_TIME       LONG,
    PART_DOWN_SPEED DOUBLE,
    PART_DEEP       DOUBLE,
    PART_ID         INTEGER,

    PART_PRESSURE   INTEGER,
    PART_DENSITY    DOUBLE,
    part_Pulp       DOUBLE,
    part_Current    DOUBLE,
    part_Ash        DOUBLE,
    DEVICE_KEY      VARCHAR(64),
    PILE_DESCRIBE   VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS SF_HISTORY_DATA
(
    ID                 VARCHAR(64) NOT NULL PRIMARY KEY,
    PACKAGE_AMOUNT     INTEGER,
    PILE_ID            LONG,
    VERSION            VARCHAR(64),
    DEVICE_KEY         VARCHAR(64),
    MACHINE_KEY        VARCHAR(64),
    PILE_DESCRIBE      VARCHAR(64),
    BEGIN_TIME         LONG,
    END_TIME           LONG,
    PROCESS_TYPE       VARCHAR(64),
    LONGITUDE          DOUBLE,
    LATITUDE           DOUBLE,
    HOLE_LONGITUDE     DOUBLE,
    HOLE_LATITUDE      DOUBLE,
    DEPTH              DOUBLE,
    FR_DEPTH           DOUBLE,
    RE_DEPTH           DOUBLE,
    EM_DEPTH           DOUBLE,
    WATER_CEMENT_RATIO DOUBLE,
    DOWN_SPEED         DOUBLE,
    UP_SPEED           DOUBLE,
    CUMULATIVE_PULP    DOUBLE,
    AVERAGE_PULP       DOUBLE,
    CUMULATIVE_ASH     DOUBLE,
    AVERAGE_ASH        DOUBLE,
    MAX_CURRENT        DOUBLE,
    AVERAGE_CURRENT    DOUBLE,
    AVERAGE_PRESSURE   INT,
    MAX_DOWN_SPEED     DOUBLE,
    MAX_UP_SPEED       DOUBLE,
    X_ANGLE            DOUBLE,
    Y_ANGLE            DOUBLE,
    T_TYPE_LENGTH      INT,
    T_TYPE_PULP        INT,
    BOTTOM_PART_PULP   DOUBLE,
    T_TYPE_ASH         INT,
    BOTTOM_PART_ASH    DOUBLE,
    PART_COUNT         INT,
    DEVICE_TYPE        VARCHAR(64),
    DEVICE_TYPE_NAME   VARCHAR(64),
    TS                 LONG,
    STANDARD_ASH       INT,
    PILE_TOP_CURRENT   DOUBLE,
    MAX_ANGLE          INT,
    PILE_TIME          INT,
    MILE_NO            VARCHAR(64),
    PART_NO            VARCHAR(64),
    MILE_PART_NO       VARCHAR(64),
    PILE_NO            VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS SF_RECORD
(
    ID                 LONG NOT NULL PRIMARY KEY,

    ACTIVE_AT          LONG,
    ALIAS              VARCHAR(64),
    CAMERA_LIVE        VARCHAR(64),
    CLIENT_ID          LONG,
    CLIENT_NAME        VARCHAR(64),
    COMMENT            VARCHAR(64),
    CONTENT            VARCHAR(64),
    CREATE_AT          LONG,
    CREATE_BY          LONG,
    DESIGN_PARAM       VARCHAR(64),
    FIRMWARE           VARCHAR(64),
    IS_MOVE            INT,
    KEY_               VARCHAR(64),
    LAST_AT            LONG,
    LOCKED             INT,
    MACHINE_KEY        VARCHAR(64),
    NAME               VARCHAR(64),
    NOTIFY_USERS       VARCHAR(64),
    ONLINE_STATUS      INT,
    ONLINE_TIME        LONG,
    POSITION           VARCHAR(64),
    PRODUCT_ID         INT,

    STATUS             INT,
    THUMBNAIL_BASE_URL VARCHAR(64),
    THUMBNAIL_PATH     VARCHAR(64),
    TYPE               VARCHAR(64),
    TYPE_NAME          VARCHAR(64),
    UPDATE_AT          LONG,
    UPDATE_BY          INT,
    URL_DATA           VARCHAR(64),
    URL_HISTORY        VARCHAR(64),
    URL_ONLINE         VARCHAR(64),
    URL_SERVER         VARCHAR(64),
    URL_TOKEN          VARCHAR(64),
    REALTIME           TEXT
);