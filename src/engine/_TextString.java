package engine;

public class _TextString {
    public static final String S_GAME_VERSION = "ВЕРСИЯ 0.1";
    public static final String S_INTERFACE_DESCRIBE_LEFT_COST= "Цена";
    public static final String S_INTERFACE_DESCRIBE_LEFT_RADIUS= "Радиус";
    public static final String S_INTERFACE_DESCRIBE_LEFT_DISTANT= "Дистанция";
    public static final String S_INTERFACE_DESCRIBE_LEFT_DIFFICULT= "Сложность";
    public static final String S_INTERFACE_DESCRIBE_LEFT_STAMINA= "Запас сил";
    public static final String S_INTERFACE_DESCRIBE_LEFT_TARGET_TYPE= "Тип цели";
    public static final String S_INTERFACE_DESCRIBE_LEFT_TIME= "Время";
    public static final String S_INTERFACE_DESCRIBE_LEFT_EFFECT= "Эффект";
    public static final String S_INTERFACE_DESCRIBE_LEFT_TYPE= "Тип";
    public static final String S_INTERFACE_DESCRIBE_LEFT_SUBTYPE= "Подтип";
    public static final String S_INTERFACE_DESCRIBE_LEFT_WEIGHT= "Вес";
    public static final String S_INTERFACE_DESCRIBE_LEFT_DAMAGE= "Урон";
    public static final String S_INTERFACE_DESCRIBE_LEFT_DAMAGETYPE= "Тип урон";
    public static final String S_INTERFACE_DESCRIBE_LEFT_INTERVAL= "Интервал";
    public static final String S_INTERFACE_DESCRIBE_LEFT_AMMO= "Заряды";
    public static final String S_INTERFACE_DESCRIBE_LEFT_DEFENSE= "Защита";
    public static final String S_INTERFACE_DESCRIBE_LEFT_STABILITY= "Стабильность";
    public static final String S_INTERFACE_DESCRIBE_LEFT_WHAT_TO_RAISE = "Влияет на";
    public static final String[] SM_SPELL_TYPE_TEXT = {"На себя","На область"};
    public static final String[] SM_SPELL_CLASSES_TEXT = {"Магия стихий","Магия чувств","Риторика","Навык оружия","Навык стрельбы","Навык щита"};
    public static final String[] SM_SPELL_SUBCLASS_ELEMENTAL_TEXT = {"Огонь", "Мороз", "Земля","Кислота"};
    public static final String[] SM_SPELL_SUBCLASS_SENSE_TEXT = {"Подавление", "Прозрение","Восстановление"};
    public static final String[] SM_SPELL_SUBCLASS_SPEECH_TEXT = {"Воодушевление", "Заговоры"};
    public static final String[] SM_SPELL_SUBCLASS_WEAPON_TEXT = {"Клинки", "Топоры", "Дубины", "Копья"};
    public static final String[] SM_SPELL_SUBCLASS_RANGE_TEXT = {"Луки", "Арбалеты"};
    public static final String[] SM_SPELL_SUBCLASS_SHIELD_TEXT = {"Лёгкие", "Средние", "Тяжелые"};
    public static final String[] SM_ITEMS_CLASSES = {"Клинок","Кор. древко","Дл. древко","Дальний бой","Щит","Броня","Реликвия","Используемое","Руна","Трофей"};
    public static final String[] SM_ITEMS_TYPES_SWORD = {"Кинжал", "Прямой меч", "Кривой меч", "Меч для выпадов", "Двуручный меч"};
    public static final String[] SM_ITEMS_TYPES_AXES = {"Топор", "Дубина", "Секира", "Молот"};
    public static final String[] SM_ITEMS_TYPES_SPEARS = {"Копьё", "Алебарда"};
    public static final String[] SM_ITEMS_TYPES_RANGE = {"Лук", "Арбалет", "Праща", "Метательное"};
    public static final String[] SM_ITEMS_TYPES_SHIELD = {"Малый", "Средний", "Большой"};
    public static final String[] SM_ITEMS_TYPES_ARMOR = {"Лёгкая", "Средняя", "Тяжелая"};
    public static final String[] SM_ITEMS_TYPES_AMULET = {"Амулет", "Кольцо"};
    public static final String[] SM_ITEMS_TYPES_USES = {"Зелье", "Устройство", "Бомба", "Порошок"};
    public static final String[] SM_ITEMS_TYPES_MATERIALS = {"Слабая", "Средняя", "Сильная"};
    public static final String[] SM_ITEMS_TYPES_MISC = {"Материал", "Важное", "Трофеи"};
    public static final String[] SM_ITEMS_TYPES_DEFENSES = {"От колющего", "От режущего", "От рубящего", "От дробящего",
            "От огня", "От холода","От электричества","От кислот","От отравления","От ментального","От негативного"};
    public static final String[] SM_ITEMS_TYPES_WHAT_TO_RAISE = {"Мощность", "Запас сил"};
    public static final String S_EVENT_NO_AMMO = "Боеприпас закончился";
    public static final String S_EVENT_BACKSTAB = "Тайный удар";
    public static final String S_EVENT_NO_WEAPON_SWITCH = "Нельзя менять оружие при атаке или блоке";
    public static final String S_EVENT_NO_SHIELD_SWITCH = "Нельзя менять щит при атаке или блоке";
    public static final String S_EVENT_NO_ITEM_AMMO = "У предмета закончились заряды";
    public static final String S_EVENT_NO_STAMINA = "Недостаточно запаса сил";
    public static final String S_EVENT_NO_KNOWLEDGE = "Недостаточно магических познаний";
    public static final String S_EVENT_GAIN_MONEY = "Вы получили деньги: ";
    public static final String S_EVENT_TASK_ADD_QUEST = "Получено задание: ";
    public static final String S_EVENT_TASK_COMPLETE_QUEST = "Выполнено задание: ";
    public static final String S_EVENT_TASK_COMPLETE_TASK = "Выполнена задача: ";
    public static final String S_EVENT_TASK_ADD_TASK = "Получена задача: ";
    public static final String S_EVENT_TOO_COMPLICATED_ACTIVATOR = "Слишком сложный механизм";
    public static final String S_EVENT_TOO_COMPLICATED_CHEST = "Слишком сложный замок";
    public static final String S_EVENT_PICK_UP_MONEY = "Вы подобрали деньги ";
    public static final String S_EVENT_PICK_UP = "Вы подобрали ";
    public static final String S_EVENT_OUTSIDE_RESORT_AREA = "Вы находитесь за пределами зон отдыха";
    public static final String S_EQUIP_DEFAULT_NAME = "Кулаки";
    public static final String[] SM_WEAPON_DAMAGE_TYPES = {"Колющий","Режущий","Рубящий","Дробящий"};
    public static final String S_ESC_MENU_BUTTONS_TEXT_NEW_GAME = "НОВАЯ ИГРА";
    public static final String S_ESC_MENU_BUTTONS_TEXT_LOAD_GAME = "ЗАГРУЗИТЬ";
    public static final String S_ESC_MENU_BUTTONS_TEXT_SETS = "НАСТРОЙКИ";
    public static final String S_ESC_MENU_BUTTONS_TEXT_CONTINUE = "ПРОДОЛЖИТЬ";
    public static final String S_ESC_MENU_BUTTONS_TEXT_QUIT = "ВЫХОД";
    public static final String S_LOAD_HEAD_TEXT = "ПРОДОЛЖИТЬ ИГРУ";
    public static final String[] SM_SETS_VIDMODE_TYPES = {"В окне","Без рамки","Весь экран"};
    public static final String[] SM_SETS_CONTROLS = {"Идти вверх","Идти вниз","Идти налево","Идти направо","Бежать","Красться","Использовать","Смена объекта использования","Атаковать","Блокировать",
            "Сменить оружие","Сменить щит","Сменить предмет","Сменить заклинание 1","Сменить заклинание 2","Использовать предмет","Использовать заклинание 1","Использовать заклинание 2", "Экран лагеря"};
    public static final String S_CONTROLS_TEXT = "УПРАВЛЕНИЕ";
    public static final String S_AUDIO_TEXT = "АУДИО";
    public static final String S_VIDEO_TEXT = "ВИДЕО";
    public static final String S_MUSIC_TEXT = "Музыка";
    public static final String S_SOUND_TEXT = "Звук";
    public static final String S_BRIGHT_TEXT = "Яркость";
    public static final String S_VIDEOMODE_TEXT = "Режим окна";
    public static final String S_NEW_GAME_NAME = "ИМЯ";
    public static final String S_NEW_GAME_ATTRIBUTES = "ХАРАКТЕРИСТИКИ";
    public static final String S_NEW_GAME_ATTRIBUTE_STRENGTH = "Сила";
    public static final String S_NEW_GAME_ATTRIBUTE_ENDURANCE = "Выносливость";
    public static final String S_NEW_GAME_ATTRIBUTE_AGILITY = "Ловкость";
    public static final String S_NEW_GAME_ATTRIBUTE_INTELLIGENCE = "Разум";
    public static final String S_NEW_GAME_ATTRIBUTE_CHARM = "Харизма";
    public static final String S_NEW_GAME_ATTRIBUTE_FREE = "Свободные очки";
    public static final String S_NEW_GAME_PARAMS = "ПАРАМЕТРЫ";
    public static final String S_NEW_GAME_PARAM_HEALTH = "Жизнь";
    public static final String S_NEW_GAME_PARAM_STAMINA = "Запас сил";
    public static final String S_NEW_GAME_PARAM_LOAD = "Нагрузка";
    public static final String S_NEW_GAME_PARAM_SPEED = "Скорость атак";
    public static final String S_NEW_GAME_PARAM_KNOWLEDGE = "Магические знания";
    public static final String S_NEW_GAME_PARAM_SPEECH = "Риторика";
    public static final String S_NEW_GAME_PARAM_SPELLCOST = "Цена заклинаний";
    public static final String S_NEW_GAME_PARAM_ARMS = "Ловкость рук";
    public static final String S_NEW_GAME_PARAM_TRADE = "Торговля";
    public static final String S_NEW_GAME_ERROR_FREE_POINTS = "РАСПРЕДЕЛИТЕ ВСЕ ОЧКИ";
    public static final String S_NEW_GAME_ERROR_NO_NAME = "ВВЕДИТЕ ИМЯ ПЕРСОНАЖА";
    public static final String S_IN_GAME_DEATH_SCREEN_TEXT = "ГЛАВНЫЙ ПЕРСОНАЖ ПОГИБ! НАЖМИТЕ ПРОБЕЛ ДЛЯ ПРОДОЛЖЕНИЯ.";
    public static final String S_BUY_HEAD = "ПОКУПКА";
    public static final String S_SELL_HEAD = "ПРОДАЖА";
    public static final String S_SPELL_CHANGE_HEAD = "ИЗМЕНЕНИЕ ЗАКЛИНАНИЙ";
    public static final String S_TEACH_HEAD = "ОБУЧЕНИЕ";
    public static final String S_GOLD = "Золото: ";
    public static final String[] SM_KNOWLEDGE_TEXT = {"Познания в магии","","Магия стихий",
            "Магия чувств","Риторика"};
    public static final String[] SM_CAMP_HEADS = {"ИНВЕНТАРЬ","СПОСОБНОСТИ","ПАРАМЕТРЫ","ЖУРНАЛ","ПЕРЕМЕЩЕНИЕ"};
    public static final String S_HELP_FOR_SPELLBOOK_TEXT = "ЛКМ/ПКМ";
    public static final String[] SM_CAMP_STATS_MAIN = {"Основные параметры","","Жизнь", "Броня", "Запас сил", "Нагрузка","", "Стойкость",
            "Скорость","Цена заклинаний","Ловкость рук","Торговля","",""};
    public static final String[] SM_CAMP_STATS_DEFENSES = {"Защита","","От колющего", "От режущего", "От рубящего", "От дробящего","",
            "От огня", "От холода","От электричества","От кислот","От отравления","От ментального","От негативного"};
    public static final String[] SM_CAMP_STATS_OFFENSES = {"Атака","","Оружие 1","Тип урона","Заряды", "Оружие 2","Тип урона","Заряды", "", "Познания в магии","","Магия стихий",
            "Магия чувств","Риторика"};
}