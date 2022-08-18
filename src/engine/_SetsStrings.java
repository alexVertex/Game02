package engine;

public class _SetsStrings {
    public static final CharSequence S_WINDOW_NAME = "GAME 02";
    public static final String E_UNABLE_TO_MAKE_GLFW ="Unable to initialize GLFW";
    public static final String E_UNABLE_TO_MAKE_WINDOW ="Failed to create the GLFW window";
    public static final int I_CAMERA_AFFECT_NO = 0;
    public static final int I_CAMERA_AFFECT_YES = 1;
    public static final String P_RES_PATH = "res/";
    public static final String P_SETS_PATH = P_RES_PATH+"sets.txt";
    public static final int I_WINDOW_STYLE_FULLSCREEN = 2;
    public static final int I_WINDOW_STYLE_BORDERLESS = 1;
    public static final int I_WINDOW_STYLE_WINDOWED = 0;
    public static final int I_SETS_DEF_VIDRES = 2;
    public static final int I_SETS_DEF_WIDTH = 1024;
    public static final int I_SETS_DEF_HEIGHT = 768;
    public static final int I_SETS_DEF_VIDMODE = I_WINDOW_STYLE_WINDOWED;
    public static final float F_SETS_DEF_BRIGHTNESS = 1.0f;
    public static final float F_SET_BRIGHTNESS_BASE = 0.3f;
    public static final float F_SET_BRIGHTNESS_MULT = 0.7f;
    public static final float F_SETS_DEF_MUSIC_VOLUME = 1.0f;
    public static final float F_SETS_DEF_SOUND_VOLUME = 1.0f;
    public static final int I_SETS_DEF_SHOW_DECALS = 1;
    public static final int I_SETS_READ_LINE_VIDRES = 0;
    public static final int I_SETS_READ_LINE_VIDMODE = 1;
    public static final int I_SETS_READ_LINE_BRIGHTNESS = 2;
    public static final int I_SETS_READ_LINE_MUSIC_VOLUME = 3;
    public static final int I_SETS_READ_LINE_SOUND_VOLUME = 4;
    public static final int I_SETS_READ_LINE_SHOW_DECALS = 5;
    public static final double D_WINDOW_UPS = 100.0;
    public static final double D_WINDOW_UPS_CAP = 1.0/D_WINDOW_UPS;
    public static final String P_CURSOR_PATH = "cursor/";
    public static final String S_CURSOR_DEF_TEXTURE = "main";
    public static final int I_CURSOR_DEF_SIZE = 32;
    public static final int I_CURSOR_DEF_HOT_SPOT_OFFSET = 16;
    public static final int I_ACTOR_DEF_SIZE = 32;
    public static final String P_DATA_PATH = P_RES_PATH+"data/";
    public static final String P_SCRIPTS_PATH = P_DATA_PATH+"scripts/";
    public static final String S_SCRIPT_KEYWORD_IF = "if";
    public static final String S_SCRIPT_KEYWORD_ENDIF = "endif";
    public static final String S_SCRIPT_KEYWORD_SET = "set";
    public static final String S_SCRIPT_KEYWORD_RETURN = "return";
    public static final String S_SCRIPT_KEYWORD_DO = "do";
    public static final String S_SCRIPT_KEYWORD_VAR = "var";
    public static final String S_SCRIPT_KEYWORD_SET_DO = "setdo";
    public static final String S_SCRIPT_KEYWORD_DO_GLOBAL = "global";
    public static final String S_SCRIPT_KEYWORD_ELSE = "else";
    public static final String S_SCRIPT_GET_ACTOR_ARROW = "->";
    public static final String S_SCRIPT_TYPE_FLOAT = "float";
    public static final String S_SCRIPT_TYPE_ACTORVAR = "var";
    public static final String S_SCRIPT_TYPE_MATH = "math";
    public static final String S_SCRIPT_MATH_PLUS = "+";
    public static final String S_SCRIPT_MATH_MINUS = "-";
    public static final String S_SCRIPT_MATH_MUL = "*";
    public static final String S_SCRIPT_MATH_DIV = "/";
    public static final String S_SCRIPT_OP_EQ = "==";
    public static final String S_SCRIPT_OP_NOT_EQ = "!=";
    public static final String S_SCRIPT_OP_BIGGER = ">";
    public static final String S_SCRIPT_OP_LESSER = "<";
    public static final String S_SCRIPT_OP_EQ_BIGGER = ">=";
    public static final String S_SCRIPT_OP_EQ_LESSER = "<=";
    public static final String P_FONT_PATH = P_DATA_PATH+"fonts/";
    public static final String P_DATA_FILE_EXE = ".csv";
    public static final String S_FONT_MAIN_NAME = "main";
    public static final String S_FONT_MAIN_TEXTURE = "FontMain";
    public static final String S_FONT_SUPPORT_NAME = "support";
    public static final String S_FONT_SUPPORT_TEXTURE = "FontSupport";
    public static final int I_LABEL_TYPE_LEFT = 0;
    public static final int I_LABEL_TYPE_RIGHT = 1;
    public static final int I_LABEL_TYPE_CENTER = 2;
    public static final int I_LABEL_TYPE_WITH_RETURNS = 4;
    public static final int I_LABEL_MULTILINE_HEIGHT = 20;
    public static final String P_SHADERS_PATH = P_DATA_PATH+"shaders/";
    public static final String P_SHADERS_VERTICES_EXE = ".vs";
    public static final String P_SHADERS_FRAGMENTS_EXE = ".fs";
    public static final String P_SHADERS_FILENAME_EXE = "shader";
    public static final float[] FM_VERTICES_MASSIVE = new float[]{
            -1,1,0,
            1,1,0,
            1,-1,0,
            -1,-1,0,
    };
    public static final int[] IM_INDICES_MASSIVE = new int[]{
            0,1,2,
            2,3,0
    };
    public static final String P_TEXTURE_PATH = P_RES_PATH+"tex/";
    public static final String P_TEXTURE_EXE = ".png";
    public static final int I_MOUSE_BUTTON_LEFT = 0;
    public static final int I_MOUSE_BUTTON_RIGHT = 1;
    public static final int I_MOUSE_BUTTON_MIDDLE = 2;
    public static final int I_FIRST_KEY_CODE = 32;
    public static final int I_LAST_MOUSE_BUTTON = 8;
    public static final int I_ELEMENT_STATE_DISABLED = 0;
    public static final int I_ELEMENT_STATE_ENABLED = 1;
    public static final int I_ELEMENT_STATE_HOVERED = 2;
    public static final int I_ELEMENT_STATE_PRESSED = 3;
    public static final float F_ELEMENT_TEXTURE_CUT = 0.25f;
    public static final int I_BUTTON_REPEAT_PRESSED_TIMEWAIT = 10;
    public static final float F_FLAG_ACTIVE_TEXTURE_CUT = 0.5f;
    public static final int I_ELEMENT_KEYHOLDER_OFFSET_X = 20;
    public static final int I_ELEMENT_KEYHOLDER_CANCEL_CHANGE_KEYCODE = 256;
    public static final int I_ELEMENT_TEXTBOX_BACKSPACE_KEYCODE = 259;
    public static final int I_ELEMENT_TEXTBOX_LSHIFT_KEYCODE = 340;
    public static final int I_ELEMENT_TEXTBOX_RSHIFT_KEYCODE = 344;
    public static final int I_ELEMENT_SCROLLER_SIZE_START_WORK = 2;
    public static final float F_ELEMENT_SCROLLER_PART_X = 0.5f;
    public static final float F_ELEMENT_SPEED_ACCELERATE = 0.05f;
    public static final float F_ELEMENT_SPEED_ACCELERATE_FIRST = 0.02f;
    public static final float F_ELEMENT_SLIDER_PART_X = 0.75f;
    public static final float F_ELEMENT_SLIDER_SPEED_ACCELERATE = 0.01f;
    public static final float F_ELEMENT_SLIDER_SPEED_BORDER = 0.05f;
    public static final float F_MUSIC_FADE_MAX = 100;
    public static final float F_SOUND_PLAYER_DIST = 320;
    public static final String P_TEXTURE_ATTACK_PATH = "weaponsAnims/";
    public static final String S_TEXTURE_ATTACK_DEF = P_TEXTURE_ATTACK_PATH+"fistAttack";
    public static final String P_SAVE_PATH = P_RES_PATH+"saves/";
    public static final String P_SAVE_EXE = ".ser";
    public static final String S_SAVE_DATE_PATTERN = "MM.dd-HH:mm";
    public static final float F_TEXT_DEFAULT_COLOR_RED = 0.87f;
    public static final float F_TEXT_DEFAULT_COLOR_GREEN = 0.85f;
    public static final float F_TEXT_DEFAULT_COLOR_BLUE = 0.79f;
    public static final int I_AUDIO_SOURCE_INTERFACE_COUNT = 10;
    public static final int I_AUDIO_SOURCE_ACTOR_COUNT = 50;
    public static final String P_AUDIO_PATH = P_RES_PATH+"audio/";
    public static final String P_AUDIO_EXE = ".ogg";
    public static final int I_EVENT_TIME_END_TIME = 200;
    public static final String P_DATA_TILES_PATH = "world/tiles.csv";
    public static final String P_DATA_PLAYER_FRACTION_PATH = "players/fractions.csv";
    public static final String P_DATA_PLAYER_CHARS_PATH = "players/chars.csv";
    public static final String P_DATA_PLAYER_TRADES_PATH = "players/trades.csv";
    public static final String P_DATA_MAPS_PATH = P_DATA_PATH+"world/maps/";
    public static final String P_DATA_DIALOGS_PATH = P_DATA_PATH+"history/dialogs/";
    public static final String P_DATA_DIALOGS_PATH_ONLY = "history/dialogs/";
    public static final String P_DATA_QUEST_PATH = P_DATA_PATH+"history/quests/";
    public static final String P_DATA_QUEST_PATH_ONLY = "history/quests/";
    public static final String P_DATA_CHARS_TEXTURES_PATH = "chars/";
    public static final String P_DATA_OBJECTS_PATH = "objects/";
    public static final String P_DATA_DOORS_PATH = P_DATA_OBJECTS_PATH+"doors.csv";
    public static final String P_DATA_ACTIVATORS_PATH = P_DATA_OBJECTS_PATH+"activators.csv";
    public static final String P_DATA_ANIMTILES_PATH = P_DATA_OBJECTS_PATH+"animTiles.csv";
    public static final String P_DATA_CHESTS_PATH = P_DATA_OBJECTS_PATH+"chests.csv";
    public static final String P_DATA_STRUCTURES_PATH = P_DATA_OBJECTS_PATH+"structures.csv";
    public static final String P_DATA_SOUNDSOURCES_PATH = P_DATA_OBJECTS_PATH+"soundSources.csv";
    public static final String P_DATA_ITEMS_PATH = "items/";
    public static final String P_DATA_WEAPONS_PATH = P_DATA_ITEMS_PATH+"weapons.csv";
    public static final String P_DATA_SHIELDS_PATH = P_DATA_ITEMS_PATH+"shields.csv";
    public static final String P_DATA_ARMORS_PATH = P_DATA_ITEMS_PATH+"armours.csv";
    public static final String P_DATA_TALISMANS_PATH = P_DATA_ITEMS_PATH+"talismans.csv";
    public static final String P_DATA_USES_PATH = P_DATA_ITEMS_PATH+"uses.csv";
    public static final String P_DATA_RUNES_PATH = P_DATA_ITEMS_PATH+"runes.csv";
    public static final String P_DATA_MISC_PATH = P_DATA_ITEMS_PATH+"misc.csv";
    public static final String P_DATA_SPELLS_PATH = P_DATA_ITEMS_PATH+"spells.csv";
    public static final int I_GAME_TIME_MAX = 86400;
    public static final int I_GAME_AI_RAST = 20*32;
    public static final int I_GAME_ITEM_ACTIVATE_RAST = 30;
    public static final int I_GAME_OBJECTS_ACTIVATE_RAST = 40;
    public static final int I_GAME_PLAYER_STATUS_FRIEND = 1;
    public static final double[][]DM_GAME_TIMECYCLE = {
            {0,0.0,0.0,0.7,0.5},//0
            {0,0.0,0.0,0.7,0.5},//1
            {0,0.0,0.0,0.7,0.5},//2
            {0,0.0,0.0,0.7,0.5},//3
            {0,0.0,0.0,0.7,0.5},//4
            {0,0.0,0.0,0.46,0.75},//5
            {0,0.0,0.0,0.23,0.75},//6
            {0.0,0,0,0.0,1.0},//7
            {0.0,0,0,0.0,1.0},//8
            {0.0,0,0,0.0,1.0},//9
            {0.0,0,0,0.0,1.0},//10
            {0.0,0,0,0.0,1.0},//11
            {0.0,0,0,0.0,1.0},//12
            {0.0,0,0,0.0,1.0},//13
            {0.0,0,0,0.0,1.0},//14
            {0.0,0,0,0.0,1.0},//15
            {0.0,0,0,0.0,1.0},//16
            {0,0.0,0.0,0.23,0.75},//17
            {0,0.0,0.0,0.46,0.75},//18
            {0,0.0,0.0,0.7,0.5},//19
            {0,0.0,0.0,0.7,0.5},//20
            {0,0.0,0.0,0.7,0.5},//21
            {0,0.0,0.0,0.7,0.5},//22
            {0,0.0,0.0,0.7,0.5},//23
    };
    public static final int I_GAME_BLOOD_SLAIN_SIZE = 48;
    public static final String S_PLAYER_LOOT_LOCAL_ID = "loot";
    public static final String S_PLAYER_SAY_DIED = "getDied";
    public static final String S_PLAYER_SAY_ATTACKED = "getAttacked";
    public static final String S_PLAYER_SAY_HITED = "getHited";
    public static final int I_INTERFACE_BOX_BORDER_TOP = 0;
    public static final int I_INTERFACE_BOX_BORDER_BOT = 1;
    public static final int I_INTERFACE_BOX_BORDER_LEFT = 2;
    public static final int I_INTERFACE_BOX_BORDER_RIGHT = 3;
    public static final int I_INTERFACE_BOX_BORDER_SIZE = 2;
    public static final int I_INTERFACE_BOX_BACKGROUND = 4;
    public static final int I_INTERFACE_BOX_HEAD_OFFSET = 20;
    public static final float F_INTERFACE_BOX_BACKGROUND_ALPHA = 0.5f;
    public static final float F_INTERFACE_DESCRIBE_BACKGROUND_ALPHA = 1f;
    public static final int I_INTERFACE_DESCRIBE_DATA_OFFSET_X = 10;
    public static final int I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_START = 40;
    public static final int I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_PER= 20;
    public static final int I_INTERFACE_DESCRIBE_DATA_SIZE= 20;
    public static final float F_INTERFACE_DESCRIBE_HEIGHT_MULT = 0.33f;
    public static final int I_INTERFACE_DESCRIBE_CURSOR_OFFSET_X= 25;
    public static final int I_INTERFACE_DESCRIBE_CURSOR_OFFSET_Y= 25;
    public static final int I_INTERFACE_DESCRIBE_HEAD_OFFSET = 15;
    public static final String S_INTERFACE_DESCRIBE_LEFT_DESCRIPTION= "=";
    public static final float F_GAMEPLAY_TRADES_KOEF = 100f;
    public static final int I_GAMEPLAY_DAMAGE_PIERCE = 0;
    public static final int I_GAMEPLAY_DAMAGE_SLASH = 1;
    public static final int I_GAMEPLAY_DAMAGE_CHOP = 2;
    public static final int I_GAMEPLAY_DAMAGE_STRIKE = 3;
    public static final int I_GAMEPLAY_DAMAGE_FIRE = 4;
    public static final int I_GAMEPLAY_DAMAGE_ICE = 5;
    public static final int I_GAMEPLAY_DAMAGE_SHOCK = 6;
    public static final int I_GAMEPLAY_DAMAGE_ACID = 7;
    public static final int I_GAMEPLAY_DAMAGE_POISON = 8;
    public static final int I_GAMEPLAY_DAMAGE_MENTAL = 9;
    public static final int I_GAMEPLAY_DAMAGE_NEGATIVE = 10;
    public static final int I_GAMEPLAY_DAMAGE_ARRAY_SIZE = 11;
    public static final String P_TEXTURE_SPELLS_PATH = "spells/";
    public static final float F_SPELLS_TEXTURE_PART_CUT = 0.0625f;
    public static final int I_AI_TYPE_ROAMING = 0;
    public static final int I_AI_TYPE_PATROL = 1;
    public static final int I_AI_MOMENT_NORMAL = 0;
    public static final int I_AI_MOMENT_BATTLE = 1;
    public static final int I_AI_MOMENT_SEARCH = 2;
    public static final int I_AI_MOMENT_FEARED = 3;
    public static final double D_AI_SCAN_KOEF_HEAR = 0.7;
    public static final double D_AI_ANGLE_SEE = 1.3;
    public static final int I_AI_ALARMED_TIME_HEAR = 75;
    public static final int I_AI_ALARMED_TIME_DAMAGE = 25;
    public static final int I_AI_ALARMED_TIME_FRIEND_ALARM = 40;
    public static final int I_AI_PLAYER_VOICE_HEAR_DIST = 100;
    public static final int I_AI_SEARCH_TIME = 500;
    public static final int I_AI_FEAR_TIME = 300;
    public static final int I_AI_SCAN_SEE_MULT = 32;
    public static final int I_AI_SCAN_HEAR_MULT = 16;
    public static final int I_AI_SCAN_STATUS_NO = -1;
    public static final int I_AI_SCAN_STATUS_HEAR = 0;
    public static final int I_AI_SCAN_STATUS_SEE = 1;
    public static final int I_AI_SCAN_STATUS_FEAR = 2;
    public static final int I_AI_FRACTION_ATTITUDE_FRIEND = 0;
    public static final int I_AI_FRACTION_ATTITUDE_NEUTRAL = 1;
    public static final int I_AI_FRACTION_ATTITUDE_ENEMY = 2;
    public static final int I_AI_FRACTION_ATTITUDE_FEAR = 3;
    public static final int I_AI_PLAYER_ATTITUDE_ENEMY = 0;
    public static final int I_AI_PLAYER_ATTITUDE_FRIEND = 1;
    public static final double D_AI_SPEED_WALK = 1.4;
    public static final float F_FEARED_DIST_MULT = 1.5f;
    public static final double D_MANUAL_SPEED_MODE_SNEAK = 0.6;
    public static final double D_MANUAL_SPEED_MODE_WALK = 1;
    public static final double D_MANUAL_SPEED_MODE_RUN = 2;
    public static final int I_MANUAL_SWITCH_ITEM = 0;
    public static final int I_MANUAL_SWITCH_SPELL1 = 1;
    public static final int I_MANUAL_SWITCH_SPELL2 = 2;
    public static final double D_PLAYER_STAMINA_FOR_HIT_WEAPON_WEIGHT_KOEF = 5;
    public static final double D_PLAYER_SHIELD_HIT_ANGLE = 1.3;
    public static final double D_PLAYER_BACKSTAB_DAMAGE_MULT = 3;
    public static final double D_PLAYER_HIT_PROGRESS_MULT = 0.8;
    public static final double D_PLAYER_DAMAGE_SPRAY_BASE = 0.8;
    public static final double D_PLAYER_DAMAGE_SPRAY_ADD = 0.4;
    public static final double D_PLAYER_DAMAGE_ARMOR_MULT = 0.001;
    public static final double D_SHIELD_AFTER_SHIELD_COLOR_PROGRESS = 0.01;
    public static final int I_BATTLE_LOSED_CONTOROL_TIME_SHIELD_BREAK = 150;
    public static final int I_PLAYER_LOSS_CONTROL_TIME_AFTER_HIT_ON_SHIELD = 30;
    public static final int I_CHANGE_ITEM_TIME = 200;

    public static final int I_ITEM_NO_USE_AMMO = -1;
    public static final String S_PLAYER_LOCAL_ID = "player";
    public static final String P_PLAYER_VOICE_PATH = "playerVoice/";
    public static final int I_PLAYER_VOICE_VARIANT_MIN = 1;
    public static final int I_PLAYER_VOICE_VARIANT_MAX = 4;
    public static final int I_PLAYER_LOSS_CONTROL_TIME_AFTER_HIT = 30;
    public static final float F_PLAYER_TEXTURE_PART = 0.25f;
    public static final float F_PLAYER_DEFAULT_DAMAGE = 14;
    public static final float F_PLAYER_DEFAULT_BALANCE_DAMAGE = 20;
    public static final int I_PLAYER_DEFAULT_DAMAGE_TYPE = 3;
    public static final int I_PLAYER_DEFAULT_AMMO = I_ITEM_NO_USE_AMMO;
    public static final int I_PLAYER_DEFAULT_POISE = 20;
    public static final int I_PLAYER_DEFAULT_INTERVAL = 1000;
    public static final double D_PLAYER_DEFAULT_SHIELD_DAMAGE_REDUCTION = 0.2;
    public static final double D_PLAYER_DEFAULT_SHIELD_STABILITY = 0.4;
    public static final int I_PLAYER_ARMAMENTS_WEAPON_ONE = 0;
    public static final int I_PLAYER_ARMAMENTS_SHIELD_ONE = 1;
    public static final int I_PLAYER_ARMAMENTS_WEAPON_TWO = 2;
    public static final int I_PLAYER_ARMAMENTS_SHIELD_TWO = 3;
    public static final int I_PLAYER_ARMAMENTS_ARMOR = 4;
    public static final int I_PLAYER_ARMAMENTS_TALISMAN_1 = 5;
    public static final int I_PLAYER_ARMAMENTS_TALISMAN_2 = 6;
    public static final int I_PLAYER_ARMAMENTS_TALISMAN_3 = 7;
    public static final int I_PLAYER_ARMAMENTS_ITEM_1 = 8;
    public static final int I_PLAYER_ARMAMENTS_ITEM_2 = 9;
    public static final int I_PLAYER_ARMAMENTS_ITEM_3 = 10;
    public static final int I_PLAYER_ARMAMENTS_ITEM_4 = 11;
    public static final int I_PLAYER_SPELL_LEFT_ROW = 0;
    public static final int I_PLAYER_SPELL_RIGHT_ROW = 1;
    public static final int I_PLAYER_SPOWNER_MULTITIMES = -1;
    public static final double D_PLAYER_SPEED_GUARD_KOEF = 0.3;
    public static final double D_PLAYER_SPEED_DIOGONAL_KOEF = 0.7;
    public static final double D_PLAYER_STAMINA_RUN_LOSS = 0.6;
    public static final double D_PLAYER_STAMINA_RECOVERY = 0.4;
    public static final double D_PLAYER_STAMINA_RECOVERY_GUARD_KOEF = 0.1;
    public static final double D_PLAYER_STAMINA_RECOVERY_PROGRESS_ADD= 0.02;
    public static final double D_PLAYER_STAMINA_RECOVERY_WAIT_AFTER_DROUGHT= -2;
    public static final double D_PLAYER_STAMINA_RECOVERY_WAIT= -1;
    public static final double D_PLAYER_GUARD_SPRITE_OFFSET = 20;
    public static final double D_PLAYER_LOAD_MAX = 0.5;
    public static final double D_PLAYER_WALK_PROGRESS_HALF_WAY = 0.5;
    public static final float F_PLAYER_WALK_PROGRESS_ADD = 0.02f;
    public static final int I_PLAYER_STEP_TIME_WAIT = 15;
    public static final double D_PLAYER_STEP_ANGLE_OFFSET = Math.PI*0.3;
    public static final double D_PLAYER_STEP_RAST_OFFSET = 4;
    public static final int I_DECAL_TYPE_STEP = 0;
    public static final int I_DECAL_TYPE_BLOOD = 1;
    public static final int I_DECAL_STEP_BLOOD_COLOR_TIME = 400;

    public static final int I_ZORDER_TILES = 0;
    public static final int I_ZORDER_ANIMTILES = 1;
    public static final int I_ZORDER_DECALS = 2;
    public static final int I_ZORDER_ACTIVATOR = 3;
    public static final int I_ZORDER_ITEM= 11;
    public static final int I_ZORDER_CHEST= 5;
    public static final int I_ZORDER_DOOR = 6;
    public static final int I_ZORDER_PLAYER = 10;
    public static final int I_ZORDER_ATTACK_PROJ = 12;
    public static final int I_ZORDER_GUARD_PROJ = 11;
    public static final int I_ZORDER_STRUCTURE_LIQUID = 20;
    public static final int I_ZORDER_STRUCTURE_HARD = 3;
    public static final int I_SPELL_SORT_ELEMENTAL = 0;
    public static final int I_SPELL_SORT_SENSE = 1;
    public static final int I_SPELL_SORT_SPEECH = 2;
    public static final int I_SPELL_SORT_WEAPONS = 3;
    public static final int I_SPELL_SORT_RANGE = 4;
    public static final int I_SPELL_SORT_SHIELDS = 5;
    public static final int I_ITEM_SORT_BLADES = 0;
    public static final int I_ITEM_SORT_AXES = 1;
    public static final int I_ITEM_SORT_SPEARS = 2;
    public static final int I_ITEM_SORT_RANGE = 3;
    public static final int I_ITEM_SORT_SHIELDS = 4;
    public static final int I_ITEM_SORT_ARMOURS = 5;
    public static final int I_ITEM_SORT_TALISMANS = 6;
    public static final int I_ITEM_SORT_USES = 7;
    public static final int I_ITEM_SORT_RUNES = 8;
    public static final int I_ITEM_SORT_MISC = 9;
    public static final int I_DIALOG_COND_NO_TALKED = 0;
    public static final int I_DIALOG_COND_HAVE_ITEMS_IN_INVENTORY = 1;
    public static final int I_DIALOG_RESULT_GAIN_MONEY = 0;
    public static final int I_QUEST_FINISHED = 1;
    public static final int I_QUEST_TASK_START_UP = 0;
    public static final int I_QUEST_TASK_FINISH = 1;
    public static final int I_QUEST_TASK_MOVE_TO = 2;
    public static final int I_QUEST_TASK_MOVE_OUT = 3;
    public static final int I_QUEST_TASK_KILL = 4;
    public static final int I_QUEST_TASK_SEE = 5;
    public static final int I_QUEST_TASK_ITEM = 6;
    public static final int I_QUEST_TASK_ACTIVATOR = 7;
    public static final int I_QUEST_TASK_STATUS_DISABLED = 0;
    public static final int I_QUEST_TASK_STATUS_ENABLED = 1;
    public static final int I_QUEST_TASK_STATUS_COMPLETED = 2;
    public static final String P_SOUND_INTERFACE = "interface/";
    public static final String P_SOUND_INTERFACE_QUEST_COMPLETE = P_SOUND_INTERFACE+"QuestComplete";
    public static final String P_SOUND_INTERFACE_QUEST_COMPLETE_TASK = P_SOUND_INTERFACE+"QuestCompleteTask";
    public static final String P_SOUND_INTERFACE_QUEST_ADDED = P_SOUND_INTERFACE+"QuestAdd";
    public static final String P_SOUND_INTERFACE_CLICK = P_SOUND_INTERFACE+"Click";
    public static final String P_SOUND_INTERFACE_CANCEL = P_SOUND_INTERFACE+"Cancel";
    public static final String P_SOUND_INTERFACE_CAMP_CHANGE = P_SOUND_INTERFACE+"CampChange";
    public static final String P_SOUND_INTERFACE_CAMP_DRESS = P_SOUND_INTERFACE+"CampInventoryDress";
    public static final String P_SOUND_INTERFACE_CAMP_UNDRESS = P_SOUND_INTERFACE+"CampInventoryUndress";
    public static final String P_SOUND_INTERFACE_OK = P_SOUND_INTERFACE+"Ok";
    public static final String P_PRINTS_TEXTURE = "prints/";
    public static final int I_DECALS_TIME_LIVE_MAX = 1000;
    public static final int I_DECALS_SIZE_ADD_TIMEWAIT= 3;
    public static final float F_DECALS_TIME_LIVE_START_FADE = 100f;
    public static final String P_MUSIC_PATH = "music/";
    public static final String P_TILES_TEXTURES_PATH = "tiles/";
    public static final String P_ACTIVATOR_TEXTURES_PATH = "activators/";
    public static final String P_ANIMTILES_TEXTURES_PATH = "animTiles/";
    public static final String P_CHESTS_TEXTURES_PATH = "chests/";
    public static final String P_DOORS_TEXTURES_PATH = "doors/";
    public static final String P_ITEMS_TEXTURES_PATH = "items/";
    public static final String P_AMBIENT_SOUND_PATH = "ambient/";
    public static final String P_STRUCTURES_TEXTURES_PATH = "objects/";
    public static final int I_PASS_FREE = 1;
    public static final int I_PASS_PIT = 0;
    public static final int I_PASS_WALL = -1;
    public static final float F_ACTIVATOR_PART = 0.25f;
    public static final float F_ACTIVATOR_PROGRESS_ADD = 0.05f;
    public static final float F_CHEST_PART = 0.25f;
    public static final float F_CHEST_PROGRESS_ADD = 0.05f;
    public static final float F_DOOR_PART = 0.25f;
    public static final float F_DOOR_PROGRESS_ADD = 0.05f;
    public static final float F_STRUCTURE_PART = 0.125f;
    public static final float F_STRUCTURE_LIQUID_PART_ALPHA = 0.5f;
    public static final String P_MAIN_MENU_BUTTON = "MainMenu/Button";
    public static final String P_MAIN_MENU_BACKGROUND = "MainMenu/Background";
    public static final int I_ESC_MENU_BUTTONS_X = 0;
    public static final int I_ESC_MENU_BUTTONS_Y_START = -160;
    public static final int I_ESC_MENU_BUTTONS_Y_PER = -90;
    public static final int I_ESC_MENU_ZORDER = 10000;
    public static final int I_ESC_MENU_BUTTONS_SIZEX = 350;
    public static final int I_ESC_MENU_BUTTONS_SIZEY = 75;
    public static final String P_BOX_BORDER_TEXTURE = "interface/Box";
    public static final String P_BOX_BACK_TEXTURE = "interface/back";
    public static final String P_BOX_BACK_BLACK_TEXTURE = "interface/blackBack";
    public static final String P_BUTTON_TEXTURE = "interface/Botton";
    public static final String P_KEYHOLDER_TEXTURE = "interface/Keyholder";
    public static final String P_SLIDER_TEXTURE = "interface/slider";
    public static final String P_LEFT_TEXTURE = "interface/Left";
    public static final String P_RIGHT_TEXTURE = "interface/Right";
    public static final String P_TEXTBOX_TEXTURE = "interface/textbox";
    public static final String P_SCROLLER_TEXTURE = "interface/scroller";
    public static final String S_OK_SYMBOL = "^";
    public static final String S_FIRE_SYMBOL = "@";
    public static final String S_CANCEL_SYMBOL = "&";
    public static final String P_MUSIC_MAIN_MUSIC = "music/main";
    public static final String P_MUSIC_DEATH_MUSIC = "music/GameOver";
    public static final String P_MINIMAPS_TEXTURES_PATH = "minimaps/";
    public static final int I_HEALTH_BASE = 100;
    public static final int I_HEALTH_ENDURANCE_MINUS = 5;
    public static final int I_HEALTH_ENDURANCE_MULT = 10;
    public static final int I_STAMINA_BASE = 100;
    public static final int I_STAMINA_ENDURANCE_MINUS = 5;
    public static final int I_STAMINA_ENDURANCE_MULT = 5;
    public static final int I_STAMINA_STRENGTH_MINUS = 5;
    public static final int I_STAMINA_STRENGTH_MULT = 5;
    public static final int I_LOAD_BASE = 100;
    public static final int I_LOAD_STRENGTH_MINUS = 5;
    public static final int I_LOAD_STRENGTH_MULT = 10;
    public static final int I_SPEED_BASE = 16;
    public static final int I_SPEED_AGILITY_MINUS = 3;
    public static final int I_SPEED_AGILITY_MULT = 3;
    public static final int I_KNOWLEDGE_BASE = 5;
    public static final int I_INTELLIGENCE_MULT = 3;
    public static final int I_SPEECH_BASE = 5;
    public static final int I_SPEECH_CHARM_MULT = 3;
    public static final int I_MAGICCOST_BASE = 100;
    public static final int I_MAGICCOST_INTELLEGENCE_MINUS = 4;
    public static final int I_MAGICCOST_INTELLEGENCE_MULT = 10;
    public static final int I_ARMS_BASE = 0;
    public static final int I_ARMS_AGILITY_MULT = 2;
    public static final int I_TRADE_BASE = 60;
    public static final int I_TRADE_CHARM_MINUS = 3;
    public static final int I_TRADE_CHARM_MULT = 5;
    public static final int I_INIT_FREE_POINTS = 10;
    public static final int I_INIT_ATTRIBUTE_VALUE = 1;
    public static final int I_MAX_ATTRIBUTE_VALUE = 5;
    public static final String S_NEW_GAME_START_MAP = "trest";
    public static final int I_START_GAME_COORDS_X = 0;
    public static final int I_START_GAME_COORDS_Y = 0;
    public static final String S_IN_GAME_PLAYER_INFO_BACK = "interface/PlayerInfo";
    public static final String S_IN_GAME_PLAYER_INFO_BAR = "interface/PlayerInfoBar";
    public static final String S_IN_GAME_ACTIVATE_BACK = "interface/Activate";
    public static final String S_IN_GAME_ACTIVATE_MULT_BACK = "interface/ActivateMult";
    public static final String S_IN_GAME_ACTIVATE_TEXT_USE = "ПРИМЕНИТЬ";
    public static final String S_IN_GAME_ACTIVATE_TEXT_PICK = "ПОДОБРАТЬ";
    public static final String S_IN_GAME_ACTIVATE_TEXT_OPEN = "ОТКРЫТЬ";
    public static final String S_IN_GAME_ACTIVATE_TEXT_CLOSE = "ЗАКРЫТЬ";
    public static final String S_IN_GAME_ACTIVATE_TEXT_SEARCH = "ОБЫСКАТЬ";
    public static final String S_IN_GAME_ACTIVATE_TEXT_SPEAK = "ГОВОРИТЬ";
    public static final String S_IN_GAME_TIME_CIRCLE = "interface/time";
    public static final String S_IN_GAME_DEATH_SCREEN = "interface/DeathScreen";
    public static final String S_IN_GAME_REST = "interface/rest";
    public static final String S_IN_GAME_FIST = "interface/fist";
    public static final String S_IN_GAME_EMPTY = "interface/empty";
    public static final float F_IN_GAME_VALE_ALPHA = 0.5f;
    public static final float F_IN_GAME_DEATH_ADD_ALPHA = 0.003f;
    public static final float F_IN_GAME_DEATH_ALPHA_SECOND_PHASE = 1;
    public static final float F_IN_GAME_DEATH_ALPHA_MAX = 2;
    public static final float F_REST_CUT_PART = 0.5f;
    public static final int I_REST_IN_RESORT = 0;
    public static final int I_REST_NOT_IN_RESORT = 1;
    public static final String S_INVENTORY_SLOTS = "interface/slots";
    public static final String S_ITEMS_SORT = "interface/ItemSorts";
    public static final String S_SPELLBOOK_SLOTS = "interface/slotsSpells";
    public static final String S_SPELLS_SORT = "interface/SpellsSorts";
    public static final String S_SORT_CHOOSER = "interface/SortsChooser";
    public static final String S_LOAOOUT = "interface/loadOut";
    public static final String S_LOAOOUT_SPELL_CHANGE = "interface/SpellChangeLoadOut";
    public static final String S_SAVE_MERCH_ITEMS_POSTFIX = "items";
    public static final String S_SAVE_MERCH_SPELLS_POSTFIX = "spells";
    public static final String S_DIALOG_DEAL_TEXTURE_SELL = "interface/sell";
    public static final String S_DIALOG_DEAL_TEXTURE_BUY = "interface/buy";
    public static final String S_DIALOG_DEAL_TEXTURE_TEACH = "interface/teach";
    public static final String S_DIALOG_DEAL_TEXTURE_CHANGE = "interface/spellChange";
    public static final String S_CAMP_MENUS_BUTTONS = "interface/CampMenus";
    public static final String S_CAMP_MENUS_BUTTONS_CHOOSER = "interface/CampMenusChooser";
    public static final int I_CAMP_INVENTORY = 0;
    public static final int I_CAMP_SPELLBOOK = 1;
    public static final int I_CAMP_STATS = 2;
    public static final int I_CAMP_JOURNAL = 3;
    public static final int I_CAMP_TRANSIT = 4;
    public static final float F_INVENTORY_LOAD_BAR_NO_RUN = 1;
    public static final float F_INVENTORY_LOAD_LIGHT = 0.4f;
    public static final float F_INVENTORY_LOAD_MEDIUM = 0.8f;
    public static final int I_FIRST_LAST_RESORT_LOC_X = 0;
    public static final int I_FIRST_LAST_RESORT_LOC_Y = 0;


}
